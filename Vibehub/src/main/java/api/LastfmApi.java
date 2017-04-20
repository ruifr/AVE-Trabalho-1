package api;

import api.dto.ArtistDto;
import api.dto.TrackDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.HttpRequest;
import util.IRequest;

public class LastfmApi {

    private static final String LASTFM_HOST = "http://ws.audioscrobbler.com/2.0/";
    private static final String LASTFM_ARTIST_ARGS = "?method=artist.getinfo&format=json&mbid=%s&api_key=%s";
    private static final String LASTFM_TRACK_ARGS = "?method=track.getinfo&format=json&mbid=%s&api_key=%s";
    private static final String LASTFM_key = "1e36f8968af975f197cf0502a2cad082";

    private final IRequest req;
    private Gson gson;

    public LastfmApi(IRequest req) {
        this.req = req;
        gson = new GsonBuilder().create();
    }

    public LastfmApi() {
        this(new HttpRequest());
    }

    public ArtistDto getArtistInfo(String ai) {
        String path = LASTFM_HOST + LASTFM_ARTIST_ARGS;
        String url = String.format(path, ai, LASTFM_key);
        Iterable<String> content = () -> req.getContent(url).iterator();
        ArtistDto adto = gson.fromJson(join(content), ArtistDto.class);
        return adto;
    }

    public TrackDto getTrackInfo(String str1, String str2) {
        throw new UnsupportedOperationException();
    }

    public static <T> String join(Iterable<T> src) {
        String res = "";
        for(T item: src) {
            res+=item.toString();
        }
        return res;
    }
}