/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var scripts = document.getElementsByTagName('script');
var jsFilePath = scripts[scripts.length - 1].src.replace('base.js', 'xface.js');
document.write('<script type="text/javascript" charset="utf-8" src="' + jsFilePath + '"><\/script>');

function backHome() {
	if(isAndroid()) {
		navigator.app.backHistory();
	} else {
		window.history.go(-1);
	}
}

document.addEventListener("DOMContentLoaded",onLoaded,false);
function onLoaded(){
    var platform = getPlatform();
    var filter = !platform || platform === "all"?"#stage.theme .btn[data-platform]":"#stage.theme .btn[data-platform=" + platform + "]";
    showTestCase(document.querySelectorAll(filter));
    showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=all]"));
    if (!isWindowsPhone()) {
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=android_ios]"));
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=ios_android]"));
    }
    if (!isIOS()) {
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=WindowsPhone_android]"));
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=android_WindowsPhone]"));
    }
    if (!isAndroid()) {
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=ios_WindowsPhone]"));
        showTestCase(document.querySelectorAll("#stage.theme .btn[data-platform=WindowsPhone_ios]"));
    }

    function showTestCase(array){
        for(var i=0;i<array.length;i++){
            array[i].style["display"] = "block";
        }
    }
}

function getPlatform(){
    if(navigator.userAgent.match(/Android/i)){
        return "android";
    }
    if(navigator.userAgent.indexOf('iPhone') != -1 || navigator.userAgent.indexOf('iPad') != -1
       || navigator.userAgent.indexOf('iPod') != -1){
        return "ios";
    }
    if(navigator.userAgent.indexOf('Windows Phone') != -1){
        return "WindowsPhone";
    }
    if(navigator.userAgent.indexOf('Windows') != -1){
        return "all";
    }
    var platform = prompt("platform","all");
    return platform.toLowerCase();
}

function isAndroid(){
    var platform = getPlatform();
    return platform === "android";
}

function isIOS(){
    var platform = getPlatform();
    return platform === "ios";
}

function isWindowsPhone(){
    var platform = getPlatform();
    return platform === "WindowsPhone";
}

var Popup = (function(window){
    var container = document.createElement("div");
    var WIDTH = 300;
    function Popup(){
        this.content = document.createElement("div");
        container.className = "infoContent";
        container.appendChild(this.content);
        var close = document.createElement("div");
        close.innerHTML = "x";
        close.className = "closeBtn";
        container.appendChild(close);
        var that = this;
        close.addEventListener("click",function(){that.hide()},false);
    }
    Popup.prototype = {
        show:function(html){
            this.content.innerHTML = html;
            container.style["left"] = ((window.innerWidth-WIDTH)/2) + "px";
            document.body.appendChild(container);
        },
        hide:function(){
            this.content.innerHTML = "";
            document.body.removeChild(container);
        }
    };
    return new Popup();
}
)(this);

function createInformationHtml(){
    return '<h4>Platform: <span id="platform"> '+device.platform+'</span></h4>'
          +'<h4>Version: <span id="version">'+device.version+'</span></h4>'
          +'<h4>model: <span id="model">'+device.model+'</span></h4>'
          +'<h4>UUID: <span id="uuid" >'+device.uuid+'  </span></h4>'
          +'<h4>IMEI: <span id="imei">'+device.imei+'</span></h4>'
          +'<h4>Name: <span id="name"> '+device.name+'</span></h4>'
          +'<h4>Width: <span id="width"> '+screen.width+' </span>,   Height: <span id="height">'+screen.height+'</span>, Color Depth: <span id="colorDepth">'+screen.colorDepth+'</span></h4>'
          +'<h4>isCameraAvailable: <span id="isCameraAvailable">'+device.isCameraAvailable+' </span></h4>'
          +'<h4>isFrontCameraAvailable: <span id="isFrontCameraAvailable">'+device.isFrontCameraAvailable+' </span></h4>'
          +'<h4>isCompassAvailable: <span id="isCompassAvailable">'+device.isCompassAvailable+' </span></h4>'
          +'<h4>isAccelerometerAvailable: <span id="isAccelerometerAvailable">'+device.isAccelerometerAvailable+' </span></h4>'
          +'<h4>isLocationAvailable: <span id="isLocationAvailable" >'+device.isLocationAvailable+' </span></h4>'
          +'<h4>isWiFiAvailable: <span id="isWiFiAvailable"> '+device.isWiFiAvailable+'</span></h4>'
          +'<h4>isTelephonyAvailable: <span id="isTelephonyAvailable"> '+device.isTelephonyAvailable+'</span></h4>'
          +'<h4>isSmsAvailable: <span id="isSmsAvailable"> '+device.isSmsAvailable+'</span></h4>'
          +'<h4>xFaceVersion: <span id="xFaceVersion"> '+device.xFaceVersion+'</span></h4>';

}

document.addEventListener("DOMContentLoaded",function(){
    var rightBtn = document.createElement("div");
    var leftBtn = document.createElement("div");
    showPlatformTag(document.body);
    document.body.appendChild(leftBtn);
    document.body.appendChild(rightBtn);
    leftBtn.id = "hLeftBtn";
    leftBtn.innerHTML = "i";
    rightBtn.id = "hRightBtn";
    rightBtn.innerHTML = "?";
    leftBtn.addEventListener("click",function(){
        Popup.show(createInformationHtml());
    },false);
    rightBtn.addEventListener("click",function(){
        var info = document.getElementById("descr");
        showPlatformTag(info);
        //TODO:日后增加其他平台时，考虑为标签添加id
        if(!info)return;
        var text = info.innerHTML;
        if(text)Popup.show(text);
    },false);
},false);

function showPlatformTag(parent){
    var platform = getPlatform();
    if(platform !== "all")
    {
        if(isAndroid())
        {
            var elems = parent.getElementsByTagName("WindowsPhone");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("ios");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("ios_WindowsPhone");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("WindowsPhone_ios");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
        }
        if(isIOS())
        {
            var elems = parent.getElementsByTagName("android");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("WindowsPhone");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("android_WindowsPhone");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("WindowsPhone_android");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
        }
        if(isWindowsPhone())
        {
            var elems = parent.getElementsByTagName("android");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("ios");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("android_ios");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
            var elems = parent.getElementsByTagName("ios_android");
            for(var i = 0; i < elems.length; i++){
                elems[i].style["display"] = "none";
            }
        }

    }
}

function importJS(url){
    var node = document.createElement('script');
    node.setAttribute('src', url);
    document.getElementsByTagName('head')[0].appendChild(node);
}
