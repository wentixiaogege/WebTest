package com.cic.localserver;

class ParamRegWriteCommand extends Command{
	
	
	byte[] ieeeAddress;	//
	
	ParamRegWriteCommand(String name, byte id, int length) {
		super(name, id, length);
		
	}
	
	ParamRegWriteCommand(String name, byte id, int length, byte[] ieeeAddress) {
		super(name, id, length);
		
		this.ieeeAddress = ieeeAddress;
	}
	
	void setIEEEAddress(byte[] ieeeaddress) {
		this.ieeeAddress = ieeeaddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	
	
}
