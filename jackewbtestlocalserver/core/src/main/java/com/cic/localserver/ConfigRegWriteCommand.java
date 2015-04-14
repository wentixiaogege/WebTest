package com.cic.localserver;

class ConfigRegWriteCommand extends Command{
	
	byte[] configurationRegister;
	byte[] ieeeAddress;	//
	
	
	ConfigRegWriteCommand(String name, byte id, int length, byte[] configurationRegister) {
		super(name, id, length);
		this.configurationRegister = configurationRegister;
		
	}
	
	ConfigRegWriteCommand(String name, byte id, int length, byte[] configurationRegister, byte[] ieeeAddress) {
		super(name, id, length);
		
		this.configurationRegister = configurationRegister;
		this.ieeeAddress = ieeeAddress;
	}
	
	void setConfigRegister(byte[] configurationRegister) {
		this.configurationRegister = configurationRegister;
	}
	
	byte[] getConfigRegister() {
		return this.configurationRegister;
	}
	
	void setIEEEAddress(byte[] ieeeaddress) {
		this.ieeeAddress = ieeeaddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	
	
}
