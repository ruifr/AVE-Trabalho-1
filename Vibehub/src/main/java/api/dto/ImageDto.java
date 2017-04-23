package api.dto;

import com.google.gson.annotations.SerializedName;

public class ImageDto {
    @SerializedName("#text")
    private String text;

    @Override
    public String toString() {
        return text;
    }
}