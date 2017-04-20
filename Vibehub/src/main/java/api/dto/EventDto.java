package api.dto;

import api.dto.deserializer.EventDtoDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import model.Track;

@JsonAdapter(EventDtoDeserializer.class)
public class EventDto {
    private String setid;
    private String mbid;
    private String eventDate;
    private String tour;
    private final JsonElement sets;

    public EventDto(String setid, String mbid, String eventDate, String tour, JsonElement sets) {
        this.setid = setid;
        this.mbid = mbid;
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

    public Track[] getTracks() {
        return null;
    }

    public String getSetid() {
        return setid;
    }
    public JsonElement getSets() {
        return sets;
    }
}