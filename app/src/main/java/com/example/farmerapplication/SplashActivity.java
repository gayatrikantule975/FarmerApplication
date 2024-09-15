package com.example.farmerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
ImageView ivMianLogo;
TextView tvMainTitle;
Animation translateanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       ivMianLogo=findViewById(R.id.ivSplashLogo);
       tvMainTitle=findViewById(R.id.tvMainTitle);
       translateanim= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.translation);
       ivMianLogo.startAnimation(translateanim);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
            }
        },3000);
    }
}