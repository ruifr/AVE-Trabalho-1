import api.SetlistApi;
import api.dto.EventDto;
import api.dto.VenueDto;
import org.junit.Assert;
import org.junit.Test;
import util.FileRequest;

public class SetlistApiTest {
    private static SetlistApi sl = new SetlistApi(new FileRequest());

    @Test
    public void getVenues() {
        VenueDto actual = sl.getVenues("london", 1).join()[0];
        String expected = "VenueDto{name='2KHz at Church Studios', id='2bd77816'}";
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void getEvents() {
        EventDto[] actual = sl.getEvents("33d4a8c9", 1).join();
        String expected1 = "EventDto{" +
                "setid='73e7a601', " +
                "mbid='b7d92248-97e3-4450-8057-6fe06738f735', " +
                "artistName='Shawn Mendes', " +
                "eventDate='10-05-2017', " +
                "tour='Illuminate World Tour', " +
                "tracks=[]}";
        Assert.assertEquals(actual[0].toString(), expected1);
        String expected2 = "EventDto{" +
                "setid='be6319e', " +
                "mbid='e43f8c07-2d2e-47fd-8279-dc0e9880882c', " +
                "artistName='Roberto Carlos', " +
                "eventDate='19-04-2017', " +
                "tour='Resource Not Found', " +
                "tracks=[]}";
        Assert.assertEquals(actual[2].toString(), expected2);
    }
}