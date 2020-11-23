package com.itbeebd.medicare.api.allInterfaces;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkState {

    private Context context;

    public CheckNetworkState(Context context) {
        this.context = context;
    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        //  ConnectivityManager cm = (ConnectivityManager) getSystemService(C);
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        boolean flag = haveConnectedWifi || haveConnectedMobile;
        // Log.i("connection",String.valueOf(flag));
        return flag;

    }
}
