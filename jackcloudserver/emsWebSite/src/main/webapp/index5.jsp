<!doctype html>
<html ng-app="myApp">

<head>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="js/jquery-2.1.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/angular.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>
    <script src="<%=request.getContextPath()%>/js/chart.js"></script>

    <script type="text/javascript">
    // load google chartclassic
    google.load("visualization", "1", {
        packages: ["corechart"]
    });
    </script>
</head>

<body>
    <div class="main" ng-controller="ChartController">
        <H2 id="title">Smart Meter Infomation Display</H2>
        <!-- <input type="button" value="refresh start" id="btnRefresh" /> -->
        <div class="filter">
            <div class="row">
                <div class="col label">Select Type</div>
                <div class="col">
                    <input type="radio" id="rdoDate" name="selType" value="datePicer">
                    <label for="rdoDate">Date Picker</label>
                    <input type="radio" name="selType" id="rdoPeriod" value="periodPicker">
                    <label for="rdoPeriod">Period Picker</label>
                </div>
                <div class="col label">Select Before Current</div>
                <div class="col">
                    <select id="selTimebeforeCurr">
                        <option value="1h">one hour</option>
                        <option value="5h">five hour</option>
                        <option value="1d">one day</option>
                        <option value="1w">one week</option>
                        <option value="1m">one month</option>
                        <option value="all">all</option>
                    </select>
                </div>
                <div class="col label">SmartMeter</div>
                <div class="col endcol">
                    <select id="selSmartMeter" ng-init="smartmeter = SmartMeters[0]" ng-model="smartmeter" ng-change="updateSmartMeter(smartmeter)" ng-options="option as option.mac for option in  SmartMeters"></select>
                </div>
            </div>
            <div class="row">
                <div class="col endrow label">InterVal</div>
                <div class="col endrow">
                    <select id="selInterval">
                        <option value="0">no</option>
                        <option value="120" selected="selected">two minitues</option>
                        <option value="600">ten minitues</option>
                        <option value="3600">one hour</option>
                        <option value="86400">one day</option>
                    </select>
                </div>
                <div class="col endrow">&nbsp;</div>
                <div class="col endrow endcol">
                    <button ng-click="confirm()">Confirm</button>
                </div>
            </div>

        </div>
        <!-- <input type = "button" id="btnTest" value="test" onclick="testObj()" /> -->
        <!-- confirmBtn -->
        <!-- <div>{{playing}}</div>     -->
        <input type="hidden" id="hidNum" />
        <input type="hidden" id="hidRoot" value="<%=request.getContextPath()%>" />
        <div id="chartDiv" class="content">
            <div id="chart_rmsV1"></div>
            <div id="chart_rmsI1"></div>
        </div>
    </div>
</body>

</html>
