package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import DB.Tables.ExamManager;
import DB.Tables.QuestionManager;
import DB.beans.Question;


/**
 * GUI of the question option interface
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */


public class QuestionGUI extends JFrame {

	private JPanel contentPane;
	private JTextField qNOField;
	private JTextField qTextField;
	private JTextField correctAnswerField;
	JList list;
	static DefaultListModel questionList = new DefaultListModel();
	static ArrayList<Question> questions = null;
	private JButton btnAddAnsweOption;
	private JButton viewAnswerOptionBtn;
	private static int ENO;
	private static int QNO;
	private static int index;
	
	/**
	 * Create the frame.
	 */
	public QuestionGUI(int ENO) {
		super("Question");
		this.ENO = ENO;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 345);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setLocationRelativeTo (null);
		setResizable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 464, 146);
		contentPane.add(scrollPane);
		
		// adding the question list into the list by the constructor
		list = new JList(questionList);
		scrollPane.setViewportView(list);
		
		// adding selection listener to the list instance 
		// to find the index of the selected question
		list.addListSelectionListener(new AddAnswerOptionActionListener());
		
		JLabel lblQno = new JLabel("Question#");
		lblQno.setBounds(32, 168, 65, 14);
		contentPane.add(lblQno);
		
		qNOField = new JTextField();
		qNOField.setBounds(117, 168, 28, 20);
		contentPane.add(qNOField);
		qNOField.setColumns(10);
		
		JLabel lblQtext = new JLabel("Question Text");
		lblQtext.setBounds(20, 199, 86, 14);
		contentPane.add(lblQtext);
		
		qTextField = new JTextField();
		qTextField.setColumns(10);
		qTextField.setBounds(117, 199, 357, 20);
		contentPane.add(qTextField);
		
		correctAnswerField = new JTextField();
		correctAnswerField.setColumns(10);
		correctAnswerField.setBounds(117, 229, 28, 20);
		contentPane.add(correctAnswerField);
		
		JLabel lblCorrectAnswer = new JLabel("Correct Answer");
		lblCorrectAnswer.setBounds(10, 232, 96, 14);
		contentPane.add(lblCorrectAnswer);
		
		JButton btnSubmit = new JButton("Add Question");
		btnSubmit.setBounds(350, 273, 124, 23);
		contentPane.add(btnSubmit);
		btnSubmit.addActionListener(new SubmitButtonActionListner());
		
		btnAddAnsweOption = new JButton("Add Answer Option");
		btnAddAnsweOption.addActionListener(new AddAnswerOptionActionListener());
		btnAddAnsweOption.setBounds(177, 273, 163, 23);
		contentPane.add(btnAddAnsweOption);
		
		viewAnswerOptionBtn = new JButton("View Answer Options");
		viewAnswerOptionBtn.setBounds(20, 273, 140, 23);
		contentPane.add(viewAnswerOptionBtn);
		viewAnswerOptionBtn.addActionListener(new ViewAnswerOptionsBtnActionListener());
		
		//so questions of the previous exam is removed
		questionList.removeAllElements();
		
			addQuestionsIntoList();
		
	}
	
	// creating an inner action listener class for submit button
	class SubmitButtonActionListner implements ActionListener{

		public void actionPerformed(ActionEvent ae) {
			boolean flag;
			try {
				Integer qno = new Integer(qNOField.getText());
				// flag of row insertion
				flag = QuestionManager.addQuestion(ENO, qno, qTextField.getText(), correctAnswerField.getText().toUpperCase());
				if(flag){
					//Clearing the fields
					qNOField.setText("");
					qTextField.setText("");
					correctAnswerField.setText("");
					updateList();
				}
				else
					JOptionPane.showMessageDialog(QuestionGUI.this,"Oops!, nothing inserted");
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(QuestionGUI.this, e.getMessage());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(QuestionGUI.this,
						"Please Enter Correct Values");
			}
		}
	}
	// creating an inner action listener class for add answer option button
	class AddAnswerOptionActionListener implements ActionListener, ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent se) {
			index = list.getSelectedIndex();
			if(index != -1)
				QNO = questions.get(index).getQNO();
			System.out.println("1: "+ENO+"\n  "+QNO);
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(QNO != 0)
				new AnswerOptionGUI(ENO, QNO).setVisible(true);
			else
				JOptionPane.showMessageDialog(QuestionGUI.this, "Please Select a Question");
		}
	}
	/**
	 * adds all questions into the list 
	 */
	private static void addQuestionsIntoList(){
		try {
		//	System.out.println("ENO: "+ENO);
		 questions = QuestionManager.getQustions(ENO);
		 for (int i = 0; i < questions.size(); i++) {
			 questionList.addElement(questions.get(i).toString());	
		 }
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	/**
	 * Updates the question list with a new question.
	 */
	public static void updateList(){
		try {
			questions = QuestionManager.getQustions(ENO);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
/*		System.out.println("size: "+questions.get(questions.size()-1));
		System.out.println("1: "+questions.get(1));
		System.out.println("index: "+ index);*/
		int index = questionList.size();
		questionList.insertElementAt(questions.get(questions.size()-1), index);
	}
	class ViewAnswerOptionsBtnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(QNO != 0)
				new ViewAnswerOptionsGUI(ENO,QNO).setVisible(true);
			else{
				JOptionPane.showMessageDialog(QuestionGUI.this, "Please Select a Question");
			}
		}
		
	}
}
