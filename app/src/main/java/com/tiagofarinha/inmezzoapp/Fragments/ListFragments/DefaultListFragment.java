package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Adapter.DefaultAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.R;

public abstract class DefaultListFragment extends Fragment {

    protected ListView listView;
    protected DefaultAdapter adapter;

    private int titleId;

    public DefaultListFragment(int titleId) {
        this.titleId = titleId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_list_layout, container, false);

        listView = view.findViewById(R.id.default_list);


        TextView error = view.findViewById(R.id.failed_view);
        if (ResourceLoader.getInstance().isOnline())
            error.setText(R.string.empty_list);
        else error.setText(R.string.connection_failed);


        listView.setEmptyView(view.findViewById(R.id.failed_view));

        ((TextView) view.findViewById(R.id.default_title)).setText(titleId);

        inflateList();

        return view;
    }

    protected abstract void inflateList();

    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

}
