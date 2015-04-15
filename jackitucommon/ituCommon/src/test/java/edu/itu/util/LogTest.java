package edu.itu.util;

import static java.lang.System.out;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

public class LogTest extends TestCase {
	
	Logger logger = Log4jUtil.getLogger(LogTest.class);
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public LogTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(LogTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testLog() {
		out.println("just a test");
		logger.debug("test log start...");
	}
}
