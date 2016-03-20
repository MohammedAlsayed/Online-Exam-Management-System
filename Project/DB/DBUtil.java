package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String USERNAME = "s201256520";
	private static final String PASSWORD = "201256520";
	private static final String JDBC = "jdbc:oracle:thin:@ics-db.ccse.kfupm.edu.sa:1521:xe";

	public static Connection getConnection() throws SQLException{
		
		Connection conn = DriverManager.getConnection(JDBC,USERNAME, PASSWORD);
		return conn;
	}
	
}
