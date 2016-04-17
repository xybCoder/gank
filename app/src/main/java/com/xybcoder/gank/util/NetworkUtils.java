package com.xybcoder.gank.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by dell on 2016/4/13.
 */
public class NetworkUtils {

    public static final int NETWORK_TYPE_NO_CONNECTION = -1231545315;

    public NetworkUtils() {
    }

    /**
     * 判断网络是否连接
     * @param context
     * @return false
     */
    public static boolean isWIFIConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    public static NetworkInfo.State getCurrentNetworkState(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null?networkInfo.getState():null;
    }

    public static int getCurrentNetworkType(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null?networkInfo.getType():-1231545315;
    }

    public static int getCurrentNetworkSubtype(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return networkInfo != null?networkInfo.getSubtype():-1231545315;
    }

    public static boolean isConnectedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED;
    }

    public static boolean isConnectingByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTING;
    }

    public static boolean isDisconnectedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.DISCONNECTED;
    }

    public static boolean isDisconnectingByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.DISCONNECTING;
    }

    public static boolean isSuspendedByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.SUSPENDED;
    }

    public static boolean isUnknownByState(Context context) {
        return getCurrentNetworkState(context) == NetworkInfo.State.UNKNOWN;
    }

    @TargetApi(13)
    public static boolean isBluetoothByType(Context context) {
        return Build.VERSION.SDK_INT < 13?false:getCurrentNetworkType(context) == 7;
    }

    @TargetApi(14)
    public static boolean isDummyByType(Context context) {
        return Build.VERSION.SDK_INT < 13?false:getCurrentNetworkType(context) == 8;
    }

    @TargetApi(13)
    public static boolean isEthernetByType(Context context) {
        return Build.VERSION.SDK_INT < 13?false:getCurrentNetworkType(context) == 9;
    }

    public static boolean isMobileByType(Context context) {
        return getCurrentNetworkType(context) == 0;
    }

    public static boolean isMobileDunByType(Context context) {
        return getCurrentNetworkType(context) == 4;
    }

    public static boolean isMobileHipriByType(Context context) {
        return getCurrentNetworkType(context) == 5;
    }

    public static boolean isMobileMmsByType(Context context) {
        return getCurrentNetworkType(context) == 2;
    }

    public static boolean isMobileSuplByType(Context context) {
        return getCurrentNetworkType(context) == 3;
    }

    public static boolean isWifiByType(Context context) {
        return getCurrentNetworkType(context) == 1;
    }

    public static boolean isWimaxByType(Context context) {
        return getCurrentNetworkType(context) == 6;
    }

    public static boolean is1XRTTBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 7;
    }

    public static boolean isCDMABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 4;
    }

    public static boolean isEDGEBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 2;
    }

    @TargetApi(11)
    public static boolean isEHRPDBySubtype(Context context) {
        return Build.VERSION.SDK_INT < 11?false:getCurrentNetworkSubtype(context) == 14;
    }

    public static boolean isEVDO_0BySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 5;
    }

    public static boolean isEVDO_ABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 6;
    }

    @TargetApi(9)
    public static boolean isEVDO_BBySubtype(Context context) {
        return Build.VERSION.SDK_INT < 9?false:getCurrentNetworkSubtype(context) == 12;
    }

    public static boolean isGPRSBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 1;
    }

    public static boolean isHSDPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 8;
    }

    public static boolean isHSPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 10;
    }

    @TargetApi(13)
    public static boolean isHSPAPBySubtype(Context context) {
        return Build.VERSION.SDK_INT < 13?false:getCurrentNetworkSubtype(context) == 15;
    }

    public static boolean isHSUPABySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 9;
    }

    public static boolean isIDENBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 11;
    }

    @TargetApi(11)
    public static boolean isLTEBySubtype(Context context) {
        return Build.VERSION.SDK_INT < 11?false:getCurrentNetworkSubtype(context) == 13;
    }

    public static boolean isUMTSBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 3;
    }

    public static boolean isUNKNOWNBySubtype(Context context) {
        return getCurrentNetworkSubtype(context) == 0;
    }

    public static boolean isChinaMobile2G(Context context) {
        return isEDGEBySubtype(context);
    }

    public static boolean isChinaUnicom2G(Context context) {
        return isGPRSBySubtype(context);
    }

    public static boolean isChinaUnicom3G(Context context) {
        return isHSDPABySubtype(context) || isUMTSBySubtype(context);
    }

    public static boolean isChinaTelecom2G(Context context) {
        return isCDMABySubtype(context);
    }

    public static boolean isChinaTelecom3G(Context context) {
        return isEVDO_0BySubtype(context) || isEVDO_ABySubtype(context) || isEVDO_BBySubtype(context);
    }

    public static int getWifiState(Context context) throws Exception {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager != null) {
            return wifiManager.getWifiState();
        } else {
            throw new Exception("wifi device not found!");
        }
    }

    public static boolean isWifiOpen(Context context) throws Exception {
        int wifiState = getWifiState(context);
        return wifiState == 3 || wifiState == 2;
    }

    public static boolean setWifi(Context context, boolean enable) throws Exception {
        if(isWifiOpen(context) != enable) {
            ((WifiManager)context.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(enable);
        }

        return true;
    }

    public static boolean isMobileNetworkOpen(Context context) {
        return ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(0).isConnected();
    }


    public static String getIpAddress() {
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            while(en.hasMoreElements()) {
                NetworkInterface ex = (NetworkInterface)en.nextElement();
                Enumeration enumIpAddr = ex.getInetAddresses();

                while(enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
                    if(!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }

            return null;
        } catch (SocketException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
