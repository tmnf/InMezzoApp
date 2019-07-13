package com.tiagofarinha.inmezzoapp.Cache;

public class FragHistory {

    private static final int MAX_HISTORY = 3;

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
        return history[current--];
    }

    public int checkLastFrag() {
        return history[current];
    }

    private void deleteFirst() {
        current--;
        history[0] = history[1];
        history[1] = history[2];
    }

    public boolean isEmpty() {
        return current == -1;
    }
}
