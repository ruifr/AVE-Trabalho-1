package api.dto;

import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class EventDto {
    @SerializedName("@mbid")
    private String mbid;

    @SerializedName("@eventDate")
    private String eventDate;

    @SerializedName("@tour")
    private String tour;

    @SerializedName("@name")
    private String trackName;

    public static class EventDtoDeserializer implements JsonDeserializer<EventDto[]> {
        @Override
        public EventDto[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement ja = json.getAsJsonObject().has().get("setlists").getAsJsonObject().get("setlist");
            if(ja.isJsonObject()) return new EventDto[] { context.deserialize(ja.getAsJsonObject(), EventDto.class) };
            EventDto[] ret = new EventDto[ja.getAsJsonArray().size()];
            int i = 0;
            for (JsonElement je: ja.getAsJsonArray()) {
                ret[i++] = context.deserialize(je, EventDto.class);
            }
            return ret;
        }
    }
}