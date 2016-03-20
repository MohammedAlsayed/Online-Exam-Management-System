package GUI;


import DB.Tables.*;
import DB.beans.*;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;

import DB.Tables.ExamManager;
import GUI.Util.ThemeSetter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewGradeReport extends JFrame implements ActionListener {

	JPanel contentPane;
	private final JPanel titlePanel = new JPanel();
	JButton viewGradesButton = new JButton("View Grades");
	JButton goBackButton = new JButton("Go Back");
	JComboBox<String> selectExamDropDown = new JComboBox<String>();
	int userNumber = 10007;
	ArrayList<Exam> userExamList;
	JTable table = new JTable();
	JLabel percentageCorrectLabel = new JLabel("Percentage Correct:");
	JLabel wrongAnswersLabel = new JLabel("Wrong Answeres:");
	JLabel percentageCorrect = new JLabel("0");
	JLabel wrongAnswers = new JLabel("0");
	JLabel gradeLabel = new JLabel("Grade:");
	JLabel grade = new JLabel("0");
	JLabel responsesLabel = new JLabel("Your Answers:");
	private final JScrollPane scrollPane;

	
	


	
	public ViewGradeReport(int userNumber) {
		
	
		
		// -------------- GUI Components -----------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		getContentPane().setLayout(null);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBounds(0, 0, 681, 470);
		contentPane.add(contentPanel);
		contentPanel.setLayout(null);
		titlePanel.setBackground(Color.LIGHT_GRAY);
		titlePanel.setBounds(0, 0, 681, 31);
		contentPanel.add(titlePanel);
		
		JLabel titleLabel = new JLabel("View Grade Report");
		titleLabel.setForeground(new Color(0, 0, 0));
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 23));
		titlePanel.add(titleLabel);
		
		JLabel slelectExamLabel = new JLabel("Select Exam:");
		slelectExamLabel.setFont(new Font("Calibri", Font.BOLD, 21));
		slelectExamLabel.setBounds(10, 42, 112, 33);
		contentPanel.add(slelectExamLabel);
		
		selectExamDropDown.setBounds(132, 42, 215, 33);
		contentPanel.add(selectExamDropDown);
		
		viewGradesButton.addActionListener(this);
		ThemeSetter.setToTheme(viewGradesButton);
		viewGradesButton.setBounds(371, 39, 123, 36);
		contentPanel.add(viewGradesButton);
		
		
		percentageCorrectLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		percentageCorrectLabel.setBounds(324, 132, 207, 30);
		contentPanel.add(percentageCorrectLabel);
		
		wrongAnswersLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		wrongAnswersLabel.setBounds(10, 126, 200, 36);
		contentPanel.add(wrongAnswersLabel);
		
		percentageCorrect.setFont(new Font("Calibri", Font.BOLD, 25));
		percentageCorrect.setBounds(569, 132, 74, 30);
		contentPanel.add(percentageCorrect);
		
		wrongAnswers.setFont(new Font("Calibri", Font.BOLD, 25));
		wrongAnswers.setBounds(229, 129, 45, 33);
		contentPanel.add(wrongAnswers);
		
		
		gradeLabel.setFont(new Font("Calibri", Font.BOLD, 24));
		gradeLabel.setBounds(10, 79, 74, 36);
		contentPanel.add(gradeLabel);
		
		grade.setFont(new Font("Calibri", Font.BOLD, 25));
		grade.setBounds(213, 81, 65, 34);
		contentPanel.add(grade);
		
		responsesLabel.setFont(new Font("Calibri", Font.BOLD, 19));
		responsesLabel.setBounds(10, 167, 123, 14);
		contentPanel.add(responsesLabel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 183, 662, 265);
		
		contentPanel.add(scrollPane);
		scrollPane.setViewportView(table);
		table.setGridColor(Color.black);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		
		ThemeSetter.setToGreyButton(goBackButton);
		goBackButton.setBounds(534, 39, 123, 36);
		contentPanel.add(goBackButton);
		goBackButton.addActionListener(this);
		
		
		setResizable(false);
		setLocationRelativeTo(null);
		//----------- END OF GUI -----------------------
		
		// saving the user ID
		this.userNumber = userNumber;
		
		// get all user exams to put them in the drop down
		try {
			 userExamList = ExamManager.getExamByStudent(userNumber);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		for(int i = 0; i<userExamList.size(); i++){
			selectExamDropDown.addItem(userExamList.get(i).getETitle());
		}
		
		
		// hiding the contents that should be visible only after selecting an exam
		 table.setVisible(false);
		 percentageCorrectLabel.setVisible(false);
		 wrongAnswersLabel.setVisible(false);
		 percentageCorrect.setVisible(false);
		 wrongAnswers.setVisible(false);
		 gradeLabel.setVisible(false);
		 grade.setVisible(false);
		 responsesLabel.setVisible(false);
		 scrollPane.setVisible(false);
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == viewGradesButton){
			
			// get the selected exam from the DB
			String selection = (String) selectExamDropDown.getSelectedItem();
			Exam selectedExam = null;
			try {
				selectedExam = ExamManager.getExam(selection);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			// check the selected exam for expiration 
			
			boolean finished = false;
			
			try {
				finished = EnrollManager.CheckTimeExpired(userNumber, selectedExam.getENO());
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
			
			
			if(!finished){
				JOptionPane.showMessageDialog(this, "The exam has not been finished yet.", "Error", JOptionPane.ERROR_MESSAGE);
				
			}
			else{// if it is finished make all unanswered questions N = not answered and view the grades
					try {
						UserResponseManager.submitUnansweredQuestions(userNumber, selectedExam.getENO());
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}

				
				// print the responses
				
				
				// get the user wrong responses in the exam
				ArrayList<UserResponse> wrongResponseList = null;
				
				try {
					if (selectedExam != null) {
						
						wrongResponseList = UserResponseManager.getAllWrongResponses(userNumber, selectedExam.getENO());
						
						if(wrongResponseList == null){
							wrongResponseList = new ArrayList<UserResponse>();
						}
					
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				// add the responses to the scroll panels
				
				// get all questions in that exam
				ArrayList<Question> questionList = null;
				try {
					questionList= QuestionManager.getQustions(selectedExam.getENO());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				// the table columns:
				String [] columnNames = {"No.", "Question", "Your Answer", "Correct Answer"};
				
				
				
				// making a 2d array to add it to the table
				ArrayList<String []> data = new ArrayList<String[]>();	
				
				
				for(int i = 0; i < questionList.size(); i++){
					String [] row = new String [4];
					Question question = questionList.get(i);
					
					row[0] = String.valueOf(question.getQNO());
					row[1] = question.getQText();
					row[3] = question.getCorrectAnswer();
				
					String answer = "";
					
					// checking if the answer was wrong. if it is, the student answer is added. 
					//If the answer is not in the wrong list (it is correct), the correct answer will be added.
	
					for(int j = 0; j<wrongResponseList.size(); j++){
						if(question.getQNO() == wrongResponseList.get(j).getQuestionNumber()){
							answer = wrongResponseList.get(j).getResponse();
							break;
						}
					}
					if(answer.equals("")){
						row[2] = question.getCorrectAnswer();
					}
					else{
						if(answer.equalsIgnoreCase("N")){
							answer = "Not Answered";
						}
						row[2] = answer;
					}
				
				data.add(row);
				}
				
				
				
				// putting the data in a 2d
				String[][] data2d = new String[data.size()][4];
				
				for(int i = 0; i<data.size(); i++){
					for (int j = 0; j < 4; j++){
						data2d[i][j] = data.get(i)[j];
					}
				}
				
				
				DefaultTableModel dtm = new DefaultTableModel(data2d, columnNames);
				
				table.setModel(dtm);
				
				// styling the table
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				table.getColumnModel().getColumn(0).setPreferredWidth(30);
				table.getColumnModel().getColumn(1).setPreferredWidth(429);
				table.getColumnModel().getColumn(2).setPreferredWidth(100);
				table.getColumnModel().getColumn(3).setPreferredWidth(100);
				table.setEnabled(false);
				//table.doLayout();
				
				
				// update the grade, correct answers, wrong answers
				
				int wrongAnswersNumber = wrongResponseList.size();
				int allQuestionsNumber = questionList.size();
				int correctAnswers = allQuestionsNumber - wrongAnswersNumber;
				
				int percentageCorrectNumber = (int) ((correctAnswers * 1.0 / allQuestionsNumber * 1.0) * 100.0);
				
				
				wrongAnswers.setText(String.valueOf(wrongAnswersNumber));
				percentageCorrect.setText(String.valueOf(percentageCorrectNumber) + "%");
				grade.setText(correctAnswers + " / " + allQuestionsNumber);
				
				
				// set contents to visible
				
				 table.setVisible(true);
				 percentageCorrectLabel.setVisible(true);
				 wrongAnswersLabel.setVisible(true);
				 percentageCorrect.setVisible(true);
				 wrongAnswers.setVisible(true);
				 gradeLabel.setVisible(true);
				 grade.setVisible(true);
				 responsesLabel.setVisible(true);
				 scrollPane.setVisible(true);

			}
		}
		else if(e.getSource() == goBackButton){
			
			
			// get the user to pass it to the main frame
			
			User user = null;
			
			try {
				user = UserManager.getUserById(userNumber);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			new StudentMainFrame(user);
			
			
			this.dispose();
		}
	}
}
