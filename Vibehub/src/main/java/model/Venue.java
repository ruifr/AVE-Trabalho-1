package model;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Venue {

    private final String name;
    private final String id;
    private final Supplier<Stream<Event>> events;

    public Venue(String name, Supplier<Stream<Event>> events, String id) {
        this.name = name;
        this.id = id;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public Stream<Event> getEvents() {
        return events.get();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{name='");
        sb.append(name);
        sb.append("', events=[");
        getEvents().forEach(sb::append);
        sb.append("]}");
        return sb.toString();
    }

    public String getId() {
        return id;
    }
}