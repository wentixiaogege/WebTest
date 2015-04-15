angular.module('admin', ['ui.bootstrap', 'dialogs.main', 'pascalprecht.translate', 'dialogs.default-translations']);
angular.module('admin').controller('SmartMeterLightCtrl', function($scope, $http, $window, dialogs) {
	$scope.alerts = [];

	// $scope.alertMe = function() {
	// 	setTimeout(function() {
	// 		$window.alert('You\'ve selected the alert tab!');
	// 	});
	// };

	$scope.doRequest = function(url) {
		$http({
			method: 'GET',
			url: "testdata.json",
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
			// data: $.param($scope.getSearchParam())
		}).success(function(data, status, headers, config) {
			console.log("success");
			$scope.pri_smartmeters = data;
			$scope.smartmeters = addDispalyOptions(data);
		}).
		error(function(data, status, headers, config) {
			console.log("errors");
			console.log(status);
			$scope.errors = [];
			$scope.errors.push(data);
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.go = function() {

		console.log("go");
		// console.log($("#testsel").length);
		// $("#testsel").bootstrapSwitch();
	}

	$scope.change_temp = function(light, min_temp, max_temp) {
		// $window.alert("ok");
		// dialogs.notify('Something Happened!','Something happened that I need to tell you.');



		$scope.alerts = [];

		if ($window.isNaN(min_temp) || $window.isNaN(max_temp)) {
			$scope.alerts.push({
				msg: "temprature must be a number",
				"type": "danger"
			});
			return;
		}

		if (Number(min_temp) >= Number(max_temp)) {
			$scope.alerts.push({
				msg: "min temprature must be not bigger than max temprature",
				"type": "danger"
			});
			return;
		}

		var dlg = dialogs.confirm("Change Temprature", "Do you want to change min and max temprature?");
		dlg.result.then(function() {
			// $scope.confirmed = 'You confirmed "Yes."';
			light.min_temp = min_temp;
			light.max_temp = max_temp;
		}, function() {
			console.log("not change");
			// $scope.confirmed = 'You confirmed "No."';
		});

	}

	$scope.change_light = function(light, status) {
		var light_status = status.toString() === "1" ? '0' : '1';
		light.status = light_status;

		var msg = "do you want to " + (light_status === "0" ? " shut down " : " open ") + "light?";
		var dlg = dialogs.confirm("Lights", msg);
		dlg.result.then(function() {
			// $scope.confirmed = 'You confirmed "Yes."';
			light.light_class = light_status === "1" ? "btn-success" : "btn-default";
			light.light_disp = light_status === "1" ? "ON" : "OFF";
			light.light_pic_class = light_status === "1" ? "light_on" : "light_off";
		}, function() {
			console.log("not change");
			// $scope.confirmed = 'You confirmed "No."';
		});


	}


	$scope.doRequest();
});

// .config(function($translateProvider){
// 		/**
// 		 * These are the defaults set by the dialogs.main module when Angular-Translate
// 		 * is not loaded.  You can reset them in your module's configuration
// 		 * function by using $translateProvider.  If you want to use these when
// 		 * Angular-Translate is used and loaded, then you need to also load
// 		 * dialogs-default-translations.js or include them where you are setting
// 		 * translations in your module.  These were separated out when Angular-Translate
// 		 * is loaded so as not to clobber translation set elsewhere in one's 
// 		 * application.
// 		 */
// 		$translateProvider.translations('en-US',{
// 	        DIALOGS_ERROR: "Error",
// 	        DIALOGS_ERROR_MSG: "An unknown error has occurred.",
// 	        DIALOGS_CLOSE: "Close",
// 	        DIALOGS_PLEASE_WAIT: "Please Wait",
// 	        DIALOGS_PLEASE_WAIT_ELIPS: "Please Wait...",
// 	        DIALOGS_PLEASE_WAIT_MSG: "Waiting on operation to complete.",
// 	        DIALOGS_PERCENT_COMPLETE: "% Complete",
// 	        DIALOGS_NOTIFICATION: "Notification",
// 	        DIALOGS_NOTIFICATION_MSG: "Unknown application notification.",
// 	        DIALOGS_CONFIRMATION: "Confirmation",
// 	        DIALOGS_CONFIRMATION_MSG: "Confirmation required.",
// 	        DIALOGS_OK: "OK",
// 	        DIALOGS_YES: "Yes",
// 	        DIALOGS_NO: "No"
//         });

// 	});

function addDispalyOptions(smartmeters) {
	var len = smartmeters.length;

	for (var i = 0; i < len; i++) {
		var smartmeter = smartmeters[i];
		for (var j = 0; j < smartmeter.lights.length; j++) {
			var light = smartmeter.lights[j];

			// add property light_class and light_disp
			var light_status = light.status.toString();
			light.light_class = light_status === "1" ? "btn-success" : "btn-default";
			light.light_disp = light_status === "1" ? "ON" : "OFF";
			light.light_pic_class = light_status === "1" ? "light_on" : "light_off";
		};
	};

	return smartmeters;
}