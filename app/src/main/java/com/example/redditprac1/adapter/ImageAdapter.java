package com.example.redditprac1.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.FontFamily;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.redditprac1.R;
import com.example.redditprac1.view.MainActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {


    List<String> imageList, titleList, videoList;
    List<Boolean> isVideo;
    List<Integer> videoX;
    List<Integer> videoY;
    MainActivity mainActivity;

    ExoPlayer player;

    public ImageAdapter(MainActivity mainActivity, List<String> arrayList, List<String> titleList, List<Boolean> isVideo, List<String> videoList, List<Integer> videoX, List<Integer> videoY) {
        this.mainActivity = mainActivity;
        this.imageList = arrayList;
        this.titleList = titleList;
        this.isVideo = isVideo;
        this.videoList = videoList;
        this.videoX = videoX;
        this.videoY = videoY;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String tle = titleList.get(position);

        //Title
        holder.title.setText(tle);

        //Image
        String url = imageList.get(position);

        System.out.println(isVideo.get(position));

        if (isVideo.get(position)) {

            //Video
            String videoUrl = videoList.get(position);
            System.out.println(videoUrl.replace("?source=fallback", ""));
            Uri uri = Uri.parse(videoUrl.replace("?source=fallback", ""));

            AudioManager audio = (AudioManager) mainActivity.getSystemService(Context.AUDIO_SERVICE);

            holder.image.setVisibility(View.GONE);
            holder.link.setVisibility(View.GONE);
            holder.video.setVisibility(View.VISIBLE);
            holder.video.getLayoutParams().height = dpToPx(videoX.get(position));
            holder.video.getLayoutParams().width = dpToPx(videoY.get(position));

//            holder.video.setVideoURI(uri);
//            MediaController controller = new MediaController(mainActivity);
//            controller.setAnchorView(holder.video);
//            controller.setMediaPlayer(holder.video);
//            holder.video.setMediaController(controller);
//            holder.video.setOnPreparedListener(mediaPlayer -> {
////                mediaPlayer.setVolume(1.0f, 1.0f);
//                audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
//                holder.loading.setVisibility(View.GONE);
//                holder.video.start();
//            });


            player = new ExoPlayer.Builder(mainActivity).build();
            holder.video.setPlayer(player);
            player.setMediaItem(MediaItem.fromUri(uri));
            player.setVolume(1.0f);
            player.prepare();


            System.out.println("video");

        }
        if (!isVideo.get(position)){

            holder.video.setVisibility(View.GONE);
            holder.link.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView)
                    .load(url)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            holder.loading.setText(":( Failed");
                            holder.link.setText(url);
                            holder.loading.setVisibility(View.GONE);
                            holder.link.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions()
                            .fitCenter()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL))
                    .into(holder.image);


            Zoomy.Builder zoom = new Zoomy.Builder(mainActivity)
                    .target(holder.image)
                    .interpolator(new AccelerateDecelerateInterpolator())
                    .enableImmersiveMode(false)
                    .animateZooming(true);
            zoom.register();
        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mainActivity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final PlayerView video;
        private final TextView title, loading, link;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            video = itemView.findViewById(R.id.video);
            title = itemView.findViewById(R.id.title);
            loading = itemView.findViewById(R.id.loading);
            link = itemView.findViewById(R.id.link);

        }
    }
}
