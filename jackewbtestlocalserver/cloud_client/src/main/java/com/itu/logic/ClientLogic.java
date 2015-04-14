package com.itu.logic;

public abstract class ClientLogic<T1, V> {

	// ClientLogicForWeb</ V>
	public V executeLogic(T1 params) throws Exception {
		V res = executeLogicDetail(params);
		return res;
	}

	protected abstract V executeLogicDetail(T1 params);
}
