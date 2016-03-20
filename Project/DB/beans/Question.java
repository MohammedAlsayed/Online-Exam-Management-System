package DB.beans;


public class Question {
	private int ENO;
	private int QNO;
	private String QText;
	private String correctAnswer;
	
	public Question(int eNO, int qNO, String qText, String correctAnswer) {
		super();
		ENO = eNO;
		QNO = qNO;
		QText = qText;
		this.correctAnswer = correctAnswer;
	}

	public Question() {
		super();
	}

	public int getENO() {
		return ENO;
	}
	public void setENO(int eNO) {
		ENO = eNO;
	}
	public int getQNO() {
		return QNO;
	}
	public void setQNO(int qNO) {
		QNO = qNO;
	}
	public String getQText() {
		return QText;
	}
	public void setQText(String qText) {
		QText = qText;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	@Override
	public String toString() {
		return "Question No: "+QNO+"\t--Question Text: "+QText+"\t--Correct Answer: "+correctAnswer;
	}
}
