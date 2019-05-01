package com.tiagofarinha.inmezzoapp.MainLogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pb = findViewById(R.id.splash_progress);
        pb.setVisibility(View.VISIBLE);

        new ResourceLoader(this).start();
    }

    public void ready(){
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        pb.setVisibility(View.GONE);
        finish();
    }
}
