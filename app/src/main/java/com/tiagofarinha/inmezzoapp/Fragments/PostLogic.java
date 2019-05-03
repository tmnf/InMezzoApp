package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class PostLogic extends Fragment {

    private EditText post_text, url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_fragment, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {

        post_text = view.findViewById(R.id.post_text);
        url = view.findViewById(R.id.post_url);

        Button publish = view.findViewById(R.id.post_submit);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ur = url.getText().toString();

                if (!ur.isEmpty() && !ur.contains("youtube.com"))
                    Utils.showMessage(getContext(), "O URL é inválido!");
                else if (!post_text.getText().toString().isEmpty())
                    createPost();
                else
                    Utils.showMessage(getContext(), "Campo *Mensagem* não preenchido!");
            }
        });
    }

    private void createPost() {
        Utils.showMessage(getContext(), "A publicar...");
        final DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Post aux = new Post(post_text.getText().toString(), url.getText().toString(), DateUtils.getCurrentDate(), user);
                postsRef.push().setValue(aux);
                onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utils.showMessage(getContext(), "Erro ao publicar!");
            }
        });
    }

    public void onSuccess() {
        MainActivity.getInstance().goToMainPage();
        Utils.showMessage(getContext(), "Publicado com sucesso!");
    }
}
