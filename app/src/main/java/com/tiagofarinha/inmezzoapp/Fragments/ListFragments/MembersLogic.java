package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.view.View;
import android.widget.AdapterView;

import com.tiagofarinha.inmezzoapp.Adapter.UserAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;

public class MembersLogic extends DefaultListFragment {

    public MembersLogic() {
        super(R.string.members_title);
    }

    @Override
    protected void inflateList() {
        adapter = new UserAdapter(ResourceLoader.getInstance().getUsers(), R.layout.user_row);
        listView.setAdapter(adapter);

        addListener();
    }

    private void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainMethods.getInstance().goToProfilePage((User) ResourceLoader.getInstance().getUsers().get(i));
            }
        });
    }
}

