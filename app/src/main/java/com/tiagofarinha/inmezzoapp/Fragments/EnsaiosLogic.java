package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiagofarinha.inmezzoapp.Adapter.EnsaioAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class EnsaiosLogic extends Fragment {

    private ListView listView;
    private EnsaioAdapter ensaiosAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensaios_fragment, container, false);

        listView = view.findViewById(R.id.concerts_list);

        getEnsaios();

        return view;
    }

    private void getEnsaios() {
        ensaiosAdapter = new EnsaioAdapter(getContext(), ResourceLoader.ensaios, R.layout.ensaio_row);
        listView.setAdapter(ensaiosAdapter);
    }
}
