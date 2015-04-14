package com.cic.localserver;

class RouteTableReadCommand extends Command{
	byte[] ieeeAddress;  // 8 bytes of IEEE address
	byte validation;	// 1: valid; 0: invalid
	byte[] smartMeterID;	   // smart meter id from manufacture
	//int maxNumber = 100;
	//byte[][] routingTable = new byte[maxNumber][];  //array of ieeeAddress + validation + index
	//int currentIndex;			// current number of address
	
	RouteTableReadCommand(String name, byte id, int length) {
		super(name, id, length);
		//currentIndex = 0;
	}
/**	
	int getCurrentNumber()
	{
		return this.currentIndex;
		
	}
*/	
	void addElement(byte[] ieeeAddress, byte validation, byte[] id) {
		this.ieeeAddress  = ieeeAddress;
		this.validation = validation;
		this.smartMeterID = id;
	}
	
	void addElement() {
		//routingTable[currentIndex] = new byte[super.dataLength];
		this.ieeeAddress = new byte[8];
		this.smartMeterID = new byte[2];
		System.arraycopy(super.data, 0, ieeeAddress, 0, 8);
		this.validation = super.data[8];
		System.arraycopy(super.data, 9, smartMeterID, 0, 2);
		
		//this.currentIndex +=1;
	}
	
	byte[] getElement() {
		return super.data;
	}
	
	byte[] getIEEEAddress() {
		/**
		byte[] ieeeAddress = new byte[8];  // 8 bytes of IEEE address
		//byte validation;	// 1: valid; 0: invalid
		//byte[] id = new byte[2];	   // id
		System.arraycopy(this.routingTable[index], 0, ieeeAddress, 0, 8);
		*/
		return this.ieeeAddress;
	}
	
	byte getValidation() {
		//return this.routingTable[index][8];
		return this.validation;
	}
	
	byte[] getSmartMeterID() {
		/**
		byte[] id = new byte[2];
		System.arraycopy(this.routingTable[index], 0, id, 9, 2);
		return id;
		*/
		return this.smartMeterID;
	}

	/**
	byte[][] getRoutingTable() {
		return this.routingTable;
	}
	*/

}
