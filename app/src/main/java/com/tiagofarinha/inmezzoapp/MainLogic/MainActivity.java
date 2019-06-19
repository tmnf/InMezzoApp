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
import com.tiagofarinha.inmezzoapp.Cache.FragManager;
import com.tiagofarinha.inmezzoapp.Fragments.AddConcert;
import com.tiagofarinha.inmezzoapp.Fragments.AddEnsaio;
import com.tiagofarinha.inmezzoapp.Fragments.AddMusic;
import com.tiagofarinha.inmezzoapp.Fragments.AddPost;
import com.tiagofarinha.inmezzoapp.Fragments.AddWarning;
import com.tiagofarinha.inmezzoapp.Fragments.ConcertsLogic;
import com.tiagofarinha.inmezzoapp.Fragments.EnsaiosLogic;
import com.tiagofarinha.inmezzoapp.Fragments.FeedLogic;
import com.tiagofarinha.inmezzoapp.Fragments.PortfolioLogic;
import com.tiagofarinha.inmezzoapp.Fragments.ProfileLogic;
import com.tiagofarinha.inmezzoapp.Fragments.WarningLogic;
import com.tiagofarinha.inmezzoapp.Models.User;
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

    private Button post_button;
    private Fragment currentFrag;

    private User auxUser;

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

        INSTANCE = this;

        /* Init Components */
        initComps();

        /* Starts Initial Fragment */
        if (savedInstanceState == null)
            startFrag();

        LoginUtils.getInstance().updateGuiAccording(mAuth, navigationView);
    }

    private void startFrag() {
        MenuUtils.filterMenuItem(R.id.menu_inicio);
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
                    changeFrag(new AddPost(), R.id.menu_inicio, true);
                else if (currentFrag instanceof PortfolioLogic)
                    changeFrag(new AddMusic(), R.id.menu_portfolio, true);
                else if (currentFrag instanceof ConcertsLogic)
                    changeFrag(new AddConcert(), R.id.menu_concertos, true);
                else if (currentFrag instanceof EnsaiosLogic)
                    changeFrag(new AddEnsaio(), R.id.menu_ensaios, true);
                else if (currentFrag instanceof WarningLogic)
                    changeFrag(new AddWarning(), R.id.menu_warnings, true);
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
            MenuUtils.filterMenuItem(R.id.menu_inicio);
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

    public void goToProfilePage(User user) {
        Fragment fragment = new ProfileLogic();

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        fragment.setArguments(bundle);

        fragment.setArguments(bundle);
        changeFrag(fragment, R.id.menu_perfil, true);
    }

    public void changeFrag(Fragment frag, int id, boolean found) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag);

        if (!found)
            FragManager.getInstance().addToFragList(frag, "Frag:" + id);

        trans.commit();

        currentFrag = frag;

        navigationView.setCheckedItem(id);
        closeKeyboard();

        if (checkSuperPermissions())
            post_button.setVisibility(View.VISIBLE);
        else
            post_button.setVisibility(View.INVISIBLE);
    }

    public boolean checkSuperPermissions() {
        Fragment f = currentFrag;

        if (auxUser == null) // Normal User
            return false;

        if ((f instanceof ConcertsLogic || f instanceof EnsaiosLogic || f instanceof PortfolioLogic || f instanceof WarningLogic) // Coord or Admin User
                && (auxUser.getUser_mode() == User.COORD || auxUser.getUser_mode() == User.ADMIN))
            return true;

        return f instanceof FeedLogic; // Normal Member

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

        LoginUtils.getInstance().updateGuiAccording(mAuth, navigationView);
    }

    private void logOut() {
        if (LoginUtils.logOutUser(mAuth)) {
            Utils.showMessage("Sessão Terminada");
            auxUser = null;
        } else
            Utils.showMessage("Erro ao tentar terminar sessão!");
    }

    private void logIn() {
        closeKeyboard();
        Utils.showMessage("Sessão Iniciada");
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

    public void setAuxUser(User user) {
        this.auxUser = user;
    }

    public User getAuxUser() {
        return auxUser;
    }

    public boolean isLoggedIn() {
        return auxUser != null;
    }
}