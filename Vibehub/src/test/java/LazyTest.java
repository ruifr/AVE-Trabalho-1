import model.Event;
import model.Track;
import model.Venue;
import org.junit.Assert;
import org.junit.Test;
import util.FileRequest;
import util.ICounter;
import util.Countify;

import java.util.Objects;
import java.util.stream.Stream;

public class LazyTest {
    @Test
    public void searchVenuesLazyTest() {
        ICounter<String, Stream<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Stream<Venue> v = vs.searchVenues("london");
        Assert.assertEquals(0,req.getCount());
        v.limit(2).forEach(item -> {});
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void getEventsLazyTest() {
        ICounter<String, Stream<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Stream<Event> e = vs.getEvents("33d4a8c9");
        Assert.assertEquals(0,req.getCount());
        e.findFirst().ifPresent(item -> {});
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void serviceLazyTest() {
        ICounter<String, Stream<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Stream<Venue> vIter = vs.searchVenues("london");
        Assert.assertEquals(0,req.getCount());
        Venue v = vIter.findFirst().get();
        Assert.assertEquals(1,req.getCount());
        Stream<Event> eIter = v.getEvents();
        Assert.assertEquals(1,req.getCount());
        Event e = eIter.findFirst().get();
        Assert.assertEquals(2,req.getCount());
        Stream<Track> tIter = e.getTracks();
        Assert.assertEquals(2,req.getCount());
        tIter.filter(Objects::nonNull).findFirst().get();
        Assert.assertEquals(3,req.getCount());
        e.getArtist();
        Assert.assertEquals(4,req.getCount());
    }

    @Test
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
        Assert.fail();
    }
    /*
    TODO: cache
    TODO: verificar conversões em stream
    TODO: verificar testes
    */
}