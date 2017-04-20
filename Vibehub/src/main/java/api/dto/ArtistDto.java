package api.dto;

import api.dto.deserializer.ArtistDtoDeserializer;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ArtistDtoDeserializer.class)
public class ArtistDto {

    private final String bio;
    private final String name;
    private final String mbid;
    private final String url;
    private final Iterable<String> str;

    public ArtistDto(String bio, String name, String mbid, String url, Iterable<String> str) {
        this.bio = bio;
        this.name = name;
        this.mbid = mbid;
        this.url = url;
        this.str = str;
    }
}
