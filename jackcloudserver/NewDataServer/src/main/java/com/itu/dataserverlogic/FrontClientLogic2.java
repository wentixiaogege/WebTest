//package com.itu.dataserverlogic;
//
//
//import org.apache.log4j.Logger;
//
//import com.itu.DAO.CloudCommandDao;
//
//import edu.itu.proto.CloudCommandProtos.CloudCommand;
//import edu.itu.util.ClassDeepCopy;
//import edu.itu.util.Log4jUtil;
//
//public class FrontClientLogic2 extends CommonProtoLogic2<CloudCommand> {
//
//	Logger logger = Log4jUtil.getLogger(FrontClientLogic2.class);
//
//	@Override
//	public CloudCommand executeActionBuffer(CloudCommand t) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String executeAction(CloudCommand cmd) {
//		if (null == cmd) 
//			return "Hello World";
//
//		logger.debug(String.format("post a new command, id:%d, param1:%d, param1:%d", cmd.getId(), cmd.getParam1(), cmd.getParam1()));
//
//		com.itu.bean.CloudCommand cmdbean = new com.itu.bean.CloudCommand();
//
//		if (ClassDeepCopy.CopyProtoToBean(cmd, cmdbean, "Timestamp")) {
//			cmdbean.setCommandId(10);
//			if (CloudCommandDao.addNewCommand(cmdbean)) {
//				return "true";
//			} else {
//				logger.debug("add new command error");
//			}
//		} else {
//			logger.debug("copy error");
//		}
//
//		return "false";
//	}
//
//	@Override
//	public String executeAction() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
