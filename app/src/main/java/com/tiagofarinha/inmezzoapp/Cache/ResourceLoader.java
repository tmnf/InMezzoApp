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
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.ProfilePic;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.Models.YoutubeContainer;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceLoader extends Thread {

    private static final int TOTAL_TASKS = 5;
    private static final int MAX_POSTS = 15;

    public static ResourceLoader INSTANCE;

    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<ProfilePic> user_pics = new ArrayList<>();
    public static ArrayList<YoutubeVideo> videos = new ArrayList<>();

    public static ArrayList<Music> portfolio = new ArrayList<>();
    public static ArrayList<Concert> concerts = new ArrayList<>();
    public static ArrayList<Ensaio> ensaios = new ArrayList<>();

    private boolean active;

    private int tasks_remaining, pics_remaining, videos_remaining;

    public ResourceLoader() {
        tasks_remaining = TOTAL_TASKS;

        INSTANCE = this;

        loadResources();
    }

    public static ResourceLoader getInstance() {
        return INSTANCE;
    }

    /* ================ General Methods ================ */

    public static YoutubeVideo findVideoWithUrl(String url) {
        YoutubeVideo aux = null;

        for (YoutubeVideo x : videos)
            if (x.getUrl().equals(url)) {
                aux = x;
                break;
            }

        return aux;
    }

    public void loadResources() {
        loadUsers();
        loadPosts();
        loadConcerts();
        loadEnsaios();
        loadPortfolio();
        loadVideos();
    }

    public synchronized void taskOver() {
        if (!active) {
            tasks_remaining--;
            notify();
        }
    }

    /* ================ Load Profile Pics ================ */

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

    private void loadPics() {
        StorageReference pic_ref = FirebaseStorage.getInstance().getReference().child("profile_images");

        user_pics.clear();
        for (User x : users) {

            final String user_pic = x.getUser_pic();

            StorageReference ref = pic_ref.child(user_pic);
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    new PicDownloader(ResourceLoader.this, user_pic, uri).start();
                }
            });
        }
    }

    /* ================ Load Videos ================ */

    public void addToPicList(String num, Bitmap pic) {
        synchronized (user_pics) {
            user_pics.add(new ProfilePic(num, pic));

            pics_remaining--;

            if (pics_remaining == 0) {
                active = true;
                SplashScreen.getInstance().ready();
            }
        }
    }

    public void loadVideos() {
        final DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference().child("videos");

        videosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videos.clear();

                ArrayList<VideoDownloader> videoTasks = new ArrayList<>();

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    YoutubeContainer aux = x.getValue(YoutubeContainer.class);

                    videoTasks.add(new VideoDownloader(ResourceLoader.this, aux.getUrl(), aux.getId()));
                }

                videos_remaining = videoTasks.size();

                for (VideoDownloader x : videoTasks)
                    x.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addToVideoList(YoutubeVideo video) {
        synchronized (videos) {
            videos.add(video);
        }
    }

    /* ================ Load Users ================ */

    private void loadUsers() {
        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("users");

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

    /* ================ Load Posts ================ */

    private void loadPosts() {
        DatabaseReference post_ref = FirebaseDatabase.getInstance().getReference().child("posts");

        post_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();

                int i = 0;
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    if (i > MAX_POSTS)
                        break;

                    posts.add(x.getValue(Post.class));
                    i++;
                }

                Collections.reverse(posts);

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /* ================ Load Portfolio ================ */

    private void loadPortfolio() {
        DatabaseReference portfolio_ref = FirebaseDatabase.getInstance().getReference().child("porfolio");

        portfolio_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                portfolio.clear();
                for (DataSnapshot x : dataSnapshot.getChildren())
                    portfolio.add(x.getValue(Music.class));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /* ================ Load Concerts ================ */

    private void loadConcerts() {
        DatabaseReference concerts_ref = FirebaseDatabase.getInstance().getReference().child("concerts");

        concerts_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                concerts.clear();
                for (DataSnapshot x : dataSnapshot.getChildren())
                    concerts.add(x.getValue(Concert.class));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /* ================ Load Ensaios ================ */

    private void loadEnsaios() {
        DatabaseReference ensaios_ref = FirebaseDatabase.getInstance().getReference().child("ensaios");

        ensaios_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ensaios.clear();
                for (DataSnapshot x : dataSnapshot.getChildren())
                    ensaios.add(x.getValue(Ensaio.class));

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
