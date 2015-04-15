<html>
<head>

  <script type="text/javascript" src="https://www.google.com/jsapi"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.js"></script>
  <script src="<%=request.getContextPath()%>/js/closure/goog/base.js"></script>
  <script type="text/javascript">
    google.load('visualization', '1.1', {packages: ['line']});
    // google.load("jquery", "2.1.3");
    google.setOnLoadCallback(doRequest);

    goog.require('goog.dom');
    goog.require('goog.net.XhrIo');
    goog.require('goog.structs.Map');
    goog.require('goog.Uri.QueryData');
             
    function doRequest() {

        // create the xhr object
        var request = new goog.net.XhrIo();

        // create a QueryData object by initializing it with a simple Map
        var data = goog.Uri.QueryData.createFromMap(new goog.structs.Map({
            title: 'Test ajax data',
            content: 'foo',
            param1: 2500
        }));

        // listen to complete event
        goog.events.listen(request, 'complete', function() {

            if (request.isSuccess()) {

                // inject response into the dom
                // goog.dom.$('response').innerHTML = request.getResponseText();
                console.log(request.getResponseText());

                // print confirm to the console
                console.log('Satus code: ', request.getStatus(), ' - ', request.getStatusText());

                drawChart(JSON.parse(request.getResponseText()));

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
        request.send('/EmsWebSite/front/cmdAction3', 'POST', data.toString());

    }

    function drawChart(content) {

      var data = new google.visualization.DataTable();
      data.addColumn('number', 'Day');
      data.addColumn('number', 'Guardians of the Galaxy');
      data.addColumn('number', 'The Avengers');
      data.addColumn('number', 'Transformers: Age of Extinction');

      data.addRows(content);

      var options = {
        chart: {
          title: 'Box Office Earnings in First Two Weeks of Opening',
          subtitle: 'in millions of dollars (USD)'
        },
        width: 900,
        height: 500
      };

      var chart = new google.charts.Line(document.getElementById('linechart_material'));

      chart.draw(data, options);
      $("#txtTest").val("jquery ok");
      // alert($)
    }
  </script>
</head>
<body>
    <input type="text" id ="txtTest" />
  <div id="linechart_material"></div>
</body>
</html>