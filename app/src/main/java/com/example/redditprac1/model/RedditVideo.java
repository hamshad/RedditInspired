package com.example.redditprac1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedditVideo {

    @SerializedName("bitrate_kbps")
    @Expose
    private Integer bitrateKbps;
    @SerializedName("has_audio")
    @Expose
    private Boolean hasAudio;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("fallback_url")
    @Expose
    private String fallbackUrl;

    public Integer getBitrateKbps() {
        return bitrateKbps;
    }

    public void setBitrateKbps(Integer bitrateKbps) {
        this.bitrateKbps = bitrateKbps;
    }

    public Boolean getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

}