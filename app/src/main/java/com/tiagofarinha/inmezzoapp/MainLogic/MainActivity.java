package com.tiagofarinha.inmezzoapp.MainLogic;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tiagofarinha.inmezzoapp.Fragments.AddConcert;
import com.tiagofarinha.inmezzoapp.Fragments.AddEnsaio;
import com.tiagofarinha.inmezzoapp.Fragments.AddMusic;
import com.tiagofarinha.inmezzoapp.Fragments.ConcertsLogic;
import com.tiagofarinha.inmezzoapp.Fragments.EnsaiosLogic;
import com.tiagofarinha.inmezzoapp.Fragments.FeedLogic;
import com.tiagofarinha.inmezzoapp.Fragments.PortfolioLogic;
import com.tiagofarinha.inmezzoapp.Fragments.PostLogic;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int MODE_LOGIN = 1, MODE_LOGOUT = 0;

    private static MainActivity INSTANCE;

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Button post_button;
    private Fragment currentFrag;

    public static MainActivity getInstance() {
        return INSTANCE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Get Drawer */
        drawer = findViewById(R.id.drawer_layout);

        /* Get Firebase */
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        INSTANCE = this;

        /* Init Components */
        initComps();

        /* Starts Initial Fragment */
        if (savedInstanceState == null)
            startFrag();

        LoginUtils.updateGuiAccording(mAuth, currentUser, navigationView);
    }

    private void startFrag() {
        goToMainPage();
    }

    private void initComps() {
        Button menu_button = findViewById(R.id.menu_button);
        post_button = findViewById(R.id.post_button);

        // START FRAG MANAGER
        new FragManager();

        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                drawer.openDrawer(Gravity.START);
            }
        });

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFrag instanceof FeedLogic)
                    changeFrag(new PostLogic(), R.id.menu_inicio, true);
                else if (currentFrag instanceof PortfolioLogic)
                    changeFrag(new AddMusic(), R.id.menu_portfolio, true);
                else if (currentFrag instanceof ConcertsLogic)
                    changeFrag(new AddConcert(), R.id.menu_concertos, true);
                else if (currentFrag instanceof EnsaiosLogic)
                    changeFrag(new AddEnsaio(), R.id.menu_ensaios, true);
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!(currentFrag instanceof FeedLogic)) {
            goToMainPage();
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        MenuUtils.filterMenuItem(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToMainPage() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("Frag:" + R.id.menu_inicio);

        if (fragment == null) {
            changeFrag(new FeedLogic(), R.id.menu_inicio, false);
            return;
        }
        changeFrag(fragment, R.id.menu_inicio, true);
    }

    public void changeFrag(Fragment frag, int id, boolean found) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag);

        if (!found)
            FragManager.getInstance().addToFragList(frag, "Frag:" + id);

        trans.commit();

        currentFrag = frag;

        navigationView.setCheckedItem(id);
        closeKeyboard();

        if (!(frag instanceof FeedLogic || frag instanceof ConcertsLogic || frag instanceof EnsaiosLogic || frag instanceof PortfolioLogic))
            post_button.setVisibility(View.INVISIBLE);
        else post_button.setVisibility(View.VISIBLE);
    }

    public void handleLog(int mode) {

        switch (mode) {
            case MODE_LOGOUT:
                logOut();
                break;
            case MODE_LOGIN:
                logIn();
                break;
            default:
                break;
        }

        LoginUtils.updateGuiAccording(mAuth, currentUser, navigationView);
        goToMainPage();
    }

    private void logOut() {
        if (LoginUtils.logOutUser(mAuth))
            Utils.showMessage(this, "Sessão Terminada");
        else
            Utils.showMessage(this, "Erro ao tentar terminar sessão!");
    }

    private void logIn() {
        closeKeyboard();
        Utils.showMessage(this, "Sessão Iniciada");
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public Button getPost_button() {
        return post_button;
    }

}