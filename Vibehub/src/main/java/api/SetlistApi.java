package api;

import api.dto.EventContainerDto;
import api.dto.VenueContainerDto;
import com.google.gson.Gson;
import api.dto.EventDto;
import api.dto.VenueDto;
import com.google.gson.GsonBuilder;
import util.HttpRequest;
import util.IRequest;

public class SetlistApi {
    private static final String SETLIST_HOST = "https://api.setlist.fm";
    private static final String SETLIST_VENUES = "/rest/0.1/search/venues.json";
    private static final String SETLIST_VENUES_ARGS = "?cityName=%s";
    private static final String SETLIST_EVENTS="/rest/0.1/venue/%s/setlists.json";

    private final IRequest req;
    private Gson gson;

    public SetlistApi(IRequest req) {
        this.req = req;
        gson = new GsonBuilder().create();
    }

    public SetlistApi() {
        this(new HttpRequest());
    }

    public VenueDto[] getVenues(String str) {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, str);
        Iterable<String> content = () -> req.getContent(url).iterator();
        VenueDto[] vdto = gson.fromJson(join(content), VenueContainerDto.class).getVenues();
        return vdto;
    }

    public VenueDto[] getVenues(String str, int i){
        throw new UnsupportedOperationException();
    }

    public EventDto[] getEvents(String str){
        String path = SETLIST_HOST + SETLIST_EVENTS;
        String url = String.format(path, str);
        Iterable<String> content = () -> req.getContent(url).iterator();
        EventDto[] edto = gson.fromJson(join(content), EventContainerDto.class).getEvents();
        return edto;
    }

    public EventDto[] getEvents(String str, int i){
        throw new UnsupportedOperationException();
    }

    public static <T> String join(Iterable<T> src) {
        String res = "";
        for(T item: src) {
            res+=item.toString();
        }
        return res;
    }
}