package model;

import java.util.Arrays;

public class Artist {
    private final String bio;
    private final String name;
    private final String url;
    private final String[] imagesUri;
    private final String mbid;

    public Artist(String name, String bio, String url, String[] imagesUri, String mbid) {
        this.name = name;
        this.bio = bio;
        this.url = url;
        this.imagesUri = imagesUri;
        this.mbid = mbid;
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
        return imagesUri;
    }

    public String getMbid() {
        return mbid;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "bio='" + bio + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", imagesUri=" + Arrays.toString(imagesUri) +
                ", mbid='" + mbid + '\'' +
                '}';
    }
}