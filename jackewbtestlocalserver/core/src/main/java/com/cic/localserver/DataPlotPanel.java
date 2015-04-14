package com.cic.localserver;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.*;


@SuppressWarnings("serial")
class DataPlotPanel extends JFrame {
	String sql;
	String name;
	String ieeeAddress;
	
	public DataPlotPanel (String name, String sql, String ieeeAddress){
		super();
		this.sql = sql;
		this.name = name;
		this.ieeeAddress = ieeeAddress;
	}

	ChartPanel creatChartPanel() {
	       // JFrame f = new JFrame("JDBCTest");
	        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	ChartPanel chartPanel = null;
	        try{
	            //String sql= "select timestamp,rms_V1 from smart_meter_data";
	        	
	            //String sql= "select timestamp,rms_V1 from smart_meter_data where timestamp between (date_sub(now(), interval 5 minute)) and now()";
	        	//String sql= "select DATE_FORMAT(timestamp,'%H:%i:%s'), rms_V1 from smart_meter_data where timestamp between (date_sub(now(), interval 5 day)) and now()";
	        	
	            JDBCCategoryDataset dataset = new JDBCCategoryDataset(
	                "jdbc:mysql://localhost/emsdb", "com.mysql.jdbc.Driver", "root", "355itu11");
	            dataset.executeQuery(sql);
	            JFreeChart chart = ChartFactory.createLineChart("Smart Meter" + name, "Time", name,
	                dataset,PlotOrientation.VERTICAL,true,true,false);
	            BarRenderer bar= null;
	            bar = new BarRenderer();
	            CategoryPlot plot =null;
	            chartPanel = new ChartPanel(chart,false);
	            //chartPanel.setPreferredSize(new Dimension(500,420));
	            //frame.setVisible(true);
	            //frame.setSize(500, 300);
	            
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	        return chartPanel;
	    }
    /**
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataPlotPanel().display();
            }
        });
    }
    */

}