package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.ConcertsAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Configurations.ConcertConfig;
import com.tiagofarinha.inmezzoapp.Fragments.PollLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class ConcertsLogic extends DefaultListFragment {

    private ConcertsAdapter concertsAdapter;

    public ConcertsLogic() {
        super(R.string.concerts_title);
    }

    @Override
    protected void inflateList() {
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
