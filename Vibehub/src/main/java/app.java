import util.HttpRequest;

public class app {

    public static void main(String[] args) {
        VibeService vs = new VibeService(new HttpRequest());
        vs.searchVenues("london");
        vs.getEvents("33d4a8c9");
    }
}
