package api;

import api.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.HttpRequest;
import util.IRequest;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class SetlistApi {
    private static final String SETLIST_HOST = "https://api.setlist.fm";
    private static final String SETLIST_VENUES = "/rest/0.1/search/venues.json";
    private static final String SETLIST_VENUES_ARGS = "?cityName=%s&p=%s";
    private static final String SETLIST_EVENTS = "/rest/0.1/venue/%s/setlists.json?p=%s";

    private final IRequest req;
    private Gson gson;

    public SetlistApi(IRequest req) {
        this.req = req;
        gson = new GsonBuilder().create();
    }

    public SetlistApi() {
        this(new HttpRequest());
    }

    public VenueContainerDto getVenueContainer(String cityName, int p) {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, cityName, p);
        Stream<String> content = req.getContent(url);
        String s = content.reduce((v1, v2) -> v1+v2).get();
        return gson.fromJson(s, VenueContainerDto.class);
    }

    public VenueDto[] getVenues(String cityName, int p){
        return getVenueContainer(cityName, p).getModel();
    }

    public EventContainerDto getEventContainer(String id, int p){
        String path = SETLIST_HOST + SETLIST_EVENTS;
        String url = String.format(path, id, p);
        Stream<String> content = req.getContent(url);
        String s = content.reduce((v1, v2) -> v1+v2).get();
        return gson.fromJson(s, EventContainerDto.class);
    }

    public EventDto[] getEvents(String id, int p){
        return getEventContainer(id, p).getModel();
    }
}