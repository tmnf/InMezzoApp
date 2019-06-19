package com.tiagofarinha.inmezzoapp.Cache;

import android.support.v4.app.Fragment;

public class FragManager {

    private static FragManager INSTANCE;

    private No prim, ult;

    public FragManager() {
        INSTANCE = this;

        prim = null;
        ult = null;
    }

    public static FragManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FragManager();
        return INSTANCE;
    }

    public Fragment findFragment(String tag) {
        No aux = prim;

        boolean encontrei = false;
        while (aux != null && !encontrei) {
            if (aux.tag.equals(tag))
                encontrei = true;
            else
                aux = aux.seg;
        }

        if (aux != null)
            return aux.value;
        else
            return null;
    }

    public void addToFragList(Fragment frag, String tag) {
        No aux = new No();
        aux.seg = null;
        aux.value = frag;
        aux.tag = tag;

        if (prim == null)
            prim = aux;

        if (ult != null)
            ult.seg = aux;

        ult = aux;
    }

    private class No {
        Fragment value;
        No seg;
        String tag;
    }

}
