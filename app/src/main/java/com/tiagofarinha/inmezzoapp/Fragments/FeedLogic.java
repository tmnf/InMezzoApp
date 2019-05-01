package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Adapter.Post;
import com.tiagofarinha.inmezzoapp.Adapter.PostAdapter;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MathUtils;

import java.util.ArrayList;

public class FeedLogic extends Fragment {

    private ArrayList<Post> posts;

    private ListView listView;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        listView = view.findViewById(R.id.feed_list);

        getPosts();

        MathUtils.getCurrentDate();

        return view;
    }

    private void getPosts() {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");
        posts = new ArrayList<>();

        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listView.clearChoices();
                for(DataSnapshot x : dataSnapshot.getChildren()){
                    posts.add(x.getValue(Post.class));
                }

                ArrayList<Post> inversed_post = new ArrayList<>();
                for(int i = posts.size()-1; i >= 0; i--)
                    inversed_post.add(posts.get(i));

                try {
                    postAdapter = new PostAdapter(getContext(), inversed_post);
                    listView.setAdapter(postAdapter);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
