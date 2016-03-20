package GUI;
import DB.beans.*;
import GUI.Util.ThemeSetter;
import DB.Tables.*;
import java.util.*;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.SystemColor;

public class EnrollExam extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnEnroll;
	private Exam ex= new Exam();
	private int uno; // This will be given by the constructor only for testing purposes
	private Enroll enrolls ;
	private JButton btnBack;
	private User users;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnrollExam frame = new EnrollExam(10000);
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
	public EnrollExam(int uno) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		btnEnroll = new JButton("ENROLL");
		//btnEnroll.setBounds(218, 420, 140, 40);
		btnEnroll.setLocation(218, 420);
		contentPane.add(btnEnroll);
		
		JLabel lblEnrollInAn = new JLabel("Enroll in an Exam");
		lblEnrollInAn.setForeground(SystemColor.textHighlight);
		lblEnrollInAn.setBounds(200, 19, 200, 70);
		lblEnrollInAn.setFont(new Font("Serif", Font.BOLD, 25));
		contentPane.add(lblEnrollInAn);
		
		btnBack = new JButton("Back");
		//btnBack.setBounds(0, 0, 111, 34);
		btnBack.setLocation(0, 0);
		contentPane.add(btnBack);
		
		ThemeSetter.setToGreyButton(btnBack);
		ThemeSetter.setToTheme(btnEnroll);
		
		btnEnroll.addActionListener(this);
		btnBack.addActionListener(this);
		
		// from the constructor
		this.uno = uno;
		
		
		
		ArrayList<Exam> exams= new ArrayList<Exam>();
		
		
		/*	
	
		Exam e1 = new Exam(22, "jljg", 50);
		Exam e2 = new Exam(234, "va", 5);
		Exam e3 = new Exam(33, "afaf", 990);
		Exam e4 = new Exam(4, "jlsfsjg", 90);
		
		exams.add(e1);
		exams.add(e2);
		exams.add(e3);
		exams.add(e4);
		
		*/
		
		
		
// *************************************SQL*****************************	
		
		try {
			//System.out.print("test2");
			exams = ExamManager.getAllExams();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "SQLException:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		}
		
//*********************************SQL*************************************
 
 
		
		DefaultListModel<Exam> DLM = new DefaultListModel<>();
		
		int i=0;
		int size = exams.size();
		
		while(i<size){
		
			DLM.add(i, exams.get(i));
			//System.out.println(exams.get(i).toString());
			//System.out.println("test");
			i++;
		}
		

		
		
		JList<Exam> list = new JList<>();
		//list.setBounds(177, 100, 694, 436);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(70, 100, 450, 300);
		
		contentPane.add(scrollPane);
		
		list.setModel(DLM);
		

		
		


		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				// TODO Auto-generated method stub
				if (evt.getValueIsAdjusting())
				 return;
				ex=list.getSelectedValue();				
			}
		});
		
	//	System.out.println(ex);

		

	}
	
	private void back() throws SQLException
	{
		users= UserManager.getUserById(uno);
		new StudentMainFrame(users);
		return;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnBack)
		{
			try {
				back();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "SQLException:\n" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				//e1.printStackTrace();
			}
			this.dispose();
		}
		
		if(e.getSource() == btnEnroll)
		{
			System.out.println(ex);
			Date date= new Date();
			Timestamp timeNow =new Timestamp(date.getTime());
			
//**********************************SQL************************************

			try {
				EnrollManager.enrollInExam(uno, ex.getENO(), timeNow, null);
				String message= " You have been enrolled in the Exam: "+ ex.getETitle()+ " \n Exam Number: "+ ex.getENO()+" \n The Time Alloted for the Exam is: "+ ex.getTimeAllowed() + "\n GOOD LUCK!!!";
				JOptionPane.showMessageDialog(this, message ,"SUCCESS" ,JOptionPane.PLAIN_MESSAGE);
				
			} catch (Exception e1) {

				String message= "There was an error Enrolling you in the Exam.. Please Make sure you meet the conditions Required, or contact system administrator if problem persists";
				JOptionPane.showMessageDialog(this, message ,"ERROR" ,JOptionPane.ERROR_MESSAGE);
				
			}
			
	
//*************************************SQL**********************************

			
			
			
		}
		
		
	}



}
