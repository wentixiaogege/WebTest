package edu.itu.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static java.lang.System.out;

/**
 * Unit test for simple App.
 */
public class DateUtilTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DateUtilTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DateUtilTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testDateUtil()
    {
//        assertTrue( true );
    	String str = "2015-03-02 00:00:00";
    	long unixTime = DateUtils.toUnixTime(str);
    	assertEquals(str, DateUtils.fromUnixTimeStr(unixTime));
    	
    	out.println(DateUtils.fromUnixTimeStr(unixTime));
    	
    }
}
