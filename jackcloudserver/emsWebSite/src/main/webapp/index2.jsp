<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/core.css">
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/js/closure/goog/base.js"></script>
	<script src="<%=request.getContextPath()%>/js/common.js"></script>
	<script type="text/javascript">
		// load google chartclassic
	     google.load("visualization", "1", {packages: ["corechart"]});

	     // if you want to use Material Line Charts, you should use this.
	    // google.load('visualization', '1.1', {packages: ['line']});

	    // google.load("jquery", "2.1.3");
	    google.setOnLoadCallback(function() {
	    	var refreshTimebeforeCurrId;
	    	var selTimebeforeCurr = goog.dom.getElement('selTimebeforeCurr');
	    	var selInterval = goog.dom.getElement('selInterval');
	    	var selSmartMeter = goog.dom.getElement('selSmartMeter');
	    	doRequest("0", selSmartMeter.value, selInterval.value, selTimebeforeCurr.value);
	    	// alert("ok");
	    	document.getElementById("btnRefresh").onclick = function() {
	    		if (this.value === "refresh stop") {
	    			clearTimebeforeCurr(refreshTimebeforeCurrId);
	    			this.value = "refresh start"
	    		} else {
	    			refreshTimebeforeCurrId = doRefresh();
	    			this.value = "refresh stop";
	    		}
	    		// doRequest($("#hidNum").val());
	    	};

	    	// refreshTimebeforeCurrId = doRefresh();

	    	/*
	    	*	add select event.
	    	*/
			goog.events.listen(selTimebeforeCurr, goog.events.EventType.CHANGE, function() {
				// one day interval 10 minites
				var value = selInterval.value;
				if (this.value === '1d') {
					value = selInterval[2].value;
				} else if (this.value === "1w") {
					// 1 week one hour
					value = selInterval[3].value;
				} else if (this.value === "1m") {
					// one month one day
					value = selInterval[4].value;
				} else {
					value = selInterval[1].value;
				}

				doRequest("0", selSmartMeter.value, value, this.value);
				selInterval.value = value;
			});

	    	/*
	    	* SmartMeter Change
	    	*/
			goog.events.listen(selSmartMeter, goog.events.EventType.CHANGE, function() {
				// alert(this.value);
				doRequest("0", this.value, selInterval.value, selTimebeforeCurr.value);
			});

			/*
			* selInterval Change
			*/
			goog.events.listen(selInterval, goog.events.EventType.CHANGE, function() {
				// alert(this.value);
				doRequest("0", selSmartMeter.value, this.value, selTimebeforeCurr.value);
			});
	    });


	    function doRefresh(){
	    	return setTimebeforeCurr(function() {
	    		doRequest($("#hidNum").val(), selTimebeforeCurr.value);
	    	}, 2000);
	    }

	    goog.require('goog.dom');
	    goog.require('goog.net.XhrIo');
	    goog.require('goog.structs.Map');
	    goog.require('goog.Uri.QueryData');
	             
	    function doRequest(num, smId, interval, timebef, startTime, endTime) {
	    	var timebeforecurr = timebef === undefined ? 360000 :timebef;
	    	var sTime, eTime;

	    	// sTime = startTime === undefined ? 0 :startTime;

	    	// eTime = endTime === undefined ? 0 :endTime;
	    	// if(undefined === endTime)
	    	// 	endTime = 0;
	    	// if (arguments.length === 2)
	    	// 	timebeforecurr = arguments[1];

	    	// create the xhr object
	    	var request = new goog.net.XhrIo();

	    	// create a QueryData object by initializing it with a simple Map
	    	var data = goog.Uri.QueryData.createFromMap(new goog.structs.Map({
	    		testNum: num,
	    		smId: smId,
	    		timebeforecurr: timebeforecurr,
	    		startTime: startTime,
	    		endTime: endTime,
	    		interval: interval
	    	}));

	    	// listen to complete event
	    	goog.events.listen(request, 'complete', function() {

	    		if (request.isSuccess()) {

	    			// inject response into the dom
	    			// goog.dom.$('response').innerHTML = request.getResponseText();
	    			console.log(request.getResponseText());

	    			// print confirm to the console
	    			console.log('Satus code: ', request.getStatus(), ' - ', request.getStatusText());

	    			var jsonResult = JSON.parse(request.getResponseText());
	    			drawChart(jsonResult, "chart_rmsV1", 'rmsV1');
	    			drawChart(jsonResult, "chart_rmsI1", 'rmsI1');

	    		} else {

	    			// print error info to the console
	    			console.log(
	    				'Something went wrong in the ajax call. Error code: ', request.getLastErrorCode(),
	    				' - message: ', request.getLastError()
	    			);

	    		}

	    	});

	    	// start the request by setting POST method and passing
	    	// the data object converted to a queryString
	    	request.send('/EmsWebSite/front/smartmetermonitoraction', 'POST', data.toString());

	    }

	    function convertToArray(info, jsonKey){
	    	var resultInfo = info.ListSmartMeterData;
	    	var results = [];

	    	var len = resultInfo.length;
			for (var i = 0; i < len; i++) {
				var info = [];
				var date = new Date(resultInfo[i].timestamp);
				info.push(date);
				info.push(resultInfo[i][jsonKey]);
				info.push(resultInfo[i][jsonKey]+"");
				// info.push(resultInfo[i][jsonKey]);
				var clor = "rmsV1"===jsonKey ?'#3366CC':'#DC3912';
				info.push("<span style='font-size:20px;'>"+ formatTime(date)+ " <b style='color:"+clor+"'>Value:" + resultInfo[i][jsonKey] + "</b></span>");
				// info.push(""+resultInfo[i][jsonKey]+' ');
				results.push(info);
			};

	    	// results[0][0] = resultInfo[0].timestamp;
	    	// results[0][1] = resultInfo[0].rmsV1;
	    	// results[1][0] = resultInfo[1].timestamp;
	    	// results[1][1] = resultInfo[1].rmsV1;
	    	return results;
	    }

	    /*
	    *	draw chart for data.
	     */
	    function drawChart(content, divName, jsonKey) {
	    	var data = new google.visualization.DataTable();
	    	data.addColumn('datetime', 'Time');
	    	data.addColumn('number', jsonKey);
	    	// data.addColumn({type:'string', role:'tooltip', p: {html:true}});
			data.addColumn({
				type: 'string',
				role: 'annotation'
			});
			data.addColumn({
				type: 'string',
				role: 'annotationText',
				p: {html: true}
			});

	    	data.addRows(convertToArray(content, jsonKey));

			var options = {
				chart: {
					title: 'The Chart of ' + jsonKey
						// subtitle: 'in millions of dollars (USD)'
				},
				// curveType: 'function',
				legend: {
					// position: 'left'
				},
				min: 0,
				max: jsonKey === "rmsV1" ? 1200 : (jsonKey === "rmsI1" ? 4 : "automatic"),
				// hAxis.format:'MM-dd HH:mm',
				// displayAnnotations: false,
				// legend: 'none',
				tooltip: {
					isHtml: true,
					trigger: 'selection'
				},
				explorer: {
					 axis: 'horizontal',
					  keepInBounds: true,
					  maxZoomOut:1 
				},
				colors: [jsonKey === "rmsV1" ? '#3366CC' : '#DC3912'],
				// // scaleColumns: [2, 3],
				// legendPosition: 'newRow',
				// // colors: ['red'],
				// dateFormat: "MM-dd HH:mm",
				hAxis: {
					format: 'MM-dd HH:mm',
					title: 'Time'
				},
				vAxis: {
					title: jsonKey === "rmsV1" ? 'Volt' : 'Abe'
				},
				legend: {
					position: 'top',
					alignment: 'center'
				},
				width: 850,
				height: 500
			};

	    	var chart = new google.visualization.LineChart(document.getElementById(divName));
	    	chart.draw(data, options);

	    	// this is for Material Line Charts
	    	// var chart = new google.charts.Line(document.getElementById(divName));
	    	// chart.draw(data, google.charts.Line.convertOptions(options));
	    	// chart.select(function() {
	    	// 	var info = getSelection();
	    	// 	console.log(info);
	    	// });
	    	// 
			// google.visualization.events.addListener(chart, 'onmouseover', select_handler);

			function select_handler(e) {
				// console.log('You changed the range to ', e['start'], ' and ', e['end']);
				var data = convertToArray(content, jsonKey);
				console.log(data);
				console.log(e);
			}

	    }
	</script>
</head>
<body>
    <H2>Smart Meter Infomation Display</H2>
    <input type="button" value="refresh start" id="btnRefresh" />
    <p>
    	Select Before Current:
    	<select id = "selTimebeforeCurr">
    		<option value="1h">one hour</option>
  			<option value="5h">five hour</option>
  			<option value="1d">one day</option>
	        <option value="1w">one week</option>
  			<option value="1m">one month</option>
  			<option value="all">all</option>
    	</select>
    	&nbsp;
    	SmartMeter:
    	<select id = "selSmartMeter">
    		<option value="0" selected="selected">[0, 18, 75, 0, 4, 15, 26, 60]</option>
  			<option value="1">[0, 18, 75, 0, 4, 15, 28, 119]</option>
  			<option value="2">[0, 18, 75, 0, 4, 14, -15, -98]</option>
    	</select>
    	&nbsp;
    	InterVal:
    	<select id = "selInterval">
    		<option value="0" >no</option>
    		<option value="120" selected="selected">two minitues</option>
    		<option value="600" >ten minitues</option>
  			<option value="3600">one hour</option>
  			<option value="86400">one day</option>
    	</select>
    </p>
    <input type="hidden" id="hidNum" />
	<div id="chart_rmsV1"></div>
	<div id="chart_rmsI1"></div>
</body>
</html>