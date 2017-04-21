import model.Artist;
import model.Event;
import model.Track;
import model.Venue;
import org.junit.Assert;
import org.junit.Test;
import util.HttpRequest;
import util.ICounter;
import util.Countify;

public class LazyTest {
    @Test
    public void Lazy() {
        ICounter<String, Iterable<String>> req = Countify.of(new HttpRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Assert.assertEquals(0,req.getCount());
        Venue v = vs.searchVenues("london").iterator().next();
        Assert.assertEquals(2,req.getCount());
        Event e = v.getEvents().iterator().next();
        Assert.assertEquals(2,req.getCount());
        Artist a = e.getArtist();
        Assert.assertEquals(3,req.getCount());
        Iterable<Track> it = e.getTracks();
        Assert.assertEquals(3,req.getCount());
        Track t = it.iterator().next();
        Assert.assertEquals(4,req.getCount());
        System.out.println(a.toString());
    }
}