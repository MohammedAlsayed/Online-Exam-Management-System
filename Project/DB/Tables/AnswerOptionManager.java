package DB.Tables;
import DB.beans.*;

import java.sql.*;
import DB.DBUtil;
import java.util.ArrayList;

public class AnswerOptionManager {

/**
 * Manages answer options in the database.
 * 
 * @author Mossad Helali, 13-12-2015.
 */
	
	
	/**
	 * Adds an answer option to a question, given the exam number, question number, option number and option text
	 * @param examNumber: ENO in DB
	 * @param questionNumber: QNO in DB
	 * @param optionNumber: ONO in DB
	 * @param optionText: OPTIONTEXT in DB
	 * @return true if inserted, false otherwise.
	 * @throws SQLException
	 */
	public static boolean addAnswerOption(int examNumber, int questionNumber, String optionNumber, String optionText) throws SQLException{
		
		String sql = "INSERT INTO ANSWEROPTION VALUES (?, ?, ?, ?)";
		
		try(Connection connection = DBUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
						
			statement.setInt(1, examNumber);
			statement.setInt(2, questionNumber);
			statement.setString(3, String.valueOf(optionNumber).toUpperCase());
			statement.setString(4, optionText);
			
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
	 * Gets all answer options for a specific question in an exam.
	 * @param examNumber: ENO in the DB table
	 * @param questionNumber: QNO in the DB table
	 * @return: an array list of AnswerOption objects or null if no questions exist.
	 * @throws SQLException
	 */
	public static ArrayList<AnswerOption> getAnswerOptions(int examNumber, int questionNumber) throws SQLException{
		
		String sql = "SELECT * FROM ANSWEROPTION WHERE QNO= ? AND ENO= ?";
		
		try(Connection connection = DBUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);){
			
			statement.setInt(1, questionNumber);
			statement.setInt(2,  examNumber);
			
			ResultSet rs = statement.executeQuery();
			
			ArrayList<AnswerOption> answerOptionList = new ArrayList<AnswerOption>();
			while(rs.next()){
				String ono = rs.getString("ONO");
				String optionText = rs.getString("OPTIONTEXT");
				
				AnswerOption aw = new AnswerOption(examNumber, questionNumber, ono, optionText);

				answerOptionList.add(aw);
			}
			
			if(!answerOptionList.isEmpty()){
				return answerOptionList;
			}
			else{
				return null;
			}
			
		}
				
	}
}
