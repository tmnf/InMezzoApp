package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.PortfolioAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public class PortfolioLogic extends Fragment {

    private ListView listView;
    private PortfolioAdapter portfolioAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.portfolio_fragment, container, false);

        listView = view.findViewById(R.id.portfolio_list);

        getPortfolio();

        return view;
    }

    private void getPortfolio() {
        portfolioAdapter = new PortfolioAdapter(ResourceLoader.getInstance().getPortfolio(), R.layout.portfolio_row);
        listView.setAdapter(portfolioAdapter);
    }
}
