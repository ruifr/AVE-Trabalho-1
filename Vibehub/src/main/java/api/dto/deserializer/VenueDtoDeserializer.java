package api.dto.deserializer;

import api.dto.VenueDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class VenueDtoDeserializer implements JsonDeserializer<VenueDto> {
    @Override
    public VenueDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        return new VenueDto(jo.get("@name").getAsString(), jo.get("@id").getAsString());
    }
}