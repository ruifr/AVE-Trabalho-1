import api.LastfmApi;
import api.SetlistApi;
import api.dto.*;
import model.*;
import model.Venue;
import util.Convert;
import util.IRequest;

import java.util.HashMap;
import java.util.LinkedList;
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

    private HashMap<String, LinkedList<Venue>> vMap = new HashMap<>();
    public Stream<Venue> searchVenues(String query){
        final LinkedList<Venue> l = vMap.get(query);
        final Function<VenueDto, Venue> dtoToVenue = this::dtoToVenue;
        if(l != null) {
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Venue>(Long.MAX_VALUE, Spliterator.ORDERED) {
                private int idx = 0;
                private boolean flag = false;
                private Stream<Venue> s;
                @Override
                public boolean tryAdvance(Consumer<? super Venue> action) {
                    if(flag) {
                        Venue v = s.findFirst().get();
                        s = s.skip(1);
                        return v != null;
                    }

                    if(idx >= l.size()) {
                        s = Convert.toStream(setlist::getVenueContainer, dtoToVenue, query, l::add);
                        s.skip(idx);
                        flag = true;
                        return tryAdvance(action);
                    }

                    action.accept(l.get(idx++));
                    return true;
                }
            }, false);
        }
        LinkedList<Venue> l1 = new LinkedList<>();
        vMap.put(query,l1);
        return Convert.toStream(setlist::getVenueContainer, this::dtoToVenue, query, l1::add);
    }

    private Venue dtoToVenue(VenueDto venue) {
        return new Venue(venue.getName(), () -> getEvents(venue.getId()));
    }

    private HashMap<String, LinkedList<Event>> eMap = new HashMap<>();
    public Stream<Event> getEvents(String query) {
        final LinkedList<Event> l = eMap.get(query);
        final Function<EventDto, Event> dtoToEvent = this::dtoToEvent;
        if(l != null) {
            return StreamSupport.stream(new Spliterators.AbstractSpliterator<Event>(Long.MAX_VALUE, Spliterator.ORDERED) {
                private int idx = 0;
                private boolean flag = false;
                private Stream<Event> s;
                @Override
                public boolean tryAdvance(Consumer<? super Event> action) {
                    if(flag) {
                        s.limit(1).forEach(action);
                        s = s.skip(1);
                    }

                    if(idx >= l.size()) {
                        s = Convert.toStream(setlist::getEventContainer, dtoToEvent, query, l::add);
                        flag = true;
                        return tryAdvance(action);
                    }

                    action.accept(l.get(idx++));
                    return true;
                }
            },false);
        }

        LinkedList<Event> l1 = new LinkedList<>();
        eMap.put(query,l1);
        return Convert.toStream(setlist::getEventContainer, this::dtoToEvent, query, l1::add);
    }

    private Event dtoToEvent(EventDto event) {
        String[] tracksNames = event.getTracksNames();
        int i[] = new int[] { 0 };
        Stream<String> res = Stream.generate(() -> tracksNames[i[0]++]).limit(tracksNames.length);
        Stream<Track> tracks = res.map(name -> getTrack(event.getArtistName(), name));
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracksNames, () -> tracks, event.getSetid());
    }

    private HashMap<String, Artist> aMap = new HashMap<>();
    public Artist getArtist(String query){
        Artist a = aMap.get(query);
        if(a == null) a = DtoToArtist(lastfm.getArtistInfo(query));
        else aMap.put(query, a);
        return a;
    }

    private Artist DtoToArtist(ArtistDto artist) {
        return artist == null ? null : new Artist(artist.getName(), artist.getBio(), artist.getUrl(), artist.getImagesUri(), artist.getMbid());
    }

    private HashMap<String, Track> tMap = new HashMap<>();
    public Track getTrack(String artist, String trackName){
        Track t = tMap.get(artist+trackName);
        if(t == null) dtoToTrack(lastfm.getTrackInfo(artist, trackName));
        else tMap.put(artist+trackName,t);
        return t;
    }

    private Track dtoToTrack(TrackDto track) {
        return new Track(track.getName(), track.getArtistName(), track.getAlbumName(), track.getTrackUrl(), track.getImagesUrl(), track.getAlbumUrl(), track.getDuration());
    }
}