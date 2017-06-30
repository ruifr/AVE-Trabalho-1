package api.dto.deserializer;

import api.dto.SingleEventDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SingleEventDtoDeserializer implements JsonDeserializer<SingleEventDto> {
    @Override
    public SingleEventDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("setlist").getAsJsonObject();
        return new SingleEventDto(
                jo.get("@id").getAsString(),
                jo.get("artist").getAsJsonObject().get("@mbid").getAsString(),
                jo.get("artist").getAsJsonObject().get("@name").getAsString(),
                jo.get("@eventDate").getAsString(),
                jo.has("@tour") ? jo.get("@tour").getAsString() : "Resource Not Found",
                jo.has("sets") ? jo.get("sets") : null
        );
    }
}