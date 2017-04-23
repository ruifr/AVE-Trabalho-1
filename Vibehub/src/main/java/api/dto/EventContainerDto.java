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

    public int getItemPerPage() {
        return itemPerPage;
    }

    public int getPage() {
        return page;
    }

    public int getTotal() {
        return total;
    }

    public EventDto[] getEvents() {
        return events;
    }
}