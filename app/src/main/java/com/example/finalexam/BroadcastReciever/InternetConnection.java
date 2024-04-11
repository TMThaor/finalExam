package com.example.finalexam.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

import com.example.finalexam.signin_signup.LoadingPage;

public class InternetConnection extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            if(isNetworkAvailable(context)){
                Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(context, "Không có kết nối internet!", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction() != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            LoadingPage.loadingPageInstance.checkNetworkConnection();
        }
    }

    public Boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null){
            return false;
        }
        Network network=connectivityManager.getActiveNetwork();
        if(network==null){
            return false;
        }
        NetworkCapabilities capabilities=connectivityManager.getNetworkCapabilities(network);
        return capabilities!=null&&capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);

    }
}

