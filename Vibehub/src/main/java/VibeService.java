import api.LastfmApi;
import api.SetlistApi;
import api.dto.ArtistDto;
import api.dto.EventDto;
import api.dto.VenueDto;
import model.*;
import model.Venue;
import util.IRequest;

import java.util.Arrays;

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
        return map(Arrays.asList(setlist.getVenues(query)), this::dtoToVenue);
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()));
    }

    public Iterable<Event> getEvents(String query) {
        return map(Arrays.asList(setlist.getEvents(query)), this::dtoToEvent);
    }

    private Event dtoToEvent(EventDto event) {
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), null, event.getSetid(), event.getSets());
    }

    public Artist getArtist(String s){
        return DtoToArtist(lastfm.getArtistInfo(s));
    }

    private Artist DtoToArtist(ArtistDto artist) {
        return null;//new Artist();
    }

    public Track getTrack(String artist, String trackName){
        throw new UnsupportedOperationException();
    }
}