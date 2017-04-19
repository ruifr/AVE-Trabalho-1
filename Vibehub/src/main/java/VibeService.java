import api.LastfmApi;
import api.SetlistApi;
import api.dto.EventDto;
import api.dto.VenueDto;
import model.*;
import util.IRequest;

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
        setlist.getVenues(query);
        return null;
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()));
    }


    public Iterable<Event> getEvents(String query){
        setlist.getEvents(query);
        throw new UnsupportedOperationException();
    }

    private Event dtoToEvent(EventDto event) {
        return new Venue(event., getEvents(venue.getId()));
    }

    public Artist getArtist(String s){
        throw new UnsupportedOperationException();
    }

    public Track getTrack(String artist, String trackName){
        throw new UnsupportedOperationException();
    }
}
