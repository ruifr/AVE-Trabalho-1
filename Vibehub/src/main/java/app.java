import model.Artist;
import model.Event;
import model.Venue;
import util.HttpRequest;

public class app {

    public static void main(String[] args) {
        VibeService vs = new VibeService(new HttpRequest());
        Venue v = vs.searchVenues("london").iterator().next();
        Event e = v.getEvents().iterator().next();
        Artist a = e.getArtist();
        //vs.getEvents("33d4a8c9");
        System.out.println();
    }
}
