package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import DB.Tables.EnrollManager;
import DB.Tables.UserManager;
import DB.beans.User;
import GUI.Util.ThemeSetter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;

public class RegisterGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField email;
	private JPasswordField password_1;
	private JPasswordField password_repeat;
	private JTextField firstName;
	private JTextField lastName;
	private JButton Register;
	private JButton btnBack;
	private User users;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		Font myFont1 = new Font("Serif", Font.BOLD, 18);
		Font myFont2 = new Font("Serif", Font.BOLD, 25);
		
		
		Register = new JButton("REGISTER");
		ThemeSetter.setToTheme(Register);
		Register.setLocation(234, 399);
		//Register.setBounds(234, 399, 140, 40);
		contentPane.add(Register);
		contentPane.setBackground( Color.WHITE );

		
		JLabel lblRegisterForAn = new JLabel("Register for an Account");
		lblRegisterForAn.setForeground(SystemColor.textHighlight);
		lblRegisterForAn.setFont(myFont2);
		lblRegisterForAn.setBounds(151, 22, 340, 30);
		contentPane.add(lblRegisterForAn);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(myFont1);
		lblEmail.setBounds(120, 93, 66, 20);
		contentPane.add(lblEmail);
		
		email = new JTextField();
		email.setBounds(298, 92, 160, 25);
		contentPane.add(email);
		email.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setFont(myFont1);
		lblNewLabel.setBounds(120, 147, 95, 20);
		contentPane.add(lblNewLabel);
		
		password_1 = new JPasswordField();
		password_1.setBounds(298, 144,  160, 25);
		contentPane.add(password_1);
		password_1.setColumns(10);
		
		JLabel lblReEnterPassword = new JLabel("Re enter Password:");
		lblReEnterPassword.setFont(myFont1);
		lblReEnterPassword.setBounds(120, 206, 160, 20);
		contentPane.add(lblReEnterPassword);
		
		password_repeat = new JPasswordField();
		password_repeat.setBounds(298, 201, 160, 25);
		contentPane.add(password_repeat);
		password_repeat.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setFont(myFont1);
		lblFirstName.setBounds(120, 266, 95, 20);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setFont(myFont1);
		lblLastName.setBounds(120, 326, 95, 20);
		contentPane.add(lblLastName);
		
		firstName = new JTextField();
		firstName.setBounds(298, 263, 160, 25);
		contentPane.add(firstName);
		firstName.setColumns(10);
		
		lastName = new JTextField();
		lastName.setBounds(298, 323, 160, 25);
		contentPane.add(lastName);
		lastName.setColumns(10);
		
		btnBack = new JButton("Back");
		//btnBack.setBounds(0, 0, 102, 30);
		btnBack.setLocation(0, 0);
		
		ThemeSetter.setToGreyButton(btnBack);
		contentPane.add(btnBack);
		
		
		Register.addActionListener(this);
		btnBack.addActionListener(this);
		
		//JTextPane txtpnResult = new JTextPane();
		//txtpnResult.setText("Result");
		//txtpnResult.setBounds(662, 184, 349, 242);
		//txtpnResult.setEditable(false);
		//contentPane.add(txtpnResult);
	}
	

	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnBack)
		{
				new StudentLogin();
				this.dispose();

		}
		
		if(e.getSource()== Register){
			if(!(password_1.getText().equals(password_repeat.getText()))){
				
				String message ="The passwords Do not match \n Please Enter again!!!";
				JOptionPane.showMessageDialog(this, message ,"ERROR" ,JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				
				String emailEntered=email.getText();
				String passwordEntered= password_1.getText();
				UserManager.createUser(emailEntered, passwordEntered, firstName.getText(), lastName.getText());
				
				
				String message= " Your Account has been registered \n Email: "+ emailEntered + " \n Name: "+ firstName.getText() +" "+ lastName.getText() +" \n Welcome to GOOD QUESTION \n HAPPY EXAM TAKING!!!";
				JOptionPane.showMessageDialog(this, message ,"SUCCESS" ,JOptionPane.PLAIN_MESSAGE);
				
			} catch (Exception e1) {

				String message= "There was an ERROR in Registering for an account. Please Make sure of all fields, or contact system administrator if problem persists";
				JOptionPane.showMessageDialog(this, message ,"ERROR" ,JOptionPane.ERROR_MESSAGE);
			
			}
		}
		
	}
}
