import model.Venue;
import org.junit.Test;
import util.HttpRequest;

import java.util.Iterator;

public class VibehubTest {
    private static VibeService vs = new VibeService(new HttpRequest());

    @Test
    public void vibehubTest() {
        Iterator<Venue> v = vs.searchVenues("london").iterator();
        System.out.println(v.next().toString());
    }
}