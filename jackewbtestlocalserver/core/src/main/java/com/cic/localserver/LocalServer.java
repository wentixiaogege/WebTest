package com.cic.localserver;

//import java.util.Arrays;
import java.util.concurrent.* ;

//import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JFrame;

import java.util.Date;
import java.util.Timer;
//import java.util.TimerTask;



public class LocalServer {
	
	//BlockingQueue<Command> generatedCommandQueue;
	//BlockingQueue<Command> executedCommandQueue;
	int id;
	
	Coordinator[] coordinatorArray;
	Coordinator coordinator0;
	Coordinator coordinator1;
	
	CommandManager commandManager;
	
	private static LocalServer localServer;
	
	//static TimerTaskProducer timerTaskProducer;
	//static Timer timer;
	
	//static GUIControlPanel frame;
	//static DBOperator dbOperator;

	
	public LocalServer(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		
		byte[] ieeeAddress0 = new byte[] {0x00, 0x12, 0x4B, 0x00, 0x04, 0x0E, (byte)0xF1, (byte)0x91};
		byte[] ieeeAddress1 = new byte[] {0x00, 0x12, 0x4B, 0x00, 0x04, 0x0F, 0x05, (byte)0xB3};
		byte[] ieeeAddress2 = new byte[] {0x00, 0x12, 0x4B, 0x00, 0x04, 0x0E, (byte)0xEE, (byte)0xED};
		
		//byte[][] ieeeAddressArray = new byte[][] {ieeeAddress0, ieeeAddress1, ieeeAddress2};
		byte[][] ieeeAddressArray = new byte[][] {ieeeAddress0, ieeeAddress2, ieeeAddress1};
		
		
		//this.coordinatorArray = new Coordinator[2];
		
		this.coordinatorArray = new Coordinator[1];
		
		
		for (int i=0; i< this.coordinatorArray.length; i++)
		{
			this.coordinatorArray[i] = new Coordinator(i, ieeeAddressArray[i]);
		}
		
		System.out.println("In Java, one coordinator array is created in local server:" + this.id);
		
		this.commandManager = new CommandManager(id);
		System.out.println("In Java, one command manager is created in local server:" + this.id);
		
		//generatedCommandQueue = coordinator.getGeneratedCommandQueue();
		//executedCommandQueue = coordinator.getExecutedCommandQueue();
				
		/**
		generatedCommandQueue = new PriorityBlockingQueue<>();
		System.out.println("In Java, one generated command queue is created.");
		executedCommandQueue = new LinkedBlockingQueue<Command>();
		System.out.println("In Java, one executed command queue is created.");
		
		*/
		
		//TimerTaskProducer timerTaskProducer = new TimerTaskProducer(generatedCommandQueue);
		//timer = new Timer(true);
		
		//frame = new GUIControlPanel(generatedCommandQueue);
		//dbOperator = new DBOperator(executedCommandQueue);
		//dbOperator.addObserver(frame);
		
	}
	
	int getNumberOfCoordinators() {
		
		return this.coordinatorArray.length;
	}

	
	
	public static void main(String[] args) {
		
			   
		
		// final BlockingQueue<Command> executedCommandQueue = new LinkedBlockingQueue<Command>();
		int localServerId = 0;
		localServer = new LocalServer(localServerId);
		
		
		
		//UARTOperator[] uartOperatorArray = new UARTOperator[2];
		
		//CommandManager commandManager = new CommandManager();
		
		//localServer.commandManager.startCoordinator(localServer.coordinatorArray);  // start with several commands in sequence
		
		//UARTOperator uartOperator = new UARTOperator(localServer.id, localServer.coordinatorArray);
		
		for (int i=0; i< localServer.coordinatorArray.length; i++)
		{
			Coordinator coordinator = localServer.coordinatorArray[i];
			UARTOperator uartOperator = new UARTOperator(getLocalServer(), coordinator, i);
			
			System.out.println("In Java, uartOperatorID: " + uartOperator.id + " is started from local server: " + localServer.id + " coordinatorID: " + i);
			new Thread(uartOperator).start();  // Coordinator as a consumer
		}
		//UARTOperator uartOperator = new UARTOperator(localServer);
		
		//new Thread(uartOperator).start();  // Coordinator as a consumer
		

		//localServer.commandManager.startCoordinator(localServer.coordinatorArray);
		
        //TimerTaskProducer timerTaskProducer = new TimerTaskProducer(generatedCommandQueue);
        // running timer task as daemon thread


		
		//GUIControlPanel frame = new GUIControlPanel(localServer.coordinator);
		GUIControlPanel guiControlPanel = new GUIControlPanel(getLocalServer());
		
		DBOperator dbOperator = new DBOperator(getLocalServer(), guiControlPanel);
		
		//dbOperator.addObserver(frame);
	/**	
		new Thread(dbOperator).start();  // Coordinator as a consumer
		System.out.println("In Java, one DBOperator is started from local server:" + localServer.id);				    
	*/	
	    SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	          //final GUIControlPanel frame = new GUIControlPanel(generatedCommandQueue);
	          //frame.setTitle("Swing Worker Demo");
	          guiControlPanel.setSize(1000, 600);
	          guiControlPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	          guiControlPanel.setVisible(true);
	          System.out.println("In Java, GUIControlPanel is started from local server:" + localServer.id);
	        }
	      });
	    
	    
		//queueConsumerWorker = new DisplaySwingWorker(messagesTextArea, command);
		//DisplaySwingWorker displaySwingWorker = new DisplaySwingWorker(messagesTextArea);
	
		new Thread(dbOperator).start();  // Coordinator as a consumer
		System.out.println("In Java, one DBOperator is started from local server:" + localServer.id);			
	    
		
		TimerTaskProducer timerTaskProducer = new TimerTaskProducer(getLocalServer());
		
		//
		//TimerTaskProducer timerTaskProducer = new TimerTaskProducer(localServer.);
        
       // Timer timer = new Timer(true);
      //  timer.scheduleAtFixedRate(timerTaskProducer, 0, 1 * 1000);
        
        System.out.println();
        System.out.println("A TimerTaskProduce begins from local server:" + localServer.id + " at time:" + new Date());
        System.out.println();
        
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	

        // cancel after sometime
        try {
        	
            Thread.sleep(160000);
            
        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    
      //  timer.cancel();
      //  System.out.println();
      //  System.out.println("TimerTaskProducer is cancelled! :" + new Date());
      //  System.out.println();

        try {

            Thread.sleep(30000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

	
		
	    
	}

	public static LocalServer getLocalServer() {
		return localServer;
	}

	public void setLocalServer(LocalServer localServer) {
		this.localServer = localServer;
	}

}

