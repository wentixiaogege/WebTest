package com.itu.util;

import java.net.HttpURLConnection;
import java.net.URL;

import edu.itu.util.ItuMediaType;

public class ConnectionUtil {

	public static HttpURLConnection getProtoConnection(String url, String method) throws Exception {
		// return null;
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", ItuMediaType.APPLICATION_PROTOBUF);
		conn.setRequestProperty("Accept", "application/x-protobuf;q=0.5");
		return conn;
	}

}
