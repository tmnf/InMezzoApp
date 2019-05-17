package com.tiagofarinha.inmezzoapp.Cache;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragManager {

    private static FragManager INSTANCE;
    private HashMap<String, Fragment> fragments;

    public FragManager() {
        fragments = new HashMap<>();
        INSTANCE = this;
    }

    public static FragManager getInstance() {
        return INSTANCE;
    }

    public HashMap<String, Fragment> getFragments() {
        return fragments;
    }

    public Fragment findFragment(String tag) {
        return fragments.get(tag);
    }

    public void addToFragList(Fragment frag, String tag) {
        fragments.put(tag, frag);
    }

}
