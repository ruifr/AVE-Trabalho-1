import api.LastfmApi;
import api.SetlistApi;
import model.Artist;
import model.Event;
import model.Track;
import model.Venue;
import util.CacheStream;
import util.IRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class VibeServiceCache extends VibeService {
    public VibeServiceCache(SetlistApi slApi, LastfmApi lstApi) {
        super(slApi, lstApi);
        vCache = CacheStream.from(setlist::getVenueContainer, this::dtoToVenue);
        eCache = CacheStream.from(setlist::getEventContainer, this::dtoToEvent);
    }

    public VibeServiceCache(IRequest req) {
        this(new SetlistApi(req), new LastfmApi(req));
    }

    private CacheStream vCache;
    @Override
    public Supplier<Stream<Venue>> searchVenues(String query) {
        return () -> vCache.toStream(query);
    }

    private CacheStream eCache;
    @Override
    public Supplier<Stream<Event>> getEvents(String query) {
        return () -> eCache.toStream(query);
    }

    private Map<String, CompletableFuture<Artist>> aCache = new HashMap<>();
    @Override
    public CompletableFuture<Artist> getArtist(String query) {
        CompletableFuture<Artist> a = aCache.get(query);
        if(a == null) a = super.getArtist(query);
        else aCache.put(query, a);
        return a;
    }

    private Map<String, CompletableFuture<Track>> tCache = new HashMap<>();
    @Override
    public CompletableFuture<Track> getTrack(String artist, String trackName) {
        CompletableFuture<Track> t = tCache.get(artist+trackName);
        if(t == null) t = super.getTrack(artist, trackName);
        else tCache.put(artist+trackName,t);
        return t;
    }

    private Map<String, CompletableFuture<Event>> sCache = new HashMap<>();
    @Override
    public CompletableFuture<Event> getEvent(String id) {
        CompletableFuture<Event> e = sCache.get(id);
        if(e == null) {
            CompletableFuture<Event> c = new CompletableFuture<>();
            c.complete(getEvents(id).get().filter(ev -> ev.getSetlistId().equals(id)).findFirst().get());
            e = c;
        }
        else sCache.put(id, e);
        return e;
    }
}