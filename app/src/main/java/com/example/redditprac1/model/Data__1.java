package com.example.redditprac1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data__1 {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("secure_media")
    @Expose
    private SecureMedia secureMedia;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("is_video")
    @Expose
    private Boolean isVideo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SecureMedia getSecureMedia() {
        return secureMedia;
    }

    public void setSecureMedia(SecureMedia secureMedia) {
        this.secureMedia = secureMedia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsVideo() {
        return isVideo;
    }

    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

}