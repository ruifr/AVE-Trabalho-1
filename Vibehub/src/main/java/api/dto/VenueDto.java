package api.dto;

import api.dto.deserializer.VenueDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

@JsonAdapter(VenueDtoDeserializer.class)
public class VenueDto {
    private String name;
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
}