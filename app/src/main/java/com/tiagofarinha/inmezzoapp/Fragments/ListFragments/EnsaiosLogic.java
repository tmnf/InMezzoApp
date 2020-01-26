package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.EnsaioAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Configurations.EnsaioConfig;
import com.tiagofarinha.inmezzoapp.Fragments.PollLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class EnsaiosLogic extends DefaultListFragment {

    public EnsaiosLogic() {
        super(R.string.ensaios_title);
    }

    @Override
    protected void inflateList() {
        adapter = new EnsaioAdapter(ResourceLoader.getInstance().getEnsaios(), R.layout.ensaio_row);
        listView.setAdapter(adapter);

        if (MainMethods.getInstance().isLoggedIn())
            addListener();

    }

    private void addListener() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Fragment frag = new PollLogic();
            Bundle args = new Bundle();
            args.putSerializable("event", ResourceLoader.getInstance().getEnsaios().get(i));
            frag.setArguments(args);
            MainMethods.getInstance().changeFrag(frag, R.id.menu_ensaios, true);
        });

        if (MainMethods.getInstance().isOp())
            listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                Fragment frag = new EnsaioConfig();

                Bundle bundle = new Bundle();
                bundle.putSerializable("ensaio", ResourceLoader.getInstance().getEnsaios().get(i));

                frag.setArguments(bundle);

                MainMethods.getInstance().changeFrag(frag, R.id.menu_ensaios, true);
                return true;
            });
    }

}
