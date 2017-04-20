package model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
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

    public String[] getTracksNames(){
        if(sets.isJsonPrimitive())
            return new String[0];
        else {
            List<String> res = new ArrayList<>();
            JsonElement set = sets.getAsJsonObject().get("set");
            if(set.isJsonObject()) insertSongsInto(set, res);
            else {
                set
                        .getAsJsonArray()
                        .forEach(elem -> insertSongsInto(elem, res));
            }
            return res.toArray(new String[res.size()]);
        }
    }

    private static void insertSongsInto(JsonElement elem, List<String> res) {
        JsonElement song = elem.getAsJsonObject().get("song");
        if(song.isJsonObject()) {
            res.add(songToString(song));
        } else {
            JsonArray songs = song.getAsJsonArray();
            songs.forEach(item -> songToString(item));
        }
    }

    private static String songToString(JsonElement song) {
        return song.getAsJsonObject().get("@name").getAsString();
    }

    public Iterable<Track> getTracks(){
        return tracks;
    }

    public String getSetlistId(){
        return setId;
    }
}