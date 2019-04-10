package com.cadrac.hap_passenger.supporter_classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connection_Detector
{


    private Context _context;

    public Connection_Detector(Context context){
        this._context = context;
    }

    public boolean isConnectingToInternet()
    {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                {
                    // System.out.println("Network Info--"+info[i].getState());
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {

                        return true;
                    }
                }


        }
        return false;
    }
}
