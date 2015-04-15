package com.itu.logic;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

public abstract class ClientLogic<T1, V> {

	protected Logger logger = Log4jUtil.getLogger(this.getClass());
	// ClientLogicForWeb</ V>
	public V executeLogic(T1 params) throws Exception {
		V res = executeLogicDetail(params);
		return res;
	}

	protected abstract V executeLogicDetail(T1 params);
}
