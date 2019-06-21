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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.Models.YoutubeContainer;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class AddPost extends Fragment {

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

        Button publish = view.findViewById(R.id.post_button);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ur = url.getText().toString();

                if (!ur.isEmpty() && !(ur.contains("youtube.com") || ur.contains("youtu.be"))) {
                    Utils.showMessage("O URL é inválido!");
                    return;
                }

                if (post_text.getText().toString().isEmpty()) {
                    Utils.showMessage("Campo *Mensagem* não preenchido!");
                    return;
                }

                createPost();
            }
        });
    }

    private void createPost() {
        Utils.showMessage("A publicar...");
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");

        Post aux = new Post(post_text.getText().toString(), url.getText().toString(), MainMethods.getInstance().getAuxUser());

        if (!url.getText().toString().isEmpty())
            saveVideo(url.getText().toString(), aux);

        postsRef.push().setValue(aux).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AddPost.this.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.showMessage("Erro ao publicar!");
            }
        });
    }

    public void saveVideo(String url, Post post) {
        YoutubeContainer video = new YoutubeContainer(url, post);

        DatabaseReference videosRef = FirebaseDatabase.getInstance().getReference().child("videos");
        videosRef.push().setValue(video);
    }

    public void onSuccess() {
        MenuUtils.filterMenuItem(R.id.menu_inicio);
        Utils.showMessage("Publicado com sucesso!");
    }
}
