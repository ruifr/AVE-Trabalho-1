package api.dto;

import api.dto.deserializer.VenueContainerDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(VenueContainerDtoDeserializer.class)
public class VenueContainerDto extends ContainerDto<VenueDto> {
    public VenueContainerDto(int itemPerPage, int page, int total, VenueDto[] venue) {
        super(itemPerPage, page, total, venue);
    }
}