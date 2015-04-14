package com.cic.localserver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

class DataRegReadCommand extends Command{
	
	Integer[] dataReg;
	
	int smIndex;
	String smIEEEAddressString;
	int rmsV1;
	int rmsI1;
	int smStatus;
	int power;
	int energy;
	int accumulatedEnergy;
	
	byte[] timeByteArray;
	int[] timeIntArray;
	Timestamp timestamp;
	
	//Timestamp timestamp;
	//Integer[] data = new Integer[length * 2]; // each is one bytes

	DataRegReadCommand(String name, byte id, int length) {
		super(name, id, length);
		this.dataReg = new Integer[length/2]; // each item has 2 bytes
		//java.util.Date date = new java.util.Date();
		//timestamp = new Timestamp(date.getTime());
		timeByteArray = new byte[12];
		timeIntArray = new int[6];
		
	}
	
	void setDataReg() {

		Integer partA;  // use Integer as uint32_t in C
		Integer partB;
		byte[] dataArray = new byte[8];
		
		//dataArray = super.getData();
		//System.out.println("In DataReReadCommand: super" + Arrays.toString(super.getData()));
		//System.out.println("In DataReReadCommand: dataArray " + Arrays.toString(dataArray));
		
		for (int i=0; i< dataReg.length; i++)
		{
			
			partA = new Integer(super.data[i*2]);
			//partA = new Integer(dataArray[i*2]);
			partA = partA & 0x000000ff;  // only the last 8 bits are needed
			partB = new Integer(super.data[i*2+1]) ;
			//partB = new Integer(dataArray[i*2+1]) ;
			partB = partB & 0x000000ff;	 // only the last 8 bits are needed
			this.dataReg[i] = ((partA << 8) | 0x00000000) | (partB | 0x00000000); // only the last 16 bits are needed
			
		}
		
		byte[] temp = new byte[8];
		
		//System.arraycopy(dataArray, 0, temp, 0, 8);	
		System.arraycopy(super.data, 0, temp, 0, 8);	
		
		System.arraycopy(super.data, 34, timeByteArray, 0, 12);  // for time
		
		for (int i=0; i< temp.length; i++)
		{
			System.out.println("In DataRegisterCommand. buf ###  "  + 
					 String.format("0x%2s", Integer.toHexString(temp[i])).replace(' ', '0'));
		}
		
		this.smIEEEAddressString = Arrays.toString(temp);
		System.out.println("In DataRegisterCommand. smIEEEAddress ###  "  + this.smIEEEAddressString);
		
		
		
		this.smIndex = this.dataReg[4];
		this.rmsV1 = this.dataReg[5];
		this.rmsI1 = this.dataReg[6];
		this.smStatus = this.dataReg[16];
		
		this.power = this.rmsV1 * this.rmsI1;
		// this.timestamp = dataReg[17-22];  in the future
	}
	
	Integer[] getDataReg() {
		
		return this.dataReg;
	}
	
	String getIEEEAddressString() {
		return this.smIEEEAddressString;
	}
	
	int isTimeout() {
		if (((super.status) >> 1) == 1)
			return 1;
		else
			return 0;
	}
	
	int getSmartMeterStatus() {
		return smStatus;
	}
	
	void setEnergy(int energy) {
		this.energy = energy;
	}
	
	void setAccumulatedEnergy(int acculmulatedEnergy) {
		this.accumulatedEnergy = acculmulatedEnergy;
	}
	
	int getAccumulatedEnergy() {
		return this.accumulatedEnergy;
	}
	
	int getPower() {
		return this.power;
	}
	
	void setTimeIntegerArray() {
		Integer partA;
		Integer partB;
		
		for (int i = 0; i< timeIntArray.length; i++)
		{
		
			partA = (int)(timeByteArray[i*2]);
			//partA = new Integer(dataArray[i*2]);
			partA = partA & 0x000000ff;  // only the last 8 bits are needed
			partB = (int)(timeByteArray[i*2+1]) ;
			//partB = new Integer(dataArray[i*2+1]) ;
			partB = partB & 0x000000ff;	 // only the last 8 bits are needed
			this.timeIntArray[i] = ((partA << 8) | 0x00000000) | (partB | 0x00000000); // only the last 16 bits are needed
		}
		System.out.println("In DataRegReadCommand, timeIntArray: " + Arrays.toString(timeIntArray));
	}
	
	void setTimestamp() {
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(timeIntArray[0], timeIntArray[1]-1, timeIntArray[2], timeIntArray[3], timeIntArray[4], timeIntArray[5]);
		Date date = calendar.getTime();
		this.timestamp = new Timestamp(date.getTime());
		
		System.out.println("In DataRegReadCommand, timestamp: " + this.timestamp);
		
	}
	
	void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
		
	}
	

	
	Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	

}
