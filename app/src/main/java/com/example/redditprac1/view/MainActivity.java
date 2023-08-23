package com.example.redditprac1.view;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redditprac1.R;
import com.example.redditprac1.adapter.ImageAdapter;
import com.example.redditprac1.model.Child;
import com.example.redditprac1.model.ProgrammerHumor;
import com.example.redditprac1.service.ImageDataService;
import com.example.redditprac1.service.RetrofitInstance;

//import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageAdapter adapter;
//    ConstraintLayout layout;
    TextView title;

    MainActivityViewModel viewModel;

    private int scrollState;

    Spinner redditSpin;
    String[] subreddits = {
            "ProgrammerHumor",
            "programming",
            "dankmemes",
            "funny",
            "todayilearned",
            "PewdiepieSubmissions",
            "technicallythetruth",
            "cursedcomments"
    };

    Spinner topSpin;
    String[] top = {
            "hour",
            "today",
            "week",
            "month",
            "year",
            "all"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        recyclerView = findViewById(R.id.recycler_view);
//        layout = findViewById(R.id.textView);
        redditSpin = findViewById(R.id.reddit_spin);
        topSpin = findViewById(R.id.top_spin);
        title = findViewById(R.id.title);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        ArrayAdapter<?> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subreddits);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        redditSpin.setAdapter(spinAdapter);

        redditSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.reddit = subreddits[i];
                getImage(viewModel.getSubreddit() ,50, viewModel.getTop());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<?> topAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, top);
        topAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topSpin.setAdapter(topAdapter);

        topSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.top = top[i];
                getImage(viewModel.getSubreddit() ,50, viewModel.getTop());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                scrollState = newState;
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (dy > 0 && (scrollState == 0 || scrollState == 2)) {
//                    layout.setVisibility(View.GONE);
//                } else if (dy < -10) {
//                    layout.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });

        title.setOnLongClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MoreActivity.class));
            return true;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            startActivity(new Intent(this, NoInternetActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }

    private void getImage(String reddit ,int limit, String top) {

        ImageDataService getImageDataService = RetrofitInstance.getService();
        Call<ProgrammerHumor> call = getImageDataService.getData(reddit ,limit, top);

        call.enqueue(new Callback<ProgrammerHumor>() {
            @Override
            public void onResponse(Call<ProgrammerHumor> call, Response<ProgrammerHumor> response) {

                List<String> url = new ArrayList<>();
                List<String> title = new ArrayList<>();
                List<String> video = new ArrayList<>();
                List<Integer> videoX = new ArrayList<>(),
                        videoY = new ArrayList<>();
                List<Boolean> isVideo = new ArrayList<>();

                if (response.body() != null) {

                    ProgrammerHumor humor = response.body();

                    for (Child child : humor.getData().getChildren()) {
                        isVideo.add(child.getData().getIsVideo());
                        title.add(child.getData().getTitle());
                        url.add(child.getData().getUrl());
                        if (child.getData().getIsVideo()) {
                            video.add(child.getData().getSecureMedia().getRedditVideo().getFallbackUrl());
                            videoX.add(child.getData().getSecureMedia().getRedditVideo().getWidth());
                            videoY.add(child.getData().getSecureMedia().getRedditVideo().getHeight());
                        } else {
                            video.add("");
                            videoX.add(0);
                            videoY.add(0);
                        }
                    }

                }
                addToRecycler(url, title, isVideo, video, videoX, videoY);
            }

            @Override
            public void onFailure(Call<ProgrammerHumor> call, Throwable t) {

            }
        });


    }

    private void addToRecycler(List<String> url, List<String> title, List<Boolean> isVideo, List<String> video, List<Integer> videoX, List<Integer> videoY) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this, url, title, isVideo, video, videoX, videoY);
        recyclerView.setAdapter(adapter);
    }


    //Clearing cache on closing app
//    @Override
//    protected void onStop() {
//        super.onStop();
//        try {
//            File dir = this.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static boolean deleteDir(File dir) {
//
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (String i : children) {
//                boolean success = deleteDir(new File(dir, i));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }
}