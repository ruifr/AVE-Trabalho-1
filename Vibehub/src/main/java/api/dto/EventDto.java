package api.dto;

import api.dto.deserializer.EventDtoDeserializer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonAdapter(EventDtoDeserializer.class)
public class EventDto {
    private final String setid;
    private final String mbid;
    private final String artistName;
    private final String eventDate;
    private final String tour;
    private String[] tracks;
    private final JsonElement sets;

    public EventDto(String setid, String mbid, String artistName, String eventDate, String tour, JsonElement sets) {
        this.setid = setid;
        this.mbid = mbid;
        this.artistName = artistName;
        this.eventDate = eventDate;
        this.tour = tour;
        this.sets = sets;
    }

    public String getMbid() {
        return mbid;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getTour() {
        return tour;
    }

    public String[] getTracksNames(){
        if(tracks != null) return tracks;
        if(sets.isJsonPrimitive())
            tracks = new String[0];
        else {
            List<String> res = new ArrayList<>();
            JsonElement set = sets.getAsJsonObject().get("set");
            if(set.isJsonObject()) insertSongsInto(set, res);
            else {
                set
                        .getAsJsonArray()
                        .forEach(elem -> insertSongsInto(elem, res));
            }
            tracks = res.toArray(new String[res.size()]);
        }
        return tracks;
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

    public String getSetid() {
        return setid;
    }

    public String getArtistName() {
        return artistName;
    }

    @Override
    public String toString() {
        return "EventDto{" +
                "setid='" + setid + '\'' +
                ", mbid='" + mbid + '\'' +
                ", artistName='" + artistName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", tour='" + tour + '\'' +
                ", tracks=" + Arrays.toString(getTracksNames()) +
                '}';
    }
}