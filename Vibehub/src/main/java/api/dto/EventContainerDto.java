package api.dto;

import api.dto.deserializer.EventContainerDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(EventContainerDtoDeserializer.class)
public class EventContainerDto extends ContainerDto<EventDto> {
    public EventContainerDto(int itemPerPage, int page, int total, EventDto[] events) {
        super(itemPerPage, page, total, events);
    }
}