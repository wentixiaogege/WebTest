<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>New Contact</title>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.1.3.js"></script>
    <script type="text/javascript">
    $(function() {
        console.log("jquery ok!");
        console.log($("#rdi0").val());

        /*
        test when a select changed.
         */
		$('#selCmd').change(function() {
			var optionSelected = $(this).find("option:selected");
			var valueSelected = optionSelected.val();
			var textSelected = optionSelected.text();
			console.log(valueSelected);
			console.log(textSelected);
			
			/**
			 * use jquery post
			 */
			$.post("<%=request.getContextPath()%>/front/cmdAction3", {
				selCmd: valueSelected, rdiParam: $("#rdi0").val()
			}, function(data) {
				console.log(data.listsmarMeterDatas); 
				/*
				how to loop json array
				 */
				var result = data.listsmarMeterDatas;
				for (var key in result) {
					if (result.hasOwnProperty(key)) {
						// here you have access to
						console.log("key:"+key+" value:"+result[key]["id"]+" "+result[key]["magI1"]);
						// var MNGR_NAME = result[key].MNGR_NAME;
						// var MGR_ID = result[key].MGR_ID;
					}
				}

			}, "json");
		});


    });
    </script>
</head>

<%
	Map<String, Object> maps = (HashMap<String, Object>) (request.getAttribute("ITU_HTML_RESPONSE"));
	String result = "";
	String checkinfo = "";
	if (null != maps) {
		result = (String) maps.get("result");
		checkinfo = (String) maps.get("checkinfo");
	}
%>
<body>
	<form action="/EmsWebSite/front/cmdAction3" method="post">
		<p>
			<select name="selCmd" id="selCmd">
				<option value="0">Parameter Register Read</option>
				<option value="1">Parameter Register Write</option>
				<option value="2">Energy Calculation Reset</option>
				<option value="3" selected="selected">Relay Control</option>
				<option value="4">Network Discovery</option>
				<option value="5">Route Table Read</option>
				<option value="6">Control Register Read</option>
				<option value="7">Data Register Read</option>
				<option value="8">Power Calculation Control</option>
				<option value="9">Voltage Calibration</option>
				<option value="10">Current Calibration</option>
				<option value="11">Energy Calibration</option>
				<option value="12">Control Register Write</option>
				<option value="13">Time Set</option>
				<option value="14">Calibration Register Read</option>

			</select>
		</p>
		<%
			if ("1".equals(checkinfo)) {
		%>
		<p>
			<input type="radio" name="rdiParam" id="rdi0" value="0"><label
				for="rdi0">Close</label> <input type="radio" checked="checked"
				name="rdiParam" id="rdi1" value="1"><label for="rdi1">
				Open</label>
		</p>
		<%
			} else {
		%>
		<p>
			<input type="radio" name="rdiParam" id="rdi0" checked="checked"
				value="0"><label for="rdi0">Close</label> <input
				type="radio" name="rdiParam" id="rdi1" value="1"><label
				for="rdi1"> Open</label>
		</p>
		<%
			}
		%>
		<input type="submit" value="Submit" />
	</form>
	<p>
		The result is:
		<%=result%>
	</p>
</body>
</html>