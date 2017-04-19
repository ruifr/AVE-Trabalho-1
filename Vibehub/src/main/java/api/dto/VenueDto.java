package api.dto;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class VenueDto {
    @SerializedName("@name")
    private String name;
    @SerializedName("@id")
    private String id;

    public VenueDto(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "VenueDto{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public static class VenueDtoDeserializer implements JsonDeserializer<VenueDto[]> {
        @Override
        public VenueDto[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonElement ja = json.getAsJsonObject().getAsJsonObject().get("venues").getAsJsonObject().get("venue");
            if(ja.isJsonObject()) return new VenueDto[] { new VenueDto(ja.getAsJsonObject().get("@id").getAsString(), ja.getAsJsonObject().get("@name").getAsString())};
            VenueDto[] ret = new VenueDto[ja.getAsJsonArray().size()];
            int i = 0;
            for (JsonElement je: ja.getAsJsonArray()) {
                ret[i++] = context.deserialize(je, VenueDto.class);
            }
            return ret;
        }
    }
}