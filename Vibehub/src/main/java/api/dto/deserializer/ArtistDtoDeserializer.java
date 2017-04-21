package api.dto.deserializer;

import api.dto.ArtistDto;
import api.dto.ImageDto;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class ArtistDtoDeserializer implements JsonDeserializer<ArtistDto> {

    @Override
    public ArtistDto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject().get("artist").getAsJsonObject();
        JsonArray ja = jo.get("image").getAsJsonArray().getAsJsonArray();
        String[] images = new String[ja.size()];
        for(int i = ja.size()-1 ; i >= 0 ; --i)
            images[i] = ((ImageDto)context.deserialize(ja.get(i), ImageDto.class)).getText();
        return new ArtistDto(
                jo.get("bio").getAsJsonObject().get("summary").getAsString(),
                jo.get("name").getAsString(),
                jo.get("mbid").getAsString(),
                jo.get("url").getAsString(),
                images);
    }
}