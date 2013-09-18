package com.polyvi.xface.extension.trafficstats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.TrafficStats;
/**
 * 流量统计类，负责对mobile、wifi、noNetwork流量进行统计，其中包含了3个内部类，
 * 分别统计不同类型的流量，同时在网络类型切换时，更新相应的流量统计
 */
public class XTrafficStats {

    private static final int CONST_NUM = 1024;
    private Context mContext;
    /** 应用的唯一标识 */
    private int mUID;
    /** 负责统计wifi流量 */
    private XWifiTraffic mWifiTraffic;
    /** 负责统计mobile流量，包括2G,3G */
    private XMobileTraffic mMobileTraffic;
    /** 统计进程间通信的流量，主要是为了减少wif和mobile流量统计的误差 */
    private XNoNetworkTraffic mNoNetworkTraffic;
    /** 负责监听网络连接状态改变 */
    private BroadcastReceiver mConnectionMonitor;
    /** 表示当前的网络状态 */
    XTrafficStatsBase mCurrentNetworkState;

    public XTrafficStats(Context context) {
        mContext = context;
        PackageManager pm = mContext.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            mUID = info.uid;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        init();
    }

    /**
     * 初始化，初始化当前的网络状态和流量数据
     */
    private void init() {
        initTrafficStats();
        initConnectionMonitor();
    }

    public void destroy()
    {
        mContext.unregisterReceiver(mConnectionMonitor);
    }

    /**
     * 初始化网络连接状态
     */
    private void initConnectionMonitor() {
        // 需要监听网络连接变化事件，以便及时更新connection
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (mConnectionMonitor == null) {
            mConnectionMonitor = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    stateTransition(context);
                }
            };
            mContext.registerReceiver(mConnectionMonitor, intentFilter);
        }
    }

    /**
     * 初始化流量数据
     */
    private void initTrafficStats() {
        mWifiTraffic = new XWifiTraffic(mUID);
        mMobileTraffic = new XMobileTraffic(mUID);
        mNoNetworkTraffic = new XNoNetworkTraffic(mUID);
        updateCurrentNetwork(mContext);
        long totalTraffic = TrafficStats.getUidRxBytes(mUID);
        mCurrentNetworkState.updateTotalTraffic(totalTraffic);
    }

    /**
     * 当网络状态改变后，会统计前一个网络状态使用的流量和更新当前网络连接状态
     *
     * @param context
     */
    private void stateTransition(Context context) {
        XTrafficStatsBase previousState = updateCurrentNetwork(context);
        previousState.updateTraffic();
        mCurrentNetworkState.updateTotalTraffic(TrafficStats
                .getUidRxBytes(mUID));
    }

    /**
     * 更新当前网络连接状态
     *
     * @param context
     * @return
     */
    private XTrafficStatsBase updateCurrentNetwork(Context context) {
        XTrafficStatsBase previousState = mCurrentNetworkState;
        State mobileState = null;
        State wifiState = null;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiNetworkInfo) {
            wifiState = wifiNetworkInfo.getState();
        }
        NetworkInfo mobileNetworkInfo = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != mobileNetworkInfo) {
            mobileState = mobileNetworkInfo.getState();
        }
        if (State.CONNECTED != wifiState && State.CONNECTED == mobileState) {
            // 手机网络连接成功
            mCurrentNetworkState = mMobileTraffic;
        } else if (State.CONNECTED != wifiState
                && State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            mCurrentNetworkState = mNoNetworkTraffic;
        } else if (State.CONNECTED == wifiState) {
            // 无线网络连接成功
            mCurrentNetworkState = mWifiTraffic;
        }
        return previousState;
    }

    /**
     * 获取Wifi流量数据，获取的流量是从应用启动到调用接口其间的流量数据
     *
     * @return 流量数据,单位为KB
     */
    public long getWifiTraffic() {
        mCurrentNetworkState.updateTraffic();
        return mWifiTraffic.getTraffic() / CONST_NUM;
    }

    /**
     * 获取2G和3G流量数据，获取的流量是从应用启动到调用接口其间的流量数据
     *
     * @return 流量数据,单位为KB
     */
    public long getMobileTraffic() {
        mCurrentNetworkState.updateTraffic();
        return mMobileTraffic.getTraffic() / CONST_NUM;
    }

    /**
     * 负责统计Wifi使用的流量
     */
    class XWifiTraffic extends XTrafficStatsBase {
        public XWifiTraffic(int uid) {
            super(uid);
        }
    }

    /**
     * 负责统计Mobile使用的流量
     */
    class XMobileTraffic extends XTrafficStatsBase {
        public XMobileTraffic(int uid) {
            super(uid);
        }
    }

    /**
     * 负责统计在没有network状态下的流量，该流量为进程间的通信流量
     */
    class XNoNetworkTraffic extends XTrafficStatsBase {
        public XNoNetworkTraffic(int uid) {
            super(uid);
        }
    }

}
