import util.HttpRequest;

public class app {

    public static void main(String[] args) {
        VibeService vs = new VibeService(new HttpRequest());
        System.out.println();
    }
}