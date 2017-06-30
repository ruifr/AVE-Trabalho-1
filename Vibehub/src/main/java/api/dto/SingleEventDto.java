package api.dto;

import api.dto.deserializer.SingleEventDtoDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(SingleEventDtoDeserializer.class)
public class SingleEventDto extends EventDto {
    public SingleEventDto(String setid, String mbid, String artistName, String eventDate, String tour, JsonElement sets) {
        super(setid, mbid, artistName, eventDate, tour, sets);
    }
}
