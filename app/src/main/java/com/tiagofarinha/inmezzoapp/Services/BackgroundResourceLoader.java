package com.tiagofarinha.inmezzoapp.Services;

import android.os.AsyncTask;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BackgroundResourceLoader extends AsyncTask {

    // CLASS INSTANCE
    private static BackgroundResourceLoader INSTANCE;

    private NotificationService ns;

    private boolean active;

    BackgroundResourceLoader(NotificationService ns) {
        this.ns = ns;

        INSTANCE = this;
    }

    public static BackgroundResourceLoader getInstance() {
        return INSTANCE;
    }

    @Override
    protected synchronized Object doInBackground(Object[] objects) {
        loadResources();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        active = true;
    }

    public boolean isActive() {
        return active;
    }

    private void loadResources() {
        loadPosts();
        loadConcerts();
        loadEnsaios();
        loadWarnings();
        loadChatMessages();
    }

    private void loadChatMessages() {
        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("messages");

        user_ref.addChildEventListener(new ChildListener("Nova Mensagem!", this));
    }

    /* ================ Load Videos ================ */

    private void loadWarnings() {
        DatabaseReference warningsRef = FirebaseDatabase.getInstance().getReference().child("warnings");

        warningsRef.addChildEventListener(new ChildListener("Novo Aviso!", this));
    }

    private void loadVotables(final String ref) {
        final DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child(ref);

        dr.addChildEventListener(new ChildListener("Novo Ensaio / Concerto!", this));
    }

    private void loadConcerts() {
        loadVotables("concerts");
    }


    private void loadEnsaios() {
        loadVotables("ensaios");
    }

    private void loadPosts() {
        DatabaseReference post_ref = FirebaseDatabase.getInstance().getReference().child("posts");

        post_ref.addChildEventListener(new ChildListener("Nova Publicação!", this));
    }

    public void sendNotification(String msg) {
        ns.sendNotification(msg);
    }
}
