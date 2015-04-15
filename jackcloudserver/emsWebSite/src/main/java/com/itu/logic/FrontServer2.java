package com.itu.logic;

import java.io.IOException;
import java.io.InputStream;

import edu.itu.proto.CloudCmdActionProtos;
import edu.itu.proto.CloudCmdActionProtos.CloudCmdAction;
import edu.itu.proto.CloudCommandProtos.CloudCommand;

public class FrontServer2 extends CloudClientLogic<CloudCmdAction,String[], CloudCommand> {

	public FrontServer2(String url, String method) {
		super(url, method);
	}

	@Override
	public CloudCmdAction buildMessage(String... params) {
		String cmdid = params[0];
		String param1 = params[1];
		logger.debug("cmdid is " + cmdid);
		logger.debug("param is " + param1);
		CloudCmdActionProtos.CloudCmdAction.Builder actionBuilder = CloudCmdAction.newBuilder();
		CloudCommand cc = CloudCommand.newBuilder().setId(Integer.parseInt(cmdid)).setParam1(Integer.parseInt(param1)).setCoordinatorId(0)
				.setSmartmeterId(2).build();
		CloudCommand cc2 = CloudCommand.newBuilder().setId(Integer.parseInt("2")).setParam1(Integer.parseInt("3")).setCoordinatorId(2)
				.setSmartmeterId(2).build();
		actionBuilder.addInsertCmds(cc);
		actionBuilder.addInsertCmds(cc2);
		return actionBuilder.build();
	}

	@Override
	protected CloudCommand convertToV(InputStream in, int size) throws IOException {
		// int size = conn.getContentLength();

		byte[] response = new byte[size];
		int curr = 0, read = 0;

		while (curr < size) {
			read = in.read(response, curr, size - curr);
			if (read <= 0)
				break;
			curr += read;
		}

		CloudCommand cc = CloudCommand.parseFrom(response);
		in.close();

		return cc;
	}


}
