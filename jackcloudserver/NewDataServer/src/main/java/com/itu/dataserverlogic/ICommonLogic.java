package com.itu.dataserverlogic;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

public abstract class ICommonLogic<T, V> {
	protected Logger logger = Log4jUtil.getLogger(ICommonLogic.class);

//	public boolean executeActionBool(String... strs){
//		return false;
//	}
//	
//	public boolean executeActionBool(){
//		return false;
//	}
//	
//	public void executeActionNo(String... strs){
//	}
	
	public abstract V executeAction(T t);
	
	public abstract V executeAction();
}
