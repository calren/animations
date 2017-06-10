package com.example.caren.animations;

import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class FlingAnimationActivity extends AppCompatActivity {
    SeekBar startVelocitySeekbar;
    SeekBar frictionSeekbar;
    TextView startVText;
    TextView frictionText;
    FlingAnimation mFlingAnimation;

    View ghostView;
    float viewStartXPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fling_animation);

        ghostView = findViewById(R.id.ghost);
        viewStartXPos = ghostView.getTranslationX();
        mFlingAnimation = new FlingAnimation(ghostView, DynamicAnimation.TRANSLATION_X);
        // moves the view on the x-axis
        findViewById(R.id.animateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFlingAnimation();
            }
        });

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
                                        startVelocitySeekbar.getProgress() * 50));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        frictionSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                frictionText.setText(
                        "friction value: " +
                                (frictionSeekbar.getProgress() == 0 ? 0.01f :
                                        frictionSeekbar.getProgress() / 100f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void startFlingAnimation() {
        // usually between 200 to 20,000, negative if going right to left
        float startVelocity = startVelocitySeekbar.getProgress() == 0 ? 1 :
                startVelocitySeekbar.getProgress() * 50;

        // derive the velocity in pixels per second
        float pixelPerSecond = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                startVelocity, getResources().getDisplayMetrics());

        float friction =
                frictionSeekbar.getProgress() == 0 ? 0.01f : frictionSeekbar.getProgress() / 100f;

        mFlingAnimation.setStartVelocity(pixelPerSecond)
                .setFriction(friction)
                .addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b,
                            float v, float v1) {
                        // reset view position
                        ghostView.setTranslationX(viewStartXPos);
                    }
                })
                .start();
    }
}
