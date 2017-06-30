package model;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Event {
    private final CompletableFuture<Artist> artist;
    private Artist artistVal;
    private final String eventDate;
    private final String tour;
    private final String[] tracksNames;
    private final CompletableFuture<Track>[] tracks;
    private final String setId;

    public Event(CompletableFuture<Artist> artist, String eventDate, String tour, String[] tracksNames, CompletableFuture<Track>[] tracks, String setId) {
        this.artist = artist;
        this.eventDate = eventDate;
        this.tour = tour;
        this.tracks = tracks;
        this.setId = setId;
        this.tracksNames = tracksNames;
    }

    public Artist getArtist() {
        return artistVal == null ? artistVal=artist.join() : artistVal;
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

    public CompletableFuture<Track>[] getTracks(){
        return tracks;
    }

    public String getSetlistId(){
        return setId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{artist=");
        sb.append(getArtist());
        sb.append(", eventDate='");
        sb.append(eventDate);
        sb.append("', tour='");
        sb.append(tour);
        sb.append("', tracks=[");
        Arrays.asList(tracks).forEach(sb::append);
        sb.append("], setId='");
        sb.append(setId);
        sb.append("'}");
        return sb.toString();
    }
}