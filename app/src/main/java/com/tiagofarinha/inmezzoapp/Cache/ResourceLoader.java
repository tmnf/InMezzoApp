package com.tiagofarinha.inmezzoapp.Cache;

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
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.SplashScreen;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.Models.PicInfo;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.Models.YoutubeContainer;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceLoader extends Thread {

    // CLASS CONSTANTS
    private static final int TOTAL_TASKS = 7;
    private static final int MAX_POSTS = 15, MAX_WARNINGS = 20;

    // PUBLIC OBJECT LISTS
    private ArrayList<Adaptable> posts = new ArrayList<>();
    private ArrayList<Adaptable> users = new ArrayList<>();
    private ArrayList<Adaptable> portfolio = new ArrayList<>();
    private ArrayList<Adaptable> concerts = new ArrayList<>();
    private ArrayList<Adaptable> ensaios = new ArrayList<>();
    public ArrayList<PicInfo> pic_info = new ArrayList<>();
    public ArrayList<YoutubeVideo> videos = new ArrayList<>();
    private ArrayList<Adaptable> warnings = new ArrayList<>();

    // CLASS INSTANCE
    private static ResourceLoader INSTANCE;

    // CONTROL VARIABLES
    private boolean active;
    private int tasks_remaining, pics_remaining;

    public ResourceLoader() {
        tasks_remaining = TOTAL_TASKS;

        INSTANCE = this;

        loadResources();
    }

    public static ResourceLoader getInstance() {
        return INSTANCE;
    }

    /* ================ General Methods ================ */

    public YoutubeVideo findVideoWithUrl(String url) {
        YoutubeVideo aux = null;

        for (YoutubeVideo x : videos) {
            if (x.getUrl().equals(url)) {
                aux = x;
                break;
            }
        }
        return aux;
    }

    public static void deleteExciding(ArrayList<Adaptable> list, int max, String type) {
        if (list.size() > max) {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(type);
            final int toDelete = list.size() - max;

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> keys = new ArrayList<>();
                    int i = toDelete;
                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        if (i == 0)
                            break;
                        keys.add(x.getKey());
                        i--;
                    }

                    for (String x : keys) {
                        ref.child(x).removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private synchronized void taskOver() {
        if (!active) {
            tasks_remaining--;
            notify();
        }
    }

    @Override
    public synchronized void run() {
        try {
            while (tasks_remaining > 0)
                wait();

            loadPics();
        } catch (Exception e) {
            e.printStackTrace();
        }
        interrupt();
    }

    /* ================ Load Profile Pics ================ */


    private void loadPics() {
        StorageReference pic_ref = FirebaseStorage.getInstance().getReference().child("profile_images");

        pic_info.clear();
        for (Adaptable x : users) {

            final User aux = (User) x;
            final String user_pic = aux.getUser_pic();

            pics_remaining = users.size();

            StorageReference ref = pic_ref.child(user_pic);
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    addPic(uri, aux.getUser_phone());
                }
            });
        }
    }

    private void addPic(Uri uri, int num) {
        synchronized (pic_info) {
            pic_info.add(new PicInfo(uri, num));
            pics_remaining--;

            if (pics_remaining == 0) {
                active = true;
                SplashScreen.getInstance().ready();
            }
        }
    }

    /* ================ Load Warning ================ */

    private void loadResources() {
        loadUsers();
        loadPosts();
        loadConcerts();
        loadEnsaios();
        loadPortfolio();
        loadVideos();
        loadWarnings();
    }

    /* ================ Load Videos ================ */

    private void loadWarnings() {
        DatabaseReference warningsRef = FirebaseDatabase.getInstance().getReference().child("warnings");

        warningsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                warnings.clear();

                int i = 0;
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    if (i == MAX_WARNINGS)
                        break;
                    warnings.add(x.getValue(Warning.class));
                    i++;
                }

                Collections.reverse(warnings);
                deleteExciding(warnings, MAX_WARNINGS, "warnings");

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addToVideoList(String video_url, String video_id) {
        synchronized (videos) {
            Uri download_url = Uri.parse("https://img.youtube.com/vi/" + video_id + "/maxresdefault.jpg");
            videos.add(new YoutubeVideo(video_url, download_url));
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

    private void loadVideos() {
        final DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference().child("videos");

        videosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videos.clear();

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    YoutubeContainer aux = x.getValue(YoutubeContainer.class);

                    addToVideoList(aux.getUrl(), aux.getId());
                }

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /* ================ Load Portfolio ================ */

    private void loadPortfolio() {
        DatabaseReference portfolio_ref = FirebaseDatabase.getInstance().getReference().child("portfolio");

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

                deleteExciding(posts, MAX_POSTS, "posts");
                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<Adaptable> getPosts() {
        return posts;
    }

    public ArrayList<Adaptable> getUsers() {
        return users;
    }

    public ArrayList<Adaptable> getPortfolio() {
        return portfolio;
    }

    public ArrayList<Adaptable> getConcerts() {
        return concerts;
    }

    public ArrayList<Adaptable> getEnsaios() {
        return ensaios;
    }

    public ArrayList<PicInfo> getPic_info() {
        return pic_info;
    }

    public ArrayList<YoutubeVideo> getVideos() {
        return videos;
    }

    public ArrayList<Adaptable> getWarnings() {
        return warnings;
    }
}
