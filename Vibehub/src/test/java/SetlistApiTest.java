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
        VenueDto actual = sl.getVenues("london")[0];
        String expected = "VenueDto{name='U.S. Embassy', id='6bd79276'}";
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void getEvents() {
        EventDto[] actual = sl.getEvents("33d4a8c9");
        String expected1 = "EventDto{" +
                "setid='be6319e', " +
                "mbid='e43f8c07-2d2e-47fd-8279-dc0e9880882c', " +
                "eventDate='19-04-2017', tour='404 not found', " +
                "sets={\"set\":[{\"song\":[{\"@name\":\"Intro\"},{\"@name\":\"Emoções\"},{\"@name\":\"Como vai você\"},{\"@name\":\"Além do Horizonte\"},{\"@name\":\"Ilegal, Imoral ou Engorda\"},{\"@name\":\"Detalhes\"},{\"@name\":\"Desabafo\"},{\"@name\":\"Outra Vez\"},{\"@name\":\"Lady Laura\"},{\"@name\":\"Nossa Senhora\"},{\"@name\":\"O Calhambeque\"},{\"@name\":\"Coimbra\"},{\"@name\":\"Olha\"},{\"@name\":\"Sua estupidez\"},{\"@name\":\"Mulher Pequena\"},{\"@name\":\"Chegaste\"},{\"@name\":\"As baleias\"},{\"@name\":\"Parabéns a você\"},{\"@name\":\"Como é grande o meu amor por você\"},{\"@name\":\"Jesus Cristo\"}]},{\"@encore\":\"1\",\"song\":[{\"@name\":\"Esse cara sou eu\"},{\"@name\":\"Amigo\"}]}]}}";
        Assert.assertEquals(actual[0].toString(), expected1);
        String expected2 = "EventDto{" +
                "setid='be68586', " +
                "mbid='d02dd67e-f655-4600-bc47-f789f59e7367', " +
                "eventDate='04-04-2017', " +
                "tour='24K Magic World Tour', " +
                "sets=\"\"}";
        Assert.assertEquals(actual[2].toString(), expected2);
    }
}