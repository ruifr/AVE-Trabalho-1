package api.dto;

import api.dto.deserializer.ArtistDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

import java.util.Arrays;

@JsonAdapter(ArtistDtoDeserializer.class)
public class ArtistDto {
    private final String bio;
    private final String name;
    private final String mbid;
    private final String url;
    private final String[] str;

    public ArtistDto(String bio, String name, String mbid, String url, String[] str) {
        this.bio = bio;
        this.name = name;
        this.mbid = mbid;
        this.url = url;
        this.str = str;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getUrl() {
        return url;
    }

    public String[] getStr() {
        return str;
    }

    @Override
    public String toString() {
        return "ArtistDto{" +
                "bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                ", mbid='" + mbid + '\'' +
                ", url='" + url + '\'' +
                ", str=" + Arrays.toString(str) +
                '}';
    }
}