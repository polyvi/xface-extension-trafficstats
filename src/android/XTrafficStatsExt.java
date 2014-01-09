
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

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * 流量统计扩展模块，用获取流量的相关信息，获取的流量 是从应用启动到调用接口其间的流量数据
 */
public class XTrafficStatsExt extends CordovaPlugin {

    private static final String COMMAND_GETMOBILETRAFFIC = "getMobileTraffic";
    private static final String COMMAND_GETWIFITRAFFIC = "getWifiTraffic";
    /** 负责统计和更新流量数据 */
    private XTrafficStats mTrafficStatic;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        mTrafficStatic = new XTrafficStats(cordova
                .getActivity().getApplicationContext());
    }

    @Override
    public boolean execute(String action, String rawArgs,
            CallbackContext callbackContext) throws JSONException {
        if (action.equals(COMMAND_GETMOBILETRAFFIC)) {
            callbackContext.sendPluginResult(new PluginResult(
                    PluginResult.Status.OK, mTrafficStatic.getMobileTraffic()));
            return true;
        } else if (action.equals(COMMAND_GETWIFITRAFFIC)) {
            callbackContext.sendPluginResult(new PluginResult(
                    PluginResult.Status.OK, mTrafficStatic.getWifiTraffic()));
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        mTrafficStatic.destroy();
    }

}
