package com.tiagofarinha.inmezzoapp.Cache;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Services.NotificationService;

public class BackgroundResourceLoader extends AsyncTask {

    // CLASS CONSTANTS
    private static final int TOTAL_TASKS = 5;

    // CLASS INSTANCE
    private static BackgroundResourceLoader INSTANCE;

    // CONTROL VARIABLES
    private boolean active;

    private int tasks_remaining;

    private NotificationService ns;

    public BackgroundResourceLoader(NotificationService ns) {
        tasks_remaining = TOTAL_TASKS;

        this.ns = ns;

        INSTANCE = this;
    }

    public static BackgroundResourceLoader getInstance() {
        return INSTANCE;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        loadResources();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        active = true;
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

    private void notifyDataChange(String msg) {
        ns.sendNotification(msg);
    }

    private synchronized void taskOver() {
        if (!active) {
            tasks_remaining--;
            notify();
        }
    }

    /* ================ Load Warning ================ */

    private void loadResources() {
        loadPosts();
        loadConcerts();
        loadEnsaios();
        loadWarnings();
        loadChatMessages();
    }

    private void loadChatMessages() {
        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("messages");

        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskOver();

                if (active)
                    notifyDataChange("Nova mensagem do coro!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    /* ================ Load Videos ================ */

    private void loadWarnings() {
        DatabaseReference warningsRef = FirebaseDatabase.getInstance().getReference().child("warnings");

        warningsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskOver();

                if (active)
                    notifyDataChange("Nova aviso publicado!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadVotables(final String ref) {
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child(ref);

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskOver();

                if (active)
                    notifyDataChange("Novo Ensaio / Concerto Publicado!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void loadConcerts() {
        loadVotables("concerts");
    }


    private void loadEnsaios() {
        loadVotables("ensaios");
    }

    private void loadPosts() {
        DatabaseReference post_ref = FirebaseDatabase.getInstance().getReference().child("posts");

        post_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskOver();

                if (active)
                    notifyDataChange("Novo Post!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
