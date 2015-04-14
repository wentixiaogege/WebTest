package com.itu.client;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import edu.itu.util.Log4jUtil;

public class ArrayToBytesTest extends TestCase {
	// Logger logger = Log4jUtil.getLogger(ArrayToBytesTest.class);

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	public void testArray() {
		// Configure logger
		// BasicConfigurator.configure();

		// PropertyConfigurator.configure("log4j.properties");
		Logger logger = Log4jUtil.getLogger(ArrayToBytesTest.class);
		String string = "[1,0,0,1,1]";
		String a = string.substring(1, string.length() - 1);
		logger.info(a);
		String[] splits = a.split(",");
		byte[] bs = new byte[splits.length];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = Byte.valueOf(splits[i]);
		}

		for (byte b : bs) {
			// logger.debug("b is " + b);
		}
	}
}
