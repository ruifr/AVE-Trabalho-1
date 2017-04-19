import api.dto.VenueDto;
import api.dto.VenueDtoDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import util.IRequest;

import static api.SetlistApi.join;

public class GsonTest {
    private static final String SETLIST_HOST = "https://api.setlist.fm";
    private static final String SETLIST_VENUES = "/rest/0.1/search/venues.json";
    private static final String SETLIST_VENUES_ARGS = "?cityName=%s";
    private static final String SETLIST_EVENTS="/rest/0.1/venue/%s/setlists.json";

    private IRequest req;
    private Gson gson;

    @Test
    public void test() {

        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(VenueDto.class, new VenueDtoDeserializer());
        gson = gb.create();
        String str = "london";
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, str);
        Iterable<String> content = () -> req.getContent(url).iterator();
        VenueDtoDeserializer cvdto = gson.fromJson(join(content), VenueDtoDeserializer.class);
    }
}
