package com.tiagofarinha.inmezzoapp.MainLogic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tiagofarinha.inmezzoapp.Fragments.AddConcert;
import com.tiagofarinha.inmezzoapp.Fragments.AddEnsaio;
import com.tiagofarinha.inmezzoapp.Fragments.AddMusic;
import com.tiagofarinha.inmezzoapp.Fragments.AddWarning;
import com.tiagofarinha.inmezzoapp.Fragments.ConfigLogic;
import com.tiagofarinha.inmezzoapp.Fragments.FeedLogic;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MainMethods mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mm = new MainMethods(this);
    }

    @Override
    public void onBackPressed() {
        if (mm.getDrawer().isDrawerOpen(GravityCompat.START)) {
            mm.getDrawer().closeDrawer(GravityCompat.START);
        } else if (mm.getCurrentFrag() instanceof AddMusic) {
            MenuUtils.filterMenuItem(R.id.menu_portfolio);
        } else if (mm.getCurrentFrag() instanceof AddConcert) {
            MenuUtils.filterMenuItem(R.id.menu_concertos);
        } else if (mm.getCurrentFrag() instanceof ConfigLogic) {
            MenuUtils.filterMenuItem(R.id.menu_perfil);
        } else if (mm.getCurrentFrag() instanceof AddWarning) {
            MenuUtils.filterMenuItem(R.id.menu_warnings);
        } else if (mm.getCurrentFrag() instanceof AddEnsaio) {
            MenuUtils.filterMenuItem(R.id.menu_ensaios);
        } else if (!(mm.getCurrentFrag() instanceof FeedLogic)) {
            MenuUtils.filterMenuItem(R.id.menu_inicio);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MenuUtils.filterMenuItem(item.getItemId());
        mm.getDrawer().closeDrawer(GravityCompat.START);
        return true;
    }
}