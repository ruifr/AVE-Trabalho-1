package model;

import java.util.function.Supplier;

public class Event {
    private final Supplier<Artist> artist;
    private Artist artistVal;
    private final String eventDate;
    private final String tour;
    private final String[] tracksNames;
    private final Iterable<Track> tracks;
    private final String setId;

    public Event(Supplier<Artist> artist, String eventDate, String tour, String[] tracksNames, Iterable<Track> tracks, String setId) {
        this.artist = artist;
        this.eventDate = eventDate;
        this.tour = tour;
        this.tracks = tracks;
        this.setId = setId;
        this.tracksNames = tracksNames;
    }

    public Artist getArtist() {
        return artistVal == null ? artistVal=artist.get() : artistVal;
    }

    public String getArtistName() {
        return getArtist().getName();
    }

    public String getEventDate(){
        return eventDate;
    }

    public String getTour(){
        return tour;
    }

    public String[] getTracksNames() {
        return tracksNames;
    }

    public Iterable<Track> getTracks(){
        return tracks;
    }

    public String getSetlistId(){
        return setId;
    }
}