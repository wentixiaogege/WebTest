$.getScript("js/config.js", function() {
	console.log("config.js loaded");
})

function DispalyObj(name, min, max, colors, vAxisName, field) {
	this.name = name;
	this.min = min;
	this.max = max;
	this.colors = colors;
	this.vAxisName = vAxisName;
	this.chartDivName = "chart_"+name;
	this.data_field = field;
	// this.template = '<h3>'+name+'</h3> <div> <div id="chart_rmsI1" class=".chart-div"></div> <div id="chart_rmsV1" class=".chart-div"></div> </div>';
}

DispalyObj.prototype.setChartDivname = function(name) {
	this.chartDivName = name;
}

DispalyObj.prototype.getData = function convertToArray(resultInfo) {
	// var resultInfo = data.ListSmartMeterData;
	var results = [];

	var len = resultInfo.length;
	for (var i = 0; i < len; i++) {
		var info = [];
		var date = new Date(resultInfo[i].timestamp);
		info.push(date);
		info.push(resultInfo[i][this.data_field]);
		info.push(resultInfo[i][this.data_field] + "");
		// info.push(resultInfo[i][this.data_field]);
		var clor = this.colors[0];
		info.push("<span style='font-size:20px;'>" + formatTime(date) + " <b style='color:" + clor + "'>Value:" + resultInfo[i][this.data_field] + "</b></span>");
		// info.push(""+resultInfo[i][this.name]+' ');
		results.push(info);
	};

	// results[0][0] = resultInfo[0].timestamp;
	// results[0][1] = resultInfo[0].rmsV1;
	// results[1][0] = resultInfo[1].timestamp;
	// results[1][1] = resultInfo[1].rmsV1;
	return results;
}

function testObj() {
	console.log("just a test");
	var obj1 = new DispalyObj("rmsV1", 0, 1200, ['#3366CC'], 'Volt');

	var obj2 = new DispalyObj("rmsI1", 0, 4, ['#DC3912'], 'Abe');

	console.log(obj1);
	console.log(obj2);
	console.log($("#selInterval").val("3600"));
}

var app = angular.module('myApp', []);

/**
 * ChartController control the chart display
 */
app.controller('ChartController', ['$scope', '$http', function($scope, $http) {

	console.log("ChartController initing...");
	$scope.playing = false;
	// $scope.rmsV1 = new DispalyObj("rmsV1", 0, 300, ['#3366CC'], 'Volt');
	// $scope.rmsI1 = new DispalyObj("rmsI1", 0, 2.5, ['#DC3912'], 'Abe');

	// $scope.rmsV1.setChartDivname("chart_rmsV1");
	// $scope.rmsI1.setChartDivname("chart_rmsI1");

	// $scope.rmsV2 = new DispalyObj("rmsV2", 0, 300, ['#3366CC'], 'Volt');
	// $scope.rmsI2 = new DispalyObj("rmsI2", 0, 2.5, ['#DC3912'], 'Abe');

	// $scope.rmsV2.setChartDivname("chart_rmsV2");
	// $scope.rmsI2.setChartDivname("chart_rmsI2");

	// the information of smartmeter
	$scope.SmartMeters = ItuConfiguration.SmartMeters;
	$scope.SearchPeriods = ItuConfiguration.SearchPeriods;
	$scope.Intervals = ItuConfiguration.Intervals;
	// current information
	$scope.smartmeter = $scope.SmartMeters[0];
	$scope.peroid = $scope.SearchPeriods[0];
	$scope.interval = $scope.Intervals[1];
	$scope.startDate = "";
	$scope.endDate = "";
	// searchTimeType: 0: peroid, 1, date range.
	$scope.searchTimeType = {
		val: "1",
		desp: "Period",
		per_desc: "Period Picker",
		date_desc: "Date Picker"
	};

	//errors
	$scope.errors = [];
	$scope.AccordionContents = [];
	$scope.loads=[];

	$scope.changeSearTime = function(val) {
		if (val === "0") {
			$("#spanDatePicker").show();
			$("#searchPeriod").hide();
			$scope.searchTimeType.desp = "Date";
			// $("#thTimeSpan").text('Date');
		} else if (val === "1") {
			$("#searchPeriod").show();
			$("#spanDatePicker").hide();
			$scope.searchTimeType.desp = "Period";
			// $("#thTimeSpan").text('Period');
		}
	}

	//accordion
	$("#accordion").accordion({
		heightStyle: "content"
	});

	// date picker
	$("#datefrom").datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 1,
		onClose: function(selectedDate) {
			$("#dateto").datepicker("option", "minDate", selectedDate);
		},
		onSelect: function(dateText, inst) {
			console.log(dateText);
			// $scope.searchParam.startDate = dateText;
			$scope.startDate = dateText;
		}
	});
	$("#dateto").datepicker({
		defaultDate: "+1w",
		changeMonth: true,
		numberOfMonths: 1,
		onClose: function(selectedDate) {
			$("#datefrom").datepicker("option", "maxDate", selectedDate);
		},
		onSelect: function(dateText, inst) {
			console.log(dateText);
			// $scope.searchParam.endDate = dateText;
			$scope.endDate = dateText;
		}
	});

	$scope.doRequest = function(url) {
		$http({
			method: 'POST',
			url: url,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			data: $.param($scope.getSearchParam())
		}).success(function(data, status, headers, config) {
			console.log(JSON.stringify(data));
			console.log(status);
			//init data
			$scope.loads = [];
			$("#accordion").empty();
			var len = data.length;

			//storage data
			for (var i = 0; i < len; i++) {
				var loadName = data[i].LoadName;
				var loadId = data[i].LoadId;
				var voltage = new DispalyObj(loadName + "_rmsV", 0, 300, ['#3366CC'], 'Volt', "rmsV1");
				var current = new DispalyObj(loadName+"_rmsI", 0, 2.5, ['#DC3912'], 'Abe', "rmsI1");
				$scope.loads.push({
					loadname: loadName,
					loadid: loadId,
					voltageO: voltage,
					currentO: current,
					data: data[i].Data,
					template: '<h3>'+loadName+'</h3> <div class="hideOverflow"> <div id="'+voltage.chartDivName+'" class="chart-div"></div> <div id="'+current.chartDivName+'" class="chart-div"></div> </div>'
				});
			};

			for (var i = 0; i < len; i++) {
				$("#accordion").append($scope.loads[i].template);
			};

			for (var i = 0; i < len; i++) {
				// data[i]
				drawChart($scope.loads[i].data, $scope.loads[i].voltageO);
				drawChart($scope.loads[i].data, $scope.loads[i].currentO);
			};

			$("#accordion").accordion("refresh");
	        $("#accordion").accordion( "option", "active", 0);

		}).
		error(function(data, status, headers, config) {
			console.log(data);
			$scope.errors=[];
			$scope.errors.push(data);
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.getSearchParam = function() {
		return {
			smId: $scope.smartmeter.id,
			peroid: $scope.peroid.id,
			startDate: $scope.startDate,
			endDate: $scope.endDate,
			interval: $scope.interval.id,
			searchTimeType: $scope.searchTimeType.val
		};
	};

	$scope.confirm = function() {
		console.log("submitting");
		console.log($("#datefrom").val());
		if ($scope.docheck())
			$scope.doRequest($("#hidRoot").val() + "/front/smartmetermonitoraction");
	}

	$scope.docheck = function() {
		if ($scope.searchTimeType.val === "0") {
			if ($scope.startDate === "" || $scope.endDate === "") {
				$scope.errors = [];
				$scope.errors.push("either startDate or endDate can not be null!");
				return false;

			}
		}
		return true;
	};

	$scope.confirm();
}]);

// app.directive('ngSparkline', function() {
// 	  return {
// 	    restrict: 'A',
// 	    replace: false,
// 	    template: '<h3>Load Two2</h3> <div> sdfsdfsd </div>',
// 	    scope: {
//             content: '='
//         }
// 	}
// });


// app.directive('ngSparkline', function($compile) {

//     var testTemplate1 = '<h3>Load One</h3> <div> load1</div>';
//     var testTemplate2 = '<h3>Load two</h3><div><div id="chart_rmsI1" class=".chart-div"></div></div>';
//     var testTemplate3 = '<h3>Load three</h3> <div> load3</div>';

//     var getTemplate = function(contentType) {
//         var template = '';

//         template = '<h3>Load One</h3> <div> load0</div>';
//         switch (contentType) {
//             case 'load1':
//                 template = testTemplate1;
//                 break;
//             case 'load2':
//                 template = testTemplate2;
//                 break;
//             case 'load3':
//                 template = testTemplate3;
//                 break;
//         }

//         return template;
//     };

//     var linker = function(scope, element, attrs) {
//     	//test
//         element.parent().append(getTemplate(scope.content));
//         drawChart
//         $compile(element.parent().contents())(scope);
//         element.remove();
//         //accordion
// 		$("#accordion").accordion("refresh");
// 		// scope.confirm();
//     };

//     return {
//         restrict: "E",
//         replace: false,
//         link: linker,
//         scope: {
//             content: '='
//         }
//     };
// });

/*
 *	draw chart for data.
 */
function drawChart(content, obj) {
	var data = new google.visualization.DataTable();
	data.addColumn('datetime', 'Time');
	data.addColumn('number', obj.name);
	// data.addColumn({type:'string', role:'tooltip', p: {html:true}});
	data.addColumn({
		type: 'string',
		role: 'annotation'
	});
	data.addColumn({
		type: 'string',
		role: 'annotationText',
		p: {
			html: true
		}
	});

	data.addRows(obj.getData(content));
	console.log("accordion width:"+$('#accordion').width());
	console.log("chartDiv width:"+$('#chartDiv').width());
	var chart_width = $('#chartDiv').width() -38;
	var options = {
		chart: {
			title: 'The Chart of ' + obj.name
				// subtitle: 'in millions of dollars (USD)'
		},
		// curveType: 'function',
		legend: {
			// position: 'left'
		},
		min: 0,
		max: obj.max,
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
			maxZoomOut: 1
		},
		colors: obj.colors,
		// // scaleColumns: [2, 3],
		// legendPosition: 'newRow',
		// // colors: ['red'],
		// dateFormat: "MM-dd HH:mm",
		hAxis: {
			format: 'MM-dd HH:mm',
			title: 'Time'
		},
		vAxis: {
			title: obj.vAxisName
		},
		legend: {
			position: 'top',
			alignment: 'center'
		},
		width: chart_width,
		height: 300
	};

	var chart = new google.visualization.LineChart(document.getElementById(obj.chartDivName));
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

	// function select_handler(e) {
	// 	// console.log('You changed the range to ', e['start'], ' and ', e['end']);
	// 	var data = convertToArray(content, jsonKey);
	// 	console.log(data);
	// 	console.log(e);
	// }

}

function convertToArray(info, obj) {
	var resultInfo = info;
	var results = [];

	var len = resultInfo.length;
	for (var i = 0; i < len; i++) {
		var info = [];
		var date = new Date(resultInfo[i].timestamp);
		info.push(date);
		info.push(resultInfo[i][obj.name]);
		info.push(resultInfo[i][obj.name] + "");
		// info.push(resultInfo[i][obj.name]);
		var clor = obj.colors[0];
		info.push("<span style='font-size:20px;'>" + formatTime(date) + " <b style='color:" + clor + "'>Value:" + resultInfo[i][obj.name] + "</b></span>");
		// info.push(""+resultInfo[i][obj.name]+' ');
		results.push(info);
	};

	// results[0][0] = resultInfo[0].timestamp;
	// results[0][1] = resultInfo[0].rmsV1;
	// results[1][0] = resultInfo[1].timestamp;
	// results[1][1] = resultInfo[1].rmsV1;
	return results;
}