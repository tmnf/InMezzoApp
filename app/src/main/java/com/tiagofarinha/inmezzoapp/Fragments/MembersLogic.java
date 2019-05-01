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
import com.tiagofarinha.inmezzoapp.Adapter.UserAdapter;
import com.tiagofarinha.inmezzoapp.AdminTools.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class MembersLogic extends Fragment {

    private ListView listView;
    private UserAdapter mAdapter;

    private ArrayList<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.members_fragment, container, false);

        listView = view.findViewById(R.id.member_list);

        getUsers();

        return view;
    }

    private void getUsers(){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        users = new ArrayList<>();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot x : dataSnapshot.getChildren()){
                    users.add(x.getValue(User.class));
                }

                mAdapter = new UserAdapter(getContext(), users);
                listView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Utils.showMessage(getContext(), "A leitura de membros falhou");
            }
        });
    }
}

