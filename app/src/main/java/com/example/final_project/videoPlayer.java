package com.example.final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class videoPlayer extends AppCompatActivity{
    Handler handler = new Handler();
    TextView Tdescription;
    TextView Tnickname;
    TextView Tlikecount;
    TextView T_id;
    ImageView Iavatar;
    VideoView video;
    ImageButton button;
    LinearLayout linear;
    SeekBar seekbar;
    TextView text1;
    TextView text2;
    static int position = 0;
    static long ti = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        Intent intent = getIntent();
        String feedurl = intent.getStringExtra("feedurl");
        String description = intent.getStringExtra("description");
        String avatar = intent.getStringExtra("avatar");
        String nickname = intent.getStringExtra("nickname");
        String likecount = intent.getStringExtra("likecount");
        String _id = intent.getStringExtra("_id");

        if (getResources().getConfiguration().orientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        Tdescription = findViewById(R.id.video_title);
        Tnickname = findViewById(R.id.nickname);
        Tlikecount = findViewById(R.id.like_count);
        T_id = findViewById(R.id._id);
        Iavatar = findViewById(R.id.avatar);
        video = findViewById(R.id.video);
        button = findViewById(R.id.button);
        linear = findViewById(R.id.linear);
        seekbar = findViewById(R.id.seekBar);
        text1 = findViewById(R.id.textViewNow);
        text2 = findViewById(R.id.textViewLength);
        seekbar.setEnabled(false);

        Tdescription.setText(description);
        Glide.with(this)
                .load(avatar)
                .into(Iavatar);
        Tnickname.setText("@" + nickname);
        Tlikecount.setText(likecount);
        T_id.setText("id:" + _id);

        video.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(video != null){
                    linear.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            linear.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.INVISIBLE);
                        }
                    }, 2000);
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(video.isPlaying()){
                    video.pause();
                    button.setImageResource(R.drawable.play);
                }
                else{
                    button.setImageResource(R.drawable.stop);
                    video.start();
                }
            }
        });

        video.setVideoURI(Uri.parse(feedurl));
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                text2.setText(getTimeFormat(video.getDuration()));
            }
        });
        if (System.currentTimeMillis() - ti < 1000)
            video.seekTo(position);
        video.requestFocus();
        video.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (video != null && video.isPlaying()) {
                    seekbar.setProgress(video.getCurrentPosition() * 100 / video.getDuration());
                    position = video.getCurrentPosition();
                    ti = System.currentTimeMillis();
                    int i = video.getCurrentPosition();
                    text1.setText(getTimeFormat(video.getCurrentPosition()));
                }
                handler.postDelayed(this, 500);
            }
        }, 100);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linear.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        }, 2000);
}

    public static String getTimeFormat(long msec) {
        long sec = msec / 1000;
        long min = sec / 60;
        long hour = min / 60;
        sec = sec % 60;
        min = min % 60;
        String res = "";
        if (hour > 0)
            res += hour + ":";
        if (min > 0 || hour > 0)
            res += min + ":";
        res += sec;
        return res;
    }
}
