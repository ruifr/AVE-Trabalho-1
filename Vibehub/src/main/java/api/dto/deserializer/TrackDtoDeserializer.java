package api.dto.deserializer;

import api.dto.ImageDto;
import api.dto.TrackDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TrackDtoDeserializer implements JsonDeserializer<TrackDto> {
    @Override
    public TrackDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("track").getAsJsonObject(),
                album = jo.get("album").getAsJsonObject();
        return new TrackDto(
                jo.get("name").getAsString(),
                jo.get("artist").getAsJsonObject().get("name").getAsString(),
                album.get("title").getAsString(),
                jo.get("url").getAsString(),
                album.get("image").isJsonArray() ?
                        context.deserialize(album.get("image").getAsJsonArray(), ImageDto[].class) :
                        new ImageDto[] {context.deserialize(album.get("image").getAsJsonObject(), ImageDto.class)},
                album.get("url").getAsString(),
                jo.get("duration").getAsInt());
    }
}