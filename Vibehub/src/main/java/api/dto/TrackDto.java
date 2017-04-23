package api.dto;

import api.dto.deserializer.TrackDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

import java.util.Arrays;

@JsonAdapter(TrackDtoDeserializer.class)
public class TrackDto {
    private final String name;
    private final String artistName;
    private final String albumName;
    private final String trackUrl;
    private final ImageDto[] imagesUrl;
    private final String albumUrl;
    private final int duration;

    public TrackDto(String name, String artistName, String albumName, String trackUrl, ImageDto[] imagesUrl, String albumUrl, int duration) {
        this.name = name;
        this.artistName = artistName;
        this.albumName = albumName;
        this.trackUrl = trackUrl;
        this.imagesUrl = imagesUrl;
        this.albumUrl = albumUrl;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getTrackUrl() {
        return trackUrl;
    }

    public String[] getImagesUrl() {
        String[] ret = new String[imagesUrl.length];
        for(int i = 0 ; i < ret.length ; ++i) ret[i] = imagesUrl[i].toString();
        return ret;
    }

    public String getAlbumUrl() {
        return albumUrl;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "TrackDto{" +
                "name='" + name + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", trackUrl='" + trackUrl + '\'' +
                ", imagesUrl=" + Arrays.toString(imagesUrl) +
                ", albumUrl='" + albumUrl + '\'' +
                ", duration=" + duration +
                '}';
    }
}