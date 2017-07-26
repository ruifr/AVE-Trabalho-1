import model.Event;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import util.HttpRequest;
import util.HttpServer;

import java.util.concurrent.CompletableFuture;

import static java.lang.ClassLoader.getSystemResource;

public class app {

    public static void main(String[] args) throws Exception {
        try(HttpRequest http = new HttpRequest()) {
            VibeServiceCache service = new VibeServiceCache(http);
            VibeController ctr = new VibeController(service);

            ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
            String resPath = getSystemResource("public").toString();
            holderHome.setInitParameter("resourceBase", resPath);
            holderHome.setInitParameter("dirAllowed", "true");
            holderHome.setInitParameter("pathInfoOnly", "true");

            new HttpServer(3000)
                    .addHandler("/search", ctr::getSearch)
                    .addHandler("/search/venues", ctr::searchVenues)
                    .addHandler("/search/events", ctr::getEvents)
                    .addHandler("/search/artist", ctr::getArtist)
                    .addHandler("/search/tracks", ctr::getTracks)
                    .run();
        }
    }
}