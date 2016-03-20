package GUI;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JRadioButton;
import javax.swing.JButton;

import DB.Tables.*;
import DB.beans.*;
import GUI.Util.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class TakeExamGUI2 extends JFrame implements ActionListener{

	JPanel contentPane;
	final JPanel panel_1 = new JPanel();
	JButton submitButton = new JButton("Submit");
	JButton cancelButton = new JButton("Cancel");
	Enroll enroll = null;								
	Exam exam = null;									
	User user = null;
	int previousQuestion = 0;
	Question question = null;
	ArrayList<AnswerOption> answerOptions = null;
	ArrayList<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
	ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton optionA = new JRadioButton("New radio button");
	JRadioButton optionB = new JRadioButton("New radio button");
	JRadioButton optionC = new JRadioButton("New radio button");
	JRadioButton optionD = new JRadioButton("New radio button");
	JRadioButton optionE = new JRadioButton("New radio button");
	int numberOfQuestions = 0;



	
	public TakeExamGUI2(Enroll enroll) {
		
		boolean terminateThisFrame = false;
		
		
		// saving the enroll
		this.enroll = enroll;
		
		
		// getting the user and exam
		
		try {
			exam = ExamManager.getExam(enroll.getExamNo());
			user = UserManager.getUserById(enroll.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// getting the previous question number
		
		try {
			previousQuestion = UserResponseManager.getMaximumAnsweredQuestion(user.getId(), exam.getENO());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// checking if all questions have been answered
		
		
		try {
			numberOfQuestions = QuestionManager.getNumberOfQuestions(exam.getENO());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(numberOfQuestions == 0){
			JOptionPane.showMessageDialog(this, "Error, check the exam with the system admin", "Error", JOptionPane.ERROR_MESSAGE);
			terminateThisFrame = true;
			this.dispose();
		}
		
		
		if(previousQuestion == numberOfQuestions){
			JOptionPane.showMessageDialog(this, "The exam is completed already, you can see your grades", "Error", JOptionPane.ERROR_MESSAGE);
			terminateThisFrame = true;
			this.dispose();
		}
		
		// getting the question to be shown
		try {
			question = QuestionManager.getQustion(exam.getENO(),
					previousQuestion + 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// getting the answer options for the question
		try {
			answerOptions = AnswerOptionManager.getAnswerOptions(exam.getENO(),
					question.getQNO());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// check the time. if it passed, submit the rest as N
		boolean expired = false;

		try {
			EnrollManager.CheckTimeExpired(user.getId(), exam.getENO());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (expired) {
			
			try {
				UserResponseManager.submitUnansweredQuestions(user.getId(), exam.getENO());
			} catch (SQLException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(this, "The exam has expired, you can now only check your grades", "Exam Expired", JOptionPane.ERROR_MESSAGE);

			// terminating the frame.
			terminateThisFrame = true;
			new StudentMainFrame(user);
			this.dispose();
		}

		if(!terminateThisFrame){ // if the frame is terminated, the rest of the code will not be executed.
		
			// --------------- Start OF GUI -----------------
			setTitle("Good Question");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 506, 347);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setResizable(false);
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(0, 0, 490, 308);
			contentPane.add(panel);
			panel.setLayout(null);
			panel_1.setBackground(Color.GRAY);
			panel_1.setBounds(0, 0, 490, 31);
			panel.add(panel_1);
			
			JLabel examTitle = new JLabel("Exam Title");
			ThemeSetter.setToTheme(examTitle);
			panel_1.add(examTitle);
			
			
			optionA.setFont(new Font("Calibri", Font.BOLD, 17));
			optionA.setBounds(12, 91, 478, 23);
			panel.add(optionA);
			
			optionB.setFont(new Font("Calibri", Font.BOLD, 17));
			optionB.setBounds(12, 127, 478, 23);
			panel.add(optionB);
			
			optionC.setFont(new Font("Calibri", Font.BOLD, 17));
			optionC.setBounds(12, 166, 478, 23);
			panel.add(optionC);
			
			optionD.setFont(new Font("Calibri", Font.BOLD, 17));
			optionD.setBounds(12, 202, 478, 23);
			panel.add(optionD);
			
			optionE.setFont(new Font("Calibri", Font.BOLD, 17));
			optionE.setBounds(12, 239, 478, 23);
			panel.add(optionE);
			
			JLabel questionText = new JLabel("QuestionText");
			questionText.setFont(new Font("Calibri", Font.BOLD, 17));
			questionText.setBounds(10, 31, 480, 53);
			panel.add(questionText);
			
			
			cancelButton.setFont(new Font("Calibri", Font.BOLD, 20));
			cancelButton.setBounds(12, 269, 131, 28);
			panel.add(cancelButton);
			cancelButton.addActionListener(this);

			submitButton.setFont(new Font("Calibri", Font.BOLD, 20));

			submitButton.setBounds(349, 269, 131, 31);
			panel.add(submitButton);
			submitButton.addActionListener(this);

			ThemeSetter.setToTheme(cancelButton);
			ThemeSetter.setToTheme(submitButton);

			buttonGroup.add(optionA);
			buttonGroup.add(optionB);
			buttonGroup.add(optionC);
			buttonGroup.add(optionD);
			buttonGroup.add(optionE);

			// setting the radio buttons invisible until we know how many answer options are there.
			optionA.setVisible(false);
			optionB.setVisible(false);
			optionC.setVisible(false);
			optionD.setVisible(false);
			optionE.setVisible(false);

			setLocationRelativeTo(null);

			// --------------- END OF GUI -----------------

			// adding the radio buttons to the list
			radioButtons.add(optionA);
			radioButtons.add(optionB);
			radioButtons.add(optionC);
			radioButtons.add(optionD);
			radioButtons.add(optionE);

			examTitle.setText(exam.getETitle());
			questionText.setText(question.getQText());

			// setting the radio buttons to the answer options

			for (int i = 0; i < answerOptions.size(); i++) {

				AnswerOption option = answerOptions.get(i);
				JRadioButton button = radioButtons.get(i);

				button.setText(option.getONO() + ": " + option.getOptionText());
				button.setVisible(true);

			}
		}
	}

	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == submitButton){
		
			// checking if the time expired while solving the question
			
			boolean expired = false;
			
			try {
				expired = EnrollManager.CheckTimeExpired(user.getId(), exam.getENO());
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			if(expired){
				try {
					UserResponseManager.submitUnansweredQuestions(user.getId(), exam.getENO());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(this, "Time expired, you can now check your grade.", "Time Expired", JOptionPane.ERROR_MESSAGE);
				new StudentMainFrame(user);
				this.dispose();
				
				
			}
			
			
			
			String selection = "";
			
			if(optionA.isSelected()){
				selection = "A";
			}
			else if(optionB.isSelected()){
				selection = "B";
			}else if(optionC.isSelected()){
				selection = "C";
			}else if(optionD.isSelected()){
				selection = "D";
			}else if(optionE.isSelected()){
				selection = "E";
			}else{
				selection = "N";
			}
			
			// adding the response to the DB
			try {
				UserResponseManager.addResponse(user.getId(), exam.getENO(), answerOptions.get(1).getQNO(), selection);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			
			// check if it is the last question
			if(question.getQNO() != numberOfQuestions){
				
				// starting the new frame with the next question
				new TakeExamGUI2(enroll).setVisible(true);
				
				// closing this frame
				this.dispose();				
				
			}else{
				
				// showing a confirmation message and closing the frame
				JOptionPane.showMessageDialog(this, "All questions have been answered, you can now view your grades", "Exam Completed", JOptionPane.INFORMATION_MESSAGE);
				
				try {
					EnrollManager.submit(user.getId(), exam.getENO());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				new StudentMainFrame(user);
				this.dispose();	
				
			}		
			
		}
		
		else if(e.getSource() == cancelButton){
			// only disposing the frame as no changes have occurred.
			JOptionPane.showMessageDialog(this, "You have cancelled the question, the exam is still running, you can comeback later.", "Question Cancelled", JOptionPane.INFORMATION_MESSAGE);
			new StudentMainFrame(user);
			this.dispose();
		}
		
	}
}
