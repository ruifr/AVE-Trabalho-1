package api.dto;

import com.google.gson.annotations.SerializedName;

public class ImageDto {
    @SerializedName("#text")
    private String text;
    public String getText() {
        return text;
    }
}