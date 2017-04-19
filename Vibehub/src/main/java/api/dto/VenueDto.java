package api.dto;

import com.google.gson.annotations.SerializedName;
import model.Event;

public class VenueDto {

    @SerializedName("@name")
    private String name;

    private Event[] events;

}