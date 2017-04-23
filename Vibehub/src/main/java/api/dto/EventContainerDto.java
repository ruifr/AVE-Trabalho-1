package api.dto;

import api.dto.deserializer.EventContainerDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(EventContainerDtoDeserializer.class)
public class EventContainerDto {
    private int itemPerPage;
    private int page;
    private int total;
    private EventDto[] events;

    public EventContainerDto(int itemPerPage, int page, int total, EventDto[] events) {
        this.itemPerPage = itemPerPage;
        this.page = page;
        this.total = total;
        this.events = events;
    }

    public boolean isValidPage(int p) {
        return p > 0 && p < (total/itemPerPage + 0.5);
    }

    public EventDto[] getEvents() {
        return events;
    }
}