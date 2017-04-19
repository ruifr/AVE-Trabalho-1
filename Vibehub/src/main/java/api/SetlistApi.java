package api;

import com.google.gson.Gson;
import api.dto.EventDto;
import api.dto.EventDto.*;
import api.dto.VenueDto;
import api.dto.VenueDto.*;
import com.google.gson.GsonBuilder;
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
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(VenueDto[].class, new VenueDtoDeserializer());
        gb.registerTypeAdapter(EventDto[].class, new EventDtoDeserializer());
        gson = gb.create();
    }

    public VenueDto[] getVenues(String str) {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, str);
        Iterable<String> content = () -> req.getContent(url).iterator();
        VenueDto[] vdto = gson.fromJson(join(content), VenueDto[].class);
        return vdto;
    }

    public VenueDto[] getVenues(String str, int i){
        throw new UnsupportedOperationException();
    }

    public EventDto[] getEvents(String str){
        String path = SETLIST_HOST + SETLIST_EVENTS;
        String url = String.format(path, str);
        Iterable<String> content = () -> req.getContent(url).iterator();
        EventDto[] edto = gson.fromJson(join(content), EventDto[].class);
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
