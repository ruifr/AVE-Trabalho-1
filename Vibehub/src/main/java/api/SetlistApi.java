package api;

import api.dto.EventContainerDto;
import api.dto.EventDto;
import api.dto.VenueContainerDto;
import api.dto.VenueDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.HttpRequest;
import util.IRequest;

import java.util.concurrent.CompletableFuture;

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

    public CompletableFuture<VenueContainerDto> getVenueContainer(String cityName, int p) {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, cityName, p);
        return req.getContent(url).thenApply(s -> gson.fromJson(s.reduce((v1, v2) -> v1 + v2).get(), VenueContainerDto.class));
    }

    public CompletableFuture<VenueDto[]> getVenues(String cityName, int p){
        return getVenueContainer(cityName, p).thenApply(VenueContainerDto::getModel);
    }

    public CompletableFuture<EventContainerDto> getEventContainer(String id, int p){
        String path = SETLIST_HOST + SETLIST_EVENTS;
        String url = String.format(path, id, p);
        return req.getContent(url).thenApply(s -> gson.fromJson(s.reduce((v1, v2) -> v1 + v2).get(), EventContainerDto.class));
    }

    public CompletableFuture<EventDto[]> getEvents(String id, int p){
        return getEventContainer(id, p).thenApply(EventContainerDto::getModel);
    }
}