package model;

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
        return "Venue{" +
                "name='" + name + '\'' +
                ", events=" + events +
                '}';
    }
}