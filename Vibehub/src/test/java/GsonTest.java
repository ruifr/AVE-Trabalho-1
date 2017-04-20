import api.dto.VenueDto;
import com.google.gson.Gson;
import org.junit.Test;
import util.HttpRequest;
import util.IRequest;

import static api.SetlistApi.join;

public class GsonTest {
    private static final String SETLIST_HOST = "https://api.setlist.fm";
    private static final String SETLIST_VENUES = "/rest/0.1/search/venues.json";
    private static final String SETLIST_VENUES_ARGS = "?cityName=%s";
    private static final String SETLIST_EVENTS="/rest/0.1/venue/%s/setlists.json";

    private IRequest req = new HttpRequest();
    private Gson gson = new Gson();

    @Test
    public void test() {
        String path = SETLIST_HOST + SETLIST_VENUES + SETLIST_VENUES_ARGS;
        String url = String.format(path, "london");
        Iterable<String> content = () -> req.getContent(url).iterator();
        String res = join(content);
        VenueDto cvdto = gson.fromJson(res, VenueDto.class);
    }
}