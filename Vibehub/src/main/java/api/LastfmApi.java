package api;

import api.dto.ArtistDto;
import api.dto.TrackDto;
import model.IRequest;

public class LastfmApi {
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
