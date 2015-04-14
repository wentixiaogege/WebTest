package source;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import dao.DataRegisterDataHome;
import dao.SmartMeterLoadDataHome;
import util.DataAccess;
import util.Log4jUtil;
import bean.DataRegisterData;
import bean.SmartMeterLoadData;
import factory.MakeFactory;

public class Decode {
	static Logger logger = Log4jUtil.getLogger(Decode.class);
	public static void main(String args[]) {
		ArrayList<DataRegisterData> testArrayList = new ArrayList<>();
		DataRegisterData testData = new DataRegisterData();
		byte[] testVaule = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, // sm_add
				0x00, 0x00,// wyle or delta
				(byte)0xC4, (byte)0xB3, (byte)0xA2, (byte)0x91, // channel config
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x00, 0x03,  //channel value
				0x11, 0x12, //status
				0x07, (byte)0xB5, 0x00, 0x03, 0x00, 0x05, 0x00, 0x07, 0x00, 0x09, 0x00, 0x11 //timestamp
				
				
		};
		testData.setSmId(1);
		testData.setProcessed("0");
		testData.setDataRegisterDataValue(testVaule);
		for(int i=0;i<8; i++)
		{
			testArrayList.add(testData);
		}
		DataRegisterDataHome.addOperation(testArrayList);
		String typename;	//tell it's wylemodel or deltamodel
//		while(true){
		logger.debug("main  begin..");
		List<DataRegisterData> dataRegList= new ArrayList<>();   //raw data;
		ArrayList<SmartMeterLoadData> smLoadDataList = new ArrayList<>(); //smloaddata to be stored
		WyleModel wyleModel =new WyleModel();
		DeltaModel deltaModel = new DeltaModel();
		int listSize;
		int unprocessed = 0;
		Query dataRegQuery;  //sql query variable
		DataAccess.session = DataAccess.factory.openSession();
		String hql = ("from DataRegisterData data where data.processed like:processed");// get data which is not processed
		dataRegQuery = DataAccess.session.createQuery(hql).setInteger("processed",unprocessed);
		dataRegList = DataRegisterDataHome.searchOperation(dataRegQuery);
		listSize = dataRegList.size();
		//logger.info("**********"+listSize);
		for(int highIndex=0; highIndex<listSize; highIndex++ ){	
			logger.debug("for  begin..");
			byte[] tellDiff = dataRegList.get(highIndex).getDataRegisterDataValue();
			if(0 ==tellDiff[9])
			{
				 typename = "wyleModel";
				 wyleModel = (WyleModel)MakeFactory.makeModel(typename);
				 smLoadDataList = wyleModel.smLoadData(dataRegList, highIndex);
				 SmartMeterLoadDataHome.addOperation(smLoadDataList);
				 DataRegisterDataHome.updateOperation(dataRegList.get(highIndex));
			}
			else {
				typename = "deltaModel";
				deltaModel = (DeltaModel)MakeFactory.makeModel(typename);
				smLoadDataList = wyleModel.smLoadData(dataRegList, highIndex);
				 SmartMeterLoadDataHome.addOperation(smLoadDataList);
				 DataRegisterDataHome.updateOperation(dataRegList.get(highIndex));
			}
//			smLoadDataList = MakeFactory.makeModel(dataRegList, highIndex);
//			DataAccess.addOperation(smLoadDataList);
//			DataAccess.updateOperation(dataRegList.get(highIndex));
		}
	//}
	}
}
