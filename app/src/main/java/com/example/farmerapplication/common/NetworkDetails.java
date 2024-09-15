package com.example.farmerapplication.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
// This class is used to check the internet connected or not it dont know who will call me,when i will work
public class NetworkDetails {
    public static boolean isconnectedtointernet(Context context)//Default value provided but can Changed in program
    {
        //Goes in the System and Access the Information of Network
        //Type Cast:Entered value Convert in the Given Format
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();//Get the infromation about all Network of connectivity
            if(networkInfos!=null)
            {
                for(int i=0;i<networkInfos.length;i++)
                {
                    if(networkInfos[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
