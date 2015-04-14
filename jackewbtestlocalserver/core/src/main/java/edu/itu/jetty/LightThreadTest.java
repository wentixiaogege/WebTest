package edu.itu.jetty;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import edu.itu.html.LocalServerLightManipulation;
import edu.itu.util.Log4jUtil;

public class LightThreadTest extends Thread implements edu.itu.localrest.LightManipulation.ILightManipulationHandler {
	Logger logger = Log4jUtil.getLogger(LightLogic.class);

	ArrayList<LocalServerLightManipulation> arrs = new ArrayList<LocalServerLightManipulation>();

	public synchronized void add(LocalServerLightManipulation s) {// 
		arrs.add(s);
	}

	public synchronized void print() {
		for (LocalServerLightManipulation string : arrs) {
			System.out.print(string + " ");
		}
		System.out.println(arrs.size());
	}

	public LightThreadTest() {
	}

	@Override
	public void run() {
		while (true) {
			print();
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void handle(LocalServerLightManipulation param) {
		// TODO Auto-generated method stub
		System.out.println("handler here");
		add(param);
		int coordinatorId = param.getCoordinator_id();
		int smId = param.getSm_id();
		int smLoadId = param.getLoad_id();
		int smLoadConfigId = param.getLoad_config_id();
		int manipulation = param.getManipulation();

		
		logger.info("manipulation coordinatorId="+coordinatorId+" smId="+smId+" smLoadId="+smLoadId+" smLoadConfigId="+smLoadConfigId+" manipulation="+manipulation+"!");
		
	}
	

}
