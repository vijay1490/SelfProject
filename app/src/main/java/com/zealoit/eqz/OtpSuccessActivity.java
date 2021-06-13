package com.zealoit.eqz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class OtpSuccessActivity extends AppCompatActivity {

    ImageView imageIcon;
    // Animation
    Animation animMoveToTop;
    private Thread mSplashThread;

    String details  ;

    ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_success);
        imageIcon = (ImageView) findViewById(R.id.icon);
        imageView = (ImageView) findViewById(R.id.imganims);
        String mobiles = getIntent().getStringExtra("details");
        details = mobiles;
        Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_down);
        imageIcon.startAnimation(bottomUp);
        imageIcon.setVisibility(View.VISIBLE);

        imageView.setVisibility(View.VISIBLE);
        Glide.with(OtpSuccessActivity.this).load(R.raw.blue_tick).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof GifDrawable) {
                    ((GifDrawable) resource).setLoopCount(1);
                }
                return false;
            }
        }).into(imageView);

        //   imageview.setAnimation(animationDownToUp);
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(4000);
                    }
                } catch (InterruptedException ex) {
                }
                finish();

                Intent intent = new Intent();
                intent.setClass(OtpSuccessActivity.this, OtpActivity.class).putExtra("REG","REG").putExtra("details",details);
                startActivity(intent);
            }
        };

        mSplashThread.start();
    }
}
