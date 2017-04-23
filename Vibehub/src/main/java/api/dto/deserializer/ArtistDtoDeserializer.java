package api.dto.deserializer;

import api.dto.ArtistDto;
import api.dto.ImageDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ArtistDtoDeserializer implements JsonDeserializer<ArtistDto> {
    @Override
    public ArtistDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(!json.getAsJsonObject().has("artist")) return null;
        JsonObject jo = json.getAsJsonObject().get("artist").getAsJsonObject();
        return new ArtistDto(
                jo.get("bio").getAsJsonObject().get("summary").getAsString(),
                jo.get("name").getAsString(),
                jo.get("mbid").getAsString(),
                jo.get("url").getAsString(),
                (json = jo.get("image")).isJsonArray() ?
                        context.deserialize(json.getAsJsonArray(), ImageDto[].class) :
                        new ImageDto[] {context.deserialize(json.getAsJsonObject(), ImageDto.class)});
    }
}