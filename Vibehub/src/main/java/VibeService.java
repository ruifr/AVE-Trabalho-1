import api.LastfmApi;
import api.SetlistApi;
import api.dto.*;
import model.Artist;
import model.Event;
import model.Track;
import model.Venue;
import util.IRequest;

import java.util.LinkedList;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public class VibeService {
    protected final SetlistApi setlist;
    protected final LastfmApi lastfm;

    public VibeService(SetlistApi slApi, LastfmApi lstApi) {
        this.setlist = slApi;
        this.lastfm = lstApi;
    }

    public VibeService(IRequest req) {
        this(new SetlistApi(req), new LastfmApi(req));
    }

    public Supplier<Stream<Venue>> searchVenues(String query){
        LinkedList<Venue> l = new LinkedList<>();
        return () -> stream(new Spliterators.AbstractSpliterator<Venue>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            CompletableFuture<VenueDto[]> dtos = setlist.getVenueContainer(query, page++).thenApply(VenueContainerDto::getModel);
            int idxDto[] = new int[] { 0 };
            Spliterator<Venue> str;

            @Override
            public boolean tryAdvance(Consumer<? super Venue> action) {
                if(str == null) {
                    VenueDto[] tmp = dtos.join();
                    str = Stream.generate(() -> tmp[idxDto[0]++]).limit(tmp.length).map(VibeService.this::dtoToVenue).spliterator();
                }
                if(str.tryAdvance(item -> { l.add(item); action.accept(item);}))
                    return true;

                str = null;
                return tryAdvance(action);
            }
        }, false);

    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()));
    }

    public Supplier<Stream<Event>> getEvents(String query) {
        LinkedList<Event> l = new LinkedList<>();
        return () -> stream(new Spliterators.AbstractSpliterator<Event>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            CompletableFuture<EventDto[]> dtos = setlist.getEventContainer(query, page++).thenApply(EventContainerDto::getModel);
            int idxDto[] = new int[] { 0 };
            Spliterator<Event> str;

            @Override
            public boolean tryAdvance(Consumer<? super Event> action) {
                if(str == null) {
                    EventDto[] tmp = dtos.join();
                    str = Stream.generate(() -> tmp[idxDto[0]++]).limit(tmp.length).map(VibeService.this::dtoToEvent).spliterator();
                }
                if(str.tryAdvance(item -> { l.add(item); action.accept(item);}))
                    return true;

                str = null;
                return tryAdvance(action);
            }
        }, false);
    }

    public CompletableFuture<Event> getEvent(String id) {
        return setlist.getEvent(id).thenApply(this::dtoToEvent);
    }

    private Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        int i[] = new int[] { 0 };
        Stream<String> res = Stream.generate(() -> tracksNames[i[0]++]).limit(tracksNames.length);
        CompletableFuture<Track>[] tracks = (CompletableFuture<Track>[]) res.map(name -> getTrack(event.getArtistName(), name)).toArray();
        return new Event(getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracksNames, tracks, event.getSetid());
    }


    public CompletableFuture<Artist> getArtist(String query){
        return lastfm.getArtistInfo(query).thenApply(this::dtoToArtist);
    }

    private Artist dtoToArtist(ArtistDto artist) {
        return artist == null ? null : new Artist(artist.getName(), artist.getBio(), artist.getUrl(), artist.getImagesUri(), artist.getMbid());
    }

    public CompletableFuture<Track> getTrack(String artist, String trackName){
        return lastfm.getTrackInfo(artist, trackName).thenApply(this::dtoToTrack);
    }

    private Track dtoToTrack(TrackDto track) {
        return new Track(track.getName(), track.getArtistName(), track.getAlbumName(), track.getTrackUrl(), track.getImagesUrl(), track.getAlbumUrl(), track.getDuration());
    }
}