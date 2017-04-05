import api.LastfmApi;
import api.SetlistApi;
import model.*;

public class VibeService {

    public VibeService(SetlistApi slApi, LastfmApi lstApi) {

    }

    public VibeService(IRequest req) {

    }

    public Iterable<Venue> searchVenues(String a){
        throw new UnsupportedOperationException();
    }


    public Iterable<Event> getEvents(String s){
        throw new UnsupportedOperationException();
    }

    public Artist getArtist(String s){
        throw new UnsupportedOperationException();
    }

    public Track getTrack(String artist, String trackName){
        throw new UnsupportedOperationException();
    }

}
