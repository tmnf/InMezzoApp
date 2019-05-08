package com.tiagofarinha.inmezzoapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Adapter.PostAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.YoutubeActivity;
import com.tiagofarinha.inmezzoapp.R;

public class FeedLogic extends Fragment {

    private ListView listView;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        listView = view.findViewById(R.id.feed_list);

        getPosts();

        return view;
    }

    private void getPosts() {
        postAdapter = new PostAdapter(getContext(), ResourceLoader.posts);
        listView.setAdapter(postAdapter);

        setListener();
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView hidden = view.findViewById(R.id.hidden_url);
                String url = hidden.getText().toString();

                if (!url.isEmpty()) {
                    Intent intent = new Intent(getContext(), YoutubeActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            }
        });
    }
}
