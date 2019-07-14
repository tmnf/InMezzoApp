package com.tiagofarinha.inmezzoapp.Cache;

import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class FragHistory {

    private static final int MAX_HISTORY = 5;

    private static FragHistory INSTANCE;

    private int[] history;
    private int current;

    public FragHistory() {
        history = new int[MAX_HISTORY];
        current = -1;
    }

    public static FragHistory getInstance() {
        if (INSTANCE == null)
            INSTANCE = new FragHistory();
        return INSTANCE;
    }

    public void addToHistory(int id) {
        if (current + 1 == MAX_HISTORY)
            deleteFirst();

        history[++current] = id;
    }

    public int getLastFrag() {
        if (current == 0)
            Utils.showMessage("Pressione voltar novamente para sair");

        return history[current--];
    }

    private void deleteFirst() {
        current--;

        for (int i = 0; i < MAX_HISTORY - 1; i++)
            history[i] = history[i + 1];
    }

    public boolean isEmpty() {
        return current == -1;
    }
}
