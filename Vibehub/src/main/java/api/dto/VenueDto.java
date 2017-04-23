package api.dto;

import com.google.gson.annotations.SerializedName;

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
}