package jacktest.mysql.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.util.JDBCUtil;
import com.mysql.jdbc.PreparedStatement;

import edu.itu.util.Log4jUtil;


public class DataAccessJDBC {
	
	static Logger logger = Log4jUtil.getLogger(DataAccess.class);
	static com.mysql.jdbc.Connection connection =null;
	static java.sql.PreparedStatement preparedStatement = null;
	
	
	public  static <T> boolean addOperation(List<T> add){
	logger.debug("add  begin..");
	logger.debug("size"+add.size());
	//get the class name? then no need to input the class name 
	
	try {
	 
		    connection =  JDBCUtil.getJDBCConnection();
		    
		    // how to make this is a little bit complicated
		    String sqlCommandLog = "INSERT INTO command_log (command_id, command_stream, command_result, command_data, command_status, generated_by, generated_time, executed_time, lsdb_inserted_time)" +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			
	    	preparedStatement = connection.prepareStatement(sqlCommandLog);
	        //conn = dataSource.getConnection();
	        System.out.println("connecting..");
	
	        
	        for (T record : add) {
	            preparedStatement.addBatch();
	        }
	        preparedStatement.executeBatch();
	
	        preparedStatement.close();
	    
			
	
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return true;
}	
}
