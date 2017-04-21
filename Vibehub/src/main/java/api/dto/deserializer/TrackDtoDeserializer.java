package api.dto.deserializer;

import api.dto.ImageDto;
import api.dto.TrackDto;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TrackDtoDeserializer implements JsonDeserializer<TrackDto> {
    @Override
    public TrackDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("track").getAsJsonObject();
        JsonArray ja = jo.get("album").getAsJsonObject().get("image").getAsJsonArray().getAsJsonArray();
        String[] images = new String[ja.size()];
        for(int i = ja.size()-1 ; i >= 0 ; --i)
            images[i] = ((ImageDto)context.deserialize(ja.get(i), ImageDto.class)).getText();
        return new TrackDto(
                jo.get("name").getAsString(),
                jo.get("artist").getAsJsonObject().get("name").getAsString(),
                jo.get("album").getAsJsonObject().get("title").getAsString(),
                jo.get("url").getAsString(),
                images,
                jo.get("album").getAsJsonObject().get("url").getAsString(),
                jo.get("duration").getAsInt());
    }
}