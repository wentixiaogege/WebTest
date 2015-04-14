package com.cic.localserver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
//import java.util.Arrays;
//import java.util.Arrays;
import java.util.concurrent.* ;

//import javax.swing.JTextArea;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;
//import javax.swing.JFrame;

//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;



class Coordinator {
	
	int id;
	byte[] ieeeAddress;
	int smartMeterNumber;
	Hashtable<String, Timestamp> smartMeterPreviousTimestamp;  // indexed by 8 bytes of IEEE address
	Hashtable<String, Integer> smartMeterPreviousAcculmulatedEnergy;  // indexed by 8 bytes of IEEE address
	Hashtable<String, Command> smartMeterPreviousCommand;  // indexed by 8 bytes of IEEE address
	
	BlockingQueue<Command> generatedCommandQueue;
	BlockingQueue<Command> executedCommandQueue;
	
	Command previousGeneratedCommand = null;
	Command previousExecutedCommand = null;
	
	int maxNumber = 100;
	byte[][] routingTable = new byte[maxNumber][];  //array of ieeeAddress + validation + index
	int currentIndex;
	int uartFD;
	
	Coordinator(int id, byte[] ieeeAddress) {
	//Coordinator(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.ieeeAddress = ieeeAddress; 
		
		this.currentIndex = -1;   // for routing table
		
		//this.ieeeAddress = Arrays.copyOf(ieeeAddress, ieeeAddress.length);
		System.out.println("Coordinator ID:" + id + "IEEE address: " + Arrays.toString(this.ieeeAddress));
		
		//this.IPAddress = Arrays.copyOf(IPAddress, IPAddress.length);
		
		smartMeterNumber = 0;
		smartMeterPreviousTimestamp = new Hashtable<String, Timestamp>();
		smartMeterPreviousAcculmulatedEnergy = new Hashtable<String, Integer>();
		smartMeterPreviousCommand = new Hashtable<String, Command>();
		
		generatedCommandQueue = new LinkedBlockingQueue<Command>();
		System.out.println("In Java, one generated command queue is created for Coordinator:" + id);
		executedCommandQueue = new LinkedBlockingQueue<Command>();
		System.out.println("In Java, one executed command queue is created for Coordinator:" + id);
		
	}
	
	int getID() {
		return this.id;
	}
	
	void setUartFd(int fd) {
		this.uartFD = fd;
	}
	
	int getUartFd() {
		return this.uartFD;
	}

	
	byte[] getIEEEAddress () {
		return this.ieeeAddress;
	}
	
	void setSmartMeterNumber(int n) {
		this.smartMeterNumber = n;
	}
	
	int getSmartMeterNumber() {
		return this.smartMeterNumber;
	}
	
	void setSmartMeterPreviousAccumulatedEnergy(String ieeeAddressString, Integer energy) {
		
		smartMeterPreviousAcculmulatedEnergy.put(ieeeAddressString, energy);
	}
	
	Command getSmartMeterPreviousCommand(String ieeeAddressString) {
		
		return smartMeterPreviousCommand.get(ieeeAddressString);
	}
	
	
	void setSmartMeterPreviousCommand(String ieeeAddressString, Command command) {
		
		smartMeterPreviousCommand.put(ieeeAddressString, command);
	}
	
	void setSmartMeterPreviousCommand(Command command)
	{
		String[] ieeeAddressStringArray;
		Set<String> st = new HashSet<String>();
			st = smartMeterPreviousCommand.keySet();
		//ieeeAddressStringArray = smartMeterPreviousCommand.keySet().toArray();
		ieeeAddressStringArray = st.toArray(new String[st.size()]);
		
		for (int i=0; i<ieeeAddressStringArray.length; i++)
		{
			smartMeterPreviousCommand.put(ieeeAddressStringArray[i], command);
		}
	}
	
	Integer getSmartMeterPreviousAccumulatedEnergy(String ieeeAddressString) {
		
		return smartMeterPreviousAcculmulatedEnergy.get(ieeeAddressString);
	}
	
	void setSmartMeterPreviousTimestamp(String ieeeAddressString, Timestamp timestamp) {
		smartMeterPreviousTimestamp.put(ieeeAddressString, timestamp);
	}
	
	void setSmartMeterPreviousTimestamp(String ieeeAddressString, int[] timeIntArray) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(timeIntArray[0], timeIntArray[1]-1, timeIntArray[2], timeIntArray[3], timeIntArray[4], timeIntArray[5]);
		Date date = calendar.getTime();
		Timestamp tempTimestamp = new Timestamp(date.getTime());
		smartMeterPreviousTimestamp.put(ieeeAddressString, tempTimestamp);
		
		System.out.println("In Coordinator, smart meter: " + ieeeAddressString + " precioustimestamp: " + tempTimestamp);
		
	}
	
	Timestamp getSmartMeterPreviousTimestamp(String ieeeAddressString) {
		return smartMeterPreviousTimestamp.get(ieeeAddressString);
	}
	
	int getCurrentIndex()   // the current index of smart meters
	{
		return this.currentIndex;
		
	}
	
	void addRoutingTableElement(byte[] element) {
		
		currentIndex +=1;
		routingTable[currentIndex] = new byte[element.length];

		System.arraycopy(element, 0, routingTable[currentIndex], 0, 8);
		routingTable[currentIndex][8] = element[8];
		System.arraycopy(element, 9, routingTable[currentIndex], 9, 2);
	}
	
	byte[] getRoutingTableElement(int index) {
		return routingTable[index];
	}
	
	byte[] getRoutingTableElement() {    // default, the current element
		if (currentIndex > 0)
		{
			return routingTable[currentIndex-1];
		}
		else
		{
			return routingTable[0];
		}
		
	}
	
	
	
	byte[] getSmartMeterIEEEAddress(int index) {
		
		byte[] ieeeAddress = new byte[8];  // 8 bytes of IEEE address
		//byte validation;	// 1: valid; 0: invalid
		//byte[] id = new byte[2];	   // id
		System.arraycopy(this.routingTable[index], 0, ieeeAddress, 0, 8);
		return ieeeAddress;
		
	}
	
	byte getValidation(int index) {
		return this.routingTable[index][8];
		
	}
	
	byte[] getSmartMeterID(int index) {
		
		byte[] id = new byte[2];
		System.arraycopy(this.routingTable[index], 0, id, 9, 2);
		return id;	
	}

	
	byte[][] getRoutingTable() {
		return this.routingTable;
	}


	
	BlockingQueue<Command> getGeneratedCommandQueue() {
		return this.generatedCommandQueue;
	}
	
	BlockingQueue<Command> getExecutedCommandQueue() {
		return this.executedCommandQueue;
	}
	
	void setPreviousGeneratedCommand(Command command) {
		this.previousGeneratedCommand = command;
	}
	
	Command getPreviousGeneratedCommand() {
		return this.previousGeneratedCommand;
	}
	
	void setPreviousExecutedCommand(Command command) {
		this.previousExecutedCommand = command;
	}
	
	Command getPreviousExecutedCommand() {
		return this.previousExecutedCommand;
	}
	
	
}