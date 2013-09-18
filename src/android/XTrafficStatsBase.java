package com.polyvi.xface.extension.trafficstats;

import android.net.TrafficStats;

/**
 * 流量统计的基类，记录了上一次统计时的总流量和某种连接状态下的流量统计数据
 */
public abstract class XTrafficStatsBase {
    /** 记录上一次统计时的总流量，包括Wifi、Mobile、NoNetWork */
    protected long mLastTotalTraffic;
    /** 记录某种连接状态下的流量统计数据 */
    protected long mTrafficData;
    /** 应用唯一表示符 */
    protected int mUID;

    public XTrafficStatsBase(int uid) {
        mUID = uid;
    }

    /**
     * 获取流量统计数据
     *
     * @return
     */
    public long getTraffic() {
        return mTrafficData;
    }

    /**
     * 更新上一次统计时的总流量
     *
     * @param totalTraffic
     */
    public void updateTotalTraffic(long totalTraffic) {
        mLastTotalTraffic = totalTraffic;
    }

    /**
     * 更新上一次统计时的总流量和某种连接状态下的流量统计数据
     */
    public void updateTraffic() {
        long totalRev = TrafficStats.getUidRxBytes(mUID);
        mTrafficData += (totalRev - mLastTotalTraffic);
        updateTotalTraffic(totalRev);
    }

}
