package com.cic.localserver;

class NetworkDiscoveryCommand extends Command{
	
	
	byte[] ieeeAddress;	//
	
	NetworkDiscoveryCommand(String name, byte id, int length) {
		super(name, id, length);
		
	}
	
	NetworkDiscoveryCommand(String name, byte id, int length, byte[] ieeeAddress) {
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
