package com.hgtec.http.task;

import android.os.AsyncTask;
import com.hgtec.http.reuqest.HttpConnection;

import java.util.Map;

/**
 * Created by Lucius on 2016/12/4.
 */
public class PostRequestAsyncTask extends AsyncTask<Object, Integer, Object> {

    private HttpConnection mConnection;

    public PostRequestAsyncTask(HttpConnection connection) {
        this.mConnection = connection;
    }
    @Override
    protected Object doInBackground(Object... objects) {
        if (mConnection != null) {
            mConnection.doPost((String) objects[1], (String)objects[2], Integer.valueOf((String) objects[3]),  Integer.valueOf((String) objects[4]), (Map<String, String>) objects[5], Boolean.valueOf((String) objects[6]));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
