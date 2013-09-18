#TrafficStats

An exentsion for counting network flow

##Methods

  xFace.TrafficStats.getMobileTraffic(success, fail);
  xFace.TrafficStats.getWifiTraffic(success, fail);

##Supported Platforms

- Android

##Full Example

<!DOCTYPE html>
  <html>
    <head>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width,height=device-height,user-scalable=no,initial-scale=1.0" />
      <meta http-equiv="Content-type" content="text/html; charset=utf-8"> <!-- ISO-8859-1 -->
      <link rel="stylesheet" href="master.css" type="text/css" media="screen" title="no title" charset="utf-8">
      <script type="text/javascript" src="cordova.js"></script>
      <script type="text/javascript" src="TrafficStats.js"></script>
      <script type="text/javascript" charset="utf-8">

         function updateStatus(status){
             document.getElementById('status').innerText = status;
         };

        function openUrlByWifi(){
          var url = "http://www.baidu.com";
          var xmlHttp;
          if(window.ActiveXObject){
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
          }
          else if(window.XMLHttpRequest){
            xmlHttp = new XMLHttpRequest();
          }
          xmlHttp.onreadystatechange = processor;
          xmlHttp.open("GET", url);
          xmlHttp.send();
          function processor(){
            var responseContext;
            if(xmlHttp.readyState == 4){
              if(xmlHttp.status == 200){
                getWifiTraffic();
              }
            }
          }
        };

        function openUrlByMobile(){
          var url = "http://www.baidu.com";
          var xmlHttp;
          if(window.ActiveXObject){
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
          }
          else if(window.XMLHttpRequest){
            xmlHttp = new XMLHttpRequest();
          }
          xmlHttp.onreadystatechange = processor;
          xmlHttp.open("GET", url);
          xmlHttp.send();
          function processor(){
            var responseContext;
            if(xmlHttp.readyState == 4){
              if(xmlHttp.status == 200){
                getMobileTraffic();
              }
            }
          }
        };

       function getWifiTraffic(){
          xFace.TrafficStats.getWifiTraffic(function (data){
            updateStatus(data);
          }, null);
       };

       function getMobileTraffic(){
          xFace.TrafficStats.getMobileTraffic(function (data){
            updateStatus(data);
          },fail);
       };

       function fail(error){
           alert(error.code);
           alert(error.source);
           alert(error.target);
       };
      </script>
    </head>

    <body id="stage" class="theme">
    <h1>TrafficStats</h1>
    <div id="descr">
          测试目的：流量统计<br/>
          1：点击第一个按钮，通过Wifi访问一个url，status显示流量统计<br/>
          2：点击第二个按钮，通过2G或者3G网络访问一个url，status显示流量统计<br/>
          注意：需要手动切换网络连接来进行测试，在测试时请确定网络连接的状态，可能由于没有2G或者3G的网络造成测试失败
    </div>
    <br/>
    <div id="info">
      status: <span id="status"></span><br/>
    </div>
      <div class="btn large" onclick="openUrlByWifi();">openUrlByWifi</div>
      <br/>
      <div class="btn large" onclick="openUrlByMobile();">openUrlByMobile</div>
  </body>
  </html>
