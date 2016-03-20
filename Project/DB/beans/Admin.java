package DB.beans;

public class Admin {

	private int adminId;
	private String userName;
	private String password;
	private String Email;
	private String FN;
	private String LN;
	
	public Admin(int id, String user, String pass){
		adminId = id;
		userName = user;
		password = pass;
	}
	public Admin(){
	}
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getFN() {
		return FN;
	}
	public void setFN(String fN) {
		FN = fN;
	}
	public String getLN() {
		return LN;
	}
	public void setLN(String lN) {
		LN = lN;
	}


}
