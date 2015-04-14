package com.cic.localserver;

class ControlRegReadCommand extends Command{
	
	
	byte[] ieeeAddress;	//
	
	ControlRegReadCommand(String name, byte id, int length) {
		super(name, id, length);
		
	}
	
	ControlRegReadCommand(String name, byte id, int length, byte[] ieeeAddress) {
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
