package com.cic.localserver;

public class ParamRegReadCommand extends Command{

	Integer[] paramReg;
	
	byte[] ieeeAddress;	//
	

	ParamRegReadCommand(String name, byte id, int length) {
		super(name, id, length);
		this.paramReg = new Integer[length/2]; // each item has 2 bytes
	}
	
	ParamRegReadCommand(String name, byte id, int length, byte[] ieeeAddress) {
		super(name, id, length);
		this.paramReg = new Integer[length/2]; // each item has 2 bytes
		
		this.ieeeAddress = ieeeAddress;
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
	
	void setIEEEAddress(byte[] ieeeaddress) {
		this.ieeeAddress = ieeeaddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	
		
}

