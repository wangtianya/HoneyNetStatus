/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.qnet.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

public class NetworkInfoManager {

    public static class Holder {
        public static NetworkInfoManager Instance = new NetworkInfoManager();
    }

    public static NetworkInfoManager getInstance() {
        return Holder.Instance;
    }


    public void init(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return;
        }


        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        NetworkRequest request = builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                NetworkCapabilities networkCapabilities = cm.getNetworkCapabilities(network);
                Log.e("NetworkInfoManager",  networkCapabilities.toString());
            }

            @Override
            public void onLost(Network network) {
                Log.e("NetworkInfoManager",  "onLost");
            }
        });
    }


    public void isConnected() {
    }

}
