package ch.uzh.ifi.seal.bachelorthesis.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import ch.uzh.ifi.seal.bachelorthesis.model.PreferenceManager;

/**
 * Created by Eros Fricker on 04/07/16.
 */
public class BugzillaConnectionManager {

    Context context;
    public BugzillaConnectionManager(Context context) {
        this.context = context;
    }

    public boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }else {

            return false;
        }
    }

    public boolean isServerReachable() {
        boolean isReachable = false;
        try {
            PreferenceManager preferenceManager = PreferenceManager.getInstance(context);
            SocketAddress socketAddress = new InetSocketAddress(preferenceManager.getServerURL(), 80);
            Socket socket = new Socket();
            int timeOut = 2000;
            socket.connect(socketAddress, timeOut);
            isReachable = true;
        }catch (Exception exception) {
            exception.printStackTrace();
        }
        return isReachable;
    }
}
