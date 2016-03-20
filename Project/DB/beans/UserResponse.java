package DB.beans;

public class UserResponse {

	private int userNumber;
	private int examNumber;
	private int questionNumber;
	private String response;
	
	
	public UserResponse(int userNumber, int examNumber, int questionNumber, String response) {
		
		this.userNumber = userNumber;
		this.examNumber = examNumber;
		this.questionNumber = questionNumber;
		this.response = response;
	}

	public int getUserNumber() {
		return userNumber;
	}
	
	public int getExamNumber() {
		return examNumber;
	}
	
	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String toString(){
		return "User: " + userNumber + "\tExam: " + examNumber + "\tQuestion: " +  questionNumber + "\tResponse: " + response;
	}
	
	
}
