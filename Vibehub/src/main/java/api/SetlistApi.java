package api;

import api.dto.EventContainerDto;
import api.dto.VenueContainerDto;
import com.google.gson.Gson;
import api.dto.EventDto;
import api.dto.VenueDto;
import com.google.gson.GsonBuilder;
import util.HttpRequest;
import util.IRequest;

import static util.queries.LazyQueries.join;

public class SetlistApi {
    private static final String SETLIST_HOST = "https://api.setlist.fm";
    private static final String SETLIST_VENUES = "/rest/0.1/search/venues.json";
    private static final String SETLIST_VENUES_ARGS = "?cityName=%s";
    private static final String SETLIST_EVENTS = "/rest/0.1/venue/%s/setlists.json";

    private final IRequest req;
    private Gson gson;

    public SetlistApi(IRequest req) {
        this.req = req;
        gson = new GsonBuilder().create();
    }

    public SetlistApi() {
        this(new HttpRequest());
    }

    public VenueDto[] getVenues(String cityName) {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, cityName);
        Iterable<String> content = () -> req.getContent(url).iterator();
        return gson.fromJson(join(content), VenueContainerDto.class).getVenues();
    }

    public VenueDto[] getVenues(String str, int i){
        throw new UnsupportedOperationException();
    }

    public EventDto[] getEvents(String id){
        String path = SETLIST_HOST + SETLIST_EVENTS;
        String url = String.format(path, id);
        Iterable<String> content = () -> req.getContent(url).iterator();
        return gson.fromJson(join(content), EventContainerDto.class).getEvents();
    }

    public EventDto[] getEvents(String str, int i){
        throw new UnsupportedOperationException();
    }
}