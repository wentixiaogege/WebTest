package com.cic.localserver;

import java.sql.SQLException;
import java.util.Date;
import java.sql.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//import java.io.File;
import java.util.concurrent.BlockingQueue;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
//import javax.swing.JFileChooser;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
//import javax.swing.JTextField;
import javax.swing.SwingWorker.StateValue;

import org.jfree.chart.ChartPanel;


public class GUIControlPanel extends JFrame implements Observer {

  /**  */
  private static final long serialVersionUID = -8668818312732181049L;
  
	 // The JDBC Connector Class.
  private static final String dbClassName = "com.mysql.jdbc.Driver";
  
  private static final String CONNECTION = "jdbc:mysql://127.0.0.1:3306/emsdb";
         // "jdbc:mysql://localhost/emsdb";

  private Action displayCancelAction;
//  private Action browseAction;

//  private JTextField wordTextField;
//  private JTextField directoryPathTextField;
  private JTextArea messagesTextArea;
  private JProgressBar searchProgressBar;
  
  private JButton buttonSelect = new JButton("Select Command");
  
  private JButton buttonPlot = new JButton("Plot Data");
  
 // private JButton buttonTimer = new JButton("Start Timer");
  
  private JButton buttonRegressionTest = new JButton("Regression Test");
  private static String ieeeaddress;
  //DisplaySwingWorker displaySwingWorker;
  //Coordinator coordinator;
  LocalServer localServer;
  //BlockingQueue<Command> generatedCommandQueue;
  
  //Command executedCommand;
 
  
 // public GUIControlPanel(Coordinator coordinator) {
	public GUIControlPanel(LocalServer localServer)  {
	super("Local Server Control Panel");
	this.localServer = localServer;
	
	//this.coordinator = coordinator;
	
	//this.generatedCommandQueue = coordinator.getGeneratedCommandQueue();
	
	//this.displaySwingWorker = new DisplaySwingWorker(messagesTextArea);
		
	 setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
	 String[] commandNames = new String[] {
			 "Control Register Read - sm0", "Control Register Read - sm1", "Control Register Read - sm2",
			 "Parameter Register Read - sm0", "Parameter Register Read - sm1", "Parameter Register Read - sm2",
			 "Parameter Register Write - sm0", "Parameter Register Write - sm1", "Parameter Register Write - sm2",
			 "Network Discovery - sm0", "Network Discovery - sm1", "Network Discovery - sm2",  
			 "Route Table Read", "Data Register Read", 
			 "Time Set - sm0", "Time Set - sm1", "Time Set - sm2",
			 "Energy Calculation Reset - sm0", "Energy Calculation Reset - sm1", "Energy Calculation Reset - sm2",
			 "Voltage Calibration - sm0", "Voltage Calibration - sm1", "Voltage Calibration - sm2",
			 "Current Calibration - sm0", "Current Calibration - sm1", "Current Calibration - sm2",
			// "Energy Calibration - sm0", "Energy Calibration - sm1", "Energy Calibration - sm2",
			 "Calibration Register Read - sm0", "Calibration Register Read - sm1", "Calibration Register Read - sm2", 
			 "Configuration Register Write - sm0", "Configuration Register Write - sm1", "Configuration Register Write - sm2",
			 "Relay Control Off - sm0", "Relay Control On - sm0",
			 "Relay Control Off - sm1", "Relay Control On - sm1",
			 "Relay Control Off - sm2", "Relay Control On - sm2"
	 };
	 
	 String[] SelectIeeeAddress = new String[] {"[0, 18, 75, 0, 4, 15, 26, 60]", //1A 3C sm0 
			 "[0, 18, 75, 0, 4, 15, 28, 119]", //1C 77 sm1
			 "[0, 18, 75, 0, 4, 14, -15, -98]", //F1 9E sm2
	 };
	 // create a combo box with items specified in the String array:
	 final JComboBox<String> commandList = new JComboBox<String>(commandNames);
	 final JComboBox<String> ieeeAddressList = new JComboBox<String>(SelectIeeeAddress);
		// make the combo box editable so we can add new item when needed
		ieeeAddressList.setEditable(true);
/**
/**
	 // customize some appearance:
	 commandList.setForeground(Color.BLUE);
	 commandList.setFont(new Font("Arial", Font.BOLD, 14));
	 commandList.setMaximumRowCount(14);
	 
*/		
	 // make the combo box editable so we can add new item when needed
	 commandList.setEditable(true);
	 

	 // add an event listener for the combo box
	 commandList.addActionListener(new ActionListener() {

	 @SuppressWarnings("unchecked")
	 @Override
	 public void actionPerformed(ActionEvent event) {
			JComboBox<String> combo = (JComboBox<String>) event.getSource();
			String selectedCommand = (String) combo.getSelectedItem();

			DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) combo
						.getModel();

			int selectedIndex = model.getIndexOf(selectedCommand);
			if (selectedIndex < 0) {
				// if the selected book does not exist before, 
				// add it into this combo box
				model.addElement(selectedCommand);
			}

		}
	});
	 ieeeAddressList.addActionListener(new ActionListener() {

		 @SuppressWarnings("unchecked")
		 @Override
		 public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedIeeeAddress = (String) combo.getSelectedItem();

				DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) combo
							.getModel();

				int selectedIndex = model.getIndexOf(selectedIeeeAddress);
				if (selectedIndex < 0) {
					// if the selected book does not exist before, 
					// add it into this combo box
					model.addElement(selectedIeeeAddress);
				}

			}
		}); 

	// add event listener for the button Select 
	buttonSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				
				int generatedCommandArrayLength = localServer.getNumberOfCoordinators();
				Command[] generatedCommandArray = new Command[generatedCommandArrayLength];
				Command generatedCommand;
				String selectedCommand = (String) commandList.getSelectedItem();
				byte[] ieeeAddress = new byte[8];
				byte controlRelay;
				byte[] resetValue = new byte[4];
				byte[] configurationRegister = new byte[6];
				
				
				try {
					switch (selectedCommand) {
					case "Control Register Read - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new ControlRegReadCommand("Control Register Read", (byte)6, 31, ieeeAddress);
						
						break;
						
					case "Control Register Read - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new ControlRegReadCommand("Control Register Read", (byte)6, 31, ieeeAddress);
						
						break;
						
					case "Control Register Read - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new ControlRegReadCommand("Control Register Read", (byte)6, 31, ieeeAddress);
						
						break;
						
					case "Parameter Register Read - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						

						generatedCommandArray[0] = new ParamRegReadCommand("Parameter Register Read", (byte)0, 36, ieeeAddress);
						
						//generatedCommand = new ParamRegReadCommand("Parameter Register Read", (byte)0, 36);
						break;
						
					case "Parameter Register Read - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new ParamRegReadCommand("Parameter Register Read", (byte)0, 36, ieeeAddress);
						
						break;
						
					case "Parameter Register Read - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new ParamRegReadCommand("Parameter Register Read", (byte)0, 36, ieeeAddress);
						
						break;
						
					case "Parameter Register Write - sm0":	
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new ParamRegWriteCommand("Parameter Register Write", (byte)1, 0, ieeeAddress);
						//System.out.println("In GUIControlPanel, ParamRegWriteCommand is generated");
						
						break;
						
					case "Parameter Register Write - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new ParamRegWriteCommand("Parameter Register Write", (byte)1, 0, ieeeAddress);
						
						break;
						
					case "Parameter Register Write - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new ParamRegWriteCommand("Parameter Register Write", (byte)1, 0, ieeeAddress);
						
						break;
						
						
					case "Data Register Read":
						for (int i=0; i < localServer.getNumberOfCoordinators(); i++)
						{
							generatedCommandArray[i] = new DataRegReadCommand("Data Register Read", (byte)7, 46);
						}
						/**
					    Class.forName(dbClassName);

					    // Properties for user and password. Here the user and password are both 'paulr'
					    Properties p = new Properties();
					    p.put("user","root");
					    p.put("password","355itu11");
					    

					    // Now try to connect
					    Connection connection = DriverManager.getConnection(CONNECTION,p);
					    
					    Statement statement = connection.createStatement();
					    
					    String selectSmartMeterSQL = "select sm_id, sm_ieeeAddress, coordinator_id from smart_meter where validation = '1'";
					    ResultSet smartMeterResultSet = statement.executeQuery(selectSmartMeterSQL);
					    
					    String selectSmartMeterChannelConfigSQL = "select data_register_length from smart_meter_channel_config where sm_id = ?";
					    PreparedStatement preparedStatement = connection.prepareStatement(selectSmartMeterChannelConfigSQL);
					   
					    
					    
					    while (smartMeterResultSet.next())
					    {
					    	int sm_id = smartMeterResultSet.getInt("sm_id");
					    	byte[] sm_ieeeAddress = smartMeterResultSet.getBytes("sm_ieeeAddress");
					    	int coordinator_id = smartMeterResultSet.getInt("coordinator_id");
					    	
					    	preparedStatement.setInt(1, sm_id);
					    	ResultSet smartMeterChannelResultSet = preparedStatement.executeQuery(selectSmartMeterChannelConfigSQL);
					    	while (smartMeterChannelResultSet.next())
					    	{
					    		byte data_register_length = (byte)smartMeterChannelResultSet.getInt("data_register_length");
					    		generatedCommand = new DataRegReadCommand("Data Register Read", (byte)7, data_register_length, sm_ieeeAddress);
					    	}
					    	
					    }
					    */
						
						//generatedCommand = new DataRegReadCommand("Data Register Read", (byte)7, 46);
						
						break;
					case "Route Table Read":
						for (int i=0; i < localServer.getNumberOfCoordinators(); i++)
						{
							generatedCommandArray[i] = new RouteTableReadCommand("Route Table Read", (byte)5, 11);
						}
						//generatedCommand = new RouteTableReadCommand("Route Table Read", (byte)5, 11);
						break;
						
					case "Network Discovery - sm0":
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new NetworkDiscoveryCommand("Network Discovery", (byte)4, 0, ieeeAddress);					
						
						break;
						
					case "Network Discovery - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new NetworkDiscoveryCommand("Network Discovery", (byte)4, 0, ieeeAddress);
						break;
						
					case "Network Discovery - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new NetworkDiscoveryCommand("Network Discovery", (byte)4, 0, ieeeAddress);
						
						break;
						
					case "Time Set - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;

						generatedCommandArray[0] = new TimeSetCommand("Time Set", (byte)13, 0, ieeeAddress);
						
						break;
						
					case "Time Set - sm1":
						
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new TimeSetCommand("Time Set", (byte)13, 0, ieeeAddress);
						
						break;
						
					case "Time Set - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;

						generatedCommandArray[1] = new TimeSetCommand("Time Set", (byte)13, 0, ieeeAddress);
						
						break;
						
					case "Energy Calculation Reset - sm0":
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						resetValue[0] = 0;
						resetValue[1] = 0;
						resetValue[2] = 0;
						resetValue[3] = 0;
						
						generatedCommandArray[0] = new EnergyCalculationResetCommand("Energy Calculation Reset", (byte)2, 0, resetValue, ieeeAddress);
						
						break;
						
					case "Energy Calculation Reset - sm1":
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						resetValue[0] = 0;
						resetValue[1] = 0;
						resetValue[2] = 0;
						resetValue[3] = 0;
						
						generatedCommandArray[0] = new EnergyCalculationResetCommand("Energy Calculation Reset", (byte)2, 0, resetValue, ieeeAddress);
						
						break;
						
					case "Energy Calculation Reset - sm2":
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						resetValue[0] = 0;
						resetValue[1] = 0;
						resetValue[2] = 0;
						resetValue[3] = 0;
						
						generatedCommandArray[1] = new EnergyCalculationResetCommand("Energy Calculation Reset", (byte)2, 0, resetValue, ieeeAddress);
						break;
						
					case "Relay Control Off - sm0" :
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						controlRelay = 0;  // off
						generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						
						break;
						
					case "Relay Control On - sm0" :
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						controlRelay = 1;  // on
						generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						break;
						
					case "Relay Control Off - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
	
						controlRelay = 0;  // off
						generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						break;
						
					case "Relay Control On - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
	
						controlRelay = 1;  // on
						generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						break;
						
					case "Relay Control Off - sm2" :
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. It is a bug!
						ieeeAddress[7] = (byte)0x9E;
						
						controlRelay = 0;  // off
						//generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						generatedCommandArray[1] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						break;
						
					case "Relay Control On - sm2" :
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. 
						ieeeAddress[7] = (byte)0x9E;
						
						controlRelay = 1;  // on
						//generatedCommandArray[0] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						generatedCommandArray[1] = new RelayControlCommand("Relay Control", (byte)3, 0, controlRelay, ieeeAddress);
						break;
						
					case "Voltage Calibration - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new CalibrationCommand("Voltage Calibration", (byte)9, 0, 
								(byte)110, (byte)0, (byte)0, (byte)100, ieeeAddress);
						break;
					case "Voltage Calibration - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;  
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new CalibrationCommand("Voltage Calibration", (byte)9, 0, 
								(byte)110, (byte)0, (byte)0, (byte)100, ieeeAddress);
						break;
						
					case "Voltage Calibration - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. 
						ieeeAddress[7] = (byte)0x9E;
						
						
						generatedCommandArray[1] = new CalibrationCommand("Voltage Calibration", (byte)9, 0, 
								(byte)110, (byte)0, (byte)0, (byte)100, ieeeAddress);
						break;
						
					case "Current Calibration - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new CalibrationCommand("Current Calibration", (byte)10, 0, 
								(byte)0, (byte)1, (byte)0, (byte)100, ieeeAddress);
						break;
						
					case "Current Calibration - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						generatedCommandArray[0] = new CalibrationCommand("Current Calibration", (byte)10, 0, 
								(byte)0, (byte)1, (byte)0, (byte)100, ieeeAddress);
						break;
						
					case "Current Calibration - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. 
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new CalibrationCommand("Current Calibration", (byte)10, 0, 
								(byte)0, (byte)1, (byte)0, (byte)100, ieeeAddress);
						break;
/**						
					case "Energy Calibration - sm0":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1A;
						ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new CalibrationCommand("Energy Calibration", (byte)11, 0, 
								(byte)110, (byte)1, (byte)30, (byte)3, ieeeAddress);
						break;
					case "Energy Calibration - sm1":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0F;
						ieeeAddress[6] = 0x1C;
						ieeeAddress[7] = 0x77;
						
						//generatedCommand = new CalibrationCommand("Energy Calibration", (byte)11, 0, 
								//(byte)110, (byte)1, (byte)30, (byte)3, ieeeAddress);
						generatedCommandArray[0] = new CalibrationCommand("Energy Calibration", (byte)11, 0, 
								(byte)110, (byte)1, (byte)30, (byte)3, ieeeAddress);
						break;
						
					case "Energy Calibration - sm2":
						
						ieeeAddress[0] = 0x00;
						ieeeAddress[1] = 0x12;
						ieeeAddress[2] = 0x4B;
						ieeeAddress[3] = 0x00;
						ieeeAddress[4] = 0x04;
						ieeeAddress[5] = 0x0E;
						ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. 
						ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new CalibrationCommand("Voltage Calibration", (byte)9, 0, 
								(byte)110, (byte)0, (byte)30, (byte)3, ieeeAddress);
						break;
						
*/
						
						case "Calibration Register Read - sm0":
							
							ieeeAddress[0] = 0x00;
							ieeeAddress[1] = 0x12;
							ieeeAddress[2] = 0x4B;
							ieeeAddress[3] = 0x00;
							ieeeAddress[4] = 0x04;
							ieeeAddress[5] = 0x0F;
							ieeeAddress[6] = 0x1A;
							ieeeAddress[7] = 0x3C;
						
						generatedCommandArray[0] = new CalibrationRegReadCommand("Calibration Register Read", (byte)14, 24, ieeeAddress);
						break;
						
						case "Calibration Register Read - sm1":
							
							ieeeAddress[0] = 0x00;
							ieeeAddress[1] = 0x12;
							ieeeAddress[2] = 0x4B;
							ieeeAddress[3] = 0x00;
							ieeeAddress[4] = 0x04;
							ieeeAddress[5] = 0x0F;
							ieeeAddress[6] = 0x1C;
							ieeeAddress[7] = 0x77;
						
						//generatedCommand = new CalibrationRegReadCommand("Calibration Register Read", (byte)14, 24, ieeeAddress);
						generatedCommandArray[0] = new CalibrationRegReadCommand("Calibration Register Read", (byte)14, 24, ieeeAddress);
						break;
						
						case "Calibration Register Read - sm2":
							
							ieeeAddress[0] = 0x00;
							ieeeAddress[1] = 0x12;
							ieeeAddress[2] = 0x4B;
							ieeeAddress[3] = 0x00;
							ieeeAddress[4] = 0x04;
							ieeeAddress[5] = 0x0E;
							ieeeAddress[6] = (byte)0xF1;  // Java's byte is smaller than C's byte. 
							ieeeAddress[7] = (byte)0x9E;
						
						generatedCommandArray[1] = new CalibrationRegReadCommand("Calibration Register Read", (byte)14, 24, ieeeAddress);
						break;	
						
						case "Configuration Register Write - sm0":
							
						break;
							
						case "Configuration Register Write - sm1":
							// for now, the configuration is: Wyle load, ch5:RMS_V1, ch6:RMS_I1_0
							configurationRegister[0] = 0x00;
							configurationRegister[1] = 0x00;
							configurationRegister[2] = (byte)0x91;
							configurationRegister[3] = 0x00;
							configurationRegister[4] = 0x00;
							configurationRegister[5] = 0x00;
							
							ieeeAddress[0] = 0x00;
							ieeeAddress[1] = 0x12;
							ieeeAddress[2] = 0x4B;
							ieeeAddress[3] = 0x00;
							ieeeAddress[4] = 0x04;
							ieeeAddress[5] = 0x0F;
							ieeeAddress[6] = 0x1C;
							ieeeAddress[7] = 0x77;
							
							
							generatedCommandArray[0] = new ConfigRegWriteCommand("Configuration Register Write", (byte)15, 0, configurationRegister, ieeeAddress);
								
						break;
						
						case "Configuration Register Write - sm2":
							
						break;
															
						
					}
					
					for (int i = 0; i < localServer.getNumberOfCoordinators(); i++)
					{
					 
						Command command = generatedCommandArray[i];
						Thread.sleep(1);  // to avoid multiple thread deadlock
						
						if (command != null)
						{
							command.setGeneratedBy("GUIControlPanel");
					 
							Date date = new Date();
							command.setGeneratedTime(new Timestamp(date.getTime()));
					 
							//coordinator.getGeneratedCommandQueue().put(generatedCommand);
					 
			       	 		localServer.commandManager.setGeneratedCommand(localServer.coordinatorArray[i], command);
			       	 		System.out.println();
			       	 		System.out.println("In GUIControlPanel, command: " + command.getName() + " is generated into the queue in CoordinatoorID: " + i);
			       	 		System.out.println();
						}
					}
			    } catch (Exception e) {
			          	
			    	   System.out.println("In GUIControlPanel, command can not be inserted into the queue.");
			           e.printStackTrace();
			    }
				
				JOptionPane.showMessageDialog(GUIControlPanel.this,
						"You selected: " + selectedCommand + " to generate command");
			}
	});
	
	/**
	buttonTimer.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			TimerTaskProducer timerTaskProducer = new TimerTaskProducer(localServer);
	        
	        Timer timer = new Timer(true);
	        timer.scheduleAtFixedRate(timerTaskProducer, 0, 1 * 1000);
	        
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

 
	        timer.cancel();
	        System.out.println();
	        System.out.println("TimerTaskProducer is cancelled! :" + new Date());
	        System.out.println();

	        try {

	            Thread.sleep(30000);

	        } catch (InterruptedException e) {

	            e.printStackTrace();

	        }

			
			
		}
		
	});
	
	*/
	
	buttonPlot.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			String selectedIeeeAddress = (String) ieeeAddressList.getSelectedItem();
			System.out.println(selectedIeeeAddress);
		    SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
		        	
		        	String ieeeAddress = selectedIeeeAddress;//"[0, 18, 75, 0, 4, 15, 28, 119]";
		        	JFrame wholeframe = new JFrame(selectedIeeeAddress);
		        	wholeframe.setSize(new Dimension(1380,900));
		        	//wholeframe.setSize(400,400);
		        	wholeframe.setLocation(10,10);
		        	wholeframe.setLayout(new FlowLayout());
		        	
		        	//String sql= "select DATE_FORMAT(timestamp,'%H:%i:%s'), rms_V1 from smart_meter_data where (timestamp between (date_sub(now(), interval 1 day)) and now()) and (sm_IEEE_address = '[0, 18, 75, 0, 4, 15, 28, 119]')";
			
		        	//String sql= "select DATE_FORMAT(timestamp,'%H:%i:%s'), rms_V1 from smart_meter_data where (timestamp between (date_sub(now(), interval 1 day)) and now())";
		        	
		        	String sql1 = "select DATE_FORMAT(timestamp,'%H:%i:%s'), rms_V1 from smart_meter_data where (timestamp between (date_sub(now(), interval 10 minute)) and now()) and (sm_IEEE_address ='"+selectedIeeeAddress+"')";//'[0, 18, 75, 0, 4, 15, 28, 119]')";
		        	String name1 = "Voltage";
		        	DataPlotPanel dataPlotPanel1;
					
					dataPlotPanel1 = new DataPlotPanel(name1, sql1, ieeeAddress);
					ChartPanel ChartPanel1 = dataPlotPanel1.creatChartPanel();
		        	
		        	
		        	String sql2 = "select DATE_FORMAT(timestamp,'%H:%i:%s'), rms_I1 from smart_meter_data where (timestamp between (date_sub(now(), interval 10 minute)) and now()) and (sm_IEEE_address ='"+selectedIeeeAddress+"')";//'[0, 18, 75, 0, 4, 15, 28, 119]')";
		        	String name2 = "Current";
		        	DataPlotPanel dataPlotPanel2 = new DataPlotPanel(name2, sql2, ieeeAddress);
		        	ChartPanel ChartPanel2 = dataPlotPanel2.creatChartPanel();
		        	
		        	String sql3 = "select DATE_FORMAT(timestamp,'%H:%i:%s'), power from smart_meter_data where (timestamp between (date_sub(now(), interval 10 minute)) and now()) and (sm_IEEE_address ='"+selectedIeeeAddress+"')";//'[0, 18, 75, 0, 4, 15, 28, 119]')";
		        	String name3 = "Power";
		        	//DataPlotPanel dataPlotPanel3 = new DataPlotPanel(name3, sql3, ieeeAddress);
		        	//dataPlotPanel3.display();
		        	
		         	DataAreaPlotPanel dataAreaPlotPanel3 = new DataAreaPlotPanel(name3, sql3, ieeeAddress);
		        	//dataAreaPlotPanel3.display();
		        	ChartPanel ChartPanel3 = dataAreaPlotPanel3.creatAreaChartPanel();
		        	/**
		        	String sql4 = "select DATE_FORMAT(timestamp,'%H:%i:%s'), energy from smart_meter_data where (timestamp between (date_sub(now(), interval 10 minute)) and now()) and (sm_IEEE_address = '[0, 18, 75, 0, 4, 15, 28, 119]')";
		        	String name4 = "Energy";
		        	DataPlotPanel dataPlotPanel4 = new DataPlotPanel(name4, sql4, ieeeAddress);
		        	dataPlotPanel4.display();
		        	*/
		        	
		        	String sql5 = "select DATE_FORMAT(timestamp,'%H:%i:%s'), accumulated_energy from smart_meter_data where (timestamp between (date_sub(now(), interval 10 minute)) and now()) and (sm_IEEE_address ='"+selectedIeeeAddress+"')";//'[0, 18, 75, 0, 4, 15, 28, 119]')";
		        	String name5 = "Accumulated Energy";
		        	DataPlotPanel dataPlotPanel5 = new DataPlotPanel(name5, sql5, ieeeAddress);
		        	ChartPanel ChartPanel5 = dataPlotPanel5.creatChartPanel();
		        	
		        	wholeframe.add(ChartPanel1,FlowLayout.LEFT);
		        	wholeframe.add(ChartPanel2,FlowLayout.CENTER);
		        	//wholeframe.add(ChartPanel3,FlowLayout.TRAILING);
		        	
		        	wholeframe.add(ChartPanel3,FlowLayout.LEFT);
		        	wholeframe.add(ChartPanel5,FlowLayout.CENTER);
		        	wholeframe.setVisible(true);
			
		        }
	      });
		}
	});
	
	buttonRegressionTest.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			
			localServer.commandManager.regressionTest(localServer.coordinatorArray);
			messagesTextArea.append("Regression Test... \n");
			
		}
	});
	
	// add components to this frame
	add(commandList);
	add(buttonSelect);
	add(buttonRegressionTest);
	add(ieeeAddressList);
	add(buttonPlot);
//	add(buttonTimer);
	

	pack();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	
    initActions();
    initComponents();
  }
  
  public void update(Observable obj, Object arg) {
	  Command executedCommand = (Command)arg;
	//  displaySwingWorker = new DisplaySwingWorker(messagesTextArea, executedCommand);
	//  displaySwingWorker.execute();
  }
  
  void updateMessagesTextArea(Command command) {
		byte command_id;
		String command_name;
		byte[] command_stream;
		byte[] command_result;
		command_id = command.getId();
		command_name = command.getName();
		command_stream = command.getCommandStream();
		command_result = command.getResult();
		//System.out.println("In GUIControlPanel, Coordinator: " + Arrays.toString(command.coordinatorIEEEAddress));
		//messagesTextArea.append("Coordinator: " + Arrays.toString(command.coordinatorIEEEAddress));

		if (command instanceof ParamRegReadCommand)
		{
			ParamRegReadCommand paramRegReadCommand = (ParamRegReadCommand)command;
			System.out.println("Command name: " + command_name + " id: " + command_id + " Parameter register: " + Arrays.toString(paramRegReadCommand.getParamReg()));
			messagesTextArea.append(command_name + " id: " + command_id + " Parameter register: " + Arrays.toString(paramRegReadCommand.getParamReg()));
		}
		else if (command instanceof DataRegReadCommand)
		{
			DataRegReadCommand dataRegReadCommand = (DataRegReadCommand)command;
			System.out.println("Command name: " + command_name + " id: " + command_id + " the Data register: " + Arrays.toString(dataRegReadCommand.getDataReg()));
			messagesTextArea.append(command_name + " id: " + command_id + " Data register: " + Arrays.toString(dataRegReadCommand.getDataReg()));

		}
		else if (command instanceof CalibrationRegReadCommand)
		{
			CalibrationRegReadCommand calibrationRegReadCommand = (CalibrationRegReadCommand)command;
			System.out.println("Command name: " + command_name + " id: " + command_id + " the Calibration register: " + Arrays.toString(calibrationRegReadCommand.getCalibrationReg()));
			messagesTextArea.append(command_name + " id: " + command_id + " Calibration register: " + Arrays.toString(calibrationRegReadCommand.getCalibrationReg()));
			
		}
		else
		{
			System.out.println("Command name: " + command_name + " id: " + command_id + " command stream: " + Arrays.toString(command_stream) + " command result: " + Arrays.toString(command_result));
		    messagesTextArea.append(command_name + " id: " + command_id + " command stream: " + Arrays.toString(command_stream) + " command result: " + Arrays.toString(command_result));
		}
	    messagesTextArea.append("\n");
	    //messagesTextArea.append(string);
	    
/**	    
		if (command instanceof ParamRegReadCommand)
		{
			ParamRegReadCommand paramRegReadCommand = (ParamRegReadCommand)command;
			paramRegReadCommand.setParamReg();
			System.out.println("In Java the Parameter register: " + Arrays.toString(paramRegReadCommand.getParamReg()));
		}
		if (command instanceof DataRegReadCommand)
		{
			DataRegReadCommand dataRegReadCommand = (DataRegReadCommand)command;
			dataRegReadCommand.setDataReg();
			System.out.println("In Java the Data register: " + Arrays.toString(dataRegReadCommand.getDataReg()));
		}
		*/
  }
  
  /**
  void setDisplaySwingWorker(DisplaySwingWorker displaySwingWorker) {
	  this.displaySwingWorker = displaySwingWorker;
  }
*/
  /**
  private void cancel() {
    displaySwingWorker.cancel(true);
  }
*/
  
  private void initActions() {
	  
/**	  
	  
    browseAction = new AbstractAction("Browse") {

      private static final long serialVersionUID = 4669650683189592364L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        final File dir = new File(directoryPathTextField.getText()).getAbsoluteFile();
        final JFileChooser fileChooser = new JFileChooser(dir.getParentFile());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        final int option = fileChooser.showOpenDialog(GUIControlPanel.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          final File selected = fileChooser.getSelectedFile();
          directoryPathTextField.setText(selected.getAbsolutePath());
        }
      }
    };

*/
	  
    displayCancelAction = new AbstractAction("Display") {

      private static final long serialVersionUID = 4669650683189592364L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        //if (displaySwingWorker == null) {
        	getExecutedCommand();
       // } else {
        //  cancel();
        //}
      }
    };
  }

  private void initComponents() {
	  
    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    
  /**
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JLabel("Displaying executed command results"), constraints);
    

    wordTextField = new JTextField();
    wordTextField.setText("Hello");
    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 0;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(wordTextField, constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JLabel("Path: "), constraints);

    directoryPathTextField = new JTextField();
    directoryPathTextField.setText("C:\\Users\\Albert\\Work\\JavaCreed\\examples");
    constraints = new GridBagConstraints();
    constraints.gridx = 1;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(directoryPathTextField, constraints);

    constraints = new GridBagConstraints();
    constraints.gridx = 2;
    constraints.gridy = 1;
    constraints.insets = new Insets(2, 2, 2, 2);
    add(new JButton(browseAction), constraints);
*/
    
  /**  
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 0;
    add(new JButton(displayCancelAction), constraints);
 */
    
    messagesTextArea = new JTextArea();
    messagesTextArea.setEditable(false);
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 7;
    constraints.gridwidth = 3;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(new JScrollPane(messagesTextArea), constraints);
    messagesTextArea.append("Displaying results from the executed command queue... \n" );
    //messagesTextArea.append("Initializing... \n" );
  //  displaySwingWorker = new DisplaySwingWorker(messagesTextArea);

    /**
    searchProgressBar = new JProgressBar();
    searchProgressBar.setStringPainted(true);
    searchProgressBar.setVisible(false);
    constraints = new GridBagConstraints();
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 2;
    constraints.insets = new Insets(2, 2, 2, 2);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.BOTH;
    add(searchProgressBar, constraints);

	*/
 
  }

  private void getExecutedCommand() {
   // final String word = wordTextField.getText();
   //  final File directory = new File(directoryPathTextField.getText());
	  
	  messagesTextArea.setText("Displaying results from the executed command queue... \n" );
    
    // queueConsumerWorker = new DisplaySwingWorker(messagesTextArea, command);
 //   displaySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
 /**   
	  @Override
      public void propertyChange(final PropertyChangeEvent event) {
        switch (event.getPropertyName()) {
        case "progress":
          searchProgressBar.setIndeterminate(false);
          searchProgressBar.setValue((Integer) event.getNewValue());
          break;
        case "state":
          switch ((StateValue) event.getNewValue()) {
          case DONE:
            searchProgressBar.setVisible(false);
            displayCancelAction.putValue(Action.NAME, "Display Commnad Result");
            displaySwingWorker = null;
            break;
          case STARTED:
          case PENDING:
            displayCancelAction.putValue(Action.NAME, "Cancel");
         //   searchProgressBar.setVisible(true);
         //   searchProgressBar.setIndeterminate(true);
            break;
          }
          break;
        }
      }
    });
    //displaySwingWorker.execute();
     
     */
  }
 
}

