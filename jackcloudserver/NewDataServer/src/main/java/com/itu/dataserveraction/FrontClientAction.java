package com.itu.dataserveraction;

import javax.ws.rs.Path;

import edu.itu.proto.CloudCmdActionProtos.CloudCmdAction;
import edu.itu.proto.CloudCommandProtos.CloudCommand;
import com.itu.dataserverlogic.FrontClientLogic;

@Path("/frontclientaction")
public class FrontClientAction extends CommonProtoAction<CloudCmdAction, CloudCommand> {

	@Override
	protected void initCommonProtoLogic() {
		cmpLogic = new FrontClientLogic();
	}

}
