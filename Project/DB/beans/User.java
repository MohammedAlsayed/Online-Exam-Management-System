package DB.beans;

public class User implements Comparable<User>{
	
	// attributes according to the schema
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	
	
	public User(int id, String email, String password, String firstName, String lastName){
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	public User (){
		this(0, "", "", "", "");
	}
	
	//getters
	public int getId(){
		return this.id;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	
	//setters
	public void setId(int id){
		this.id = id;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	// throws NullPointerException
	public int compareTo(User other){
		if(other != null){
			return this.id - other.id;
		}else{
			throw new NullPointerException("Null User Object");
		}
	}
	
	// uses compareTo method
	// throws: ClassCastException, NullPointerException (from compareTo())
	public boolean equals(Object other){
		if(other instanceof User){
			return this.compareTo((User) other) == 0;
		}else{
			throw new ClassCastException("Comparing User with non User class");
		}
	}
	
	
	public String toString(){
		return "ID: " + id + "\nEmail: " + email + "\nPassword: " + password + "\n" + firstName + "\t" + lastName;
	}
}
