package model;

import com.google.gson.JsonElement;

import java.util.function.Supplier;

public class Event {

    private final Supplier<Artist> artist;
    private Artist artistVal;
    private final String eventDate;
    private final String tour;
    private final Iterable<Track> tracks;
    private final String setId;
    private final JsonElement sets;

    public Event(Supplier<Artist> artist, String eventDate, String tour, Iterable<Track> tracks, String setId, JsonElement sets) {
        this.artist = artist;
        this.eventDate = eventDate;
        this.tour = tour;
        this.tracks = tracks;
        this.setId = setId;
        this.sets = sets;
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

    public Iterable<Track> getTracks(){
        return tracks;
    }

    public String getSetlistId(){
        return setId;
    }
}