package com.tiagofarinha.inmezzoapp.Cache;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tiagofarinha.inmezzoapp.MainLogic.SplashScreen;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.User;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceLoader extends Thread {

    private static final int TOTAL_TASKS = 2;

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference post_ref;
    public static DatabaseReference user_ref;
    public static StorageReference pic_ref;

    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<PicContainer> user_pics = new ArrayList<>();

    private SplashScreen splash;
    private boolean active;

    private int tasks_remaining, pics_remaining;

    public ResourceLoader(SplashScreen splash) {
        tasks_remaining = TOTAL_TASKS;
        this.splash = splash;

        loadResources();
    }

    @Override
    public synchronized void run() {
        try {
            while (tasks_remaining > 0)
                wait();

            pics_remaining = users.size();
            loadPics();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadResources() {
        loadUsers();
        loadPosts();
    }

    private void loadPics() {
        pic_ref = FirebaseStorage.getInstance().getReference().child("profile_images");

        user_pics.clear();
        for (User x : users) {
            final User us = x;
            StorageReference ref = pic_ref.child(us.getUser_pic());
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    new PicDownloader(ResourceLoader.this, us.getUser_pic(), uri).start();
                }
            });
        }
    }

    public void addToPicList(String num, Bitmap pic) {
        synchronized (user_pics) {
            user_pics.add(new PicContainer(num, pic));
        }

        pics_remaining--;

        if (pics_remaining == 0) {
            active = true;
            splash.ready();
        }
    }

    private void loadPosts() {
        post_ref = database.getReference().child("posts");

        post_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot x : dataSnapshot.getChildren())
                    posts.add(x.getValue(Post.class));

                Collections.reverse(posts);

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUsers() {
        user_ref = database.getReference().child("users");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot x : dataSnapshot.getChildren())
                    users.add(x.getValue(User.class));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public synchronized void taskOver() {
        if (!active) {
            tasks_remaining--;
            notify();
        }
    }

}
