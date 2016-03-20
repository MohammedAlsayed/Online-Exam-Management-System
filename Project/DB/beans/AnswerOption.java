package DB.beans;

public class AnswerOption{
	private int ENO;
	private int QNO;
	private String ONO;
	private String optionText;
	
	
	
	
	public AnswerOption(int eNO, int qNO, String oNO, String optionText) {
		ENO = eNO;
		QNO = qNO;
		ONO = oNO;
		this.optionText = optionText;
	}
	
	public int getENO() {
		return ENO;
	}
	public int getQNO() {
		return QNO;
	}
	public String getONO() {
		return ONO;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setENO(int eNO) {
		ENO = eNO;
	}
	public void setQNO(int qNO) {
		QNO = qNO;
	}
	public void setONO(String oNO) {
		ONO = oNO;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	@Override
	public String toString() {
		return "AnswerOption [ENO= " + ENO + ", QNO= " + QNO + ", ONO= " + ONO
				+ ", optionText= " + optionText + "]";
	}

}
