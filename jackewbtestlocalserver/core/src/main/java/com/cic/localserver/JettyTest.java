package com.cic.localserver;

import javax.ws.rs.Path;

@Path("/LightOperation")
public class JettyTest {

	public static LocalServer localServer;
	public void test(){
		
		localServer = LocalServer.getLocalServer();
		
//		command = new RelayControlCommand("Relay Control", (byte)commandID, dataLength, controlRelay, smartMeterTable[smartMeterID]);
		
		// localserver
//		localServer.commandManager.setGeneratedCommand(localServer.coordinatorArray[coordinatorID], command);
	}
}
