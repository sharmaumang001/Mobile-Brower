package com.sharmaumang001.srpbrowser.utility;

import android.content.Context;
import android.net.ConnectivityManager;

public class MyNetworkState {

    public static boolean connectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
