package api.dto;

import api.dto.deserializer.ArtistDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

import java.util.Arrays;

@JsonAdapter(ArtistDtoDeserializer.class)
public class ArtistDto {
    private final String bio;
    private final String name;
    private final String url;
    private final ImageDto[] imagesUri;
    private final String mbid;

    public ArtistDto(String bio, String name, String mbid, String url, ImageDto[] imagesUri) {
        this.bio = bio;
        this.name = name;
        this.mbid = mbid;
        this.url = url;
        this.imagesUri = imagesUri;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String[] getImagesUri() {
        String[] ret = new String[imagesUri.length];
        for(int i = 0 ; i < ret.length ; ++i) ret[i] = imagesUri[i].toString();
        return ret;
    }

    public String getMbid() {
        return mbid;
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
                "bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imagesUri=" + Arrays.toString(imagesUri) +
                ", mbid='" + mbid + '\'' +
                '}';
    }
}