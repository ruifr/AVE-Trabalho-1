package api.dto.deserializer;

import api.dto.EventContainerDto;
import api.dto.EventDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class EventContainerDtoDeserializer implements JsonDeserializer<EventContainerDto> {
    @Override
    public EventContainerDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("setlists").getAsJsonObject();

        EventDto[] temp = (json = jo.get("setlist")).isJsonArray() ? context.deserialize(json.getAsJsonArray(), EventDto[].class) : new EventDto[] {context.deserialize(json.getAsJsonObject(), EventDto.class)};
        return new EventContainerDto(
                jo.get("@itemsPerPage").getAsInt(),
                jo.get("@page").getAsInt(),
                jo.get("@total").getAsInt(),
                temp);
    }
}