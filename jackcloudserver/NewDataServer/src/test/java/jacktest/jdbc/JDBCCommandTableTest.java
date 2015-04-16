package jacktest.jdbc;

import jacktest.mysql.jdbc.JDBCCommandTableAccess;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.itu.bean.Command;


public class JDBCCommandTableTest extends TestCase{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JDBCCommandTableTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( JDBCCommandTableTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testJDBCCommandTableADD()
    {/*
//    	Set set = new HashSet<Character>();
//    	set.add('0');
    	Command cmd = new Command();
    	cmd.setId(106);
    	cmd.setName("dataregisterread");
    	cmd.setParam1("123");
    	cmd.setParam2("456");
    	cmd.setDataLength(12);
//    	cmd.setCommandLogs(set);// still test
    	
    	JDBCCommandTableAccess dao = new JDBCCommandTableAccess();
    	try {
			dao.addCommand(cmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
    public void testJDBCCommandTableUpdate()
    {
          
          JDBCCommandTableAccess dao = new JDBCCommandTableAccess();
          try {
			Command cmd= dao.findById(103);
			cmd.setName("update");
			dao.updateCommand(cmd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
          
	
    }
    public void testJDBCCommandTableDelete()
    {
    	  JDBCCommandTableAccess dao = new JDBCCommandTableAccess();
          try {
//			Command cmd= dao.findById(101);
        	Command cmd = new Command();
        	cmd.setId(102);
			dao.delete(cmd);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
    }
}