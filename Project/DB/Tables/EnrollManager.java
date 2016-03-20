package DB.Tables;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import DB.DBUtil;
import DB.beans.Enroll;
import DB.beans.Exam;

public class EnrollManager {
	
	/**
	 * Creates a enroll by id, exam no., start time and end time.
	 * @param id: UNO in DB
	 * @param examNo: eno in DB
	 * @param startTime: startTime in DB
	 * @param endTime: endTime in DB
	 * @return true if inserted, false otherwise
	 * @throws SQLException
	 */
	
	public static boolean enrollInExam(int id, int examNo, Timestamp startTime, Timestamp endTime ) throws SQLException
	{
		String sql = "INSERT INTO ENROLL VALUES(?,?,?,?)";
		
		try(Connection connection= DBUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);	){
			
			statement.setInt(1, id);
			statement.setInt(2, examNo);
			statement.setTimestamp(3, startTime);
			statement.setTimestamp(4, endTime);
			
			
			int affected = statement.executeUpdate();
			
			if(affected == 1){
				return true;
			}
			
			else{
				return false;
			}
			
		}
		
	
	}
	
	/**
	 * Returns an ArrayList of all the exams the user is enrolled in.
	 * @param id: UNO in DB
	 * @param examNo: eno in DB
	 * @param startTime: startTime in DB
	 * @param endTime: endTime in DB
	 * @return true if inserted, false otherwise
	 * @throws SQLException
	 */
	
	public static ArrayList<Enroll> getAllEnrolledExams(int id) throws SQLException{
		
		String sql = "SELECT * FROM ENROLL WHERE UNO= ? ";
		
		ArrayList<Enroll> enrolls = new ArrayList<Enroll>();
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setInt(1, id);
				
				ResultSet rs = statement.executeQuery();
				
				enrolls = addInstances(rs);
				
				return enrolls;
			}	
	} 
	
	
	/**
	 * Updates the startTime from their given id and examNo.
	 * @param id: UNO in DB
	 * @param examNo: eno in DB
	 * @param newStartTime: startTime in DB
	 * @return true if inserted, false otherwise
	 * @throws SQLException
	 */
	
	public static boolean editStartTime(int id, int eno, Timestamp newStartTime) throws SQLException{
		
		String sql = "UPDATE ENROLL SET STARTTIME= ? WHERE UNO= ? AND ENO= ?";
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setTimestamp(1, newStartTime);
				statement.setInt(2,  id);
				statement.setInt(3,  eno);

				
				int affected = statement.executeUpdate();
				
				if(affected == 1){
					return true;
				}
				else{
					return false;
				}
			}	
	}
	
	public static Enroll getEnrolledObject(int id, int eno) throws SQLException
	{
		String sql = "SELECT * FROM ENROLL WHERE UNO=? AND ENO=?";
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setInt(1, id);
				statement.setInt(2,  eno);
				
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {
					Enroll enrolled = new Enroll();
					enrolled.setId(rs.getInt(1));
					enrolled.setExamNo(rs.getInt(2));
					enrolled.setStartTimestamp(rs.getTimestamp(3));
					enrolled.setFinishTimestamp(rs.getTimestamp(4));

					return enrolled;
				}
				
				else {
					return null;
				}
				
		}
				
		
	}
	
	
	/**
	 * Updates the FinishTime from their given id and examNo.
	 * @param id: UNO in DB
	 * @param examNo: eno in DB
	 * @param newFinishTime: finishtime in DB
	 * @return true if inserted, false otherwise
	 * @throws SQLException
	 */
	
	
	public static boolean editFinsihTime(int id, int eno, Timestamp newFinishTime) throws SQLException{
		
		String sql = "UPDATE ENROLL SET FINISHTIME= ? WHERE UNO= ? AND ENO= ?";
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setTimestamp(1, newFinishTime);
				statement.setInt(2,  id);
				statement.setInt(3,  eno);

				
				int affected = statement.executeUpdate();
				
				if(affected == 1){
					return true;
				}
				else{
					return false;
				}
			}	
	}
	
	
	/**
	 * Returns an ArrayList of enrolled where finishtime is null for a given id
	 * @param id: UNO in DB
	 * @param FinishTime: finishtime in DB
	 * @return true if inserted, false otherwise
	 * @throws SQLException
	 */
	
	public static ArrayList<Enroll> getNullFinishTime(int id) throws SQLException{
		
		String sql = "SELECT * FROM ENROLL WHERE UNO= ? AND FINISHTIME IS null ";
		
		ArrayList<Enroll> enrolls = new ArrayList<Enroll>();
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setInt(1, id);
				
				ResultSet rs = statement.executeQuery();
				
				enrolls = addInstances(rs);
				
				return enrolls;
			}	
	}
	
	
	
	
	public static boolean submit(int id, int eno) throws SQLException{
		
		String sql = "UPDATE ENROLL SET FINISHTIME = ? WHERE UNO= ? AND ENO= ?";
		
		
		
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
			
			java.util.Date date= new java.util.Date();
			
			
			Timestamp FinishTime = new Timestamp(date.getTime());
			
				statement.setTimestamp(1, FinishTime);
				statement.setInt(2, id);
				statement.setInt(3, eno);
				
				int affected = statement.executeUpdate();
				
				if(affected == 1){
					return true;
				}
				else{
					return false;
				}
			}
	}
	
	
	
	/**
	 * If the time of the given exam for a user has expired then this method updates the finished time
	 * This class calls CheckTimeExpiredHelper which makes a enroll object of the exam student is enrolled in.
	 * @param id: UNO in DB
	 * @param eno: ExamNo in DB
	 * @return true if time expired, false otherwise
	 * @throws SQLException
	 */
	
	
	public static boolean CheckTimeExpired(int id, int eno )throws SQLException{
		
		String sql = "SELECT TIMEALLOWED FROM EXAM WHERE ENO = ?";
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setInt(1, eno);
				ResultSet rs = statement.executeQuery();
				
				rs.next();
				int timeallowed = rs.getInt(1);
				
				int timeallowedInMilli= timeallowed * 60000;
				
				Enroll enroll = CheckTimeExpiredHelper(id, eno);
				
				if(enroll.getFinishTimestamp() != null){
					return true;
				}
				
				java.util.Date date= new java.util.Date();
				
				
				Timestamp timedone = enroll.getStartTimestamp();
				timedone.setTime(timedone.getTime()+ timeallowedInMilli);
				
				Timestamp timeNow =new Timestamp(date.getTime());
				
				if(timeNow.after(timedone)){
					// this means that the time for the exam has been exceeded..
					
					editFinsihTime(id, eno, timedone);
					
						return true;
				}
				
				else
					return false;
						
				
			}

	}
	
	//A helper for CheckedTimeExpired
	
	private static Enroll CheckTimeExpiredHelper(int id, int eno )throws SQLException{
		
		String sql = "SELECT * FROM ENROLL WHERE UNO = ? AND ENO = ?";
		
		try(Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);){
				
				statement.setInt(1, id);
				statement.setInt(2, eno);
				ResultSet rs = statement.executeQuery();
				 
				rs.next();
				Enroll enroll = new Enroll();
				enroll.setId(rs.getInt(1));
				enroll.setExamNo(rs.getInt(2));
				enroll.setStartTimestamp(rs.getTimestamp(3));
				enroll.setFinishTimestamp(rs.getTimestamp(4));
				
				
				return enroll;
				
			}

		
	}
	
	
	
	
	
	private static ArrayList<Enroll> addInstances(ResultSet rs){
		ArrayList<Enroll> enrollList = new ArrayList<Enroll>();
		try {
			while(rs.next()){
				Enroll enrolls = new Enroll();
				enrolls.setId(rs.getInt(1));
				enrolls.setExamNo(rs.getInt(2));
				enrolls.setStartTimestamp(rs.getTimestamp(3));
				enrolls.setFinishTimestamp(rs.getTimestamp(4));
				enrollList.add(enrolls);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return enrollList;
	}
		

	
	
	
	

	
	
	
	
	

}
