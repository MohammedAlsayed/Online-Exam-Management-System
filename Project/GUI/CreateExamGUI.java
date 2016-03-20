package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import DB.Tables.ExamManager;

public class CreateExamGUI extends JFrame {
	private Integer integerTimeAllowed;
	JLabel examNumLabel;
	private JPanel contentPane;
	private JTextField examTitle;
	private JTextField timeAllowed;
	private int ENO = 0;
	private JButton submitBtn;

	// Constructor for creating a new exam where no ENO will be passed.
	public CreateExamGUI() {
		super("Create Exam");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 346, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo (null);
		setResizable(false);
		
		creatingComponents();
	}
	// Constructor that has ENO for updating an existing exam
	public CreateExamGUI(int ENO) {
		super("Edit Exam");
		this.ENO = ENO;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 346, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		creatingComponents();
		setLocationRelativeTo (null);
		setResizable(false);
	}
	/**
	 * Method initializes and adds actions listeners to components.  
	 *
	 **/
	private void creatingComponents(){		
		JLabel labelExam = new JLabel("Exam");
		labelExam.setBounds(10, 11, 83, 14);
		contentPane.add(labelExam);
		
		JLabel labelTitle = new JLabel("Exam Title");
		labelTitle.setBounds(10, 46, 83, 17);
		contentPane.add(labelTitle);
		
		examTitle = new JTextField();
		examTitle.setColumns(10);
		examTitle.setBounds(114, 43, 147, 20);
		contentPane.add(examTitle);
		
		timeAllowed = new JTextField();
		timeAllowed.setColumns(10);
		timeAllowed.setBounds(114, 81, 147, 20);
		contentPane.add(timeAllowed);
		
		JLabel labelTimeAllowed = new JLabel("Time Allowed");
		labelTimeAllowed.setBounds(10, 84, 94, 17);
		contentPane.add(labelTimeAllowed);
		
		JLabel labelMinute = new JLabel("minutes");
		labelMinute.setBounds(271, 84, 49, 14);
		contentPane.add(labelMinute);
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(127, 142, 99, 23);
		contentPane.add(submitBtn);
		submitBtn.addActionListener(new submitBtnActionListener());
		
		JLabel labelENO = new JLabel(ENO+"");
		labelENO.setBounds(117, 11, 75, 14);
		contentPane.add(labelENO);
	}
	
	
	class submitBtnActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// ENO == 0 because no exam has been selected. Becuase we are using the no parameter constructor.
			if (ENO == 0) 
			{
				boolean examInserted;
				try {
					integerTimeAllowed = new Integer(timeAllowed.getText());
					examInserted = ExamManager.createExam(examTitle.getText(),
							integerTimeAllowed);
					if (examInserted) {
						JOptionPane.showMessageDialog(CreateExamGUI.this,
								"Exam is Inserted Successfully");
						timeAllowed.setText("");
						examTitle.setText("");
						ExamGUI.updateList();
					} else {
						JOptionPane.showMessageDialog(CreateExamGUI.this,
								"Sorry, No Exam was Inserted");
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(CreateExamGUI.this, "Error",
							e.getMessage(), JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(CreateExamGUI.this,
							"Please Check the Field Values", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			// updating an exam
			else{
				boolean examUpdated;
				try{
				integerTimeAllowed = new Integer(timeAllowed.getText());
				examUpdated = ExamManager.updateExam(examTitle.getText(),
						integerTimeAllowed, ENO);
				if(examUpdated){
					JOptionPane.showMessageDialog(CreateExamGUI.this,
							"Exam is Updated Successfully");
					ExamGUI.examList.removeAllElements();
					ExamGUI.addExamsIntoList();
				}
				}catch(SQLException e){
					JOptionPane.showMessageDialog(CreateExamGUI.this, "Error",
							e.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
