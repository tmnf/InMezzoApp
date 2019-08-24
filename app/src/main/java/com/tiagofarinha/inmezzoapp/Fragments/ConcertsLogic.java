package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.ConcertsAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Configurations.ConcertConfig;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
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
        concertsAdapter = new ConcertsAdapter(ResourceLoader.getInstance().getConcerts(), R.layout.concert_row);
        listView.setAdapter(concertsAdapter);

        if (MainMethods.getInstance().isLoggedIn())
            addListener();
    }

    private void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment frag = new PollLogic();
                Bundle args = new Bundle();
                args.putSerializable("event", ResourceLoader.getInstance().getConcerts().get(i));
                frag.setArguments(args);
                MainMethods.getInstance().changeFrag(frag, 0, true);
            }
        });

        if (MainMethods.getInstance().isOp())
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment frag = new ConcertConfig();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("concert", ResourceLoader.getInstance().getConcerts().get(i));

                    frag.setArguments(bundle);

                    MainMethods.getInstance().changeFrag(frag, R.id.menu_concertos, true);
                    return true;
                }
            });
    }
}
