package com.tiagofarinha.inmezzoapp.Cache;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.SplashScreen;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.Models.Vote;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.Models.YoutubeContainer;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;

public class ResourceLoader extends AsyncTask {

    // CLASS CONSTANTS
    private static final int TOTAL_TASKS = 7;
    private static final int MAX_POSTS = 15, MAX_WARNINGS = 20;

    private SplashScreen ss;

    // PUBLIC OBJECT LISTS
    private ArrayList<Adaptable> posts = new ArrayList<>();
    private ArrayList<Adaptable> users = new ArrayList<>();
    private ArrayList<Adaptable> portfolio = new ArrayList<>();
    private ArrayList<Adaptable> concerts = new ArrayList<>();
    private ArrayList<Adaptable> ensaios = new ArrayList<>();
    public ArrayList<YoutubeVideo> videos = new ArrayList<>();
    private ArrayList<Adaptable> warnings = new ArrayList<>();

    // CLASS INSTANCE
    private static ResourceLoader INSTANCE;

    // CONTROL VARIABLES
    private boolean active;
    private int tasks_remaining;

    public ResourceLoader(SplashScreen ss) {
        tasks_remaining = TOTAL_TASKS;

        INSTANCE = this;

        this.ss = ss;

        loadResources();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        posts.clear();
        users.clear();
        portfolio.clear();
        concerts.clear();
        ensaios.clear();
        videos.clear();
        warnings.clear();
        active = false;
        ss.ready();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        active = true;
        ss.ready();
        ss = null;
    }

    @Override
    protected synchronized Object doInBackground(Object[] objects) {
        try {
            while (tasks_remaining > 0)
                wait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    private void deleteExciding(ArrayList<Adaptable> list, int max, String type) {
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

    private void deleteVotes(final Adaptable event) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("votes");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> keysToDelete = new ArrayList<>();

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Vote aux = x.getValue(Vote.class);

                    if (aux.getConcert() != null && aux.getConcert().equals(event))
                        keysToDelete.add(x.getKey());
                    else if (aux.getEnsaio() != null && aux.getEnsaio().equals(event))
                        keysToDelete.add(x.getKey());

                    for (String key : keysToDelete)
                        ref.child(key).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private synchronized void taskOver() {
        if (!active) {
            tasks_remaining--;
            notify();
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

    private void addToVideoList(String video_url, Post post, String video_id) {
        synchronized (videos) {
            Uri download_url = Uri.parse("https://img.youtube.com/vi/" + video_id + "/maxresdefault.jpg");
            videos.add(new YoutubeVideo(video_url, post, download_url));
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

                    addToVideoList(aux.getUrl(), aux.getPost(), aux.getId());
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
        final DatabaseReference concerts_ref = FirebaseDatabase.getInstance().getReference().child("concerts");

        concerts_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                concerts.clear();
                ArrayList<String> keysToDelete = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Concert aux = x.getValue(Concert.class);
                    String[] date = aux.getDate().split(",");

                    if (DateUtils.hasPassed(date[0])) {
                        keysToDelete.add(x.getKey());
                        deleteVotes(aux);
                    } else
                        concerts.add(x.getValue(Concert.class));
                }

                for (String key : keysToDelete)
                    concerts_ref.child(key).removeValue();

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /* ================ Load Ensaios ================ */

    private void loadEnsaios() {
        final DatabaseReference ensaios_ref = FirebaseDatabase.getInstance().getReference().child("ensaios");

        ensaios_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ensaios.clear();
                ArrayList<String> keysToDelete = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Ensaio aux = x.getValue(Ensaio.class);
                    String[] date = aux.getDate().split(",");

                    if (DateUtils.hasPassed(date[0])) {
                        keysToDelete.add(x.getKey());
                        deleteVotes(aux);
                    } else
                        ensaios.add(x.getValue(Ensaio.class));

                    for (String key : keysToDelete)
                        ensaios_ref.child(key).removeValue();
                }

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

    public ArrayList<Adaptable> getWarnings() {
        return warnings;
    }
}
