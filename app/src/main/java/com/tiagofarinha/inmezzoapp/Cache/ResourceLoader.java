package com.tiagofarinha.inmezzoapp.Cache;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tiagofarinha.inmezzoapp.Fragments.ListFragments.DefaultListFragment;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Interfaces.Votable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.MainLogic.SplashScreen;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.Models.MezzoDate;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.Models.PicInfo;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.Models.Vote;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.Models.YoutubeContainer;
import com.tiagofarinha.inmezzoapp.Models.YoutubeVideo;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResourceLoader extends AsyncTask {

    // CLASS CONSTANTS
    private static final int TOTAL_TASKS = 7;
    private static final int MAX_POSTS = 15, MAX_WARNINGS = 20;

    private SplashScreen ss;

    // OBJECT LISTS
    private ArrayList<Adaptable> posts = new ArrayList<>();
    private ArrayList<Adaptable> users = new ArrayList<>();
    private ArrayList<Adaptable> portfolio = new ArrayList<>();
    private ArrayList<Adaptable> concerts = new ArrayList<>();
    private ArrayList<Adaptable> ensaios = new ArrayList<>();
    private ArrayList<PicInfo> pic_info = new ArrayList<>();

    private ArrayList<YoutubeVideo> videos = new ArrayList<>();
    private ArrayList<Adaptable> warnings = new ArrayList<>();

    // CLASS INSTANCE
    private static ResourceLoader INSTANCE;

    // CONTROL VARIABLES
    private boolean active, offline;
    private int tasks_remaining, pics_remaining;

    public ResourceLoader(SplashScreen ss) {
        tasks_remaining = TOTAL_TASKS;

        INSTANCE = this;

        this.ss = ss;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        loadResources();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        active = true;
        offline = true;
        ss.ready();
        ss = null;
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

            loadPics();

            while (pics_remaining != 0)
                wait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isOnline() {
        return !offline;
    }

    public static ResourceLoader getInstance() {
        return INSTANCE;
    }

    /* ================ General Methods ================ */

    public YoutubeVideo findVideoWithUrl(String url) {
        YoutubeVideo aux = null;

        for (YoutubeVideo x : videos)
            if (x.getUrl().equals(url)) {
                aux = x;
                break;
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

    public void deleteVotes(final Adaptable event) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("votes");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> keysToDelete = new ArrayList<>();

                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Vote aux = x.getValue(Vote.class);

                    if (event instanceof Concert) {
                        if (aux.getConcert() != null && aux.getConcert().equals(event))
                            keysToDelete.add(x.getKey());
                    } else if (aux.getEnsaio() != null && aux.getEnsaio().equals(event))
                        keysToDelete.add(x.getKey());
                }

                for (String key : keysToDelete)
                    ref.child(key).removeValue();
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
        } else {
            if (MainMethods.getInstance().getCurrentFrag() instanceof DefaultListFragment)
                ((DefaultListFragment) MainMethods.getInstance().getCurrentFrag()).refreshList();
        }
    }

    /* ================ Load Profile Pics ================ */


    private void loadPics() {
        StorageReference pic_ref = FirebaseStorage.getInstance().getReference().child("profile_images");

        pic_info.clear();
        StorageReference ref;
        for (Adaptable x : users) {
            final User aux = (User) x;
            final String user_pic = aux.getUser_pic();

            pics_remaining = users.size();

            ref = pic_ref.child(user_pic);
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    addPic(uri, aux.getUser_phone());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    PicFailed();
                }
            });
        }
    }

    private synchronized void addPic(Uri uri, int num) {
        pic_info.add(new PicInfo(uri, num));
        pics_remaining--;
        notify();
    }

    private synchronized void PicFailed() {
        pics_remaining--;
        notify();
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

                Collections.sort(users, new Comparator<Adaptable>() {
                    @Override
                    public int compare(Adaptable o1, Adaptable o2) {
                        User aux1, aux2;
                        aux1 = (User) o1;
                        aux2 = (User) o2;

                        String[] name1 = aux1.getUser_name().split(" ");
                        String[] name2 = aux2.getUser_name().split(" ");

                        int checkFirst = name1[0].compareTo(name2[0]);
                        if (checkFirst == 0)
                            return name1[1].compareTo(name2[1]);

                        return checkFirst;
                    }
                });

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

    private void loadVotables(final String ref, final ArrayList<Adaptable> list) {
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child(ref);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                ArrayList<String> keysToDelete = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    Votable aux = null;

                    if (ref.equals("concerts"))
                        aux = x.getValue(Concert.class);
                    else if (ref.equals("ensaios"))
                        aux = x.getValue(Ensaio.class);

                    String[] date = aux.getDate().split(",");

                    if (DateUtils.hasPassed(date[0])) {
                        keysToDelete.add(x.getKey());
                        deleteVotes(aux);
                    } else
                        list.add(aux);
                }

                for (String key : keysToDelete)
                    dr.child(key).removeValue();

                orderList(list);

                taskOver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void orderList(ArrayList<Adaptable> list) {
        Collections.sort(list, new Comparator<Adaptable>() {
            @Override
            public int compare(Adaptable o1, Adaptable o2) {
                Votable v1, v2;
                v1 = (Votable) o1;
                v2 = (Votable) o2;
                MezzoDate date1 = DateUtils.parseToDate(v1.getDate().split(",")[0]);
                MezzoDate date2 = DateUtils.parseToDate(v2.getDate().split(",")[0]);

                if (date1.getYear() > date2.getYear())
                    return 1;
                if (date1.getYear() < date2.getYear())
                    return -1;
                if (date1.getMonth() > date2.getMonth())
                    return 1;
                if (date1.getMonth() < date2.getMonth())
                    return -1;
                if (date1.getDay() > date2.getDay())
                    return 1;
                if (date1.getDay() < date2.getDay())
                    return -1;

                return 0;
            }
        });
    }

    private void loadConcerts() {
        loadVotables("concerts", concerts);
    }

    /* ================ Load Ensaios ================ */

    private void loadEnsaios() {
        loadVotables("ensaios", ensaios);
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

    public ArrayList<Adaptable> getWarnings() {
        return warnings;
    }
}
