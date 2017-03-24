package com.inventec.newsblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.inventec.newsblog.R;
import com.inventec.newsblog.activity.MainActivity;

/**
 * app启动展示界面
 * Created by Test on 2017/2/8.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*View view = new View(this);
        view.setBackgroundResource(R.mipmap.git_star);*/
        setContentView(R.layout.activity_splash);
        RelativeLayout splashRelativeLayout = (RelativeLayout)
                findViewById(R.id.splash_relativeLayout);

        //渐变展示启动屏
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(800);
        splashRelativeLayout.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void redirectTo() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
