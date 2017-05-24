import model.Event;
import model.Track;
import model.Venue;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import util.FileRequest;
import util.ICounter;
import util.Countify;

public class LazyTest {
    @Test
    public void searchVenuesLazyTest() {
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Assert.assertEquals(0,req.getCount());
        Iterable<Venue> v = vs.searchVenues("london");
        Assert.assertEquals(0,req.getCount());
        v.iterator().next();
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void getEventsLazyTest() {
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Assert.assertEquals(0,req.getCount());
        Iterable<Event> e = vs.getEvents("33d4a8c9");
        Assert.assertEquals(0,req.getCount());
        e.iterator().next();
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void serviceLazyTest() {
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Iterable<Venue> vIter = vs.searchVenues("london");
        Assert.assertEquals(0,req.getCount());
        Venue v = vIter.iterator().next();
        Assert.assertEquals(1,req.getCount());
        Iterable<Event> eIter = v.getEvents();
        Assert.assertEquals(1,req.getCount());
        Event e = eIter.iterator().next();
        Assert.assertEquals(2,req.getCount());
        Iterable<Track> tIter = e.getTracks();
        Assert.assertEquals(2,req.getCount());
        tIter.iterator().next();
        Assert.assertEquals(3,req.getCount());
        e.getArtist();
        Assert.assertEquals(4,req.getCount());
    }

    @Test
    public void serviceMemoizeLazyTest() {
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(Cache.memoize(req)::apply);
        vs.searchVenues("london").iterator();
        Assert.assertEquals(1,req.getCount());
        vs.searchVenues("lisbon").iterator();
        Assert.assertEquals(2,req.getCount());
        Venue v = vs.searchVenues("london").iterator().next();
        Assert.assertEquals(2,req.getCount());
        vs.searchVenues("lisbon").iterator();
        Assert.assertEquals(2,req.getCount());

        v.getEvents().iterator();
        Assert.assertEquals(3,req.getCount());
        Event e = v.getEvents().iterator().next();
        Assert.assertEquals(3,req.getCount());

        e.getTracks().iterator().next();
        Assert.assertEquals(4,req.getCount());
        e.getArtist();
        Assert.assertEquals(5,req.getCount());
        e.getTracks().iterator().next();
        e.getArtist();
        Assert.assertEquals(5,req.getCount());
    }

    @Test
    @Ignore
    public void serviceCacheLazyTest() {
        /*
        ICounter<String, Iterable<String>> req = Countify.of(new FileRequest()::getContent);
        VibeServiceCache vs = new VibeServiceCache(req::apply);
        vs.searchVenues("london").iterator();
        Assert.assertEquals(1,req.getCount());
        vs.searchVenues("lisbon").iterator();
        Assert.assertEquals(2,req.getCount());
        Venue v = vs.searchVenues("london").iterator().next();
        Assert.assertEquals(2,req.getCount());
        vs.searchVenues("lisbon").iterator();
        Assert.assertEquals(2,req.getCount());

        v.getEvents().iterator();
        Assert.assertEquals(3,req.getCount());
        Event e = v.getEvents().iterator().next();
        Assert.assertEquals(3,req.getCount());

        e.getTracks().iterator().next();
        Assert.assertEquals(4,req.getCount());
        e.getArtist();
        Assert.assertEquals(5,req.getCount());
        e.getTracks().iterator().next();
        e.getArtist();
        Assert.assertEquals(5,req.getCount());
        */
    }
}