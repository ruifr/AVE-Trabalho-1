import api.SetlistApi;
import api.dto.EventDto;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import util.HttpRequest;
import util.HttpServer;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.ClassLoader.getSystemResource;

public class app {

    public static void main(String[] args) throws Exception {
        try(HttpRequest http = new HttpRequest()) {
            VibeService service = new VibeService(http);
            VibeController ctr = new VibeController(service);

            ServletHolder holderHome = new ServletHolder("static-home", DefaultServlet.class);
            String resPath = getSystemResource("public").toString();
            holderHome.setInitParameter("resourceBase", resPath);
            holderHome.setInitParameter("dirAllowed", "true");
            holderHome.setInitParameter("pathInfoOnly", "true");

            new HttpServer(3000)
                    .addHandler("/search", ctr::getSearch)
                    .addHandler("/search/venues", ctr::searchVenues)
                    .addHandler("/get/event", ctr::getEvents)
                    //.addServletHolder("/public/*", ctr::getArtist)
                    //.addHandler("", ctr::getTracks)
                    .run();
        }
    }
}