package com.tiagofarinha.inmezzoapp.MainLogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.tiagofarinha.inmezzoapp.Cache.FragHistory;
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

public class MainMethods {

    public static final int MODE_LOGIN = 1, MODE_LOGOUT = 0;

    private static MainMethods INSTANCE;

    private MainActivity mainActivity;

    private NavigationView navigationView;

    private Button post_button;

    private Fragment currentFrag;
    private int currentFragId;

    private User auxUser;

    private DrawerLayout drawer;

    private FirebaseAuth mAuth;


    public MainMethods(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        INSTANCE = this;

        /* Get Drawer */
        drawer = mainActivity.findViewById(R.id.drawer_layout);

        /* Get Firebase */
        mAuth = FirebaseAuth.getInstance();

        /* Init Components */
        initComps();

        /* Starts Initial Fragment */
        MenuUtils.filterMenuItem(R.id.menu_inicio);

        updateGui();
    }

    private void updateGui() {
        LoginUtils.getInstance().updateGuiAccording(mAuth, navigationView);
    }

    public static MainMethods getInstance() {
        return INSTANCE;
    }

    private void initComps() {
        Button menu_button = mainActivity.findViewById(R.id.menu_button);
        post_button = mainActivity.findViewById(R.id.post_button);

        // START FRAG MANAGER
        FragManager.start();

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

        navigationView = mainActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mainActivity);
    }

    public void goToProfilePage(User user) {
        Fragment fragment = new ProfileLogic();

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        fragment.setArguments(bundle);

        changeFrag(fragment, R.id.menu_perfil, true);
    }

    public void changeFrag(Fragment frag, int id, boolean found) {
        FragmentTransaction trans = mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag);

        if (currentFragId != 0)
            if (mainActivity.getBackPressed())
                mainActivity.setBackPressed(false);
            else
                FragHistory.getInstance().addToHistory(currentFragId);

        if (!found)
            FragManager.getInstance().addToFragList(frag, "Frag:" + id);

        trans.commit();

        currentFrag = frag;
        currentFragId = id;

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

    public void startActivity(Intent intent) {
        mainActivity.startActivity(intent);
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
        View view = mainActivity.getCurrentFocus();

        if (view != null) {
            InputMethodManager inm = (InputMethodManager) mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public User getAuxUser() {
        return auxUser;
    }

    public void setAuxUser(User user) {
        this.auxUser = user;
    }

    public MainActivity getContext() {
        return mainActivity;
    }

    public Fragment getCurrentFrag() {
        return currentFrag;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public boolean isOp() {
        return (auxUser.getUser_mode() == User.COORD || auxUser.getUser_mode() == User.ADMIN);
    }

    public boolean isLoggedIn() {
        return auxUser != null;
    }
}
