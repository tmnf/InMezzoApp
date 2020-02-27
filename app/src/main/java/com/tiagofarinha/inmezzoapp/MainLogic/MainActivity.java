package com.tiagofarinha.inmezzoapp.MainLogic;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.tiagofarinha.inmezzoapp.Cache.FragHistory;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainMethods mm;
    private boolean backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mm = new MainMethods(this);
    }

    @Override
    public void onBackPressed() {
        backPressed = true;

        if (mm.getDrawer().isDrawerOpen(GravityCompat.START))
            mm.getDrawer().closeDrawer(GravityCompat.START);
        else if (!FragHistory.getInstance().isEmpty())
            MenuUtils.filterMenuItem(FragHistory.getInstance().getLastFrag());
        else
            moveTaskToBack(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MenuUtils.filterMenuItem(item.getItemId());
        mm.getDrawer().closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean getBackPressed() {
        return backPressed;
    }

    public void setBackPressed(boolean value) {
        backPressed = value;
    }
}