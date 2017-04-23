package api.dto.deserializer;

import api.dto.VenueContainerDto;
import api.dto.VenueDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class VenueContainerDtoDeserializer implements JsonDeserializer<VenueContainerDto> {
    @Override
    public VenueContainerDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("venues").getAsJsonObject();
        return new VenueContainerDto(
                jo.get("@itemsPerPage").getAsJsonPrimitive().getAsInt(),
                jo.get("@page").getAsJsonPrimitive().getAsInt(),
                jo.get("@total").getAsJsonPrimitive().getAsInt(),
                (json = jo.get("venue")).isJsonArray() ?
                        context.deserialize(json.getAsJsonArray(), VenueDto[].class) :
                        new VenueDto[] {context.deserialize(json.getAsJsonObject(), VenueDto.class)});
    }
}