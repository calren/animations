package com.example.caren.animations;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FlingAnimationActivity extends AppCompatActivity {

    float maxScroll = 3;
    float velocityX = 10;

    SeekBar startVelocitySeekbar;
    SeekBar frictionSeekbar;
    TextView startVText;
    TextView frictionText;
    FlingAnimation mFlingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_animation);

//        final RecyclerView recyclerView = findViewById(R.id.rv);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getImages());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        View view = findViewById(R.id.ghost);
        mFlingAnimation = new FlingAnimation(view, DynamicAnimation.TRANSLATION_X);

        startVelocitySeekbar = findViewById(R.id.startVelocity);
        frictionSeekbar = findViewById(R.id.friction);
        startVText = findViewById(R.id.startVelocityText);
        frictionText = findViewById(R.id.frictionText);

        startVelocitySeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                startVText.setText(
                        "start velocity value: " +
                                (startVelocitySeekbar.getProgress() == 0 ? 1 :
                                        startVelocitySeekbar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startFlingAnimation(5000);
            }
        });

        frictionSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frictionText.setText(
                        "friction value: " + (frictionSeekbar.getProgress() == 0 ? 1 :
                                frictionSeekbar.getProgress() / 50f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

//        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
//            @Override
//            public boolean onFling(int i, int i1) { //velocityX , velocityY
//                startFlingAnimation(i);
//                return true;
//            }
//        });
    }

    private void startFlingAnimation(int velocityX) {


        float startVelocity = startVelocitySeekbar.getProgress() == 0 ? 1 :
                startVelocitySeekbar.getProgress() *
                        200; // 200 to 20,000, negative if going// right to left

        float pixelPerSecond = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                startVelocity,
                getResources().getDisplayMetrics());

        float friction =
                frictionSeekbar.getProgress() == 0 ? 0.01f : frictionSeekbar.getProgress() / 50f;
        Log.i("Caren2", "velocity: " + startVelocity);
        mFlingAnimation.setStartVelocity(pixelPerSecond)
                .setFriction(1.1f)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b,
                            float v, float v1) {

                    }
                })
                .start();
    }

    private List<Integer> getImages() {
        List<Integer> images = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                images.add(R.drawable.ghost);
            } else {
                images.add(R.drawable.ghost2);
            }
        }
        return images;
    }
}
