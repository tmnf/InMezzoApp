package com.tiagofarinha.inmezzoapp.Cache;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;

public class FragManager {

    private static FragManager INSTANCE;

    private final static int MAX_FRAGS = 15;

    private LinkedList<Fragment> frags;
    private String[] tags;

    private int current;

    public FragManager() {
        INSTANCE = this;

        current = 0;
        frags = new LinkedList<>();
        tags = new String[MAX_FRAGS];
    }

    public static void start() {
        getInstance();
    }

    public static FragManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FragManager();
        return INSTANCE;
    }

    public Fragment findFragment(String tag) {
        Fragment aux = null;

        for (int i = 0; i != frags.size(); i++)
            if (tags[i].equals(tag))
                aux = frags.get(i);

        return aux;
    }

    public void addToFragList(Fragment frag, String tag) {
        if (current + 1 != MAX_FRAGS) {
            frags.add(frag);
            tags[current] = tag;
            current++;
        }
    }
}
