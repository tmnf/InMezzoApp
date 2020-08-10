package com.tiagofarinha.inmezzoapp.Utils;

import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Fragments.LoginLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.PicInfo;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.UserClasses.LoginHandler;

public class LoginUtils {

    private static LoginUtils INSTANCE;

    private MenuItem login, perfil, logout, ensaios, warnings, chat, top;
    private Menu menu;
    private View header;

    private TextView user_name;
    private ImageView pic;

    public LoginUtils() {
    }

    public static LoginUtils getInstance() {
        if (INSTANCE == null)
            INSTANCE = new LoginUtils();
        return INSTANCE;
    }

    public static void logInUser(String email, String password, LoginLogic ll) {
        new LoginHandler(email, password, ll).start();
    }

    public static boolean logOutUser(FirebaseAuth auth) {
        try {
            auth.signOut();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void updateGuiAccording(FirebaseAuth mAuth, NavigationView navigationView) {
        if (login == null) {
            menu = navigationView.getMenu();
            header = navigationView.getHeaderView(0);

            login = menu.findItem(R.id.menu_login);
            perfil = menu.findItem(R.id.menu_perfil);
            logout = menu.findItem(R.id.menu_logout);
            ensaios = menu.findItem(R.id.menu_ensaios);
            warnings = menu.findItem(R.id.menu_warnings);
            chat = menu.findItem(R.id.menu_chat);
            top = menu.findItem(R.id.menu_top);

            user_name = header.findViewById(R.id.menu_name);
            pic = header.findViewById(R.id.menu_pic);

        }

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            login.setVisible(true);
            perfil.setVisible(false);
            logout.setVisible(false);
            ensaios.setVisible(false);
            warnings.setVisible(false);
            chat.setVisible(false);
            top.setVisible(false);

            user_name.setText("Menu");
            pic.setVisibility(View.INVISIBLE);
            MenuUtils.filterMenuItem(R.id.menu_inicio);
        } else {
            login.setVisible(false);
            perfil.setVisible(true);
            logout.setVisible(true);
            ensaios.setVisible(true);
            warnings.setVisible(true);
            chat.setVisible(true);
            top.setVisible(true);

            user_name.setText(currentUser.getDisplayName());
            pic.setVisibility(View.VISIBLE);

            getPicAndTools(currentUser, pic);
        }
    }

    private void getPicAndTools(FirebaseUser currentUser, final ImageView pic) {
        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                putInto(pic, user);

                MainMethods.getInstance().setAuxUser(user);
                MenuUtils.filterMenuItem(R.id.menu_inicio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void putInto(final ImageView view, User user) {
        for (PicInfo x : ResourceLoader.getInstance().getPic_info())
            if (x.getNum() == user.getUser_phone()) {
                fillView(view, x.getUri());
                return;
            }
        view.setImageResource(R.drawable.empty_photo);
    }

    public static void fillView(ImageView view, Uri uri) {
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load(uri).noFade().networkPolicy(NetworkPolicy.OFFLINE).into(view);
    }
}
