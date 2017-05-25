import api.LastfmApi;
import api.dto.ArtistDto;
import api.dto.TrackDto;
import org.junit.Assert;
import org.junit.Test;
import util.FileRequest;

public class LastfmApiTest {
    private static LastfmApi lf = new LastfmApi(new FileRequest());

    @Test
    public void getArtistTest() {
        ArtistDto actual = lf.getArtistInfo("9c9f1380-2516-4fc9-a3e6-f9f61941d090");
        String expected = "ArtistDto{bio='Muse are an alternative rock band from Teignmouth, England, United Kingdom. The band consists of Matthew Bellamy on lead vocals, piano, keyboard and guitar, Chris Wolstenholme on backing vocals and bass guitar, and Dominic Howard on drums and percussion.  \n" +
                "\n" +
                "They have been friends since their formation in early 1994 and changed band names a number of times (such as Gothic Plague, Fixed Penalty, and Rocket Baby Dolls) before adopting the name Muse. Since the release of their fourth album <a href=\"https://www.last.fm/music/Muse\">Read more on Last.fm</a>', name='Muse', url='https://www.last.fm/music/Muse', imagesUri=[https://lastfm-img2.akamaized.net/i/u/34s/eed9e80ca0872def19a028db01fc1b70.png, https://lastfm-img2.akamaized.net/i/u/64s/eed9e80ca0872def19a028db01fc1b70.png, https://lastfm-img2.akamaized.net/i/u/174s/eed9e80ca0872def19a028db01fc1b70.png, https://lastfm-img2.akamaized.net/i/u/300x300/eed9e80ca0872def19a028db01fc1b70.png, https://lastfm-img2.akamaized.net/i/u/eed9e80ca0872def19a028db01fc1b70.png, https://lastfm-img2.akamaized.net/i/u/arQ/eed9e80ca0872def19a028db01fc1b70.png], mbid='fd857293-5ab8-40de-b29e-55a69d4e4d0f'}";
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void getTrackTest() {
        TrackDto actual = lf.getTrackInfo("Skunk+Anansie", "Cheap+Honesty");
        String expected = "TrackDto{name='Cheap Honesty', " +
                "artistName='Skunk Anansie', " +
                "albumName='Post Orgasmic Chill', " +
                "trackUrl='https://www.last.fm/music/Skunk+Anansie/_/Cheap+Honesty', " +
                "imagesUrl=[https://lastfm-img2.akamaized.net/i/u/34s/80a47a15a5f94f438c022111c99f0e92.png, https://lastfm-img2.akamaized.net/i/u/64s/80a47a15a5f94f438c022111c99f0e92.png, https://lastfm-img2.akamaized.net/i/u/174s/80a47a15a5f94f438c022111c99f0e92.png, https://lastfm-img2.akamaized.net/i/u/300x300/80a47a15a5f94f438c022111c99f0e92.png], " +
                "albumUrl='https://www.last.fm/music/Skunk+Anansie/Post+Orgasmic+Chill', " +
                "duration=227000}";
        Assert.assertEquals(actual.toString(), expected);
    }
}