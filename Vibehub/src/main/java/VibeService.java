import api.LastfmApi;
import api.SetlistApi;
import api.dto.*;
import model.*;
import model.Venue;
import util.IRequest;

import javax.swing.text.html.HTMLDocument;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static util.queries.LazyQueries.map;

public class VibeService {

    private final SetlistApi setlist;
    private final LastfmApi lastfm;

    public VibeService(SetlistApi slApi, LastfmApi lstApi) {
        this.setlist = slApi;
        this.lastfm = lstApi;
    }

    public VibeService(IRequest req) {
        this(new SetlistApi(req), new LastfmApi(req));
    }

    public Iterable<Venue> searchVenues(String query){
        Function<VenueDto, Venue> func = this::dtoToVenue;
        return () -> new Iterator<Venue>() {
            int page = 1;
            VenueContainerDto vcd = setlist.getVenueContainer(query, page++);
            Iterator<Venue> iter = map(Arrays.asList(vcd.getVenues()), func).iterator();
            @Override
            public boolean hasNext() {
                if (!iter.hasNext() && vcd.isValidPage(page))
                    iter = map(Arrays.asList(setlist.getVenues(query, page++)), func).iterator();
                return iter.hasNext();
            }
            @Override
            public Venue next() {
                if(hasNext())
                    return iter.next();
                throw new NoSuchElementException();
            }
        };
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()));
    }

    public Iterable<Event> getEvents(String query) {
        Function<EventDto, Event> func = this::dtoToEvent;
        return () -> new Iterator<Event>() {
            int page = 1;
            EventContainerDto ecd = setlist.getEventContainer(query, page++);
            Iterator<Event> iter = map(Arrays.asList(ecd.getEvents()), func).iterator();
            @Override
            public boolean hasNext() {
                if (!iter.hasNext() && ecd.isValidPage(page))
                    iter = map(Arrays.asList(setlist.getEvents(query, page++)), func).iterator();
                return iter.hasNext();
            }

            @Override
            public Event next() {
                if(hasNext())
                    return iter.next();
                throw new NoSuchElementException();
            }
        };
    }

    private Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        Iterable<Track> tracks = map(Arrays.asList(tracksNames), name -> getTrack(event.getArtistName(), name));
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracksNames, tracks, event.getSetid());
    }

    public Artist getArtist(String s){
        return DtoToArtist(lastfm.getArtistInfo(s));
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