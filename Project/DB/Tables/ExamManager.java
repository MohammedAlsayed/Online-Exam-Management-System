package DB.Tables;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DB.DBUtil;
import DB.beans.Enroll;
import DB.beans.Exam;

/**
 * Manages exam options in the database
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */


public class ExamManager{
	 /*The methods I created for the Exam Table are as following: 
	 	DDL's
		 Create Exam
		 Delete Exam
	
		 DML's
		 update exam title
		 update exam allowed time
	
		 Queries 
		 *Dispaly Exam
		 Display all Exams
		 Display Exams where no one signed up
		 Exams token by a student
	  */

	
	// DDL's
	/**
	 * A method that adds an exams into the database
	 * @param ENO
	 * Exam number
	 * @param title 
	 * title of the exam
	 * @param timeAllowed 
	 * the time allowed for the exam
	 * @throws SQLException
	 */
	public static boolean createExam(String title, int timeAllowed)throws SQLException{
		String sql = "INSERT INTO EXAM (ENO, ETITLE, TIMEALLOWED) VALUES(examSequence.nextval,?,?)";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){

			stmt.setString(1, title);
			stmt.setInt(2, timeAllowed);
			
			int affected = stmt.executeUpdate();
			if(affected == 1){
				return true;
			}
			else{
				return false;
			}
			
		}
	}
	/**
	 * The method deletes an exam.
	 * @param ENO the exam number
	 * @throws SQLException
	 */
	public static boolean deleteExam(int ENO)throws SQLException{
		String sql = "DELETE FROM EXAM WHERE ENO = ?";
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			){
			stmt.setInt(1, ENO);
			int affected = stmt.executeUpdate();
			if(affected == 1)
				return true;
			else
				return false;	
		}
	}
	
	
	// DML's
	/**
	 * The method updates the title of the exam by taking only the exam number.
	 * @param ENO exam number
	 * @param title the new title
	 * @returns true if the row is updated or false otherwise
	 * @throws SQLException
	 */
	public static boolean setTitle(int ENO, String title)throws SQLException{
		String sql = "UPDATE EXAM SET ETITLE = ? WHERE ENO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			
			stmt.setString(1, title);
			stmt.setInt(2, ENO);
			
			int affected = stmt.executeUpdate();
			if(affected == 1){
				return true;
			}
			else{
				return false;
			}
		}
	}
	/**
	 * The method updates the allowed time for the exam
	 * @param ENO exam number
	 * @param timeAllowed for the exam
	 * @return true if the row is updated or false otherwise
	 * @throws SQLException
	 */
	public static boolean setAllowedTime(int ENO, int timeAllowed)throws SQLException{
		String sql = "UPDATE EXAM SET TIMEALLOWED = ? WHERE ENO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){

			stmt.setInt(1, ENO);
			stmt.setInt(2, timeAllowed);
			
			int affected = stmt.executeUpdate();
			if(affected == 1){
				return true;
			}
			else{
				return false;
			}
		}
	}
	public static boolean updateExam(String etitle, int timeAllowed, int ENO)throws SQLException{
		String sql = "UPDATE EXAM SET ETITLE = ?, TIMEALLOWED = ? WHERE ENO = ?";
		System.out.println("Hello");
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			System.out.println("World");

			stmt.setString(1, etitle);
			stmt.setInt(2, timeAllowed);
			stmt.setInt(3, ENO);
			
			int affected = stmt.executeUpdate();
			System.out.println("Hello1");
			if(affected == 1){
				System.out.println("world2");
				return true;
			}
			else{
				return false;
			}
		}
	}
	// Queries
	/**
	 * The method retrieves an exam row and encapsulates it in an exam object
	 * @param ENO exam number
	 * @return Exam object containing row info
	 * @throws SQLException
	 */
	public static Exam getExam(int ENO) throws SQLException{
	String sql = "SELECT * FROM EXAM WHERE ENO = ?";
	try(Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		){
		stmt.setInt(1, ENO);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Exam exam = new Exam();
		exam.setENO(rs.getInt(1));
		exam.setETitle(rs.getString(2));
		exam.setTimeAllowed(rs.getInt(3));
		
		return exam;
		}
	}
	/**
	 * A method returns all exams in the DB 
	 * @returns Arraylist of all exams
	 * @throws SQLException
	 */
	public static ArrayList<Exam> getAllExams()throws SQLException{
		String sql = "SELECT * FROM EXAM ORDER BY ENO";
		ArrayList<Exam> exams = new ArrayList<Exam>();
		try(Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
				){	
			ResultSet rs = stmt.executeQuery(sql);
			// adding exams into the exam ArrayList
			exams = addInstancesForGetAllExams(rs);
		}
		return exams;
	}
	/**
	 * The method returns exams where no one signed up in it.
	 * @return Result set
	 * @throws SQLException
	 */
	public static ArrayList<Exam> getExamsNoOneSignedUp()throws SQLException{
		String sql = "SELECT * FROM EXAM WHERE ENO NOT IN("
				+ "SELECT ENO FROM ENROLL)";
		
		ArrayList<Exam> exams = new ArrayList<Exam>();
		try(Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
				){
			ResultSet rs = stmt.executeQuery(sql);
			exams = addInstances(rs);
		}
		return exams;
	}
	public static boolean isSignedUp(int ENO)throws SQLException{
		String sql = "SELECT DISTINCT EXAM.ENO FROM EXAM,ENROLL WHERE EXAM.ENO = ENROLL.ENO AND EXAM.ENO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
				){
			stmt.setInt(1, ENO);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		}
	}
	/**
	 * The method returns only the specified user's exams.
	 * @param userNumber the user's ID Number
	 * @return Result set
	 * @throws SQLException
	 */
	public static ArrayList<Exam> getExamByStudent(int userNumber)throws SQLException{
		String sql = "SELECT * FROM ENROLL WHERE UNO = ?";
		ArrayList<Exam> exams = new ArrayList<Exam>();
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, userNumber);
			ResultSet rs = stmt.executeQuery();
			exams = addInstances(rs);
			return exams;
		}
			
	}
	
	private static ArrayList<Exam> addInstancesForGetAllExams(ResultSet rs){
		ArrayList<Exam> examList = new ArrayList<Exam>();
		try {
			while(rs.next()){
				Exam exams = new Exam();
				exams.setENO(rs.getInt(1));
				exams.setETitle(rs.getString(2));
				exams.setTimeAllowed(rs.getInt(3));
				examList.add(exams);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return examList; 
	}
	
	/**
	 * The method add instances into an array list
	 * @param ResultSet of an execute query
	 * @return a ArrayList of exams
	 */
	private static ArrayList<Exam> addInstances(ResultSet rs){
		ArrayList<Exam> exams = new ArrayList<Exam>();
		try {
			while(rs.next()){
				int examNumber = rs.getInt(2);
				exams.add(getExam(examNumber));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return exams;
	}
	
	/**
	 * The method retrieves an exam row and encapsulates it in an exam object
	 * 
	 * @param ETITLE: the exam title
	 * @return Exam object containing row info
	 * @throws SQLException
	 */
	public static Exam getExam(String ETITLE) throws SQLException{
		String sql = "SELECT * FROM EXAM WHERE ETITLE = ?";
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			){
			stmt.setString(1, ETITLE);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Exam exam = new Exam();
				exam.setENO(rs.getInt(1));
				exam.setETitle(rs.getString(2));
				exam.setTimeAllowed(rs.getInt(3));

				return exam;
			} else {
				return null;
			}
		}
	}
	
}