package com.example.redditprac1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("data")
    @Expose
    private Data__1 data;

    public Data__1 getData() {
        return data;
    }

    public void setData(Data__1 data) {
        this.data = data;
    }

}