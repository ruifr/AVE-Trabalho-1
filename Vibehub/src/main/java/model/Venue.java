package model;

import java.util.Arrays;

public class Venue {

    private final String name;
    private final Iterable<Event> events;

    public Venue(String name, Iterable<Event> events) {
        this.name = name;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public Iterable<Event> getEvents() {
        return events;
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