package source;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import util.BaseElement;
import util.DataAccess;
import util.Log4jUtil;
import bean.DataRegisterData;
import bean.MonitorData;
import bean.SmartMeterLoadConfig;
import bean.SmartMeterLoadData;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import dao.MonitorDataHome;
import dao.SmartMeterLoadConfigHome;

public class DeltaModel implements Model{
	private static String[] current1 = { "RMS_I12_0", "RMS_I12_1", "RMS_I12_2",
		"RMS_I12_3", "RMS_I12_4", "RMS_I12_5", "RMS_I12_6" };
	private static String[] current2 = { "RMS_I23_0", "RMS_I23_1", "RMS_I23_2",
		"RMS_I23_3", "RMS_I23_4", "RMS_I23_5", "RMS_I23_6" };
	private static String[] current3 = { "RMS_I13_0", "RMS_I13_1", "RMS_I13_2",
		"RMS_I13_3", "RMS_I13_4", "RMS_I13_5", "RMS_I13_6" };
	private static String[] theta1 = { "THETA12_0", "THETA12_1", "THETA12_2",
		"THETA12_3", "THETA12_4", "THETA12_5", "THETA12_6" };
	private static String[] theta2 = { "THETA23_0", "THETA23_1", "THETA23_2",
		"THETA23_3", "THETA23_4", "THETA23_5", "THETA23_6" };
	private static String[] theta3 = { "THETA13_0", "THETA13_1", "THETA13_2",
		"THETA13_3", "THETA13_4", "THETA13_5", "THETA13_6" };
	static Logger logger = Log4jUtil.getLogger(Decode.class);
	private Client client = Client.create();  
	//****get byte[] data from DRD databse and convert it to an arraylist data
	public  byte[] getOrigin(List<DataRegisterData> dataRegList, int highIndex) {
		byte[] originInByte;
		originInByte = dataRegList.get(highIndex).getDataRegisterDataValue();
		for(int i= 0; i<originInByte.length; i++){
			logger.debug(originInByte[i]);
		}
		logger.debug(originInByte.length);
		return originInByte;
	}
	//*****convert byte[] data to arraylist data
	public ArrayList<Integer> getOriginInList(byte[] originInByte){
		ArrayList<Integer> originInList = new ArrayList<>();
		int partA = 0;
		int partB = 0;
		int combine = 0;
		for (int i = 0; i < (originInByte.length / 2); i++) {
			partA = originInByte[i * 2];
			partB = originInByte[i * 2 + 1];			
			combine = ((partA << 8) | partB);
			originInList.add(combine);
			partA = 0;
			partB = 0;
			combine = 0;
			
		}
//		for(int i= 0; i<originInList.size(); i++){
//			logger.debug(originInList.get(i));
//		}
//		logger.debug(originInList.size());
		return originInList;
	}
	
	//*****get theta byte[], 
	/*public byte[] getTheta(int theta){
		byte[] thetabyte = new byte[2];
		thetabyte[0] = (byte)(theta & 0xff);
		thetabyte[1] = (byte)((theta>>8) & 0xff);
		return thetabyte;
		
	}*/
	//***get status byte[], timestamp byte[] from originInByte
	public byte[] getStatus(byte[]originInByte){
		byte[] status = new byte[2];
		int posLast= 8;   //status is at the last 8th;
		int length = 0;
		length  = originInByte.length;
		status[0] = originInByte[length-posLast];
		status[1] = originInByte[length-posLast+1];
		return status;
		
	}

	public Date getDate(byte[] originInByte){
		byte[] date = new byte[12];
		int dataLength;
		ArrayList<Integer> datelList = new ArrayList<>();
		dataLength= originInByte.length;
		for(int i=12; i>0; i--){
			date[12-i]=originInByte[dataLength-i];
		}
		int partA = 0;
		byte partB = 0;
		int combine = 0;
		for (int i = 0; i < (date.length / 2); i++) {
			partA = date[i * 2];
			partB = date[i * 2 + 1];
			
			combine = ((partA << 8) | (partB&0x00ff));   //need to be changed!!!!!!
			datelList.add(combine);
			partA = 0;
			partB = 0;
			combine = 0;
		}
		
	//	Date dateInfo =new Date(datelList.get(0), datelList.get(1), datelList.get(2), datelList.get(3), datelList.get(4), datelList.get(5));
		//Date dateInfo = DateFormat..UTC(datelList.get(0), datelList.get(1), datelList.get(2), datelList.get(3), datelList.get(4), datelList.get(5));
		Calendar timestampCalendar = Calendar.getInstance();
		timestampCalendar.set(datelList.get(0), (datelList.get(1)-1), datelList.get(2), datelList.get(3), datelList.get(4), datelList.get(5));
		Date dateInfo = timestampCalendar.getTime();
		logger.debug("this is date of time");
		logger.debug("***************8");
		for(int test:datelList){
			logger.debug(test);
		}
	//	Timestamp timeDate = new Timestamp(dateInfo.getTime());
	

		
/*		logger.debug("this is date of time");
		for(int i= 0; i<datelList.size(); i++){
			logger.debug(datelList.get(i));
		}
		logger.debug("***************8");
		logger.debug(timeDate);
		logger.debug(dateInfo);
		logger.debug("***************8");*/
		return dateInfo;
	}
	
	//***get configure byte from byte[]originInByte and convert it to arraylist
	public  ArrayList<Integer> getConfigure(byte[] originInByte) {
		byte[] configIn = new byte[4];
		ArrayList<Integer> configList = new ArrayList<Integer>();
		for (int index = 10; index < 14; index++){	
			configIn[index - 10] = originInByte[index];
		}
		for (int i = 0; i < 8; i++) {
			if (0 == i % 2) {
				configList.add(i, (configIn[3 - i / 2] & 0x0F));
			} else {
				configList.add(i, ((configIn[3 - i / 2] & 0xF0) >> 4));
			}
		}
		return configList;
	}
	//***creat an post method to localserver restful API ***//
	public void lightTurnon(){
		LocalServerLightManipulation manipulation = new LocalServerLightManipulation();
		manipulation.setLoad_config_id(1); 
		manipulation.setManipulation(true);
		WebResource webResource = client.resource("http://localhost:9999/LightManipulation/LocalServerLightManipulation");
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, manipulation);
		if (response.getStatus() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatus());
		}
	}
	public void lightTurnoff(){
		LocalServerLightManipulation manipulation = new LocalServerLightManipulation();
		manipulation.setLoad_config_id(20); 
		manipulation.setManipulation(false);
		WebResource webResource = client.resource("http://localhost:9999/LightManipulation/LocalServerLightManipulation");
		ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, manipulation);
		if (response.getStatus() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
		+ response.getStatus());
		}
	}
/*	//***creat smartMeterLoadConfigQuery for connecting with database****
	public Query smLoadConfigQuery(String paramName1, String paramName2, int smId){
		Query query = DataAccess.session.createQuery("from SmartMeterLoadConfig data where data.paramName1=:paramName1 and data.paramName2 =:paramName2 and data.smId =:smId");// .setTimestamp("beginTime",
		// begin).setTimestamp("endTime",
		query.setString("paramName1", paramName1);
		query.setString("paramName2", paramName2);
		query.setInteger("smId", smId);
		return query;
	}
	public Query smLoadConfigTempQuery(String paramName1,int smId){
		Query query = DataAccess.session.createQuery("from SmartMeterLoadConfig data where data.paramName1=:paramName1 data.smId =:smId");
		query.setString("paramName1", paramName1);
		query.setInteger("smId", smId);
		return query;
	}
	//***create monitorDataQuery for connecting with database****
	public Query monitorQuery(int SmLoadConfigId, int unprocessed){
		Query query = DataAccess.session.createQuery("from MonitorData data where data.smLoadConfigId = :SmLoadConfigId and data.processed = :unproccessed order by data.timestamp desc limit 1");
		query.setInteger("SmLoadConfigId", SmLoadConfigId);
		query.setInteger("unprocessed", unprocessed);
		return query;
	}
	
*/
	//*** creat an arraylist of BaseElement for SmartMeterLoadData final list
	public  ArrayList<BaseElement> getBaseElement(
			ArrayList<Integer> originInList, ArrayList<Integer> configList) {
	
		ArrayList<BaseElement> baseElementList = new ArrayList<BaseElement>();
		BaseElement baseElement = new BaseElement();
		int action = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		
		for (int i = 0; i < configList.size(); i++) {
			action = configList.get(i).intValue();
			if (action == 1) {
				baseElement.config = "RMS_V12";
				baseElement.value = originInList.get(i + 7);    //the former is SM_ADD and SM_config, 4+3
				baseElementList.add((BaseElement) baseElement.clone());
			} else if (action == 2) {
				baseElement.config = "RMS_V23";
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
			} else if (action == 3) {
				baseElement.config = "RMS_V13";
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
			} else if (action == 9) {
				baseElement.config = current1[i1];
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
				baseElement.config = theta1[i1];
				baseElement.value = originInList.get(i + 8);
				originInList.remove(i + 8);
				baseElementList.add((BaseElement) baseElement.clone());
			} else if (action == 10) {
				baseElement.config = current2[i2];
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
				baseElement.config = theta2[i2];
				baseElement.value = originInList.get(i + 8);
				originInList.remove(i + 8);
				baseElementList.add((BaseElement) baseElement.clone());
				i2++;
			} else if (action == 11) {
				baseElement.config = current3[i3];
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
				baseElement.config = theta3[i3];
				baseElement.value = originInList.get(i + 8);
				originInList.remove(i + 8);
				baseElementList.add((BaseElement) baseElement.clone());
				i3++;
			} else if (action == 4) {
				baseElement.config = "SM_V";
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
			} else if (action == 12) {
				baseElement.config = "Temperature";
				baseElement.value = originInList.get(i + 7);
				baseElementList.add((BaseElement) baseElement.clone());
			}
			System.out.println("action value" + action);
		}
		
		logger.debug("basement test");
		for(int i=0; i<baseElementList.size();i++)
		{
			logger.debug(baseElementList.get(i).config);
		}
		return baseElementList;
	}
	//***create an arraylist of SmartMeterLoadData and prepare inserting into database
	public ArrayList<SmartMeterLoadData> smLoadData(List<DataRegisterData> dataRegList, int highIndex){
		
		byte[] originInByte;
		int v1 = 100;
		int v2 = 100;
		int v3 = 100;
//		int bv = 0;  //board value
		int smId;
		ArrayList<Integer> originInList = new ArrayList<>();
		ArrayList<Integer> configList = new ArrayList<>();
		ArrayList<BaseElement> baseElementList = new ArrayList<BaseElement>();
		ArrayList<SmartMeterLoadData> smLoadDataList = new ArrayList<>();
		originInByte = getOrigin(dataRegList, highIndex);
		originInList = getOriginInList(originInByte);
		configList = getConfigure(originInByte);
		baseElementList = getBaseElement(originInList, configList);
		smId = dataRegList.get(highIndex).getSmId();
		SmartMeterLoadData smData = new SmartMeterLoadData();
//		int[] sequence = { 100, 100, 100, 100 }; // the v1, v2, v3, bv are  available
													
		String actionV = null;  //actionV is to decide v
		String queryString = null;
		Query loadConfigQuery;  //smLoadconfig query
		Query monitorQuery;   // monitordata query
		List<SmartMeterLoadConfig> loadConfigList = new ArrayList<>();
		List<MonitorData> monitorList = new ArrayList<MonitorData>();
		int SmLoadConfigId = -1;
		int processed = 1;
		int unprocessed = 0;
		float thetaValue;
		float temperature;
		Date timeDate;
		byte[] statusValue = new byte[2];
		statusValue = getStatus(originInByte);
		timeDate = getDate(originInByte);
		for (int i = 0; i < baseElementList.size(); i++) {
			// ********if RMS_V12 exists**************//
			if ("RMS_V12" == baseElementList.get(i).config) {
//				sequence[0] = i;
				v1 = i;
			}
			// ********if RMS_V23 exists**************//
			else if ("RMS_V23" == baseElementList.get(i).config) {
//				sequence[1] = i;
				v2 = i;
			}
			// ********if RMS_V13 exists**************//
			else if ("RMS_V13" == baseElementList.get(i).config) {
//				sequence[2] = i;
				v3 = i;
			}
			// ********if RMS_BV exists**************//
			else if ("SM_V" == baseElementList.get(i).config) {
//				sequence[3] = i;
//				bv = i;
			}
			else if("Temperature" == baseElementList.get(i).config){
				DataAccess.session = DataAccess.factory.openSession();
				queryString = "from SmartMeterLoadConfig data where data.paramName1='Temperature' and data.smId like:smId";
				loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
				loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
				SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); //loadConfigList should be only one element!!
				logger.debug("smId= "+smId+"SmLoadConfigId= "+SmLoadConfigId);
				DataAccess.session = DataAccess.factory.openSession();
				queryString = "from MonitorData data where data.smLoadConfigId like:SmLoadConfigId and data.processed ='0' order by data.timestamp desc limit 1";
				//                                                            
				monitorQuery = DataAccess.session.createQuery(queryString).setInteger("SmLoadConfigId", SmLoadConfigId);
				//get the lastest max and min value
				monitorList = MonitorDataHome.searchOperation(monitorQuery);
				temperature = baseElementList.get(i).value;
				logger.debug("this is temperature");
				logger.debug(temperature);
				logger.debug(monitorList.get(0).getTimestamp()+"**********");
				logger.debug("**************"+monitorList.size());
				//compare with the max and min temperature
				if(null != monitorList){
					if(temperature > monitorList.get(0).getParamMax()){
					//	lightTurnoff();
						logger.info("lightTurnoff");
					}
					else if(temperature < monitorList.get(0).getParamMin()){
						//lightTurnon();
						logger.info("lightTurnon");
					}
					//set "processed" value to the Monitor data table
					MonitorDataHome.updateOperation(monitorList.get(0));
					
				}
				
				// insert temperature data into smartmeter load data table
				smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
				smData.setParamValue1(temperature);
				smData.setLoadStatus(statusValue);
				smData.setTimestamp(timeDate);// int trans to date!!)
				smData.setProcessed(unprocessed);
				//*************create a list of SmartMeterLoadData type to insert to database******//
				smLoadDataList.add((SmartMeterLoadData)smData.clone());
				
				
			}
		}
		
		
		if (100 != v1) {
			for (int index = (v1+1); index < baseElementList.size(); index++) {
				actionV = baseElementList.get(index).config;
//				statusValue = getStatus(originInByte);
//				timeDate = getDate(originInByte);

//				java.sql.Date dateDB = timeStamp.getTime();
				// ******check which I is here
				if (actionV == "RMS_I12_0") {
					DataAccess.session = DataAccess.factory.openSession();
				    queryString = "from SmartMeterLoadConfig data where data.paramName1='RMS_V12' and data.paramName2='RMS_I12_0' and data.smId like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					
					
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I12_1") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.paramName1='RMS_V12' and data.paramName2='RMS_I12_1' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I12_2") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V12' and data.currentName='RMS_I12_2'  and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I12_3") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V12' and data.currentName='RMS_I12_3' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I12_4") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V12' and data.currentName='RMS_I12_4' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					
					
					
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I12_5") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V12' and data.currentName='RMS_I12_5' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I12_6") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V12' and data.currentName='RMS_I12_6' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v1).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
			
			}

		}
		if (100 != v2) {
			for (int index = (v2+1); index < baseElementList.size(); index++) {
				actionV = baseElementList.get(index).config;
				
//				statusValue = getStatus(originInByte);
//				timeDate = getDate(originInByte);
				// ******check which I is here
				if (actionV == "RMS_I23_0") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.paramName1='RMS_V23' and data.paramName2='RMS_I23_0' and data.smId like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I23_1") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_1' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I23_2") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_2' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I23_3") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_3' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I23_4") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_4' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I23_5") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_5' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I23_6") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V23' and data.currentName='RMS_I23_6' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v2).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
			}
		
		}
		if (100 != v3) {
			for (int index = (v3+1); index < baseElementList.size(); index++) {
				actionV = baseElementList.get(index).config;
			
//				statusValue = getStatus(originInByte);
//				timeDate = getDate(originInByte);
				if (actionV == "RMS_I13_0") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.paramName1='RMS_V13' and data.paramName2='RMS_I13_0' and data.smId like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I13_1") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_1' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I13_2") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_2' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I13_3") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_3' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
				else if (actionV == "RMS_I13_4") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_4' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I13_5") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_5' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				else if (actionV == "RMS_I13_6") {
					DataAccess.session = DataAccess.factory.openSession();
					queryString = "from SmartMeterLoadConfig data where data.voltageName='RMS_V13' and data.currentName='RMS_I13_6' and data.sm_id like:smId";
					loadConfigQuery = DataAccess.session.createQuery(queryString).setInteger("smId", smId);
					loadConfigList = SmartMeterLoadConfigHome.searchOperation(loadConfigQuery);
					SmLoadConfigId = loadConfigList.get(0).getSmLoadConfigId(); // get(0) is this right???????
					thetaValue = baseElementList.get(index + 1).value;
					//*****fill a SmartMeterLoadData type bean*******//
					smData.setSmLoadConfigId(SmLoadConfigId); // load Id how to decide?????????
					smData.setParamValue1( baseElementList.get(v3).value);
					smData.setParamValue2(baseElementList.get(index).value);
					smData.setParamValue3(thetaValue);
					smData.setLoadStatus(statusValue);
					smData.setTimestamp(timeDate);// int trans to date!!)
					smData.setProcessed(unprocessed);
					//*************create a list of SmartMeterLoadData type to insert to database******//
					smLoadDataList.add((SmartMeterLoadData)smData.clone());
				}
				
			}
			
		}
//		if (100 != sequence[3]) {
//			thetaValue = getTheta((int)baseElementList.get(v3 + 1).value);
//			statusValue = getStatus(originInByte);
//			timeStamp = getDate(originInByte);
//			String queryString = "from SmartMeterLoadConfig data where data.voltageName='SM_V' and data.currentName='SM_I'";
//			loadConfigList = DataAccess.HibernateSearchOperation(loadConfigString);
//			smLoadId = loadConfigList.get(0).getSmLoadId();  // get(0) is this right???????
//			//*****fill a SmartMeterLoadData type bean*******//
//			smData.setSmId(smId);
//			smData.setSmLoadId(smLoadId); // load Id how to decide?????????
//			smData.setVoltageValue((int) baseElementList.get(sequence[0]).value);
//			smData.setCurrentValue(baseElementList.get(v1).value);
//			smData.setThetaValue(thetaValue);
//			smData.setStatus(statusValue);
//			smData.setTimestamp(timeStamp);// int trans to date!!
//			smData.setProcessed("0");
//			//*************create a list of SmartMeterLoadData type to insert to database******//
//			smLoadDataList.add((SmartMeterLoadData)smData.clone());
//		}
/*		logger.debug("***********************************");
		logger.debug(smLoadDataList.size());
		for(int i=0; i<smLoadDataList.size();i++)
		{
			logger.debug(smLoadDataList.get(i).getSmId());
		}
		logger.debug("***********************************");*/
		return smLoadDataList;
	}

}
