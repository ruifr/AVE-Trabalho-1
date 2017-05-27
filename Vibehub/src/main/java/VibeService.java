import api.LastfmApi;
import api.SetlistApi;
import api.dto.*;
import model.*;
import model.Venue;
import util.CacheStream;
import util.IRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class VibeService {

    protected final SetlistApi setlist;
    protected final LastfmApi lastfm;

    public VibeService(SetlistApi slApi, LastfmApi lstApi) {
        this.setlist = slApi;
        this.lastfm = lstApi;
        vCache = CacheStream.from(setlist::getVenueContainer, this::dtoToVenue);
        eCache = CacheStream.from(setlist::getEventContainer, this::dtoToEvent);
    }

    public VibeService(IRequest req) {
        this(new SetlistApi(req), new LastfmApi(req));
    }

    private CacheStream vCache;
    public Supplier<Stream<Venue>> searchVenues(String query){
        return () -> vCache.toStream(query);
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), getEvents(venue.getId()));
    }

    private CacheStream eCache;
    public Supplier<Stream<Event>> getEvents(String query) {
        return () -> eCache.toStream(query);
    }

    private Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        int i[] = new int[] { 0 };
        Stream<String> res = Stream.generate(() -> tracksNames[i[0]++]).limit(tracksNames.length);
        Stream<Track> tracks = res.map(name -> getTrack(event.getArtistName(), name));
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracksNames, () -> tracks, event.getSetid());
    }

    private Map<String, Artist> aCache = new HashMap<>();
    public Artist getArtist(String query){
        Artist a = aCache.get(query);
        if(a == null) a = dtoToArtist(lastfm.getArtistInfo(query));
        else aCache.put(query, a);
        return a;
    }

    private Artist dtoToArtist(ArtistDto artist) {
        return artist == null ? null : new Artist(artist.getName(), artist.getBio(), artist.getUrl(), artist.getImagesUri(), artist.getMbid());
    }

    private Map<String, Track> tCache = new HashMap<>();
    public Track getTrack(String artist, String trackName){
        Track t = tCache.get(artist+trackName);
        if(t == null) dtoToTrack(lastfm.getTrackInfo(artist, trackName));
        else tCache.put(artist+trackName,t);
        return t;
    }

    private Track dtoToTrack(TrackDto track) {
        return new Track(track.getName(), track.getArtistName(), track.getAlbumName(), track.getTrackUrl(), track.getImagesUrl(), track.getAlbumUrl(), track.getDuration());
    }
}