import api.LastfmApi;
import api.SetlistApi;
import api.dto.*;
import model.*;
import model.Venue;
import util.IRequest;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public Stream<Venue> searchVenues(String query){
        Function<VenueDto, Venue> func = this::dtoToVenue;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Venue>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            VenueContainerDto vcd = null;
            VenueDto dtos[];
            int idxDto[] = new int[] { 0 };
            Spliterator<Venue> str;

            @Override
            public boolean tryAdvance(Consumer<? super Venue> action) {
                if(vcd == null) {
                    vcd = setlist.getVenueContainer(query, page++);
                    idxDto[0] = 0;
                    VenueDto dtos[] = vcd.getVenues();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).map(func).spliterator();
                    return tryAdvance(action);
                }
                if(str.tryAdvance(action::accept))
                    return true;
                if(vcd.isValidPage(page)) {
                    idxDto[0] = 0;
                    VenueDto dtos[] = vcd.getVenues();
                    str = Stream.generate(() -> dtos[idxDto[0]++]).map(func).spliterator();
                    return tryAdvance(action);
                }
                return false;
            }
        }, false);
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), () -> getEvents(venue.getId()));
    }

    public Stream<Event> getEvents(String query) {
        Function<EventDto, Event> func = this::dtoToEvent;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Event>(Long.MAX_VALUE, Spliterator.ORDERED) {
            int page = 1;
            EventContainerDto ecd = setlist.getEventContainer(query, page++);
            EventDto dtos[] = ecd.getEvents();
            int idxDto[] = new int[] { 0 };
            Spliterator<Event> str = Stream.generate(() -> dtos[idxDto[0]++]).map(func).spliterator();

            @Override
            public boolean tryAdvance(Consumer<? super Event> action) {
                if(str.tryAdvance(action::accept))
                    return true;
                if(ecd.isValidPage(page)) {
                    dtos = ecd.getEvents();
                    idxDto[0] = 0;
                    str = Stream.generate(() -> dtos[idxDto[0]++]).map(func).spliterator();
                    return tryAdvance(action);
                }
                return false;
            }
        }, false);
    }

    private Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        int i[] = new int[] { 0 };
        Stream<String> res = Stream.generate(() -> tracksNames[i[0]++]);
        Stream<Track> tracks = res.map(name -> getTrack(event.getArtistName(), name));
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracksNames, () -> tracks, event.getSetid());
    }

    public Artist getArtist(String query){
        return DtoToArtist(lastfm.getArtistInfo(query));
    }

    private Artist DtoToArtist(ArtistDto artist) {
        return artist == null ? null : new Artist(artist.getName(), artist.getBio(), artist.getUrl(), artist.getImagesUri(), artist.getMbid());
    }

    public Track getTrack(String artist, String trackName){
        return dtoToTrack(lastfm.getTrackInfo(artist, trackName));
    }

    private Track dtoToTrack(TrackDto track) {
        return new Track(track.getName(), track.getArtistName(), track.getAlbumName(), track.getTrackUrl(), track.getImagesUrl(), track.getAlbumUrl(), track.getDuration());
    }
}