package DB.Tables;

import java.sql.*;
import java.util.ArrayList;
import DB.DBUtil;
import DB.beans.*;


/**
 * Manages the user responses in the database.
 * 
 * @author Mossad Helali 14-12-2015
 *
 */
public class UserResponseManager {

	public static boolean addResponse(int userNumber, int examNumber, int questionNumber, String response) throws SQLException {

		String sql = "INSERT INTO USERRESPONSE VALUES (?, ?, ?, ?)";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);
			statement.setInt(3, questionNumber);
			statement.setString(4, String.valueOf(response).toUpperCase());

			int affected = statement.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Gets a UserResponse object given the userNumber, the examNumber, the qusetionNumber
	 * @param userNumber: UID
	 * @param examNumber: ENO
	 * @param questionNumber: QNO
	 * @return UserResponse object if it exists, null otherwise
	 * @throws SQLException
	 */

	public static UserResponse getResponse(int userNumber, int examNumber, int questionNumber) throws SQLException {
		String sql = "SELECT * FROM USERRESPONSE WHERE UNO= ?, ENO= ?, QNO= ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);
			statement.setInt(3, questionNumber);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				String response = rs.getString(4);

				UserResponse ur = new UserResponse(userNumber, examNumber,
						questionNumber, response);

				return ur;
			} else {
				return null;
			}
		}
	}

	
	/**
	 * Gets all the user responses for a given exam
	 * @param userNumber: UNO
	 * @param examNumber: ENO
	 * @return an ArrayList<UserResponse> object or null if there are no responses
	 * @throws SQLException
	 */
	public static ArrayList<UserResponse> getAllExamResponses(int userNumber, int examNumber) throws SQLException {

		String sql = "SELECT * FROM USERRESPONSE WHERE UNO= ? AND ENO= ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);

			ResultSet rs = statement.executeQuery();
			ArrayList<UserResponse> userResponseList = new ArrayList<UserResponse>();

			while (rs.next()) {
				int questionNumber = rs.getInt(3);
				String response = rs.getString(4);

				UserResponse ur = new UserResponse(userNumber, examNumber,
						questionNumber, response);

				userResponseList.add(ur);
			}

			if (!userResponseList.isEmpty()) {
				return userResponseList;
			} else {
				return null;
			}
		}
	}

	
	/**
	 * Gets all user responses for all exams.
	 * 
	 * @param userNumber: UNO
	 * @return ArrayList<UserResponse> object or null if there are no responses exist
	 * @throws SQLException
	 */
	public static ArrayList<UserResponse> getAllUserResponses(int userNumber) throws SQLException {

		String sql = "SELECT * FROM USERRESPONSE WHERE UNO= ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			
			ResultSet rs = statement.executeQuery();
			ArrayList<UserResponse> userResponseList = new ArrayList<UserResponse>();

			while (rs.next()) {
				int examNumber = rs.getInt(2);
				int questionNumber = rs.getInt(3);
				String response = rs.getString(4);

				UserResponse ur = new UserResponse(userNumber, examNumber,
						questionNumber, response);

				userResponseList.add(ur);
			}

			if (!userResponseList.isEmpty()) {
				return userResponseList;
			} else {
				return null;
			}
		}
	}
	

	/**
	 * Gets all correct responses for a user in an exam
	 * @param userNumber: UNO
	 * @param examNumber: ENO
	 * @return ArrayList<UserReponse> that contains all correct responses for the given user in the given exam, 
	 * or null if there are no responses exist.
	 * @throws SQLException
	 */
	public static ArrayList<UserResponse> getAllCorrectResponses(int userNumber, int examNumber) throws SQLException {

		String sql = "SELECT DISTINCT USERRESPONSE.UNO, USERRESPONSE.ENO, USERRESPONSE.QNO, USERRESPONSE.RESPONSE "
				+ "FROM USERRESPONSE JOIN QUESTION ON USERRESPONSE.QNO = QUESTION.QNO AND USERRESPONSE.ENO = QUESTION.ENO "
				+ "WHERE USERRESPONSE.UNO = ? AND USERRESPONSE.ENO= ? AND USERRESPONSE.RESPONSE=QUESTION.CORRECTANSWER";
		
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);

			ResultSet rs = statement.executeQuery();
			ArrayList<UserResponse> userResponseList = new ArrayList<UserResponse>();

			while (rs.next()) {

				int questionNumber = rs.getInt(3);
				String response = rs.getString(4);

				UserResponse ur = new UserResponse(userNumber, examNumber,
						questionNumber, response);

				userResponseList.add(ur);
			}

			if (!userResponseList.isEmpty()) {
				return userResponseList;
			} else {
				return null;
			}
		}
	}
	
	
	/**
	 * Gets all wrong responses for a user in an exam
	 * @param userNumber: UNO
	 * @param examNumber: ENO
	 * @return ArrayList<UserReponse> that contains all wrong responses for the given user in the given exam, 
	 * or null if there are no responses exist.
	 * @throws SQLException
	 */
	public static ArrayList<UserResponse> getAllWrongResponses(int userNumber, int examNumber) throws SQLException {

		String sql = "SELECT * FROM USERRESPONSE WHERE UNO=? AND ENO=? MINUS "
				+ "SELECT DISTINCT USERRESPONSE.UNO, USERRESPONSE.ENO, USERRESPONSE.QNO, USERRESPONSE.RESPONSE "
				+ "FROM USERRESPONSE JOIN QUESTION ON USERRESPONSE.QNO = QUESTION.QNO AND USERRESPONSE.ENO = QUESTION.ENO "
				+ "WHERE USERRESPONSE.UNO = ? AND USERRESPONSE.ENO= ? AND USERRESPONSE.RESPONSE=QUESTION.CORRECTANSWER";
		
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			
			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);
			statement.setInt(3, userNumber);
			statement.setInt(4, examNumber);

			ResultSet rs = statement.executeQuery();
			ArrayList<UserResponse> userResponseList = new ArrayList<UserResponse>();

			while (rs.next()) {

				int questionNumber = rs.getInt(3);
				String response = rs.getString(4);

				UserResponse ur = new UserResponse(userNumber, examNumber, questionNumber, response);

				userResponseList.add(ur);
			}

			if (!userResponseList.isEmpty()) {
				return userResponseList;
			} else {
				return null;
			}
		}
	}
	
	
	/**
	 *  gets the maximum number of answered question for a student in an exam
	 * @param userNumber: UNO
	 * @param examNumber: ENO
	 * @return the maximum number of questions answered, returns 0 if there are no such questions.
	 * @throws SQLException
	 */
	public static int getMaximumAnsweredQuestion(int userNumber, int examNumber) throws SQLException{
		
		String sql = "SELECT MAX(QNO) FROM USERRESPONSE WHERE UNO=? AND ENO=?";
		
		try (Connection connection = DBUtil.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			statement.setInt(1, userNumber);
			statement.setInt(2, examNumber);

			ResultSet rs = statement.executeQuery();
			
			
			if (rs.next()) {
				int max = rs.getInt(1);
				return max;
			} else {
				return 0;
			}
		}
	}

	
	/**
	 * submits user responses for the questions they didn't solve. THIS METHOD SHOULD BE CALLED ONLY WHEN THE TIME EXPIRES.
	 * after executing this method, you should call enroll manager to make the finish time = start time + time allowed
	 * @param userNumber: UNO
	 * @param examNumber: ENO
	 * @return true if 
	 * @throws SQLException
	 */
	public static boolean submitUnansweredQuestions(int userNumber, int examNumber) throws SQLException{
		
		int maximumQuestion = getMaximumAnsweredQuestion(userNumber, examNumber);
		
		int numberOfQuestions = QuestionManager.getNumberOfQuestions(examNumber);
		
		String sql = "INSERT INTO USERRESPONSE VALUES (?, ?, ?, 'N')";
		
		try(Connection connection = DBUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				
				statement.setInt(1, userNumber);
				statement.setInt(2, examNumber);
				for (int i = maximumQuestion + 1; i<= numberOfQuestions; i++){
					
					statement.setInt(3, i);
					
					int affected = statement.executeUpdate();

				if (affected == 0) {
					return false; // if there is one query not executed
				}
			}
			return true;
		}
	}
	
}
