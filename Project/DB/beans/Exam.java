package DB.beans;
import javax.swing.JList;

public class Exam{

	private int ENO;
	private String ETitle;
	private int timeAllowed;
	
	public Exam(int eNO, String eTitle, int timeAllowed) {
		ENO = eNO;
		ETitle = eTitle;
		this.timeAllowed = timeAllowed;
	}
	public Exam(){
		super();
	}
	public int getENO() {
		return ENO;
	}
	public String getETitle() {
		return ETitle;
	}
	public int getTimeAllowed() {
		return timeAllowed;
	}
	public void setENO(int eNO) {
		ENO = eNO;
	}
	public void setETitle(String eTitle) {
		ETitle = eTitle;
	}
	public void setTimeAllowed(int timeAllowed) {
		this.timeAllowed = timeAllowed;
	}
	@Override
	public String toString() {
		
		return  ETitle + "                               " + "Time Allowed: "+timeAllowed;
	}
	
	

}
