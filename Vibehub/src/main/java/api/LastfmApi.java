package api;

import api.dto.ArtistDto;
import api.dto.TrackDto;
import util.IRequest;

public class LastfmApi {

    private static final String key = "1e36f8968af975f197cf0502a2cad082";

    public LastfmApi(IRequest req) {
    }

    public LastfmApi() {
    }

    public ArtistDto getArtistInfo(String ai) {
        throw new UnsupportedOperationException();
    }

    public TrackDto getTrackInfo(String str1, String str2) {
        throw new UnsupportedOperationException();
    }
}
