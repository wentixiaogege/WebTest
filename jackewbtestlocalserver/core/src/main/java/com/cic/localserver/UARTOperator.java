package com.cic.localserver;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class UARTOperator implements Runnable {
	
	static {
		      System.loadLibrary("UART"); // jniUART.dll (Windows) or libjniUART.so (Uinuxes)
	}
	
	private native static synchronized int init(int id);  // returns the uart port file handle
	private native static synchronized byte[] sendAndRetrieveMessage(int id, int fd, byte[] cmd);
	private native static synchronized byte[] ackAndRetrieveMessage(int id, int fd, byte command_id, byte[] ack);  // use 3 bytes of ack
	private native static synchronized void close(int id, int fd);
	
	//Coordinator coordinator;
	//Coordinator[] coordinatorArray;
	/**
	BlockingQueue<Command> generatedCommandQueue;
	BlockingQueue<Command> executedCommandQueue;
	*/
	
	//int localServerID;
	LocalServer localServer;
	int uartFD;
	Coordinator coordinator;
	int id;
	
	//UARTOperator(int id, Coordinator[] coordinatorArray) {
	UARTOperator(LocalServer localServer) {
		//this.localServerID = id;
		//this.coordinatorArray = coordinatorArray;
		//this.coordinator = coordinatorArray[0];
		this.localServer = localServer;
	/**	
		generatedCommandQueue = new PriorityBlockingQueue<>();
		System.out.println("In Java, one generated command queue is created for UARTOperator");
		executedCommandQueue = new LinkedBlockingQueue<Command>();
		System.out.println("In Java, one executed command queue is created for UARTOperator");
	*/
		
	}
	
	UARTOperator(LocalServer localServer, Coordinator coordinator, int id) {
		this.localServer = localServer;
		this.coordinator = coordinator;
		this.id = id;
	}
	
	//int getLocalServerID() {
	//	return this.localServerID;
	//}
	
	LocalServer getLocalServer() {
		return this.localServer;
	}
	
	void setUartFd(int fd) {
		this.uartFD = fd;
	}
	
	int getUartFd() {
		return this.uartFD;
	}
	
	Timestamp getTimestamp(int[] timeIntArray) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(timeIntArray[0], timeIntArray[1]-1, timeIntArray[2], timeIntArray[3], timeIntArray[4], timeIntArray[5]);
			Date date = calendar.getTime();
			Timestamp tempTimestamp = new Timestamp(date.getTime());
			
			return tempTimestamp;
	}
	
	Timestamp getCurrentTimestamp() {
		
		Calendar rightnow = Calendar.getInstance();
		Date date = rightnow.getTime();
		Timestamp currentTimestamp = new Timestamp(date.getTime());
		return currentTimestamp;
	}
	
	
	Command executeNewCommand(Coordinator coordinator) {
		
		Command command = localServer.commandManager.getGeneratedCommand(coordinator);  // Dequeue from the array of coordinator's generatedComamndQueue through commandManager
		if (command != null)
		{
			byte command_id = command.getId();
			String command_name = command.getName();
			System.out.println("In UARTOperator, the sendMessage. Command name: " + command_name + " id: " + command_id);
			
			command.setCoordinatorIEEEAddress(coordinator.ieeeAddress);

			if (command instanceof TimeSetCommand)
			{		
				java.util.Date date = new java.util.Date();
				Timestamp currentTime = new Timestamp(date.getTime());
				System.out.println("In UARTOperator, currentTimestamp: " + currentTime);

				TimeSetCommand timeSetCommand = (TimeSetCommand)command;
				timeSetCommand.setTimeShortArray();							
				command.setParam1(timeSetCommand.getTimeByteArray());
				command.setParam2(timeSetCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1(), command.getParam2());	
				//Thread.sleep(100000);
			}
			else if (command instanceof CalibrationCommand)
			{
				CalibrationCommand calibrationCommand = (CalibrationCommand)command;
				command.setParam1(calibrationCommand.getParam1());
				command.setParam2(calibrationCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1(), command.getParam2());									
			}
			else if (command instanceof CalibrationRegReadCommand)
			{
				CalibrationRegReadCommand calibrationRegReadCommand = (CalibrationRegReadCommand)command;
				command.setParam1(calibrationRegReadCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1());
			}
			else if (command instanceof ControlRegReadCommand)
			{
				ControlRegReadCommand controlRegReadCommand = (ControlRegReadCommand)command;
				command.setParam1(controlRegReadCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1());
			}
			else if (command instanceof ParamRegReadCommand)
			{
				ParamRegReadCommand paramRegReadCommand = (ParamRegReadCommand)command;
				command.setParam1(paramRegReadCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1());
			}
			else if (command instanceof RelayControlCommand)
			{
				RelayControlCommand relayControlCommand = (RelayControlCommand)command;
				command.setParam1(relayControlCommand.getParam1());
				command.setParam2(relayControlCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1(), command.getParam2());	
			}
			else if (command instanceof NetworkDiscoveryCommand)
			{
				NetworkDiscoveryCommand networkDiscoveryCommand = (NetworkDiscoveryCommand)command;
				command.setParam1(networkDiscoveryCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1());	
			}
			else if (command instanceof ParamRegWriteCommand)
			{
				ParamRegWriteCommand paramRegWriteCommand = (ParamRegWriteCommand)command;
				command.setParam1(paramRegWriteCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1());	
			}
			else if (command instanceof EnergyCalculationResetCommand)
			{
				EnergyCalculationResetCommand energyCalculationResetCommand = (EnergyCalculationResetCommand)command;
				command.setParam1(energyCalculationResetCommand.getParam1());
				command.setParam2(energyCalculationResetCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1(), command.getParam2());	
			}
			else if (command instanceof ConfigRegWriteCommand)
			{
				ConfigRegWriteCommand configRegWriteCommand = (ConfigRegWriteCommand)command;
				command.setParam1(configRegWriteCommand.getConfigRegister());
				command.setParam2(configRegWriteCommand.getIEEEAddress());
				command.setCommandStream(command.getParam1(), command.getParam2());	
			}
			else
			{
				command.setCommandStream();
			}
			
			byte[] command_stream = command.getCommandStream();
			System.out.println();
			System.out.println("In UARTOperator executes new command, CoordinatorID: " + coordinator.id + " the sendMessage. Command name: " + command_name + " id: " + command_id + " command stream: " + Arrays.toString(command_stream));
			//byte[] command_result = this.sendAndRetrieveMessage(this.localServer.id, this.uartFD, command_stream);
			//synchronized(this) {
				//byte[] command_result = this.sendAndRetrieveMessage(coordinator.id, coordinator.uartFD, command_stream);
				byte[] command_result = sendAndRetrieveMessage(coordinator.id, coordinator.uartFD, command_stream);
				command.setResult(command_result);
			//}
			
		}
		
		return command;
		
	}
	
	void continueRoundRobin(Command command, Coordinator coordinator)
	{
		byte[] ack_array = new byte[3];
		byte[] ack_commandStream = new byte[3+coordinator.ieeeAddress.length];
		
		command.resetStatus();
		ack_array = command.getAckArray();
		ack_array[1] = (byte)(ack_array[1] & 0xFE);	  // reset control register write to be 
		ack_array[2] = (byte)(ack_array[2] | 0x08);  // to set Ack in local server
		
		System.arraycopy(ack_array, 0, ack_commandStream, 0, ack_array.length);
		System.arraycopy(coordinator.ieeeAddress, 0, ack_commandStream, ack_array.length, coordinator.ieeeAddress.length);
		
		System.out.println();
		System.out.println("In UARTOperator continues round robin, CoordinatorID: " + coordinator.id + " . Command name: " + command.name + " id: " + command.id + " command ack command stream: " + Arrays.toString(ack_commandStream));
		//synchronized(this) {
			//byte[] command_result = this.ackAndRetrieveMessage(coordinator.id, coordinator.uartFD, command.id, ack_commandStream);
			byte[] command_result = ackAndRetrieveMessage(coordinator.id, coordinator.uartFD, command.id, ack_commandStream);
			command.setResult(command_result);
		//}
		
	}

	int getYear(Timestamp timestamp)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timestamp.getTime()));
		int year = cal.get(Calendar.YEAR);
		
		return year;
		
	}
	
	int getMonth(Timestamp timestamp)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(timestamp.getTime()));
		int month = cal.get(Calendar.MONTH);
		
		return month;
	}
	
	
	public void run() {
		
		
		byte command_id;
		String command_name;
		Command command;
		//Coordinator coordinator;
		
		byte[] command_stream;
		byte[] command_result;
		byte[] ack_array;
		//byte status;
		int uartFD;
		
		
		while (true) {
			
			//uartFD = this.init(this.coordinator.id);
			uartFD = init(this.coordinator.id);
			
			System.out.println("In Java, local server: " + this.localServer.id + " coordinator: " + this.coordinator.id + " initiates UART port id: " + uartFD);
			if (uartFD > 0)
			{
				this.coordinator.setUartFd(uartFD);
				break;
			}
			else
			{
				System.out.println("In Java, local server: " + this.localServer.id + " coordinator: " + this.coordinator.id + " failed to initiate UART port.");
			}

		}
		
		while (true) {
		  try {
			// move on to the next command
			  
			//Thread.sleep(5000); // when there was delay, it needs to wait for 5 seconds
			  Thread.sleep(2000); // now only wait for 2 second
			//for (int i=0; i<this.localServer.coordinatorArray.length; i++)	
			//{
				//synchronized(this) {
					
					//Thread.sleep(5000);
					//coordinator = this.localServer.coordinatorArray[i];
					//command = coordinator.getPreviousExecutedCommand(); 
					command = this.coordinator.previousExecutedCommand;
					
					
					//System.out.println("In UARTOperator, the sendMessage. ### check out the previous command first: ");
					if (command == null)  // no previous executed command, it is the first time
					{
						//System.out.println("In UARTOperator, there is no previous command");
						command = this.executeNewCommand(this.coordinator);
						
					}
					else   // there is a previous executed command
					{					
						command_id = command.getId();
						command_name = command.getName();
						ack_array = command.getAckArray();
						
						System.out.println();
						System.out.println("In UARTOperator. coordinator: " + this.coordinator.id + " ### Previous command name: " + 
								command_name + " command result: " + Arrays.toString(command.result) +
								" ack_array[0]: " + String.format("0x%2s", Integer.toHexString(ack_array[0])).replace(' ', '0') + 
								" ack_array[1]: " + String.format("0x%2s", Integer.toHexString(ack_array[1])).replace(' ', '0') +
								" ack_array[2]: " + String.format("0x%2s", Integer.toHexString(ack_array[2])).replace(' ', '0'));
						
						
						switch (command_name) {
							case "Data Register Read":
						
								//Thread.sleep(10000);  // when there was delay, it needs to wait for 10 seconds
								//Thread.sleep(1000);		// now only wait for 1 second
								ack_array = command.getAckArray();
								//System.out.println("### ack_array[0]: "+ String.format("0x%8s", Integer.toHexString(ack_array[0]).replace(' ', '0')));
								if ((ack_array[0] >> 7) == 0) 	// the round robin of the coordinator is done
								{
									//System.out.println("##### In UARTOperator. Data Regiter read round robin stops. ");
									//System.out.println("### ack_array[0]: "+ String.format("0x%8s", Integer.toHexString(ack_array[0]).replace(' ', '0')));
									//Thread.sleep(1000);
									
									command = this.executeNewCommand(this.coordinator);

								}
								else
								{
								
									if (this.coordinator.getGeneratedCommandQueue().isEmpty() == true)  // there is no new command
									{			
										//System.out.println("##### In UARTOperator, without new command. Data Regiter read continues  ");
										//Thread.sleep(10000);
									
										this.continueRoundRobin(command, this.coordinator);  // command gets updated in the function call.
									

									}
									else
									{
										//System.out.println("##### In UARTOperator,  there is new command. Stop power calculation. Data Regiter read round robin doesn't stop yet.");
										ack_array[1] = (byte)(ack_array[1] & 0xFD);  // set the power calculation = 0		
										
										//Thread.sleep(5000);
										this.continueRoundRobin(command, this.coordinator);			

									}
								}
								break;
								
							case "Route Table Read": 
						
								ack_array = command.getAckArray();
								System.out.println("In UARTOperator, coordinator " + coordinator.id + " Route table read, ack_array: " + Arrays.toString(ack_array));
								if ((ack_array[0] >> 5) == 0)  // round robin is done 
								{
									System.out.println("In UARTOperator, coordinator " + coordinator.id + " Route table read is done, ack_array: " + Arrays.toString(ack_array));
									command = this.executeNewCommand(this.coordinator);

								
								}
								else  // continue the round robin
								{
									System.out.println("In UARTOperator, coordinator " + coordinator.id + " Route table read continues, ack_array: " + Arrays.toString(ack_array));
									this.continueRoundRobin(command, this.coordinator);

								}
								break;

							default:  // other commands which do not need to use ackAndRetrieveMessage()
								
								command = this.executeNewCommand(this.coordinator);
						
								break;													

							}
						}
					
						if (command != null)  // this could be null when there is no new command or ack executed
						{
						
							Date date = new Date();
							command.setExecutedTime(new Timestamp(date.getTime()));

							command.setData();
							command.setAckArray();
							command.setStatus();
			
							System.out.println("In UARTOperator, the command result: " + Arrays.toString(command.getResult()));
							System.out.println("In UARTOperator, the command data: " + Arrays.toString(command.getData()));
							System.out.println("In UARTOperator, the command status: " + command.getStatus());	
					
							if (command instanceof RouteTableReadCommand)
							{
								RouteTableReadCommand routeTableReadCommand = (RouteTableReadCommand)command;
								routeTableReadCommand.addElement();
						
								System.out.println("In UARTOperator routing table read command, the current element: " + Arrays.toString(routeTableReadCommand.getElement()));
								coordinator.addRoutingTableElement(routeTableReadCommand.getElement());
						
								System.out.println("In UARTOperator coordinatorID: " + coordinator.id + " the current element: " + Arrays.toString(this.coordinator.getRoutingTableElement()));
							}
							if (command instanceof ParamRegReadCommand)
							{
								ParamRegReadCommand paramRegReadCommand = (ParamRegReadCommand)command;
								paramRegReadCommand.setParamReg();
								System.out.println("In UARTOperator the Parameter register: " + Arrays.toString(paramRegReadCommand.getParamReg()));
							}
							if (command instanceof CalibrationRegReadCommand)
							{
								CalibrationRegReadCommand calibrationRegReadCommand = (CalibrationRegReadCommand)command;
								calibrationRegReadCommand.setCalibrationReg();
								calibrationRegReadCommand.setSmartMeterIndex();
								System.out.println("In UARTOperator the Calibration register: " + Arrays.toString(calibrationRegReadCommand.getCalibrationReg()));
							}
							if (command instanceof DataRegReadCommand)
							{
								String ieeeAddressString;
								long diffSeconds = 0;
								int energy = 0;
								int accumulatedEnergy = 0;
								int[] nullTimeIntArray = {1990, 1, 1, 1, 1, 1};  // In Java, the hash table can't have null value.
								//int year=1990;
								Timestamp nullTimestamp = getTimestamp(nullTimeIntArray);
								Integer previousAccumulatedEnergy=0;
								Timestamp currentPoll = nullTimestamp;
								Timestamp previousPoll = nullTimestamp;;
						
						
								DataRegReadCommand dataRegReadCommand = (DataRegReadCommand)command;
								dataRegReadCommand.setDataReg();
								dataRegReadCommand.setTimeIntegerArray();
								dataRegReadCommand.setTimestamp();
								System.out.println("In UARTOperator the Data register: " + Arrays.toString(dataRegReadCommand.getDataReg()));
								ieeeAddressString = dataRegReadCommand.getIEEEAddressString();
						
								Command previousCommand = this.coordinator.getSmartMeterPreviousCommand(ieeeAddressString);
												
						
								System.out.println("In UARTOperator, null timestamp: " + nullTimestamp);
								//previousPoll = nullTimestamp;
											
								if (previousCommand != null) 
								{
									previousPoll = this.coordinator.getSmartMeterPreviousTimestamp(ieeeAddressString);
									currentPoll = dataRegReadCommand.getTimestamp();
									previousAccumulatedEnergy = this.coordinator.getSmartMeterPreviousAccumulatedEnergy(ieeeAddressString);
									System.out.println("In UARTOperator, previous accumulatd energy: " + previousAccumulatedEnergy);
													
									if (previousPoll != null)
									{
										if (this.getMonth(previousPoll) == this.getMonth(currentPoll))  // within the same month
										{
											if (previousCommand instanceof DataRegReadCommand)
											{
									
												diffSeconds = (currentPoll.getTime() - previousPoll.getTime())/1000;							
												//System.out.println("In UARTOperator Data register read, currentPoll: " + currentPoll + " previousPOll: " + previousPoll + " diffSeconds: " + diffSeconds);								
												energy = dataRegReadCommand.getPower() * (int)diffSeconds;			
								
											}

											accumulatedEnergy = previousAccumulatedEnergy + energy;   // interruption within the same month, energy =  the difference or 0;
									
										}
								
									}		
								
								}   // otherwise, energy and accumulatedEnergy are all default 0
						
								dataRegReadCommand.setEnergy(energy);
								dataRegReadCommand.setAccumulatedEnergy(accumulatedEnergy);
						
								System.out.println("In UARTOperator, previousPoll: " + previousPoll + " currentPoll: " + currentPoll);
						
								this.coordinator.setSmartMeterPreviousTimestamp(ieeeAddressString, dataRegReadCommand.getTimestamp());						
								this.coordinator.setSmartMeterPreviousAccumulatedEnergy(ieeeAddressString, new Integer(accumulatedEnergy));
								this.coordinator.setSmartMeterPreviousCommand(ieeeAddressString, dataRegReadCommand);
						
								
								System.out.println("In UARTOperator, ieeeAddress: " + ieeeAddressString + "updated timestamp: " + this.coordinator.getSmartMeterPreviousTimestamp(ieeeAddressString)
										+ " diffSecond: " + diffSeconds + " Energy: " + energy + " Accumulated energy: " + accumulatedEnergy 
										+ " Updated previous command: " + this.coordinator.getSmartMeterPreviousCommand(ieeeAddressString).name);
								
						
							}	
					
							// to set the smart meter previous command.
										
							if (command instanceof DataRegReadCommand)
							{
								DataRegReadCommand dataRegReadCommand = (DataRegReadCommand)command;
							
								this.coordinator.setSmartMeterPreviousCommand(dataRegReadCommand.smIEEEAddressString, command);
							}
							else if (command instanceof RouteTableReadCommand)
							{
								RouteTableReadCommand routeTableReadCommand = (RouteTableReadCommand)command;
						
								this.coordinator.setSmartMeterPreviousCommand(Arrays.toString(routeTableReadCommand.getIEEEAddress()), command);
							}
							else
							{
								this.coordinator.setSmartMeterPreviousCommand(command);    // record the previous command for all the smart meters
							}
					
							localServer.commandManager.setExecutedCommand(this.coordinator, command);   // Enqueue through commandManager, so it will update the previous command as well
					
							System.out.println();
							System.out.println("In UARTOperator, coordinator: " + this.coordinator.id + " the command: " + command.getName() + 
									" command result" + Arrays.toString(command.result) + " is inserted into the executed command queue. " +
									"Previous command:" + this.coordinator.previousExecutedCommand.name  +
									"command result: " + Arrays.toString(this.coordinator.previousExecutedCommand.result));
							
							
							System.out.println();
						}  // end of if command null 
						
					//}  // end of synchronization block

				//}  // end of for each coordinator loop
		  	} catch (Exception e) {
		  			System.out.println("In Java, something is wrong wiht UART Operator");
		  			e.printStackTrace();
		  			break;
		  	}
		}  // end of while loop 
		
		//for (int i = 0; i < localServer.coordinatorArray.length; i++)
		//{
			//coordinator = localServer.coordinatorArray[i];
			//this.close(this.coordinator.id, coordinator.uartFD);
			close(this.coordinator.id, coordinator.uartFD);
			System.out.println("In UARTOperator, local server: " + this.localServer.id + " coordinator: " + coordinator.id + " closed UART Port:" + coordinator.uartFD);
	
		//}
	}
	
}
