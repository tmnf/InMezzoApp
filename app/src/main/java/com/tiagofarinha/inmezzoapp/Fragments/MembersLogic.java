package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiagofarinha.inmezzoapp.Adapter.UserAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class MembersLogic extends Fragment {

    private ListView listView;
    private UserAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.members_fragment, container, false);

        listView = view.findViewById(R.id.member_list);

        getUsers();

        return view;
    }

    private void getUsers(){
        mAdapter = new UserAdapter(getContext(), ResourceLoader.users);
        listView.setAdapter(mAdapter);
    }
}

