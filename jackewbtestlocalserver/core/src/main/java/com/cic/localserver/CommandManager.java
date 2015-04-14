package com.cic.localserver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.* ;

class CommandManager {
	//BlockingQueue<Command> generatedCommandQueue;
	//BlockingQueue<Command> executedCommandQueue;
	
	//Command previousGeneratdCommand;
	//Command currentExecutedCommand;
	//Command currentCommand;
	//Command nextCommand;
	
	int localServerID;
	
	CommandManager(int localServerID) {
		/**
		generatedCommandQueue = new PriorityBlockingQueue<>();
		System.out.println("In Java, one generated command queue is created for the command manager");
		executedCommandQueue = new LinkedBlockingQueue<Command>();
		System.out.println("In Java, one executed command queue is created for the command manager");
		*/
		
		this.localServerID = localServerID;
	}
	
	/**
	BlockingQueue<Command> getGeneratedCommandQueue() {
		return this.generatedCommandQueue;
	}
	
	BlockingQueue<Command> getExecutedCommandQueue() {
		return this.executedCommandQueue;
	}

	 */
	
	void setPreviousGeneratedCommand(Coordinator coordinator, Command command) {
		
		coordinator.setPreviousGeneratedCommand(command);
	}
	
	Command getPreviousGeneratedCommand(Coordinator coordinator) {
		return coordinator.getPreviousGeneratedCommand();
	}
	
	private void setPreviousExecutedCommand(Coordinator coordinator, Command command) {
		coordinator.setPreviousExecutedCommand(command);
		   
	}

	
	private Command getPreviousExecutedCommand(Coordinator coordinator) {
		return coordinator.getPreviousExecutedCommand();
	}
	
	

	/**
	void setCurrentCommand(Command command) {
		this.currentCommand = command;
	}
	
	Command getCurrentCommand() {
		return currentCommand;
	}
	
	void setNextCommand(Command command) {
		this.nextCommand = command;
	}
	
	Command getNextCommand() {
		return nextCommand;
	}
	
	*/
	
	void startCoordinator(Coordinator coordinator) {
		
		Command command1 = new Command("Network Discovery", (byte)4, 2);
		command1.setGeneratedBy("CommandManager");	 
		Date date1 = new Date();
		command1.setGeneratedTime(new Timestamp(date1.getTime()));
		
		Command command2 = new RouteTableReadCommand("Route Table Read", (byte)5, 11);
	    command2.setGeneratedBy("CommandManager");	 
		Date date2 = new Date();
		command2.setGeneratedTime(new Timestamp(date2.getTime()));

		
		Command command3 = new Command("Control register read", (byte)6, 31);
		command3.setGeneratedBy("CommandManager");	 
		Date date3 = new Date();
		command3.setGeneratedTime(new Timestamp(date3.getTime()));
				
 		Command command4 = new ParamRegReadCommand("Parameter register read", (byte)0, 36); 
	    command4.setGeneratedBy("CommandManager");	 
		Date date4 = new Date();
		command4.setGeneratedTime(new Timestamp(date4.getTime()));
		
		Command command5 = new Command("Parameter Register Write", (byte)1, 0);
	    command5.setGeneratedBy("CommandManager");	 
		Date date5 = new Date();
		command5.setGeneratedTime(new Timestamp(date5.getTime()));
		
		Command command6 = new DataRegReadCommand("Data Register Read", (byte)7, 46);
	    command6.setGeneratedBy("CommandManager");	 
		Date date6 = new Date();
		command6.setGeneratedTime(new Timestamp(date6.getTime()));
			
 		try {
 			coordinator.getGeneratedCommandQueue().put(command1); 
 			setPreviousGeneratedCommand(coordinator, command1);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command2);
 			setPreviousGeneratedCommand(coordinator, command2);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 	
 			/**
 			coordinator.getGeneratedCommandQueue().put(command3);
 			setPreviousGeneratedCommand(coordinator, command3);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command4);
 			setPreviousGeneratedCommand(coordinator, command4);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command5);
 			setPreviousGeneratedCommand(coordinator, command5);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command6);
 			setPreviousGeneratedCommand(coordinator, command6);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			 */
 		}
 		catch (Exception e)
 		{
 			System.out.println("Something is wrong to start coordiantors from Command Manager!");
 			e.printStackTrace();
 		}
	}
	
	void startCoordinator(Coordinator[] coordinatorArray) {
		for (int i=0; i<coordinatorArray.length; i++)
		{
			startCoordinator(coordinatorArray[i]);
		}
		
	}
	
	void stopCoordinator(Coordinator coordinator) {
		
	}
	
	void stopCoordinator(Coordinator[] coordinatorArray) {
		
	}
	
	void pauseCoordinator(Coordinator coordinator) {
		
	}
	
	void pauseCoordinator(Coordinator[] coordinatorArray) {
		
	}
	
	void regressionTest(Coordinator coordinator) {
		Command command1 = new Command("Network Discovery", (byte)4, 2);
		command1.setGeneratedBy("CommandManager");	 
		Date date1 = new Date();
		command1.setGeneratedTime(new Timestamp(date1.getTime()));
		
		Command command2 = new RouteTableReadCommand("Route Table Read", (byte)5, 11);
	    command2.setGeneratedBy("CommandManager");	 
		Date date2 = new Date();
		command2.setGeneratedTime(new Timestamp(date2.getTime()));
		
		Command command3 = new Command("Control register read", (byte)6, 31);
		command3.setGeneratedBy("CommandManager");	 
		Date date3 = new Date();
		command3.setGeneratedTime(new Timestamp(date3.getTime()));
				
 		Command command4 = new ParamRegReadCommand("Parameter register read", (byte)0, 36); 
	    command4.setGeneratedBy("CommandManager");	 
		Date date4 = new Date();
		command4.setGeneratedTime(new Timestamp(date4.getTime()));
		
		Command command5 = new Command("Parameter Register Write", (byte)1, 0);
	    command5.setGeneratedBy("CommandManager");	 
		Date date5 = new Date();
		command5.setGeneratedTime(new Timestamp(date5.getTime()));
		
		Command command6 = new DataRegReadCommand("Data Register Read", (byte)7, 46);
	    command6.setGeneratedBy("CommandManager");	 
		Date date6 = new Date();
		command6.setGeneratedTime(new Timestamp(date6.getTime()));
		
		Command timeSetCommand = new TimeSetCommand("Time Set", (byte)13, 0);
	    command6.setGeneratedBy("CommandManager");	 
		Date date7 = new Date();
		command6.setGeneratedTime(new Timestamp(date7.getTime()));
			
 		try {
 			coordinator.getGeneratedCommandQueue().put(command1); 
 			setPreviousGeneratedCommand(coordinator, command1);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command2);
 			setPreviousGeneratedCommand(coordinator, command2);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 	
 			coordinator.getGeneratedCommandQueue().put(timeSetCommand);
 			setPreviousGeneratedCommand(coordinator, timeSetCommand);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command3);
 			setPreviousGeneratedCommand(coordinator, command3);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command4);
 			setPreviousGeneratedCommand(coordinator, command4);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			//coordinator.getGeneratedCommandQueue().put(command5);
 			//setPreviousGeneratedCommand(coordinator, command5);
 			//System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			
 			coordinator.getGeneratedCommandQueue().put(command6);
 			setPreviousGeneratedCommand(coordinator, command6);
 			System.out.println("Coordinator ID: " + coordinator.id + " Previous Coomand:" + coordinator.previousGeneratedCommand.name + " is generated from Command Manager");
 			 
 		}
 		catch (Exception e)
 		{
 			System.out.println("Something is wrong to start coordiantors from Command Manager!");
 			e.printStackTrace();
 		}
		
	}
	
	void regressionTest(Coordinator[] coordinatorArray) {
		try {
			for (int i=0; i<coordinatorArray.length; i++)
			{
				regressionTest(coordinatorArray[i]);
				Thread.sleep(100);  // to avoid multiple thread conflicting
			}
		}
		catch (Exception e)
		{
			System.out.println("Something is wrong to start coordiantors from Command Manager!");
 			e.printStackTrace();
		}
	}

	void resetCoordinator(Coordinator coordiantor) {
		
	}
	
	void resetCoordinator(Coordinator[] coordiantorArray) {
		
	}
	
	void maintainCoordinator(Coordinator coordinator, Command command) {
		// reinforce command sequence here.
	}
	
	void maintainCoordinator(Coordinator[] coordinatorArray, Command command) {
		// reinforce command sequence here.
	}
	
	void setGeneratedCommand(Coordinator coordinator, Command command) {
		// reinforce command sequence here.
		try 
		{
			coordinator.getGeneratedCommandQueue().put(command);
			setPreviousGeneratedCommand(coordinator, command);
			System.out.println("Coordinator ID: " + coordinator.id + " generated command:" + this.getPreviousGeneratedCommand(coordinator).name);
		}
		catch (Exception e)
		{
			System.out.println("Something is wrong to set generated command from Command Manager!");
			e.printStackTrace();
		}
	}
	
	Command getGeneratedCommand(Coordinator coordinator) {
		Command command = null;
		try 
		{
			//if (coordinator.getGeneratedCommandQueue().isEmpty() != true)
			//{
				command = coordinator.getGeneratedCommandQueue().take();
			
				
			//}
				return command;
			
		}
		catch (Exception e)
		{
			System.out.println("Something is wrong to get executed command from Command Manager!");
			e.printStackTrace();
			return null;
		}
	}
	
	Command[] getGeneratedCommand(Coordinator[] coordinatorArray)
	{
		Command[] commandArray = new Command[coordinatorArray.length];
		for (int i=0; i<coordinatorArray.length; i++)
		{
			commandArray[i] = getGeneratedCommand(coordinatorArray[i]);
		}
		return commandArray;
			
	}
	
	void setGeneratedCommand(Coordinator[] coordinatorArray, Command command) {
		// reinforce command sequence here.
		for (int i=0; i<coordinatorArray.length; i++)
		{
			setGeneratedCommand(coordinatorArray[i], command);
			
		}
	}
	
	void setExecutedCommand(Coordinator coordinator, Command command) {
		try 
		{
			coordinator.getExecutedCommandQueue().put(command);
			setPreviousExecutedCommand(coordinator, command);
			System.out.println("Coordinator ID: " + coordinator.id + " executed command:" + 
					this.getPreviousExecutedCommand(coordinator).name + " command result: " + Arrays.toString(command.result));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	Command getExecutedCommand(Coordinator coordinator) {
		
		Command command = null;
		try 
		{
			if (coordinator.getExecutedCommandQueue().isEmpty() != true)
			{
				command = coordinator.getExecutedCommandQueue().take();
			}
			
			return command;
			
		}
		catch (Exception e)
		{
			System.out.println("Something is wrong to get executed command from Command Manager!");
			e.printStackTrace();
			return null;
		}
	}
	
	Command[] getExecutedCommand(Coordinator[] coordinatorArray)
	{
		Command[] commandArray = new Command[coordinatorArray.length];
		for (int i=0; i<coordinatorArray.length; i++)
		{
			commandArray[i] = getExecutedCommand(coordinatorArray[i]);
		}
		return commandArray;
			
	}
	
	
	

}
