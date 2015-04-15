package com.itu.logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import javax.ws.rs.HttpMethod;

import com.google.protobuf.Message;
import com.itu.util.ConnectionUtil;

public abstract class CloudClientLogic<T extends Message, T1, V> extends ClientLogic<T1, V> {

	public abstract T buildMessage(T1 strings);
	protected abstract V convertToV(InputStream inputStream, int length) throws IOException;

	private String requestUrl;
	private String method;

	public CloudClientLogic(String url, String method) {
		this.requestUrl = url;
		this.method = method;
	}

	@Override
	public V executeLogic(T1 params) throws Exception {
		// return executeLogic(true, params);
		HttpURLConnection conn = ConnectionUtil.getProtoConnection(requestUrl, method);
		if (method.equals(HttpMethod.POST))
			return doPostHttpRequest(params, conn);
		else {
			return doGetHttpRequest(params, conn);
		}
	}

	/**
	 * http request using post method.
	 * @param params
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private V doPostHttpRequest(T1 params, HttpURLConnection conn) throws Exception {
		T t = buildMessage(params);
		byte[] content = t.toByteArray();
		try {
			// String method = isPost ? HttpMethod.POST : HttpMethod.GET;
			// HttpURLConnection conn =
			// ConnectionUtil.getProtoConnection(requestUrl, method);
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
			return operateWithCode(code, conn);
		} catch (Exception e) {
			throw e;
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * http request using get method
	 * @param params
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private V doGetHttpRequest(T1 params, HttpURLConnection conn) throws Exception {
		try {
			conn.connect();
			// check response code
			int code = conn.getResponseCode();
			return operateWithCode(code, conn);

		} catch (Exception e) {
			throw e;
		} finally {
			conn.disconnect();
		}
	}

	/**
	 * when http code returns, operate with it and return a V value.
	 * @param code
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private V operateWithCode(int code, HttpURLConnection conn) throws Exception {
		boolean success = (code >= 200) && (code < 300);
		if (success) {
			logger.debug("result is ok " + code);
			V v = convertToV(conn.getInputStream(), conn.getContentLength());
			logger.debug("executeLogic ending ...");
			return v;
		} else {
			throw new Exception("error code " + code);
		}
	}

	@Override
	protected V executeLogicDetail(T1 params) {
		return null;
	}

//	public V executeLogic(boolean isPost, T1 params) throws Exception {
//		logger.debug("executeLogic starting... ");
//		// Arrays.stream(params).forEach(x -> logger.debug(x));
//
//		T t = buildMessage(params);
//		byte[] content = t.toByteArray();
//		try {
//			String method = isPost ? HttpMethod.POST : HttpMethod.GET;
//			HttpURLConnection conn = ConnectionUtil.getProtoConnection(requestUrl, method);
//			conn.setRequestProperty("Content-Length", Integer.toString(content.length));
//
//			// set stream mode to decrease memory usage
//			conn.setFixedLengthStreamingMode(content.length);
//			OutputStream out = conn.getOutputStream();
//			out.write(content);
//			out.flush();
//			out.close();
//			conn.connect();
//			// check response code
//			int code = conn.getResponseCode();
//			boolean success = (code >= 200) && (code < 300);
//			if (success) {
//				logger.debug("result is ok " + code);
//				V v = convertToV(conn.getInputStream(), conn.getContentLength());
//				logger.debug("executeLogic ending ...");
//				return v;
//			} else {
//				throw new Exception("error code " + code);
//			}
//			// BufferedReader in = new BufferedReader(new
//			// InputStreamReader(conn.getInputStream()));
//			// in.re
//			// String inputLine = in.readLine();
//			// // while ((inputLine = in.readLine()) != null)
//			// // logger.debug("input line:" + inputLine);
//			// in.close();
//			// // conn.getr
//			// logger.debug("message:" + conn.getResponseMessage());
//			// logger.debug("code:" + code);
//			// return inputLine;
//
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}
	
	// public V doRequest(String url) throws Exception {
	// T t = buildMessage();
	// byte[] content = t.toByteArray();
	// try {
	//
	// HttpURLConnection conn = ConnectionUtil.getProtoConnection(url, "POST");
	// conn.setRequestProperty("Content-Length",
	// Integer.toString(content.length));
	// // set stream mode to decrease memory usage
	// conn.setFixedLengthStreamingMode(content.length);
	// OutputStream out = conn.getOutputStream();
	// out.write(content);
	// out.flush();
	// out.close();
	// conn.connect();
	// // check response code
	// int code = conn.getResponseCode();
	// boolean success = (code >= 200) && (code < 300);
	// if (success)
	// return convertToV(conn.getInputStream());
	// else {
	// throw new Exception("error code " + code);
	// }
	// // BufferedReader in = new BufferedReader(new
	// // InputStreamReader(conn.getInputStream()));
	// // in.re
	// // String inputLine = in.readLine();
	// // // while ((inputLine = in.readLine()) != null)
	// // // logger.debug("input line:" + inputLine);
	// // in.close();
	// // // conn.getr
	// // logger.debug("message:" + conn.getResponseMessage());
	// // logger.debug("code:" + code);
	// // return inputLine;
	//
	// } catch (Exception e) {
	// throw e;
	// }
	// }

	public void doAfterCovertToV() {
	}

}
