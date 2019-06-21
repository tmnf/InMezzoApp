package com.tiagofarinha.inmezzoapp.AdminTools.UserCreation;

import android.os.AsyncTask;
import android.util.Log;

import com.tiagofarinha.inmezzoapp.AdminTools.AdminLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;

public class AutoUserCreator extends AsyncTask {

    private String[] users;

    public AutoUserCreator() {
        users = MainMethods.getInstance().getContext().getResources().getStringArray(R.array.usersToAdd);
        Log.d("USERS", "Para adicionar: " + users.length);

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        for (String x : users) {
            String[] info = x.split("-");

            new UserCreator(info[0], AdminLogic.DEFAULT_PASSWORD, info[1], info[2], info[3], info[4], info[5]).start();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
