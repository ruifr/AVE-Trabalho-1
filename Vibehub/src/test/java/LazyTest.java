import model.Event;
import model.Track;
import model.Venue;
import org.junit.Assert;
import org.junit.Test;
import util.FileRequest;
import util.HttpRequest;
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
        e.getArtist();
        Assert.assertEquals(3,req.getCount());
    }

    @Test
    public void serviceCacheLazyTest() {
        ICounter<String, Stream<String>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);

        Venue vlo1 = vs.searchVenues("london").findFirst().get();
        Assert.assertEquals(1,req.getCount());
        Venue vli1 = vs.searchVenues("lisbon").findFirst().get();
        Assert.assertEquals(2,req.getCount());
        Venue vlo2 = vs.searchVenues("london").findFirst().get();
        Assert.assertEquals(2,req.getCount());
        Venue vli2 = vs.searchVenues("lisbon").findFirst().get();
        Assert.assertEquals(2,req.getCount());

        Stream<Venue> str = vs.searchVenues("london");
        str.limit(3).forEach(item -> System.out.println(item.getName()));

        Assert.assertTrue(vlo1 == vlo2);
        Assert.assertTrue(vli1 == vli2);

        Event eli1 = vli1.getEvents().findFirst().get();
        Event eli2 = vs.getEvents("13d67585").findFirst().get();

        Assert.assertTrue(eli1 == eli2);
    }
    /*
    TODO: cache
    TODO: verificar conversões em stream
    TODO: verificar testes
    */
}