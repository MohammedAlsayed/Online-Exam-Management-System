package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 * Main login page.
 * The interface is for students, but admins can click on sign in as admin
 * to go to admin sign in.
 * 
 * @author Ahmad Tayeb
 * 
 */
public class StudentLogin extends JFrame implements ActionListener {
	private JPanel pnl = new JPanel();
	private JLabel lblSignIn = new JLabel("Sign in");
	private JLabel lblEmail = new JLabel("Email:");
	private JLabel lblPassword = new JLabel("Password:");
	private JLabel lblSignUp = new JLabel("Don't have an account? Sign up here.");
	private JLabel lblSignInAsAdmin = new JLabel("Sign in as admin");
	private JTextField txtEmail = new JTextField(15);
	private JPasswordField txtPassword = new JPasswordField(15);
	private JButton btnSignIn = new JButton("Sign in");
	
	public StudentLogin() {
		// Style the frame
		super("Good Question");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Styling panel and adding all components to it
		pnl.setLayout(null);
		pnl.setBackground(Color.white);
		pnl.add(lblSignIn);
		pnl.add(lblEmail);
		pnl.add(lblPassword);
		pnl.add(lblSignUp);
		pnl.add(lblSignInAsAdmin);
		pnl.add(txtEmail);
		pnl.add(txtPassword);
		pnl.add(btnSignIn);
		
		// Set all components to theme
		ThemeSetter.setToBigCyan(lblSignIn);
		ThemeSetter.setToTheme(lblEmail);
		ThemeSetter.setToTheme(txtEmail);
		ThemeSetter.setToTheme(lblPassword);
		ThemeSetter.setToTheme(txtPassword);
		ThemeSetter.setToTheme(btnSignIn);
		ThemeSetter.setToSmallBlue(lblSignUp);
		ThemeSetter.setToSmallBlue(lblSignInAsAdmin);
		
		// Set location of all components
		lblSignIn.setLocation((getWidth() - lblSignIn.getWidth()) / 2, 10);
		lblEmail.setLocation((getWidth() - (lblEmail.getWidth() + 5 + txtEmail.getWidth())) / 2, lblSignIn.getY() + lblSignIn.getHeight() + 70);
		txtEmail.setLocation(lblEmail.getX() + lblEmail.getWidth() + 5, lblSignIn.getY() + lblSignIn.getHeight() + 70);
		lblPassword.setLocation((getWidth() - (lblPassword.getWidth() + 5 + txtPassword.getWidth())) / 2 - 16, lblEmail.getY() + lblEmail.getHeight() + 15);
		txtPassword.setLocation(lblPassword.getX() + lblPassword.getWidth() + 5, lblEmail.getY() + lblEmail.getHeight() + 15);
		btnSignIn.setLocation((getWidth() - btnSignIn.getWidth()) / 2, lblPassword.getY() + lblPassword.getHeight() + 20);
		lblSignUp.setLocation((getWidth() - lblSignUp.getWidth()) / 2 , btnSignIn.getY() + btnSignIn.getHeight() + 10);
		lblSignInAsAdmin.setLocation(3, getHeight() - lblSignInAsAdmin.getHeight() - 33);
		
		// Add listeners
		btnSignIn.addActionListener(this);
		lblSignUp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				new RegisterGUI().setVisible(true);
				dispose();
			}
		});
		lblSignInAsAdmin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				new Login().createUI();
				dispose();
			}
		});
		txtEmail.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn();
				}
			}
		});
		txtPassword.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn();
				}
			}
		});
		
		// Pass all components to the animator to hide them before showing the frame 
		JComponent[] components = {lblSignIn, lblEmail, txtEmail, lblPassword, txtPassword, btnSignIn, lblSignUp, lblSignInAsAdmin};
		Animator animator = new Animator(pnl, components, 2);
		
		// Add the panel to the frame and show the frame
		add(pnl);
		setVisible(true);
		
		// Start Animation
		animator.showComponents();
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnSignIn) {
			signIn();
		}
	}
	
	public void signIn() {
		if(txtEmail.getText().isEmpty())
			JOptionPane.showInternalMessageDialog(this, "Please enter your email", "Missing email", JOptionPane.WARNING_MESSAGE);
		try {
			User user = UserManager.getLoginedUser(txtEmail.getText(), new String(txtPassword.getPassword()));
			
			if(user == null) {
				JOptionPane.showMessageDialog(this, "This email and password combination does not exist!", "Incorrect Credentials", JOptionPane.WARNING_MESSAGE);
			} else {
				new StudentMainFrame(user);
				dispose();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "SQLException:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	public static void main(String[] args) {
		new StudentLogin();
	}
}
