package com.cic.localserver;

import javax.swing.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.*;
import java.util.Properties;
import java.util.Arrays;
import java.util.Observable;
//import java.util.Arrays;
import java.util.concurrent.*;

class DBOperator extends Observable implements Runnable {
	
	//BlockingQueue<Command> executedCommandQueue;
	//DisplaySwingWorker displaySwingWorker;
	//GUIControlPanel frame;
	
	//Coordinator coordinator;
	LocalServer localServer;
	GUIControlPanel guiControlPanel;
	
	//JFrame frame;
	
	//DBOperator(Coordinator coordinator) {
	DBOperator (LocalServer localServer, GUIControlPanel guiControlPanel) {
		
		this.localServer = localServer;
		this.guiControlPanel = guiControlPanel;
		//this.frame = frame;
		//this.coordinator = coordinator;
		//this.executedCommandQueue = coordinator.getExecutedCommandQueue();
		//this.frame = frame;
	}

	/**
	BlockingQueue<Command> getExecutedCommandQueue() {
		return this.executedCommandQueue;
	}
	*/
	
	  /**
	  private static final String CONNECTION =
	                          "jdbc:mysql://127.0.0.1/emsdb";
	*/
	  
	 // The JDBC Connector Class.
	  private static final String dbClassName = "com.mysql.jdbc.Driver";
	  
	  private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/emsdb";
	         // "jdbc:mysql://localhost/emsdb";

	
	public void run() {
		
		//byte command_id;
		//String command_name;
		Command command;
		//byte[] command_stream;
//		byte[] command_result;
	    // The number of instances the word is found
	    //int matches = 0;

		
		while (true) {
			
			//QueueConsumerWorker.failIfInterrupted();
			// move on to the next command
			
			try {
				
				//Thread.sleep(5000);
				
			    //System.out.println(dbClassName);
			    // Class.forName(xxx) loads the jdbc classes and
			    // creates a drivermanager class factory
			    Class.forName(dbClassName);

			    // Properties for user and password. Here the user and password are both 'paulr'
			    Properties p = new Properties();
			    p.put("user","root");
			    p.put("password","355itu11");

			    // Now try to connect
			    Connection connection = DriverManager.getConnection(CONNECTION,p);
			    
			    
			    String sqlCommandLog = "INSERT INTO command_log (command_id, command_stream, command_result, command_data, command_status, generated_by, generated_time, executed_time, lsdb_inserted_time)" +
		    			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			    
			    String sqlSmartMeterData = "INSERT INTO smart_meter_data (sm_index, sm_IEEE_address, rms_V1, rms_I1, status, timestamp, power, energy, accumulated_energy, checked)" +
			    		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    		
			 /**   
			    String sql = "INSERT INTO command_log (command_id, command_stream, command_result)" +
		    			"VALUES (?, ?, ?)";
			  */  
				//command = coordinator.getExecutedCommandQueue().take();  // Dequeue from the generatedComamndQueue
				Command[] commandArray = localServer.commandManager.getExecutedCommand(localServer.coordinatorArray);
				for (int i=0; i<commandArray.length; i++)
				{
					
					command = commandArray[i];
					
					if (command != null)
					{
					
						PreparedStatement prepareStatement = connection.prepareStatement(sqlCommandLog);
						prepareStatement.setInt(1, command.getId());
						//prepareStatement.setString(2, Arrays.toString(command.getCommandStream()));
						//prepareStatement.setString(3, Arrays.toString(command.getResult()));
						
						prepareStatement.setBytes(2, command.getCommandStream());
						prepareStatement.setBytes(3, command.getResult());
			    
						if (command instanceof ParamRegReadCommand)
						{
							ParamRegReadCommand paramRegReadCommand = (ParamRegReadCommand)command;
							prepareStatement.setString(4, Arrays.toString(paramRegReadCommand.getParamReg()));
							
						}
						else if (command instanceof CalibrationRegReadCommand)
						{
							CalibrationRegReadCommand calibrationRegReadCommand = (CalibrationRegReadCommand)command;
							prepareStatement.setString(4, Arrays.toString(calibrationRegReadCommand.getCalibrationReg()));
						}
						else if (command instanceof DataRegReadCommand)
						{
							DataRegReadCommand dataRegReadCommand = (DataRegReadCommand)command;

							prepareStatement.setString(4, Arrays.toString(dataRegReadCommand.getDataReg())); // this should be set by coordinator in the future
						
							PreparedStatement prepareStatementSmartMeterData = connection.prepareStatement(sqlSmartMeterData);
							prepareStatementSmartMeterData.setInt(1, dataRegReadCommand.smIndex);
							prepareStatementSmartMeterData.setString(2, dataRegReadCommand.smIEEEAddressString);
							prepareStatementSmartMeterData.setInt(3, dataRegReadCommand.rmsV1);
							prepareStatementSmartMeterData.setFloat(4, (float)(dataRegReadCommand.rmsI1)/100);   // transfer back 
							prepareStatementSmartMeterData.setInt(5, dataRegReadCommand.status);
							prepareStatementSmartMeterData.setTimestamp(6, dataRegReadCommand.timestamp); 
							prepareStatementSmartMeterData.setFloat(7, (float)(dataRegReadCommand.power)/100);
							prepareStatementSmartMeterData.setFloat(8, (float)(dataRegReadCommand.energy)/100);
							prepareStatementSmartMeterData.setFloat(9, (float)(dataRegReadCommand.accumulatedEnergy)/100);
							prepareStatementSmartMeterData.setString(10, "0");  // unchecked yet
							prepareStatementSmartMeterData.execute();
	
						}
						else
						{
							prepareStatement.setString(4, Arrays.toString(command.getData()));
						}
				
						//prepareStatement.setInt(5, command.getStatus());
						prepareStatement.setByte(5, command.getStatus());
			    
						prepareStatement.setString(6, command.getGeneratedBy());
						prepareStatement.setTimestamp(7, command.getGeneratedTime());
						prepareStatement.setTimestamp(8, command.getExecutedTime());
			    
						Date date = new Date();
						command.setLsDBInsertedTime(new Timestamp(date.getTime()));
						prepareStatement.setTimestamp(9, command.getLsDBInsertedTime());
  
						prepareStatement.execute();
					
						this.guiControlPanel.updateMessagesTextArea(command);
					}
				}
			    
			    //System.out.println("In DBOperator, JDBC works !");
			    connection.close();
				
				
			}catch (SQLException se) {
				//Handle errors for JDBC
				se.printStackTrace();
				break;
			}		
			catch (Exception e) {
				e.printStackTrace();
				break;
			}
			
		}

	}

}
