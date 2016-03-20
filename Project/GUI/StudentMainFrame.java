package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB.Tables.UserManager;
import DB.beans.User;
import GUI.Util.Animator;
import GUI.Util.ThemeSetter;

/**
 * 
 * Main Screen for student, it contains buttons to take exams, view reports and edit profile,
 * in addition to the sign out functionality. Students can edit their profiles from this
 * frame as well.
 * 
 * @author Ahmad Tayeb
 *
 */
public class StudentMainFrame extends JFrame implements ActionListener {
	private User user;
	
	// For the main panel
	private Animator anmMain;
	private JPanel pnlMain = new JPanel();
	private JLabel lblHello = new JLabel();
	private JButton btnEditProfile = new JButton("Edit profile");
	private JButton btnSignOut = new JButton("Sign out");
	private JButton btnEnrollInExam = new JButton("Enroll in exam");
	private JButton btnTakeAnExam = new JButton("Take an exam");
	private JButton btnViewGradeReports = new JButton("View grade reports");
	
	// For the edit profile panel
	private Animator anmEditProfile;
	private JPanel pnlEditProfile = new JPanel();
	private JLabel lblEditProfile = new JLabel("Edit Profile");
	private JLabel lblEmail = new JLabel("Email:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblConfirmPassword = new JLabel("Confirm password:");
	private JLabel lblFirstName = new JLabel("First Name:");
	private JLabel lblLastName = new JLabel("Last Name:");
	private JTextField txtEmail = new JTextField(15);
	private JPasswordField txtPassword = new JPasswordField(15);
	private JPasswordField txtConfirmPassword = new JPasswordField(15);
	private JTextField txtFirstName = new JTextField(15);
	private JTextField txtLastName = new JTextField(15);
	private JButton btnUpdate = new JButton("Update");
	private JButton btnCancel = new JButton("Cancel");
	
	public StudentMainFrame(User user) {
		// Style the frame
		super("Good Question");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		this.user = user;
		
		// Styling main panel and adding all components to it
		pnlMain.setLayout(null);
		pnlMain.setBackground(Color.white);
		pnlMain.add(lblHello);
		pnlMain.add(btnEditProfile);
		pnlMain.add(btnSignOut);
		pnlMain.add(btnEnrollInExam);
		pnlMain.add(btnTakeAnExam);
		pnlMain.add(btnViewGradeReports);
		
		// Styling edit profile panel and adding all components to it
		pnlEditProfile.setLayout(null);
		pnlEditProfile.setBackground(Color.white);
		pnlEditProfile.add(lblEditProfile);
		pnlEditProfile.add(lblEmail);
		pnlEditProfile.add(lblPassword);
		pnlEditProfile.add(lblConfirmPassword);
		pnlEditProfile.add(lblFirstName);
		pnlEditProfile.add(lblLastName);
		pnlEditProfile.add(txtEmail);
		pnlEditProfile.add(txtPassword );
		pnlEditProfile.add(txtConfirmPassword);
		pnlEditProfile.add(txtFirstName);
		pnlEditProfile.add(txtLastName);
		pnlEditProfile.add(btnUpdate);
		pnlEditProfile.add(btnCancel);
		
		// Update the greeting label with user name
		lblHello.setText("Hello " + user.getFirstName() + " " + user.getLastName() + "!");
		
		// Set main components to theme
		ThemeSetter.setToBold(lblHello);
		ThemeSetter.setToGreyButton(btnEditProfile);
		ThemeSetter.setToGreyButton(btnSignOut);
		ThemeSetter.setToTheme(btnEnrollInExam);
		ThemeSetter.setToTheme(btnTakeAnExam);
		ThemeSetter.setToTheme(btnViewGradeReports);
		
		// Set edit profile components to theme
		ThemeSetter.setToMediumBlack(lblEditProfile);
		ThemeSetter.setToTheme(lblEmail);
		ThemeSetter.setToTheme(lblPassword);
		ThemeSetter.setToTheme(lblConfirmPassword);
		ThemeSetter.setToTheme(lblFirstName);
		ThemeSetter.setToTheme(lblLastName);
		ThemeSetter.setToTheme(txtEmail);
		ThemeSetter.setToTheme(txtPassword);
		ThemeSetter.setToTheme(txtConfirmPassword);
		ThemeSetter.setToTheme(txtFirstName);
		ThemeSetter.setToTheme(txtLastName);
		ThemeSetter.setToTheme(btnUpdate);
		ThemeSetter.setToGreyButton(btnCancel);
		
		// Adjust button sizes for consistency
		btnEnrollInExam.setSize(btnViewGradeReports.getSize());
		btnTakeAnExam.setSize(btnViewGradeReports.getSize());
		
		// Set location of main components
		lblHello.setLocation(0, 0);
		btnSignOut.setLocation(getWidth() - btnSignOut.getWidth(), 0);
		btnEditProfile.setLocation(getWidth() - btnEditProfile.getWidth() - btnSignOut.getWidth(), 0);
		btnEnrollInExam.setLocation((getWidth() - btnEnrollInExam.getWidth()) / 2, getHeight() / 2 - btnEnrollInExam.getHeight() - 15);
		btnTakeAnExam.setLocation((getWidth() - btnTakeAnExam.getWidth()) / 2, btnEnrollInExam.getY() + btnEnrollInExam.getHeight() + 15);
		btnViewGradeReports.setLocation((getWidth() - btnViewGradeReports.getWidth()) / 2, btnTakeAnExam.getY() + btnTakeAnExam.getHeight() + 15);
		
		// Set location of edit profile components
		lblEditProfile.setLocation(10, 10);
		lblEmail.setLocation(70, lblEditProfile.getHeight() + 30);
		txtEmail.setLocation(232, lblEmail.getY());
		lblPassword.setLocation(70, lblEmail.getY() + lblEmail.getHeight() + 15);
		txtPassword.setLocation(232, lblPassword.getY());
		lblConfirmPassword.setLocation(70, lblPassword.getY() + lblPassword.getHeight() + 15);
		txtConfirmPassword.setLocation(lblConfirmPassword.getX() + lblConfirmPassword.getWidth() + 5, lblConfirmPassword.getY());
		lblFirstName.setLocation(70, lblConfirmPassword.getY() + lblConfirmPassword.getHeight() + 15);
		txtFirstName.setLocation(232, lblFirstName.getY());
		lblLastName.setLocation(70, lblFirstName.getY() + lblFirstName.getHeight() + 15);
		txtLastName.setLocation(232, lblLastName.getY());
		btnUpdate.setLocation(380, lblLastName.getY() + lblLastName.getHeight() + 20);
		btnCancel.setLocation(70, btnUpdate.getY());
		
		// Add listeners
		btnSignOut.addActionListener(this);
		btnEditProfile.addActionListener(this);
		btnEnrollInExam.addActionListener(this);
		btnTakeAnExam.addActionListener(this);
		btnViewGradeReports.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		
		// Pass main components to the animator to hide them before showing the frame 
		JComponent[] mainComponents = {lblHello, btnEditProfile, btnSignOut, btnEnrollInExam, btnTakeAnExam, btnViewGradeReports};
		anmMain = new Animator(pnlMain, mainComponents, 3);
		
		// Pass edit profile components to the animator to hide them before showing the frame 
		JComponent[] editProfileComponents = {lblEditProfile, lblEmail, lblPassword, lblConfirmPassword, lblFirstName,
				lblLastName, txtEmail, txtPassword, txtConfirmPassword, txtFirstName, txtLastName, btnUpdate, btnCancel};
		anmEditProfile = new Animator(pnlEditProfile, editProfileComponents, 6);
		
		// Add the main panel to the frame and show the frame
		add(pnlMain);
		//add(pnlEditProfile); // for testing
		setVisible(true);
		
		// Start animation of main frame
		anmMain.showComponents();
		//anmEditProfile.showComponents(); // for testing
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == btnSignOut) {
			new StudentLogin();
			dispose();
		}
		else if(source == btnEditProfile) {
			editProfilePressed();
		}
		else if(source == btnEnrollInExam) {
			if(!updateUser())
				return;
			new EnrollExam(user.getId()).setVisible(true);
			dispose();
		}
		else if(source == btnTakeAnExam) {
			takeAnExamPressed();
		}
		else if(source == btnViewGradeReports) {
			if(!updateUser())
				return;
			new ViewGradeReport(user.getId()).setVisible(true);
			dispose();
		}
		else if(source == btnUpdate) {
			updatePressed();
		}
		else if(source == btnCancel) {
			showMainPanel();
		}
	}
	
	public void editProfilePressed() {
		if(!updateUser())
			return;
		
		txtEmail.setText(user.getEmail());
		txtPassword.setText(user.getPassword());
		txtConfirmPassword.setText(user.getPassword());
		txtFirstName.setText(user.getFirstName());
		txtLastName.setText(user.getLastName());
		
		new Thread() {
			public void run() {
				anmMain.hideComponents();
				try{sleep(1000);}catch(Exception e){} // 1000 is the length of the animation
				getContentPane().removeAll();
				add(pnlEditProfile);
				anmEditProfile.showComponents();
				// To solve the problem of the new interface not shown
				setSize(getWidth() + 1, getHeight());
				setSize(getWidth() - 1, getHeight());
			}
		}.start();
	}
	
	public void showMainPanel() {
		new Thread() {
			public void run() {
				anmEditProfile.hideComponents();
				try{sleep(1000);}catch(Exception e){} // 1000 is the length of the animation
				getContentPane().removeAll();
				add(pnlMain);
				anmMain.showComponents();
				// To solve the problem of the new interface not shown
				setSize(getWidth() + 1, getHeight());
				setSize(getWidth() - 1, getHeight());
			}
		}.start();
	}
	
	public void updatePressed() {
		if(!updateUser())
			return;
		
		String email = txtEmail.getText();
		String pass1 = new String(txtPassword.getPassword());
		String pass2 = new String(txtConfirmPassword.getPassword());
		String fName = txtFirstName.getText();
		String LName = txtLastName.getText();
		
		if(email.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || fName.isEmpty() || LName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill all fields", "Missing fields", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(!pass1.equals(pass2)) {
			JOptionPane.showMessageDialog(this, "Password fields doesn't match", "Password mismatch", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			UserManager.editUserById(user.getId(), email, pass1, fName, LName);
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(this, "SQLException:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		showMainPanel();
	}
	
	public boolean updateUser() {
		try {
			user = UserManager.getUserById(user.getId());
			return true;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(this, "SQLException:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public void takeAnExamPressed() {
		if(!updateUser())
			return;
		
		new TakeExamGUI(user.getId()).setVisible(true);
		dispose();
	}
}
