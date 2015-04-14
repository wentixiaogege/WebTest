package com.cic.localserver;

class CalibrationRegReadCommand extends Command {
	
	byte[] ieeeAddress = new byte[8];  // 8 bytes smart meter
	
	Integer smartMeterIndex;	// 2 bytes
	
	Integer[] calibrationReg;
	
	CalibrationRegReadCommand(String name, byte id, int length, byte[] ieeeAddress) {
		
		super(name, id, length);
		this.ieeeAddress = ieeeAddress;
		this.calibrationReg = new Integer[length/2];  // each item has 2 bytes
	}
	
	void setCalibrationReg() {

		Integer partA;  // use Integer as uint32_t in C
		Integer partB;
		
		for (int i=0; i< calibrationReg.length; i++)
		{
			
			partA = new Integer(super.data[i*2]);
			partA = partA & 0x000000ff;  // only last 8 bits are needed
			partB = new Integer(super.data[i*2+1]) ;
			partB = partB & 0x000000ff;	 // only last 8 bits are needed
			this.calibrationReg[i] = ((partA << 8) | 0x00000000) | (partB | 0x00000000); // only the last 16 bits are needed
			
		}
	}

	Integer[] getCalibrationReg() {
		
		return this.calibrationReg;
	}
	
	void setSmartMeterIndex() {
		this.smartMeterIndex = calibrationReg[4];
	}
	
	Integer getSmartMeterIndex() {
		return this.smartMeterIndex;
	}
	
	void setIEEEAddress(byte[] ieeeAddress) {
		this.ieeeAddress = ieeeAddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	
}

/**
Integer[] paramReg;


ParamRegReadCommand(String name, byte id, int length) {
	super(name, id, length);
	this.paramReg = new Integer[length/2]; // each item has 2 bytes
}

void setParamReg() {

	Integer partA;  // use Integer as uint32_t in C
	Integer partB;
	
	for (int i=0; i< paramReg.length; i++)
	{
		
		partA = new Integer(super.data[i*2]);
		partA = partA & 0x000000ff;  // only last 8 bits are needed
		partB = new Integer(super.data[i*2+1]) ;
		partB = partB & 0x000000ff;	 // only last 8 bits are needed
		this.paramReg[i] = ((partA << 8) | 0x00000000) | (partB | 0x00000000); // only the last 16 bits are needed
		
	}
}

Integer[] getParamReg() {
	
	return this.paramReg;
}
*/

