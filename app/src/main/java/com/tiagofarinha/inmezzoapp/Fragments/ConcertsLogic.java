package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiagofarinha.inmezzoapp.Adapter.ConcertsAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.R;

public class ConcertsLogic extends Fragment {

    private ListView listView;
    private ConcertsAdapter concertsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.concerts_fragment, container, false);

        listView = view.findViewById(R.id.concerts_list);

        getConcerts();

        return view;
    }

    private void getConcerts() {
        concertsAdapter = new ConcertsAdapter(MainActivity.getInstance(), ResourceLoader.getInstance().getConcerts(), R.layout.concert_row);
        listView.setAdapter(concertsAdapter);
    }
}
