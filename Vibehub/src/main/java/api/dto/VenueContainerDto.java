package api.dto;

import api.dto.deserializer.VenueContainerDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

@JsonAdapter(VenueContainerDtoDeserializer.class)
public class VenueContainerDto {
    private int itemPerPage;
    private int page;
    private int total;
    private VenueDto[] venue;

    public VenueContainerDto(int itemPerPage, int page, int total, VenueDto[] venue) {
        this.itemPerPage = itemPerPage;
        this.page = page;
        this.total = total;
        this.venue = venue;
    }

    public VenueDto[] getVenues() {
        return venue;
    }
}