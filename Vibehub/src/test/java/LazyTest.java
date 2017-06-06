import model.Event;
import model.Venue;
import org.junit.Assert;
import org.junit.Test;
import util.Countify;
import util.FileRequest;
import util.HttpRequest;
import util.ICounter;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LazyTest {
    @Test
    public void searchVenuesLazyTest() {
        ICounter<String, CompletableFuture<Stream<String>>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Assert.assertEquals(0,req.getCount());
        vs.searchVenues("london").get().limit(2).forEach(item -> {});
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void getEventsLazyTest() {
        ICounter<String, CompletableFuture<Stream<String>>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Assert.assertEquals(0,req.getCount());
        vs.getEvents("33d4a8c9").get().findFirst().ifPresent(item -> {});
        Assert.assertEquals(1,req.getCount());
    }

    @Test
    public void serviceLazyTest() {
        ICounter<String, CompletableFuture<Stream<String>>> req = Countify.of(new FileRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Supplier<Stream<Venue>> vIter = vs.searchVenues("london");
        Assert.assertEquals(0,req.getCount());
        Venue v = vIter.get().findFirst().get();
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
        ICounter<String, CompletableFuture<Stream<String>>> req = Countify.of(new HttpRequest()::getContent);
        VibeService vs = new VibeService(req::apply);
        Supplier<Stream<Venue>> vSupp = vs.searchVenues("london");
        Venue vlo1 = vSupp.get().findFirst().get();
        Assert.assertEquals(1,req.getCount());
        Venue vli1 = vs.searchVenues("lisbon").get().findFirst().get();
        Assert.assertEquals(2,req.getCount());
        Venue vlo2 = vSupp.get().findFirst().get();
        Assert.assertEquals(2,req.getCount());
        Venue vli2 = vs.searchVenues("lisbon").get().findFirst().get();
        Assert.assertEquals(2,req.getCount());

        vSupp.get().limit(3).forEach(item -> {});

        Assert.assertEquals(3,req.getCount());

        Assert.assertTrue(vlo1 == vlo2);
        Assert.assertTrue(vli1 == vli2);

        Event eli1 = vli1.getEvents().findFirst().get();
        Event eli2 = vs.getEvents("13d67585").get().findFirst().get();

        Assert.assertTrue(eli1 == eli2);
        Assert.assertEquals(4,req.getCount());
    }
}