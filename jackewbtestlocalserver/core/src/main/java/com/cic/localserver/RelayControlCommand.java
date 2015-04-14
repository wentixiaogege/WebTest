package com.cic.localserver;

public class RelayControlCommand extends Command{
	byte relayControl;	// 1: on; 0: off.
	byte[] param1 = new byte[1];
	
	byte[] ieeeAddress;	//
	
	RelayControlCommand(String name, byte id, int length, byte relayControl) {
		super(name, id, length);
		this.relayControl = relayControl;
		this.param1[0] = relayControl;
	}
	
	RelayControlCommand(String name, byte id, int length, byte relayControl, byte[] ieeeAddress) {
		super(name, id, length);
		this.relayControl = relayControl;
		this.param1[0] = relayControl;
		this.ieeeAddress = ieeeAddress;
	}
	
	void setIEEEAddress(byte[] ieeeaddress) {
		this.ieeeAddress = ieeeaddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	
	byte[] getParam1() {
		return this.param1;
	}
	
}
