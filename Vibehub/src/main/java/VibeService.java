import api.LastfmApi;
import api.SetlistApi;
import api.dto.ArtistDto;
import api.dto.EventDto;
import api.dto.TrackDto;
import api.dto.VenueDto;
import model.*;
import model.Venue;
import util.IRequest;
import util.queries.LazyQueries;

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
        Iterable<Track> tracks = map(Arrays.asList(event.getTracksNames()), name -> getTrack(event.getArtistName(), name));
        return new Event(() -> getArtist(event.getMbid()), event.getEventDate(), event.getTour(), tracks, event.getSetid(), event.getSets());
    }

    public Artist getArtist(String s){
        return DtoToArtist(lastfm.getArtistInfo(s));
    }

    private Artist DtoToArtist(ArtistDto artist) {
        return new Artist(artist.getName(), artist.getBio(), artist.getUrl(), artist.getStr(), artist.getMbid());
    }

    public Track getTrack(String artist, String trackName){
        return dtoToTrack(lastfm.getTrackInfo(artist, trackName));
    }

    private Track dtoToTrack(TrackDto track) {
        return new Track(track.getName(), track.getArtistName(), track.getAlbumName(), track.getTrackUrl(), track.getImagesUrl(), track.getAlbumUrl(), track.getDuration());
    }
}