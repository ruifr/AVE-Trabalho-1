import model.Event;
import model.Venue;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import util.Countify;
import util.FileRequest;
import util.HttpRequest;
import util.ICounter;

public class Vibehub {
    @Test
    public void nextPageTest() {
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Iterable<Venue> vIter = vs.searchVenues("london");
        int i  = 0, expected = 1;
        for (Venue v: vIter) {
            ++i;
            Assert.assertEquals(expected, req.getCount());
            if(i == 30) {
                expected++;
                i = 0;
                if(expected == 2)   return;
            }
        }
    }

    @Ignore
    @Test
    public void runAllTest() {
        VibeService vs = new VibeService(new HttpRequest());
        for(Venue v : vs.searchVenues("london")) {
            for(Event e : v.getEvents()) {
                e.getArtist();
                e.getTracks().iterator();
            }
        }
    }
}