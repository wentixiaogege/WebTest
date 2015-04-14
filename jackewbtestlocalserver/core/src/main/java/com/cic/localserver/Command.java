package com.cic.localserver;

import java.sql.Timestamp;

public class Command {
	

	String name;
	byte id;
	int dataLength;
	
	byte[] commandStream;
	byte[] result = null;
	byte[] data = null;	
	byte[] ackArray = null;
	byte status = 0;
	
	byte[] param1;
	byte[] param2;
	byte[] param3;

	String generatedBy;
	Timestamp generatedTime;
	Timestamp executedTime;
	Timestamp lsDBInsertedTime;
	
	byte[] coordinatorIEEEAddress;
	
	
	Command(String name, byte id, int length) {
		this.name = name;
		this.id = id;
		this.dataLength = length;
		
		//this.commandStream = new byte[1];
		//this.commandStream[0] = id;
		byte[] temp = new byte[1];
		temp[0] = id;
		this.commandStream = new byte[temp.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
					
	}
	
	Command(String name, byte id, int length, byte[] param1) {
		this.name = name;
		this.id = id;
		this.dataLength = length;
				
		byte[] temp = new byte[1];
		temp[0] = id;
		this.commandStream = new byte[temp.length + param1.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		
	}
	
	Command(String name, byte id, int length,  byte[] param1, byte[] param2) {
		this.name = name;
		this.id = id;
		this.dataLength = length;
		
		byte[] temp = new byte[1];
		temp[0] = id;
		this.commandStream = new byte[temp.length + param1.length + param2.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		System.arraycopy(param2, 0, this.commandStream, temp.length+param1.length, param2.length);
		
	}
	
	Command(String name, byte id, int length, byte[] param1, byte[] param2, byte[] param3) {
		this.name = name;
		this.id = id;
		this.dataLength = length;	
		
		byte[] temp = new byte[1];
		temp[0] = id;
		this.commandStream = new byte[temp.length + param1.length + param2.length+ param3.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		System.arraycopy(param2, 0, this.commandStream, temp.length+param1.length, param2.length);
		System.arraycopy(param3, 0, this.commandStream, temp.length+param1.length+param2.length, param3.length);
	
		
	}
	
	void setCoordinatorIEEEAddress(byte[] ieeeAddress)
	{
		this.coordinatorIEEEAddress = ieeeAddress;
	}
	
	byte[] getCoordinatorIEEEAddress()
	{
		return this.coordinatorIEEEAddress;
	}
	
	void setParam1(byte[] param1)
	{
		this.param1 = param1;
	}
	
	void setParam2(byte[] param2)
	{
		this.param2 = param2;
	}
	
	void setParam3(byte[] param3)
	{
		this.param3 = param3;
	}
	
	byte[] getParam1()
	{
		return this.param1;
	}
	
	byte[] getParam2()
	{
		return this.param2;
	}
	
	byte[] getParam3()
	{
		return this.param3;
	}
	
	byte getId() {
		return this.id;
	}
	
	String getName() {
		return this.name;
	}

	
	byte[] getCommandStream() {
		return this.commandStream;
	}
	
	void setCommandStream() {
		
		byte[] temp = new byte[this.commandStream.length];
		System.arraycopy(this.commandStream, 0, temp, 0, this.commandStream.length);
		this.commandStream = new byte[temp.length + this.coordinatorIEEEAddress.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(this.coordinatorIEEEAddress, 0, this.commandStream, temp.length, this.coordinatorIEEEAddress.length);
		
	}
	
	void setCommandStream(byte[] param1) {
		byte[] temp = new byte[1];
		temp[0] = this.id;
		this.commandStream = new byte[temp.length + param1.length + this.coordinatorIEEEAddress.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		System.arraycopy(this.coordinatorIEEEAddress, 0, this.commandStream, temp.length + param1.length, this.coordinatorIEEEAddress.length);
	}
	
	void setCommandStream(byte[] param1, byte[] param2) {
		byte[] temp = new byte[1];
		temp[0] = this.id;
		this.commandStream = new byte[temp.length + param1.length + param2.length + this.coordinatorIEEEAddress.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		System.arraycopy(param2, 0, this.commandStream, temp.length+param1.length, param2.length);
		System.arraycopy(this.coordinatorIEEEAddress, 0, this.commandStream, temp.length + param1.length + param2.length, this.coordinatorIEEEAddress.length);
	}
	
	void setCommandStream(byte[] param1, byte[] param2, byte[] param3) {
		byte[] temp = new byte[1];
		temp[0] = this.id;
		this.commandStream = new byte[temp.length + param1.length + param2.length+ param3.length + this.coordinatorIEEEAddress.length];
		System.arraycopy(temp, 0, this.commandStream, 0, temp.length);
		System.arraycopy(param1, 0, this.commandStream, temp.length,param1.length);
		System.arraycopy(param2, 0, this.commandStream, temp.length+param1.length, param2.length);
		System.arraycopy(param3, 0, this.commandStream, temp.length+param1.length+param2.length, param3.length);
		System.arraycopy(this.coordinatorIEEEAddress, 0, this.commandStream, temp.length + param1.length + param2.length + param3.length, this.coordinatorIEEEAddress.length);
	}
	
	
	int getDataLength() {
		return this.dataLength;
	}
	
	
	void setResult(byte[] result) {
		this.result = result;
	}
	
	byte[] getResult() {
		return this.result;
	}
	
	void setData() {
		
		this.data = new byte[dataLength];  // it will be null when length is 0;
		//if (this.dataLength > 0)
		System.out.println("In Java, the result.length:" + this.result.length + " DataLength:" + dataLength);
		if ((this.result.length - 3 ) == dataLength)
		{
			System.out.println("There is data returned from JNI");
			System.arraycopy(this.result, 0, this.data, 0, this.dataLength);
		}
		else
		{
			for (int i=0; i< dataLength; i++)
			{
				data[0] = 0x00;
			}
		}
	
	}
	
	byte[] getData() {
		return this.data;
	}
	
	void setAckArray() {
		this.ackArray = new byte[3];  // ackArray: always the last three byte of the retrieved message.
		
		System.arraycopy(this.result, this.result.length-3, this.ackArray, 0, 3);
	}
	
	void resetStatus() {
		this.ackArray[2] = 0x00;
	}
	
	byte[] getAckArray() {
		return this.ackArray;
	}
	
	void setStatus() {
		//this.status = new byte[1];  // status: always the last one byte of the retrieved message.
		
			//System.arraycopy(this.result, this.result.length-1, this.status, 0, 1);
		this.status = this.result[result.length-1];
	
	}
	
	byte getStatus() {
		return this.status;
	}
	
	int is_timeout() {
		if ((this.status >> 1) == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	void updateStatus(byte status) {
		this.status = status;
	}
	
	void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	
	String getGeneratedBy() {
		return this.generatedBy;
	}
	
	void setGeneratedTime(Timestamp generatedTime) {
		this.generatedTime = generatedTime;
	}
	
	Timestamp getGeneratedTime() {
		return this.generatedTime;
	}
	
	void setExecutedTime(Timestamp executedTime) {
		this.executedTime = executedTime;
	}
	
	Timestamp getExecutedTime() {
		return this.executedTime;
	}
	
	void setLsDBInsertedTime(Timestamp lsDBInsertedTime) {
		this.lsDBInsertedTime = lsDBInsertedTime;
	}
	
	Timestamp getLsDBInsertedTime() {
		return this.lsDBInsertedTime;
	}
	
}