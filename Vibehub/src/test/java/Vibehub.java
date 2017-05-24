import model.Venue;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import util.*;

import java.util.stream.Stream;

public class Vibehub {
    @Test
    public void nextPageTest() {
        ICounter<String, Stream<String>> req = Countify.of(new HttpRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Stream<Venue> vIter = vs.searchVenues("london");
        final int i[] = new int[] {0}, expected[] = new int[] { 1 };

        vIter.forEach(item -> {
            ++i[0];
            Assert.assertEquals(expected[0], req.getCount());
            if(i[0] == 30) {
                expected[0]++;
                i[0] = 0;
                if(expected[0] == 2)   return;
            }
        });
    }

    @Ignore
    @Test
    public void runAllTest() {
        VibeService vs = new VibeService(new HttpRequest());
        vs.searchVenues("london").forEach(item1 -> {
            item1.getEvents().forEach(item2 -> {
                item2.getArtist();
                item2.getTracks();
            });
        });
    }
}