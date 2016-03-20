package DB.Tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DB.DBUtil;
import DB.beans.AnswerOption;
import DB.beans.Exam;
import DB.beans.Question;

/**
 * Manages question options in the database
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */

public class QuestionManager {
	 /*The methods I created for the Question Table are as following: 
		 DDL's
		 Insert Question 
		 Delete Question
		 Delete all questions of an exam
		
		 DML's
		 update a question text
		 update a question answer
	
		 Queries
		 display a Qustion
		 displayQustions*/
	
	// DDL's
	/**
	 * Adds a question into an Exam
	 * @param ENO Exam Number
	 * @param QNO Question Number
	 * @param Qtext Question Text
	 * @param correctAnswer Correct Answer Char
	 * @throws sql exception
	 */
	public static boolean addQuestion(int ENO, int QNO, String Qtext, String correctAnswer)throws SQLException{
		String sql = "INSERT INTO QUESTION (ENO, QNO, QTEXT, CORRECTANSWER)"
				+"VALUES(?,?,?,?)";
	
	try(Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){
		
		stmt.setInt(1, ENO);
		stmt.setInt(2, QNO);
		stmt.setString(3, Qtext);
		stmt.setString(4, correctAnswer);
		
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
	 * The method deletes a specific question in a specific exam
	 * @param ENO exam number
	 * @param QNO question number
	 * @return true if question deleted or false otherwise
	 * @throws SQLException
	 */
	public static boolean deleteQuestion(int ENO, int QNO)throws SQLException{
		String sql = "DELETE FROM QUESTION WHERE ENO = ? AND QNO = ?";
	
	try(Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){
		
		stmt.setInt(1, ENO);
		stmt.setInt(2, QNO);
		
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
	 * 
	 * @param ENO exam number
	 * @return true if questions deleted or false otherwise
	 * @throws SQLException
	 */
	public static boolean deleteAllQuestionsOfExam(int ENO)throws SQLException{
		String sql = "DELETE FROM QUESTION WHERE ENO = ?";
	
	try(Connection conn = DBUtil.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			){

		stmt.setInt(1, ENO);
		
		int affected = stmt.executeUpdate();
			if(affected > 0){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	// DML's
	/**
	 * The method updates the question text
	 * @param ENO exam number
	 * @param QNO question number
	 * @param Qtext the new question text
	 * @return true if row updated or false otherwise
	 * @throws SQLException
	 */
	public static boolean setQuestionText(int ENO, int QNO, String Qtext)throws SQLException{
		String sql = "UPDATE QUESTION SET QTEXT = ? WHERE ENO = ? AND QNO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			
			stmt.setString(1, Qtext);
			stmt.setInt(2, ENO);
			stmt.setInt(3, QNO);


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
	 * The method updates the correct answer of a question
	 * @param ENO exam number
	 * @param QNO question number
	 * @param correctAnswer the new correct answer
	 * @return true if row updated or false otherwise
	 * @throws SQLException
	 */
	public static boolean setQuestionAnswer(int ENO, int QNO, String correctAnswer)throws SQLException{
		String sql = "UPDATE QUESTION SET CORRECTANSWER = ? WHERE ENO = ? AND QNO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			
			stmt.setString(1, correctAnswer);	
			stmt.setInt(2, ENO);
			stmt.setInt(3, QNO);


			int affected = stmt.executeUpdate();
			if(affected == 1){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	// Queries
	/**
	 * Retrieves a question
	 * @param ENO exam number
	 * @param QNO question number
	 * @return a question object encapsulating question row info.
	 * @throws SQLException
	 * @throws NullPointerException	
	 */
	public static Question getQustion(int ENO, int QNO)throws SQLException, NullPointerException{
		String sql = "SELECT * FROM QUESTION WHERE ENO = ? AND QNO = ?";
		
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			Question q;
			stmt.setInt(1, ENO);
			stmt.setInt(2, QNO);
			ResultSet rs = stmt.executeQuery();
			q = addInstance(rs);
			return q;
		}
	}
	/**
	 * returns all questions in an exam
	 * @param ENO exam number
	 * @return ArrayList of all quesitons
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public static ArrayList<Question> getQustions(int ENO)throws SQLException{
		String sql = "SELECT * FROM QUESTION WHERE ENO = ? ORDER BY QNO";
		ArrayList<Question> qs = new ArrayList<Question>();
		try(Connection conn = DBUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				){
			stmt.setInt(1, ENO);
			ResultSet rs = stmt.executeQuery();
			qs = addInstances(rs);
			return qs;
		}
	}
	/**
	 * Encapsulates the question row info into an obejct
	 * @param rs the resultSet from the query
	 * @return	Question object containing the row info
	 * @throws SQLException
	 */
	private static Question addInstance(ResultSet rs) throws SQLException{
		rs.next();
		Question q = new Question();
			q.setENO(rs.getInt(1));
			q.setQNO(rs.getInt(2));
			q.setQText(rs.getString(3));
			q.setCorrectAnswer(rs.getString(4));
		return q;
	}
	private static ArrayList<Question> addInstances(ResultSet rs){
		ArrayList<Question> qs = new ArrayList<Question>();
		try {
			while(rs.next()){
				Question q = new Question();
				q.setENO(rs.getInt(1));
				q.setQNO(rs.getInt(2));
				q.setQText(rs.getString(3));
				q.setCorrectAnswer(rs.getString(4));
				qs.add(q);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return qs;
	}
	/**
	 * gets the number of questions in a given exam
	 * @param examNumber: ENO
	 * @return number of question in an exam, returns 0 if there are no question in the exam
	 * @throws SQLException
	 */
	public static int getNumberOfQuestions(int examNumber) throws SQLException{
		
		String sql = "SELECT ENO, COUNT(*) FROM QUESTION GROUP BY ENO";
		
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				int eno = rs.getInt(1);
				int count = rs.getInt(2);
				
				if(eno == examNumber){
					return count;
				}
			}
			
			return 0;
		
		}
		
	}
}
