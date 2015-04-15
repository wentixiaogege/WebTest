package edu.itu.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static java.lang.System.out;

/**
 * Unit test for simple App.
 */
public class DateUtil2Test 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DateUtil2Test( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DateUtil2Test.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testDateUtil()
    {
		String todaySimplestr = DateUtils2.getTodaySimplestr();
		out.println("DateUtils2.getTodaySimplestr----"+todaySimplestr);

		long unixTime1 = DateUtils2.toUnixTime(LocalDate.of(2015, Month.APRIL, 8));
		long unixTime2 = DateUtils2.toUnixTime("2015-04-08 00:00:00", DateUtils2.FOMAT_DATE);
		long unixTime3 = DateUtils.toUnixTime("2015-04-08", "yyyy-MM-dd");
		long unixTime4 = DateUtils2.toUnixTimeDate("2015-04-08");
		assertEquals(unixTime1, unixTime2);
		assertEquals(unixTime1, unixTime3);
		assertEquals(unixTime1, unixTime4);
		out.println("unixTime1:" + unixTime1);

		out.println("***********to unixtime ok!*************");

		String fromUnixTimeStr = DateUtils2.fromUnixTimeStr(unixTime1, "yyyy-MM-dd");
		assertEquals("2015-04-08", fromUnixTimeStr);
		
		long unixTime5 = DateUtils2.toUnixTime(LocalDateTime.of(LocalDate.of(2015, Month.APRIL, 8), LocalTime.of(06, 25, 33)));
		String fromUnixTimeStr2 = DateUtils2.fromUnixTimeStr(unixTime5, DateUtils2.FOMAT_DATE);
		out.println(fromUnixTimeStr2);
		assertEquals("2015-04-08 06:25:33", fromUnixTimeStr2);
		out.println("***********from unixtime ok!*************");
    }
}
