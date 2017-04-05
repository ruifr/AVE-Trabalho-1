package model;

import java.util.Iterator;
import java.util.function.Supplier;

public class Event {

    private final Supplier<Artist> artist;
    private final String eventDate;
    private final String tour;
    private final Iterable<Track> tracks;

    public Event(Supplier<Artist> artist, String eventDate, String tour, Iterable<Track> tracks) {
        this.artist = artist;
        this.eventDate = eventDate;
        this.tour = tour;
        this.tracks = tracks;
    }

    public Artist getArtist() {
        return artist.get();
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

    public String[] getTracksNames(){
        return null;
    }

    public Iterable<Track> getTracks(){
        return tracks;
    }

    public String getSetlistId(){
        return "";
    }

    @Override
    public String toString() {
        return "TODO tostring event";
    }
}
