package model;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class Venue {

    private final String name;
    private final Supplier<Stream<Event>> events;

    public Venue(String name, Supplier<Stream<Event>> events) {
        this.name = name;
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
}