// var SmartMeters = [{
// 	id: "0",
// 	mac: "[0, 18, 75, 0, 4, 15, 26, 60]"
// }, {
// 	id: "1",
// 	mac: "[0, 18, 75, 0, 4, 15, 28, 119]"
// }, {
// 	id: "2",
// 	mac: "[0, 18, 75, 0, 4, 14, -15, -98]"
// }];

ItuConfiguration = (function() {
	return {
		SmartMeters: [{
			id: "0",
			mac: "[0, 18, 75, 0, 4, 15, 26, 60]"
		}, {
			id: "1",
			mac: "[0, 18, 75, 0, 4, 15, 28, 119]"
		}, {
			id: "2",
			mac: "[0, 18, 75, 0, 4, 14, -15, -98]"
		}],
		SearchPeriods: [{
			id: "1h",
			desp: "one hour"
		}, {
			id: "5h",
			desp: "5 hours"
		}, {
			id: "1d",
			desp: "one day"
		}, {
			id: "1w",
			desp: "one week"
		}, {
			id: "1m",
			desp: "one month"
		}, {
			id: "all",
			desp: "all"
		}],
		Intervals: [{
			id: "0",
			desp: "no"
		}, {
			id: "120",
			desp: "two minitues"
		}, {
			id: "600",
			desp: "ten minitues"
		}, {
			id: "3600",
			desp: "one hour"
		}, {
			id: "86400",
			desp: "one day"
		}]
	};
}());