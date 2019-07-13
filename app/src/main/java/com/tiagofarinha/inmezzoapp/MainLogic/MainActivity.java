package com.tiagofarinha.inmezzoapp.MainLogic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
        if (mm.getDrawer().isDrawerOpen(GravityCompat.START))
            mm.getDrawer().closeDrawer(GravityCompat.START);
        else if (!FragHistory.getInstance().isEmpty())
            MenuUtils.filterMenuItem(FragHistory.getInstance().getLastFrag());
        else
            super.onBackPressed();

        backPressed = true;
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