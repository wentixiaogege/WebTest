package com.cic.localserver;

class EnergyCalculationResetCommand extends Command{
	
	byte[] energyResetValue = new byte[4];	// 1: on; 0: off.
	byte[] param1 = new byte[4];
	
	byte[] ieeeAddress = new byte[8];	//
	
	EnergyCalculationResetCommand(String name, byte id, int length, byte[] energyResetValue) {
		super(name, id, length);
		this.energyResetValue = energyResetValue;
		this.param1 = energyResetValue;
	}
	
	EnergyCalculationResetCommand(String name, byte id, int length, byte[] energyResetValue, byte[] ieeeAddress) {
		super(name, id, length);
		this.energyResetValue = energyResetValue;
		this.param1 = energyResetValue;
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
