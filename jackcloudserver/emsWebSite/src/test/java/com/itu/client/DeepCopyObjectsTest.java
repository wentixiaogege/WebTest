package com.itu.client;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.itu.bean.SmartMeterData;

import edu.itu.util.ClassDeepCopy;
import edu.itu.util.DateUtils;

public class DeepCopyObjectsTest extends TestCase {
//	static final Logger logger = Logger.getLogger(DeepCopyObjectsTest.class);
//
//	@Override
//	protected void setUp() throws Exception {
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//	}
//
//	public void testCopy() {
//		FrontServerSmartMeterDataRecord r1 = FrontServerSmartMeterDataRecord.newBuilder().setId(3).setIeeeAddress("max").setRmsV1(110)
//				.setRmsI1(1.02f).setTimestamp(DateUtils.toUnixTime("2014-11-21 18:24:12")).build();
//		FrontServerSmartMeterDataRecord r2 = FrontServerSmartMeterDataRecord.newBuilder().setId(2).setIeeeAddress("min").setRmsV1(120).setRmsI1(1f)
//				.setTimestamp(DateUtils.toUnixTime("2014-11-21 18:23:56")).build();
//		// return
//		// FrontServerSmartMeterDataAction.newBuilder().mergeFrom(t).addRecords(r1).addRecords(r2).build();
//		SmartMeterData data = new SmartMeterData();
//		// <<<<<<< HEAD
//		ClassDeepCopy.CopyProtoToBean(r1, data);
//
//		logger.debug("\n\nCopyBeanToProto");
//
//		FrontServerSmartMeterDataRecord.Builder r3 = FrontServerSmartMeterDataRecord.newBuilder();
//		ClassDeepCopy.CopyBeanToProto(data, r3);
//
//		logger.debug("======================================test==============================================");
//		assertEquals(r3.build().getTimestamp(), DateUtils.toUnixTime("2014-11-21 18:24:12"));
//		// =======
//		// ClassDeepCopy.CopyProtoToBean(r1, data);
//		//
//		// logger.debug("\n\nCopyBeanToProto");
//		//
//		// FrontServerSmartMeterDataRecord.Builder r3 =
//		// FrontServerSmartMeterDataRecord.newBuilder();
//		// ClassDeepCopy.CopyBeanToProto(data, r3);
//		// >>>>>>> 4f08a7ce1ff46f6b3eac907799ec35d8f1f5ccea
//
//		// assertEquals(Float.class, float.class);
//		// logger.info("copy test starting...");
//		// // CloudCmdAction cmd =
//		// Builder newBuilder = CloudCommand.newBuilder();
//		// com.itu.bean.CloudCommand ccbean = new com.itu.bean.CloudCommand();
//		//
//		// ccbean.setSmartmeterId(100);
//		// ccbean.setChecked(1);
//		// ccbean.setCoordinatorId(-1);
//		// // newBuilder.setId(33);
//		// ccbean.setId(55);
//		//
//		// if (ClassDeepCopy.CopyBeanToProto(ccbean, newBuilder, "CommandId",
//		// "Timestamp")) {
//		// logger.debug("id is " + newBuilder.build().getId());
//		// logger.debug("smaret meter id is " +
//		// newBuilder.build().getSmartmeterId());
//		// logger.debug("checked is " + newBuilder.build().getChecked());
//		// logger.debug("coori is " + newBuilder.build().getCoordinatorId());
//		// } else {
//		// logger.debug("copy error, please check log!");
//		// }
//		// newBuilder.set
//		// newBuilder.set
//		// GenericTest<String> test1 = new GenericTest<String>();
//		// System.out.print("tt");
//		// test1.object = "sss";
//		// test1.print();
//		//
//		// GenericTest<Integer> test2 = new GenericTest<Integer>();
//		// test2.object=33;
//		// test2.print();
//		//
//		// GenericTest<Stu> test3 = new GenericTest<Stu>();
//		// test3.object = new Stu("3","zhangsan");
//		// // test3
//		// test3.print();
//	}
//
//	public void testProtoToBeanCopy() {
//		// logger.info("copy test starting...");
//		// // CloudCmdAction cmd =
//		// Builder newBuilder = CloudCommand.newBuilder();
//		// com.itu.bean.CloudCommand ccbean = new com.itu.bean.CloudCommand();
//		//
//		// // ccbean.setSmartmeterId(100);
//		// // ccbean.setChecked(1);
//		// // ccbean.setCoordinatorId(-1);
//		// // // newBuilder.setId(33);
//		// // ccbean.setId(55);
//		//
//		// newBuilder.setId(1).setSmartmeterId(2).setChecked(3).setCoordinatorId(4);
//		//
//		// if (ClassDeepCopy.CopyProtoToBean(newBuilder, ccbean, "Timestamp")) {
//		// logger.debug("id is " + ccbean.getId());
//		// logger.debug("smaret meter id is " + ccbean.getSmartmeterId());
//		// logger.debug("checked is " + ccbean.getChecked());
//		// logger.debug("coori is " + ccbean.getCoordinatorId());
//		// } else {
//		// logger.debug("copy error, please check log!");
//		// }
	// }
}
