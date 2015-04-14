package com.itu.client;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class DeepCopyObjectsTest extends TestCase {
	static final Logger logger = Logger.getLogger(DeepCopyObjectsTest.class);

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	public void testCopy() {
//		logger.info("copy test starting...");
//		// CloudCmdAction cmd =
//		Builder newBuilder = CloudCommand.newBuilder();
//		com.itu.bean.CloudCommand ccbean = new com.itu.bean.CloudCommand();
//
//		ccbean.setSmartmeterId(100);
//		ccbean.setChecked(1);
//		ccbean.setCoordinatorId(-1);
//		// newBuilder.setId(33);
//		ccbean.setId(55);
//
//		if (ClassDeepCopy.CopyBeanToProto(ccbean, newBuilder, "CommandId", "Timestamp")) {
//			logger.debug("id is " + newBuilder.build().getId());
//			logger.debug("smaret meter id is " + newBuilder.build().getSmartmeterId());
//			logger.debug("checked is " + newBuilder.build().getChecked());
//			logger.debug("coori is " + newBuilder.build().getCoordinatorId());
//		} else {
//			logger.debug("copy error, please check log!");
//		}
		// newBuilder.set
		// newBuilder.set
		// GenericTest<String> test1 = new GenericTest<String>();
		// System.out.print("tt");
		// test1.object = "sss";
		// test1.print();
		//
		// GenericTest<Integer> test2 = new GenericTest<Integer>();
		// test2.object=33;
		// test2.print();
		//
		// GenericTest<Stu> test3 = new GenericTest<Stu>();
		// test3.object = new Stu("3","zhangsan");
		// // test3
		// test3.print();
	}

	public void testProtoToBeanCopy() {
//		logger.info("copy test starting...");
//		// CloudCmdAction cmd =
//		Builder newBuilder = CloudCommand.newBuilder();
//		com.itu.bean.CloudCommand ccbean = new com.itu.bean.CloudCommand();
//
//		// ccbean.setSmartmeterId(100);
//		// ccbean.setChecked(1);
//		// ccbean.setCoordinatorId(-1);
//		// // newBuilder.setId(33);
//		// ccbean.setId(55);
//
//		newBuilder.setId(1).setSmartmeterId(2).setChecked(3).setCoordinatorId(4);
//
//		if (ClassDeepCopy.CopyProtoToBean(newBuilder, ccbean, "Timestamp")) {
//			logger.debug("id is " + ccbean.getId());
//			logger.debug("smaret meter id is " + ccbean.getSmartmeterId());
//			logger.debug("checked is " + ccbean.getChecked());
//			logger.debug("coori is " + ccbean.getCoordinatorId());
//		} else {
//			logger.debug("copy error, please check log!");
//		}
	}
}
