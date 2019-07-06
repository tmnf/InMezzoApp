package com.tiagofarinha.inmezzoapp.MainLogic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class SplashScreen extends AppCompatActivity {

    private static final int TIMEOUT = 20000;

    public void ready() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ProgressBar pb = findViewById(R.id.splash_progress);
        pb.setVisibility(View.VISIBLE);

        final ResourceLoader rl = new ResourceLoader(this);
        rl.execute();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl.cancel(true);
            }
        }, TIMEOUT);
    }
}
