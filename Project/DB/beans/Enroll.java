package DB.beans;
import java.sql.Timestamp;

public class Enroll implements Comparable<Enroll>{
	
	// attributes
	private int id;
	private int eno;
	private Timestamp StartTimestamp;
	private Timestamp FinishTimestamp;
	//Timestamp timestamp = Timestamp.valueOf("2007-09-23 10:10:10.0");
	
	
	public Enroll(int id, int eno, Timestamp StartTimestamp, Timestamp FinishTimestamp){
		this.id=id;
		this.eno=eno;
		this.StartTimestamp=StartTimestamp;
		this.FinishTimestamp=FinishTimestamp;
		
	}
	
	public Enroll(){
		this(0,0,null,null);
	}
	
	//getters
	
	public int getId(){
		return id;
		
	}
	
	public int getExamNo(){
		return eno;
	}
	
	public Timestamp getStartTimestamp(){
		return StartTimestamp;
	}
	
	public Timestamp getFinishTimestamp(){
		return FinishTimestamp;
	}
	
	
	//setters
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setExamNo(int eno){
		this.eno=eno;
	}
	
	public void setStartTimestamp(Timestamp StartTimestamp){
		this.StartTimestamp=StartTimestamp;
	}
	
	public void setFinishTimestamp(Timestamp FinishTimestamp){
		this.FinishTimestamp=FinishTimestamp;
	}

	@Override
	public int compareTo(Enroll other) {
		if (other!= null){
			return (this.id-other.id)+(this.eno-other.eno);
			}
		else{
			throw new NullPointerException("Null User Object");
		}
	}
	
	public boolean equals(Object other){
		if(other instanceof Enroll){
			return(this.compareTo((Enroll) other)==0);
		}
		
		else{
			throw new ClassCastException("Comparing Enroll with non Enroll class");
		}
		
	}
	

	
	public String toString(){
		
		if(StartTimestamp==null && FinishTimestamp== null)
		{
			return "ID: " + id +" Exam No.: " + eno + " Start Time: "+ "null"+"Finish Time: "+ "null";
		}
		
		else if(StartTimestamp==null)
		{
			return "ID: " + id +" Exam No.: " + eno + " Start Time: "+ "null" +" Finish Time: "+ FinishTimestamp.toString();
			
		}
		
		else if(FinishTimestamp==null)
		{
			return "ID: " + id +" Exam No.: " + eno + " Start Time: "+ StartTimestamp.toString()+" Finish Time: "+ "null";
		}
		
		else
			return "ID: " + id +" Exam No.: " + eno + " Start Time: "+ StartTimestamp.toString() +" Finish Time: "+ FinishTimestamp.toString();
		
	}	
	


}
