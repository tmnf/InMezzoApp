package com.tiagofarinha.inmezzoapp.Utils;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Fragments.LoginLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.Models.ProfilePic;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.UserClasses.LoginHandler;

public class LoginUtils {

    public static void logInUser(String email, String password, LoginLogic ll){
        new LoginHandler(email,password, ll).start();
    }

    public static boolean logOutUser(FirebaseAuth auth){
        try{
            auth.signOut();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static void updateGuiAccording(FirebaseAuth mAuth, FirebaseUser currentUser, NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        View header = navigationView.getHeaderView(0);

        currentUser = mAuth.getCurrentUser();

        MenuItem login = menu.findItem(R.id.menu_login);
        MenuItem perfil = menu.findItem(R.id.menu_perfil);
        MenuItem logout = menu.findItem(R.id.menu_logout);
        MenuItem admin = menu.findItem(R.id.menu_admin);
        MenuItem ensaios = menu.findItem(R.id.menu_ensaios);

        TextView user_name = header.findViewById(R.id.menu_name);
        ImageView pic = header.findViewById(R.id.menu_pic);

        Button post_button = MainActivity.getInstance().getPost_button();

        if(currentUser == null){
            login.setVisible(true);
            perfil.setVisible(false);
            logout.setVisible(false);
            admin.setVisible(false);
            ensaios.setVisible(false);

            user_name.setText("Menu");
            pic.setVisibility(View.INVISIBLE);
            post_button.setVisibility(View.INVISIBLE); // BUG AQUI

        } else {
            login.setVisible(false);
            perfil.setVisible(true);
            logout.setVisible(true);
            ensaios.setVisible(true);

            user_name.setText(currentUser.getDisplayName());
            pic.setVisibility(View.VISIBLE);
            post_button.setVisibility(View.VISIBLE);

            getPicAndTools(currentUser, admin, pic);
        }
    }

    private static void getPicAndTools(FirebaseUser currentUser, final MenuItem admin, final ImageView pic) {
        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                putInto(pic, user);

                if(user.getUser_mode() == User.ADMIN)
                    admin.setVisible(true);
                else admin.setVisible(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void putInto(final ImageView view, User user){
        for (ProfilePic x : ResourceLoader.user_pics)
            if(x.getNumber().equals(user.getUser_pic())){
                view.setImageBitmap(x.getPic());
            }
    }

}
