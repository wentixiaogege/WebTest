package com.cic.localserver;

//import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.sql.*;
import java.util.Properties;

public class TimerTaskProducer extends TimerTask {
	
	 //BlockingQueue<Command> commandQueue;
	 //Coordinator coordinator;
	 LocalServer localServer;
	 TimerTaskProducer(LocalServer localServer) {
		 //this.coordinator = coordinator;
	     //this.commandQueue = coordinator.getGeneratedCommandQueue();
		 this.localServer = localServer;
	 }

	 
	 
   @Override
   public void run() {
   	
       //System.out.println("Start time:" + new Date());
       doSomeWork();
       //System.out.println("End time:" + new Date());

   }

   // simulate a time consuming task
   
   private static final String dbClassName = "com.mysql.jdbc.Driver";

   private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/emsdb";
        // "jdbc:mysql://localhost/emsdb";
		   

 /*  public void rest(int id, int xx){
	   localServer.commandManager.setGeneratedCommand(localServer.coordinatorArray[coordinatorID], command);
   }
   */

   private void doSomeWork() {
	   
	   
	   byte[][] smartMeterTable = {{0, 18, 75, 0, 4, 15, 26, 60}, {0, 18, 75, 0, 4, 15, 28, 119}, {0, 18, 75, 0, 4, 14, (byte)-15, (byte)-98}};
	     
  	   int generatedCommandArrayLength = localServer.getNumberOfCoordinators();
   	   Command[] generatedCommandArray = new Command[generatedCommandArrayLength];
	   

       try {
           Thread.sleep(10000);
           
        	    System.out.println("Timer task producer: " + dbClassName);
   	    		// Class.forName(xxx) loads the jdbc classes and
        	    // creates a drivermanager class factory
   	    	    try {
					Class.forName(dbClassName);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

   	    	    // Properties for user and password. Here the user and password are both 'paulr'
   	    	    Properties p = new Properties();
   	    	    p.put("user","root");
   	    	    p.put("password","355itu11");

   	    	    // Now try to connect
   	    
   	    	    try {
					Connection connection = DriverManager.getConnection(CONNECTION,p);
					Statement stmt = null;
	   	    	    ResultSet rs = null;
	   	    	    
	   	    	    Timestamp maxTimestamp = null;
	   	    	    Timestamp timestamp = null;
	   		
	   			
	   	    	    String sql = "select * from cloud_command where checked = 0 order by timestamp";
	   			
	   	    	    stmt = connection.createStatement();
	   	    	    rs = stmt.executeQuery(sql);
	   	    	    
	   	    	    
	   	    	    while (rs.next())
	    			{
	   	    	    	int commandID= rs.getInt("command_id");
	   	    	    	String name = rs.getString("name");
	   	    	    	int dataLength  = rs.getInt("data_length");
	   	    	    	//int checked = rs.getInt("checked");
	   	    	    	timestamp = rs.getTimestamp("timestamp");
	   	    	    	int smartMeterID = rs.getInt("smartmeter_id");
	   	    	    	int coordinatorID = rs.getInt("coordinator_id");
	   	    	    	int param1 = rs.getInt("param1");
	   	    	    	
	   	    	    	Command command= null;
	   	    	    	
						switch (commandID) {
						case 3:  // Relay Control
							byte controlRelay = (byte)param1;  
							command = new RelayControlCommand("Relay Control", (byte)commandID, dataLength, controlRelay, smartMeterTable[smartMeterID]);
							
							break;
						}	
						
						command.setGeneratedTime(timestamp);
						command.setGeneratedBy("CloudCommand");
						localServer.commandManager.setGeneratedCommand(localServer.coordinatorArray[coordinatorID], command);
		       	 		System.out.println();
		       	 		System.out.println("In GUIControlPanel, command: " + command.getName() + " is generated into the queue in CoordinatoorID: " + coordinatorID);
		       	 		System.out.println();
						
						maxTimestamp = timestamp;
						
	    			}
	   	    	    
	   	    	    
	   	    	    stmt = connection.createStatement();
	   	    	    sql = "UPDATE cloud_command " +
	   	                   "SET checked = 1 WHERE (checked = 0) and (timestamp <= '" + maxTimestamp + "')";
	   	    	    stmt.executeUpdate(sql);
	   	    	    
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   	    	  
   	    	    /*
   	 		
				for (int i = 0; i < localServer.getNumberOfCoordinators(); i++)
				{
				 
					Command command = generatedCommandArray[i];
					Thread.sleep(1);  // to avoid multiple thread deadlock
					
					if (command != null)
					{
						command.setGeneratedBy("CloudCommand");
				 
						//Date date = new Date();
						//command.setGeneratedTime(new Timestamp(date.getTime()));
				 
						//coordinator.getGeneratedCommandQueue().put(generatedCommand);
				 
		       	 		localServer.commandManager.setGeneratedCommand(localServer.coordinatorArray[i], command);
		       	 		System.out.println();
		       	 		System.out.println("In GUIControlPanel, command: " + command.getName() + " is generated into the queue in CoordinatoorID: " + i);
		       	 		System.out.println();
					}
				}
				*/
   	    	    
           

       } catch (InterruptedException e) {
       	
    	   System.out.println("In Java, command can not be inserted into the queue.");
           e.printStackTrace();
       }

   }
}