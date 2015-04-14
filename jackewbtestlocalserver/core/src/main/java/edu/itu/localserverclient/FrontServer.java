package edu.itu.localserverclient;
/*

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.itu.bean.CloudCommand;
import com.itu.util.ConnectionUtil;
import com.itu.util.Log4jUtil;

public class FrontServer {

	static String url = "http://172.16.5.157:8080/dataservertest/rest/frontendpost/";
	Logger logger = Log4jUtil.getLogger(FrontServer.class);

	*//**
	 * pass Class CloudCommandProtos through google buffer.
	 * 
	 * @param cmdId
	 *//*
	public String postCommand(String cmdId) {
		// File file = new File(CreatePerson.FILE_PATH + name + ".per");
		// Person p = Person.parseFrom(new FileInputStream(file));
		Builder bd = CloudCommand.newBuilder();
		CloudCommand cc = bd.setId(Integer.parseInt(cmdId)).build();
		byte[] content = cc.toByteArray();
		try {

			HttpURLConnection conn = ConnectionUtil.getProtoConnection(url, "GET");
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