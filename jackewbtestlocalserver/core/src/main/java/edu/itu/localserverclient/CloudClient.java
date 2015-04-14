package edu.itu.localserverclient;
/*

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.itu.dto.DataAccess;
import com.itu.myserver.CloudCommandProtos.CloudCommand;
import com.itu.myserver.CloudCommandProtos.CloudCommand.Builder;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.myserver.SmartMeterDataRecordsProtos.SmartMeterDataRecord;
import com.itu.util.ConnectionUtil;
import com.itu.util.Log4jUtil;

public class CloudClient {

	static String url = "http://172.16.5.157:8080/dataservertest/rest/localserverget/cloudcommand";
	static String urlPost = "http://172.16.5.157:8080/dataservertest/rest/frontendpost";
	
	static String urlPostSmartMeterRecord = "http://172.16.5.157:8080/dataservertest/rest/localserverpost/smartmeterrecord";
	static String urlPostSmartMeterData = "http://172.16.5.157:8080/dataservertest/rest/localserverpost/smartmeterdata";
	
	Logger logger = Log4jUtil.getLogger(FrontServer.class);

	*//**
	 * pass Class CloudCommandProtos through google buffer.
	 * 
	 * @param cmdId
	 *//*
	public static void main(String[] args) throws IOException {
	
		CloudClient local = new CloudClient();
		while (true) 
		{
			//local.getNewCommands(url);
			//local.postCommand("1","0");
			//local.postCommand("2","1");
			
			//local.getNewCommands(url);
			//local.getNewCommands(url);
			
			local.postSmartMeterDataRecords("2");
			//local.postSmartMeterRecord("64000");
			
			try {
			
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
		       	
		    	   System.out.println("In Java, local server client can't get commands from data server.");
		           e.printStackTrace();
		   }
		//}

	}
	
	private  void getNewCommands(String url) throws IOException {
		// TODO Auto-generated method stub
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/x-protobuf");
		conn.setRequestProperty("Accept", "application/x-protobuf");
		conn.connect();
		// check response code
		int code = conn.getResponseCode();
		boolean success = (code >= 200) && (code < 300);
		// @SuppressWarnings("resource")
		InputStream in = success ? conn.getInputStream() : conn
				.getErrorStream();

		int size = conn.getContentLength();
		//logger.debug("message:" + conn.getResponseMessage());
		logger.debug("code:" + code);
		byte[] response = new byte[size];
		int curr = 0, read = 0;

		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0)
				break;
			curr += read;
		}

		CloudCommand cmd = CloudCommand.parseFrom(response);
		
		
		logger.debug(String.format("id:%d, name:%s, param1:%s", cmd.getId(),cmd.getName(),cmd.getParam1()));

		
		DataAccess.localserver_addNewCommand(cmd);
		for (Person p : ab.getPersonList()) {
			logger.debug(String.format("id:%d, name:%s, mail:%s", p.getId(),
					p.getName(), p.getEmail()));
		}

	}
	
	public String postSmartMeterRecord(String Id) {
		// File file = new File(CreatePerson.FILE_PATH + name + ".per");
		// Person p = Person.parseFrom(new FileInputStream(file));
		//Builder bd = CloudCommand.newBuilder();
		//CloudCommand cc = bd.setId(Integer.parseInt(Id)).setParam1(Integer.parseInt(param1)).build();
		SmartMeterDataRecord smartMeterDataRecord = com.itu.dto.DataAccess.cloudClient_getSmartMeterDataRecord(Integer.parseInt(Id));
		logger.debug("smartMeterRecord:" + smartMeterDataRecord.getRmsV1());
		
		byte[] content = smartMeterDataRecord.toByteArray();
		try {

			HttpURLConnection conn = ConnectionUtil.getProtoConnection(urlPostSmartMeterRecord, "POST");
			conn.setRequestProperty("Content-Length", Integer.toString(content.length));
			// set stream mode to decrease memory usage
			conn.setFixedLengthStreamingMode(content.length);
			OutputStream out = conn.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
			conn.connect();
			// check response code
			int code = conn.getResponseCode();
			boolean success = (code >= 200) && (code < 300);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = in.readLine();
			// while ((inputLine = in.readLine()) != null)
			// logger.debug("input line:" + inputLine);
			in.close();
			// conn.getr
			logger.debug("message:" + conn.getResponseMessage());
			logger.debug("code:" + code);
			return inputLine;

		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}

	}
	
	public String postSmartMeterDataRecords(String Id) {
		// File file = new File(CreatePerson.FILE_PATH + name + ".per");
		// Person p = Person.parseFrom(new FileInputStream(file));
		//Builder bd = CloudCommand.newBuilder();
		//CloudCommand cc = bd.setId(Integer.parseInt(Id)).setParam1(Integer.parseInt(param1)).build();
		SmartMeterDataRecords smartMeterDataRecords = com.itu.dto.DataAccess.cloudClient_getSmartMeterDataRecords(Integer.parseInt(Id));
		*//**
		for (SmartMeterRecord smartmeterRecord : smartMeterData.getSmartMeterRecordList()) {
			
		}
		*//*
		
		byte[] content = smartMeterDataRecords.toByteArray();
		try {

			HttpURLConnection conn = ConnectionUtil.getProtoConnection(urlPostSmartMeterData, "POST");
			conn.setRequestProperty("Content-Length", Integer.toString(content.length));
			// set stream mode to decrease memory usage
			conn.setFixedLengthStreamingMode(content.length);
			OutputStream out = conn.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
			conn.connect();
			// check response code
			int code = conn.getResponseCode();
			boolean success = (code >= 200) && (code < 300);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = in.readLine();
			// while ((inputLine = in.readLine()) != null)
			// logger.debug("input line:" + inputLine);
			in.close();
			// conn.getr
			logger.debug("message:" + conn.getResponseMessage());
			logger.debug("code:" + code);
			return inputLine;

		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}

	}
	
	public String postCommand(String cmdId, String param1) {
		// File file = new File(CreatePerson.FILE_PATH + name + ".per");
		// Person p = Person.parseFrom(new FileInputStream(file));
		Builder bd = CloudCommand.newBuilder();
		CloudCommand cc = bd.setId(Integer.parseInt(cmdId)).setParam1(Integer.parseInt(param1)).build();
		byte[] content = cc.toByteArray();
		try {

			HttpURLConnection conn = ConnectionUtil.getProtoConnection(urlPost, "POST");
			conn.setRequestProperty("Content-Length", Integer.toString(content.length));
			// set stream mode to decrease memory usage
			conn.setFixedLengthStreamingMode(content.length);
			OutputStream out = conn.getOutputStream();
			out.write(content);
			out.flush();
			out.close();
			conn.connect();
			// check response code
			int code = conn.getResponseCode();
			boolean success = (code >= 200) && (code < 300);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine = in.readLine();
			// while ((inputLine = in.readLine()) != null)
			// logger.debug("input line:" + inputLine);
			in.close();
			// conn.getr
			logger.debug("message:" + conn.getResponseMessage());
			logger.debug("code:" + code);
			return inputLine;

		} catch (Exception e) {
			logger.debug(e.getMessage());
			return null;
		}

	}

	public void putContacts(String cmdId) throws IOException {

		Builder bd = CloudCommand.newBuilder();
		CloudCommand p = bd.setId(Integer.parseInt(cmdId)).build();
		byte[] content = p.toByteArray();

		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/x-protobuf");
		conn.setRequestProperty("Accept", "application/x-protobuf;q=0.5");

		conn.setRequestProperty("Content-Length", Integer.toString(content.length));
		// set stream mode to decrease memory usage
		conn.setFixedLengthStreamingMode(content.length);
		OutputStream out = conn.getOutputStream();
		out.write(content);
		out.flush();
		out.close();
		conn.connect();
		// check response code
		int code = conn.getResponseCode();
		boolean success = (code >= 200) && (code < 300);
		logger.debug("code" + code);
	}
}
*/