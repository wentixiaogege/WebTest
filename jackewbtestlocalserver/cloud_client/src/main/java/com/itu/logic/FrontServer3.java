package com.itu.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.itu.proto.CloudCommandProtos.CloudCommand;

public class FrontServer3 extends CloudClientLogic<CloudCommand,String[], String> {


	public FrontServer3(String url, String method) {
		super(url, method);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CloudCommand buildMessage(String... params) {
		String cmdid = params[0];
		String param1 = params[1];
		logger.debug("cmdid is " + cmdid);
		logger.debug("param is " + param1);
		// com.itu.action.CloudCmdActionProtos.CloudCmdAction.Builder
		// actionBuilder = CloudCmdAction.newBuilder();
		CloudCommand cc = CloudCommand.newBuilder().setId(Integer.parseInt(cmdid)).setParam1(Integer.parseInt(param1)).setCoordinatorId(0)
				.setSmartmeterId(2).setName("peter").setDataLength(8).setChecked(1).build();
		// CloudCommand cc2 =
		// CloudCommand.newBuilder().setId(Integer.parseInt("2")).setParam1(Integer.parseInt("3")).setCoordinatorId(2)
		// .setSmartmeterId(2).build();
		// actionBuilder.addInsertCmds(cc);
		// actionBuilder.addInsertCmds(cc2);
		// return actionBuilder.build();
		return cc;
	}

	@Override
	protected String convertToV(InputStream in, int size) throws IOException {
		BufferedReader inr = new BufferedReader(new InputStreamReader(in));
		String inputLine = inr.readLine();
		// while ((inputLine = in.readLine()) != null)
		// logger.debug("input line:" + inputLine);
		inr.close();

		return inputLine;
	}


}
