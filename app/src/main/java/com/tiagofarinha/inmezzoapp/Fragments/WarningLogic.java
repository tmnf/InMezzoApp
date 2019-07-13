package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tiagofarinha.inmezzoapp.Adapter.WarningsAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class WarningLogic extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.warning_fragment, container, false);

        getWarnings(view);

        return view;
    }

    private void getWarnings(View view) {
        WarningsAdapter wa = new WarningsAdapter(ResourceLoader.getInstance().getWarnings(), R.layout.warning_row);

        ListView listView = view.findViewById(R.id.warning_list);
        listView.setAdapter(wa);
    }
}
