public class VibeServiceCache /*extends VibeService*/ {
/*
    public VibeServiceCache(SetlistApi slApi, LastfmApi lstApi) {
        super(slApi, lstApi);
        vCache = CacheStream.from(setlist::getVenueContainer, this::dtoToVenue);
        eCache = CacheStream.from(setlist::getEventContainer, this::dtoToEvent);
    }

    public VibeServiceCache(IRequest req) {
        super(req);
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

    private Map<String, Artist> aCache = new HashMap<>();
    @Override
    public Artist getArtist(String query) {
        Artist a = aCache.get(query);
        if(a == null) a = super.getArtist(query);
        else aCache.put(query, a);
        return a;
    }

    private Map<String, Track> tCache = new HashMap<>();
    @Override
    public Track getTrack(String artist, String trackName) {
        Track t = tCache.get(artist+trackName);
        if(t == null) super.getTrack(artist, trackName);
        else tCache.put(artist+trackName,t);
        return t;
    }
    */
}