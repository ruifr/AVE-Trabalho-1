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
            CompletableFuture<ContainerDto<VenueDto>> vcd = setlist.getVenueContainer(query, page);
            int idxDto[] = new int[] { 0 };
            Spliterator<Venue> str;

            @Override
            public boolean tryAdvance(Consumer<? super Venue> action) {
                if(str == null) {
                    VenueDto[] tmp = setlist.getVenueContainer(query, page++).thenApply(ContainerDto::getModel).join();
                    idxDto[0] = 0;
                    str = Stream.generate(() -> tmp[idxDto[0]++]).limit(tmp.length).map(VibeService.this::dtoToVenue).spliterator();
                    if(str == null) return false;
                }
                if(str.tryAdvance(item -> { l.add(item); action.accept(item);})) return true;
                if(!vcd.join().isValidPage(page)) return false;
                str = null;
                return tryAdvance(action);
            }
        }, false);
    }

    protected Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()), venue.getId());
    }

    public Supplier<Stream<Event>> getEvents(String query) {
        LinkedList<Event> l = new LinkedList<>();
        return () -> stream(new Spliterators.AbstractSpliterator<Event>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            CompletableFuture<ContainerDto<EventDto>> ecd = setlist.getEventContainer(query, page);
            int idxDto[] = new int[] { 0 };
            Spliterator<Event> str;

            @Override
            public boolean tryAdvance(Consumer<? super Event> action) {
                if(str == null) {
                    EventDto[] tmp = setlist.getEventContainer(query, page++).thenApply(ContainerDto::getModel).join();
                    idxDto[0] = 0;
                    str = Stream.generate(() -> tmp[idxDto[0]++]).limit(tmp.length).map(VibeService.this::dtoToEvent).spliterator();
                    if(str == null) return false;
                }
                if(str.tryAdvance(item -> { l.add(item); action.accept(item);})) return true;
                if(!ecd.join().isValidPage(page)) return false;
                str = null;
                return tryAdvance(action);
            }
        }, false);
    }

    public CompletableFuture<Event> getEvent(String id) {
        return setlist.getEvent(id).thenApply(this::dtoToEvent);
    }

    protected Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        CompletableFuture<Track>[] tracks = new CompletableFuture[tracksNames.length];
        int i[] = new int[] { 0, 0 };
        Stream.generate(() -> tracksNames[i[0]++])
                .limit(tracksNames.length)
                .forEach(name -> tracks[i[1]++] = getTrack(event.getArtistName(), name));
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