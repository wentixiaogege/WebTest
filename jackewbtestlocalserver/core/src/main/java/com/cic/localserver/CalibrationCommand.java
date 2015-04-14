package com.cic.localserver;

class CalibrationCommand extends Command{
	byte vCal;  // 11 V for now
	byte iCal;	// 1 A
	byte tCal;  // 30 seconds
	byte nCal;	// 3
	byte[] param1 = new byte[4];
	byte[] ieeeAddress = new byte[8];  // 8 bytes
	
	CalibrationCommand(String name, byte id, int length, byte vCal, byte iCal, byte tCal, byte nCal) {
		super(name, id, length);
		this.vCal = vCal;
		this.iCal = iCal;
		this.tCal = tCal;
		this.nCal = nCal;
		this.param1[0] = vCal;
		this.param1[1] = iCal;
		this.param1[2] = tCal;
		this.param1[3] = nCal;
	}
	
	CalibrationCommand(String name, byte id, int length, byte vCal, byte iCal, byte tCal, byte nCal, byte[] ieeeAddress) {
		super(name, id, length);
		this.vCal = vCal;
		this.iCal = iCal;
		this.tCal = tCal;
		this.nCal = nCal;
		this.param1[0] = vCal;
		this.param1[1] = iCal;
		this.param1[2] = tCal;
		this.param1[3] = nCal;
		this.ieeeAddress = ieeeAddress;
	}
	
	void setVCal(byte vCal) {
		this.vCal = vCal;
	}
	
	byte getVCal() {
		return this.vCal;
	}
	
	void setICal(byte iCal) {
		this.iCal = iCal;
	}
	
	byte getICal() {
		return this.iCal;
	}
	
	void setTCal(byte tCal) {
		this.tCal = tCal;
	}
	
	byte getTCal() {
		return this.tCal;
	}
	
	void setNCal(byte nCal) {
		this.nCal = nCal;
	}
	
	byte getNCal() {
		return this.nCal;
	}
	
	void setParam1(byte vCal, byte iCal, byte tCal, byte nCal) {
		this.param1[0] = vCal;
		this.param1[1] = iCal;
		this.param1[2] = tCal;
		this.param1[3] = nCal;
	}
	
	byte[] getParam1() {
		return this.param1;
	}
	
	void setIEEEAddress(byte[] ieeeAddress) {
		this.ieeeAddress = ieeeAddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}

}
