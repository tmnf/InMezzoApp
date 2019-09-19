package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.PortfolioAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Configurations.MusicConfig;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class PortfolioLogic extends DefaultListFragment {

    private PortfolioAdapter portfolioAdapter;

    public PortfolioLogic() {
        super(R.string.portfolio_title);
    }

    @Override
    protected void inflateList() {
        portfolioAdapter = new PortfolioAdapter(ResourceLoader.getInstance().getPortfolio(), R.layout.portfolio_row);
        listView.setAdapter(portfolioAdapter);

        if (MainMethods.getInstance().isOp())
            addListener();
    }

    private void addListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment frag = new MusicConfig();

                Bundle bundle = new Bundle();
                bundle.putSerializable("music", ResourceLoader.getInstance().getPortfolio().get(i));

                frag.setArguments(bundle);

                MainMethods.getInstance().changeFrag(frag, R.id.menu_portfolio, true);
                return true;
            }
        });
    }
}
