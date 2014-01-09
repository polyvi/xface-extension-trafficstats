
/*
 Copyright 2012-2013, Polyvi Inc. (http://polyvi.github.io/openxface)
 This program is distributed under the terms of the GNU General Public License.

 This file is part of xFace.

 xFace is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 xFace is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with xFace.  If not, see <http://www.gnu.org/licenses/>.
*/

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
