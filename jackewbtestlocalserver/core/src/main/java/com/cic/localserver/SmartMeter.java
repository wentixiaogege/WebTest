package com.cic.localserver;

class SmartMeter {
	byte[] ieeeAddress;  // 8 bytes of IEEE address
	byte validation;	// 1: valid; 0: invalid
	byte[] id;	   // smart meter id for operation
	byte[] coordinatorId;	// coordinator ID
	int dataRegisterLength;    // for data register read command
	byte[] configurationCode;	// for smart meter multiple channel
	byte[] status;		// smart meter status
	
	SmartMeter(byte[] ieeeAddress, byte[] id) {
	
		this.ieeeAddress = ieeeAddress;
		this.id = id;
		
	}
	
}
