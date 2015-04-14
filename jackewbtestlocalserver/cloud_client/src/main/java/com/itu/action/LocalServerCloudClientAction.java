package com.itu.action;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.bean.SmartMeterData;
import com.itu.logic.LocalServerCloudClientLogic;

import edu.itu.proto.CommonEnum.ResultType;
import edu.itu.proto.ResultsProtos.Result;
import edu.itu.util.Log4jUtil;
//import com.itu.myserver.CloudClient;

class LocalServerCloudClientAction implements Callable<Integer> {

	Logger logger = Log4jUtil.getLogger(LocalServerCloudClientLogic.class);
	LocalServerCloudClientLogic localServerCloudClientLogic;

	public LocalServerCloudClientAction() {

		initLogic();
	}

	public Integer call() throws Exception {

		// int lastID = 50000;

		// from后面是对象，不是表名
		int id = 0;
		int minId = 0, maxId = 0;

		// String searchSQL =
		// "from SmartMeterData as smdata where smdata.checked='"+id+"' order by smdata.id asc";//
		// 使用命名参数，推荐使用，易读
		String searchSQL = "from SmartMeterData as smdata where smdata.checked='0' order by smdata.id asc";// 使用命名参数，推荐使用，易读。

		// String updateSQL =
		// "update SmartMeterData set checked = '1' where checked = '0' and id >= 65600";
		// String updateSQL =
		// "update SmartMeterData set checked = '1' where checked = '0' and id >='"+minId+"' and id <='"+maxId+"'";
		// String sql = "from smart_meter_data where id > 65500";
		// + lastID;

		List<SmartMeterData> list = DataAccess.searchOperation(searchSQL);
		minId = list.get(0).getId();
		maxId = list.get(list.size() - 1).getId();

		String updateSQL = "update SmartMeterData set checked = '1' where checked = '0' and id >='"
				+ minId + "' and id <='" + maxId + "'";

		try {
			Result result = localServerCloudClientLogic.executeLogic(list);

			logger.debug("result errmsg is " + result.getErrMsg());
			logger.debug("result res is " + result.getRes());
			if (result.getRes().equals(ResultType.TRUE)) {
				System.out.println("In Java, local server client minID:"
						+ minId + " maxId:" + maxId);
				DataAccess.modifyOperation(updateSQL);
			} else {
				System.out.println("Cloud Client execute logic error");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("LocalServerCloudClientAction Result:"
				+ Result.getDescriptor());
		return list.size();

	}

	protected void initLogic() {
		//String url = "http://172.16.5.157:8080/newDataServer/rest/LocalServerAddSmartMeterData";
		
		String url = "http://172.16.0.107:8888/newDataServer/rest/LocalServerAddSmartMeterData"; 

		localServerCloudClientLogic = new LocalServerCloudClientLogic(url,
				"POST");
	}

	public static void main(String[] args) throws Exception {

		// LocalServerCloudClientAction localServerCloudClientAction = new
		// LocalServerCloudClientAction();
		while (true) {
			Integer count = 0;
			ExecutorService executor = Executors.newSingleThreadExecutor();
			try {
				
				Future<Integer> future = executor
						.submit(new LocalServerCloudClientAction());

				count = future.get();
				Thread.sleep(300000);  // sleep for 6 minutes
				System.out
						.println("In Java, local server client push data to data server, count:"
								+ count);

			} catch (InterruptedException e) {

				System.out
						.println("In Java, local server client can't push data to data server.");
				e.printStackTrace();
			} finally {

				executor.shutdown(); // Important!
			}
		}

	}

}
