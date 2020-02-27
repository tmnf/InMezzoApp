package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.ThroneAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ThroneLogic extends Fragment {

    private ArrayList<Adaptable> usersByVotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.throne_fragment, container, false);

        getComponents(view);

        return view;
    }

    private void getComponents(View view) {
        ImageView top1 = view.findViewById(R.id.throne_pic1), top2 = view.findViewById(R.id.throne_pic2), top3 = view.findViewById(R.id.throne_pic3);

        usersByVotes = new ArrayList<>(ResourceLoader.getInstance().getUsers());

        Collections.sort(usersByVotes, new Comparator<Adaptable>() {
            @Override
            public int compare(Adaptable o1, Adaptable o2) {
                return ((User) o2).getVotes() - ((User) o1).getVotes();
            }
        });

        ListView lv = view.findViewById(R.id.throne_list);
        lv.setAdapter(new ThroneAdapter(usersByVotes, R.layout.throne_row));

        LoginUtils.putInto(top1, ((User) usersByVotes.get(1)));
        LoginUtils.putInto(top2, ((User) usersByVotes.get(0)));
        LoginUtils.putInto(top3, ((User) usersByVotes.get(2)));
    }


}
