package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import DB.Tables.EnrollManager;
import DB.Tables.ExamManager;
import DB.Tables.UserManager;
import DB.beans.Enroll;
import DB.beans.Exam;
import DB.beans.User;
import GUI.Util.ThemeSetter;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class TakeExamGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnBegin;
	private int uno;
	private String ex;
	private JButton btnBack;
	private User users;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TakeExamGUI frame = new TakeExamGUI(10000);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public TakeExamGUI(int uno){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setBackground(Color.WHITE);
		
		JLabel lblChooseTheExam = new JLabel("Choose the exam to begin");
		lblChooseTheExam.setForeground(SystemColor.textHighlight);
		lblChooseTheExam.setFont(new Font("Serif", Font.BOLD, 25));
		lblChooseTheExam.setBounds(147, 43, 325, 35);
		contentPane.add(lblChooseTheExam);
		
		btnBack = new JButton("Back");
		ThemeSetter.setToGreyButton(btnBack);
		btnBack.setLocation(0, 0);
		//btnBack.setBounds(0, 0, 113, 35);
		contentPane.add(btnBack);
		
		JList<String> list = new JList<>();
		list.setBounds(115, 68, 483, 284);
		contentPane.add(list);
		
		btnBegin = new JButton("Begin");
		ThemeSetter.setToTheme(btnBegin);
		btnBegin.setLocation(220, 418);	
		//btnBegin.setBounds(220, 418, 113, 35);
		contentPane.add(btnBegin);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(70, 100, 450, 300);
		
		contentPane.add(scrollPane);
		btnBegin.addActionListener(this);
		btnBack.addActionListener(this);
		
		//for testing purposes
		//int uno= 10005;
		this.uno= uno;
		
		
		
		
		ArrayList<Enroll> enrolledExams = new ArrayList<>();
		
// *************************************SQL*****************************	

		try {
			//System.out.print("test2");
			enrolledExams = EnrollManager.getAllEnrolledExams(uno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(this, "SQLException:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
//*********************************SQL*************************************
		
		DefaultListModel<String> DLM = new DefaultListModel<>();
		
		int i=0;
		int size = enrolledExams.size();
		
		while(i<size){
		
			try {
				DLM.add(i, (ExamManager.getExam((enrolledExams.get(i).getExamNo())).getETitle()));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "SQLException:\n" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			//System.out.println(enrolledExams.get(i).toString());
			//System.out.println("test");
			i++;
		}
		
		
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
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "SQLException:\n" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			this.dispose();
		}
		
		if(e.getSource() == btnBegin)
		{
			try {
				
				Exam examination= ExamManager.getExam(ex);
				int eno= examination.getENO();
				// user number is in uno
				
				Enroll enrolled =EnrollManager.getEnrolledObject(uno, eno);
				
				// Mussad call your Take exam constructor here and continue from the choose Exam Screen
				// Here the Enroll object is enrolled ready, pass it to your GUI.
				
				//System.out.println(enrolled.toString());
				
				new TakeExamGUI2(enrolled).setVisible(true);;
				this.dispose();
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "SQLException:\n" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		
	}
}
