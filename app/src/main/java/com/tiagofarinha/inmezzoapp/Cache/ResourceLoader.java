package com.tiagofarinha.inmezzoapp.Cache;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Adapter.Post;
import com.tiagofarinha.inmezzoapp.AdminTools.User;
import com.tiagofarinha.inmezzoapp.MainLogic.SplashScreen;

import java.util.ArrayList;

public class ResourceLoader extends Thread{

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference post_ref;
    public static DatabaseReference user_ref;

    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

    private SplashScreen splash;

    private static final int TASKS = 2;
    private int loadMethods;

    public ResourceLoader(SplashScreen splash){
        loadMethods = TASKS;
        this.splash = splash;

        loadResources();
    }

    @Override
    public synchronized void run(){
        try {
            while (loadMethods > 0)
                wait();

            splash.ready();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadResources(){
        loadUsers();
        loadPosts();
    }

    private void loadPosts(){
        post_ref = database.getReference().child("posts");

        post_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Post> aux = new ArrayList<>();
                for(DataSnapshot x : dataSnapshot.getChildren())
                    aux.add(x.getValue(Post.class));

                for(int i = aux.size() - 1; i >= 0; i--)
                    posts.add(aux.get(i));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUsers(){
        user_ref = database.getReference().child("users");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot x : dataSnapshot.getChildren())
                    users.add(x.getValue(User.class));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public synchronized void taskOver(){
        loadMethods--;
        notify();
    }

}
