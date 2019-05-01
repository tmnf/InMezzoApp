package com.tiagofarinha.inmezzoapp.MainLogic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.tiagofarinha.inmezzoapp.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ProgressBar pb = findViewById(R.id.splash_progress);
        pb.setVisibility(View.VISIBLE);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent it = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(it);
               pb.setVisibility(View.GONE);
               finish();
           }
       }, 1500);

    }
}
