package api.dto.deserializer;

import api.dto.EventDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EventDtoDeserializer implements JsonDeserializer<EventDto> {

    @Override
    public EventDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        return new EventDto(
                jo.get("@id").getAsString(),
                jo.get("artist").getAsJsonObject().get("@mbid").getAsString(),
                jo.get("artist").getAsJsonObject().get("@name").getAsString(),
                jo.get("@eventDate").getAsString(),
                jo.has("@tour") ? jo.get("@tour").getAsString() : "404 not found",
                jo.has("sets") ? jo.get("sets") : null
        );
    }
}