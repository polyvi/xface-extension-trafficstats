
/**
 * TrafficStats模块提供流量统计功能
 * @module trafficStats
 * @main trafficStats
 */

  var exec = require('cordova/exec');
  var argscheck = require('cordova/argscheck');

 /**
  *TrafficStats模块提供流量统计功能 (Android) <br/>
  *直接使用xFace.TrafficStats对象来获取流量的相关信息，获取的流量是从应用启动到调用接口其间的网络流量，
  * @class TrafficStats
  * @platform Android
  */
  var TrafficStats = function() {};

/**
 * 获取2G和3G网络的网络流量
  @example
        xFace.getMobileTraffic( function(trafficData)
        {
            alert(trafficData);
        },null);
 * @method getMobileTraffic
 * @param {Function} successCallback 成功回调函数
 * @param {String} successCallback.trafficData 网络流量,单位为KB
 * @param {Function} [errorCallback]   失败回调函数
 * @platform Android
 * @since 3.0.0
 */
  TrafficStats.prototype.getMobileTraffic = function(successCallback, errorCallback){
    argscheck.checkArgs('fF','TrafficStats.getMobileTraffic', arguments);
    exec(successCallback, errorCallback, "TrafficStats", "getMobileTraffic", []);
  };

/**
 * 获取Wifi网络流量
  @example
        xFace.getWifiTraffic(
        function(trafficData)
        {
            alert(trafficData);
        },null);
 * @method getWifiTraffic
 * @param {Function} successCallback 成功回调函数
 * @param {String} successCallback.trafficData 网络流量,单位为KB
 * @param {Function} [errorCallback]   失败回调函数
 * @platform Android
 * @since 3.0.0
 */
  TrafficStats.prototype.getWifiTraffic = function(successCallback, errorCallback){
    argscheck.checkArgs('fF','TrafficStats.getWifiTraffic', arguments);
    exec(successCallback, errorCallback, "TrafficStats", "getWifiTraffic", []);
  };

  module.exports = new TrafficStats();
