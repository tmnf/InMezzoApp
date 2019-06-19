package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiagofarinha.inmezzoapp.Adapter.PostAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class FeedLogic extends Fragment {

    private ListView listView;
    private PostAdapter postAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_fragment, container, false);

        listView = view.findViewById(R.id.post_list);
        listView.setEmptyView(view.findViewById(R.id.failed_view));

        getPosts();

        return view;
    }

    private void getPosts() {
        postAdapter = new PostAdapter(MainMethods.getInstance().getContext(), ResourceLoader.getInstance().getPosts(), R.layout.post_row);
        listView.setAdapter(postAdapter);
    }
}
