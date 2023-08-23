package com.example.redditprac1.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MainActivityViewModel extends AndroidViewModel {

    public String reddit;
    public String top;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public String getSubreddit() {
        return reddit;
    }

    public String getTop() {
        return top;
    }

}
