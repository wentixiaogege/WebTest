<!doctype html>
<html ng-app="myApp">

<head>
   	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="peter">
    <link rel="icon" href="../../favicon.ico">

    <title>SmartMeter</title>

    <!-- Bootstrap core CSS -->
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/custom_bootstrap.css" rel="stylesheet">

    <link href="<%=request.getContextPath()%>/js/jquery-ui/jquery-ui.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/core.css" rel="stylesheet">

    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.3.js"></script>
    <script src="<%=request.getContextPath()%>/js/jquery-ui/jquery-ui.js"></script>
    <script src="<%=request.getContextPath()%>/js/angular.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/common.js"></script>
    <script src="<%=request.getContextPath()%>/js/chart.js"></script>

    <script type="text/javascript">
    // load google chartclassic
    google.load("visualization", "1", {
        packages: ["corechart"]
    });

    </script>
</head>

<body  ng-controller="ChartController">
    <div class="container">
        <H2 id="title">Smart Meter Infomation Display</H2>
        <!-- <input type="button" value="refresh start" id="btnRefresh" /> -->
        <div class="form-group has-error">
            <ul>
                <li class="control-label" ng-repeat=" error in errors" ng-bind-html-unsafe="error" >
                    {{ error }}
                </li>
            </ul>
        </div>
        <table class="table table-bordered">
        <tbody>
        	<tr>
        		<th class="vcenter text-nowrap">Search Type</th>
        		<td class="vcenter">
        			<input type="radio" ng_change="changeSearTime(searchTimeType.val)" id="rdoDate" ng-model="searchTimeType.val" value="0">
                    <label for="rdoDate">{{searchTimeType.date_desc}}</label>
                </td>
                <td class="vcenter">
                    <input type="radio" ng_change="changeSearTime(searchTimeType.val)" ng-model="searchTimeType.val" name="selType" id="rdoPeriod" value="1">
                    <label for="rdoPeriod">{{searchTimeType.per_desc}}</label>
        		</td>
        		
        		<th class="text-nowrap vcenter">SmartMeter</th>
                <td colspan="2">
                    <select class="form-control" id="selSmartMeter" ng-model="smartmeter" ng-change="updateSmartMeter(smartmeter)" ng-options="option as option.mac for option in  SmartMeters"></select>
                </td>
        	</tr>
        	<tr>
                <th id="thTimeSpan" class="text-nowrap vcenter">{{searchTimeType.desp}}</th>
        		<td colspan="2" class="text-center">
                    <select class="form-control" id="searchPeriod" ng-model="peroid" ng-options="option as option.desp for option in  SearchPeriods"></select>
                    <span style="display:none" id="spanDatePicker">
                        <input type="text" ng-model="startDate" class="form-control devide" id="datefrom" name="datefrom"> --- <input ng-model="endDate" class="form-control devide" type="text" id="dateto" name="dateto">
                    </span>
        		</td>
        		<th class="text-nowrap vcenter">InterVal</th>
                <td>
                    <select class="form-control" id="selInterval" ng-model="interval" ng-change="updateInterval(interval)" ng-options="option as option.desp for option in  Intervals"></select>
                </td>
                <td class="text-right">
                    <button class="btn btn-default optmize_width" ng-click="confirm()">Confirm</button>
                </td>
            </tr>
            
        </tbody>
		</table>

   	</div>
    <div class="container withborder" id="chartDiv">
        <div id="accordion">
        </div>
    </div>
    <input type="hidden" id="hidNum" />
    <input type="hidden" id="hidRoot" value="<%=request.getContextPath()%>" />
</body>

</html>
