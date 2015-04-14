package com.cic.localserver;

import java.util.Arrays;
import java.util.Calendar;

class TimeSetCommand extends Command{
	short[] timeShortArray;
	
	byte[] ieeeAddress;	//

	TimeSetCommand(String name, byte id, int length) {
		super(name, id, length);
		this.timeShortArray = new short[6];  // 12 bytes
	}
	
	TimeSetCommand(String name, byte id, int length, byte[] ieeeAddress) {
		super(name, id, length);
		this.timeShortArray = new short[6];  // 12 bytes
		
		this.ieeeAddress = ieeeAddress;
	}
	
	void setTimeShortArray() {
		
		
		Calendar rightnow = Calendar.getInstance();
		timeShortArray[5] = (short)rightnow.get(Calendar.SECOND);
		timeShortArray[4] = (short)rightnow.get(Calendar.MINUTE);
		timeShortArray[3] = (short)rightnow.get(Calendar.HOUR_OF_DAY);
		timeShortArray[2] = (short)rightnow.get(Calendar.DAY_OF_MONTH);
		timeShortArray[1] = (short)(rightnow.get(Calendar.MONTH)+1);
		timeShortArray[0] = (short)rightnow.get(Calendar.YEAR);
		System.out.println("In UARTOperator, currentTime array: " + Arrays.toString(timeShortArray));
			
		}
	
	
	short[] getTimeShortArray() {
		
		return this.timeShortArray;
	}
	
	byte[] getTimeByteArray() {
		byte[] timeByteArray = new byte[timeShortArray.length*2];
		for (int i=0; i<timeShortArray.length; i++)
		{
			short value = timeShortArray[i];
			timeByteArray[i*2] = (byte)((value & 0xff00) >> 8);
			System.out.println(String.format("0x%2s", Integer.toHexString(timeByteArray[i*2])).replace(' ', '0'));
			timeByteArray[i*2+1] = (byte)(value & 0x00ff);
			System.out.println(String.format("0x%2s", Integer.toHexString(timeByteArray[i*2+1])).replace(' ', '0'));
		}
		return timeByteArray;
	}
	
	void setIEEEAddress(byte[] ieeeaddress) {
		this.ieeeAddress = ieeeaddress;
	}
	
	byte[] getIEEEAddress() {
		return this.ieeeAddress;
	}
	

}
