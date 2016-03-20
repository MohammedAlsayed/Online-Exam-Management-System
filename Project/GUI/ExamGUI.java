package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;

import DB.Tables.ExamManager;
import DB.beans.Exam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

/**
 * GUI of the exam option interface
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */


public class ExamGUI extends JFrame{
	private JButton btnAddQuestion;
	private JButton btnCreateExam;
	private JPanel contentPane;
	static DefaultListModel examList = new DefaultListModel();
	static JList list_1;
	GroupLayout gl_contentPane;
	JScrollPane scrollPane;
	static ArrayList<Exam> exams = null;
	int selectedIndex;
	int selectedENO;
	private JButton btnEditExam;
	private JButton btnDeleteExam;
	
	JLabel examNumLabel;

	
	/*	
	public static void main(String[] args) {
		 try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch(Exception e) {
		        System.out.println("Error setting native LAF: " + e);
		    }		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamGUI frame = new ExamGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public ExamGUI() {
		super("Exam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		scrollPane = new JScrollPane();
		list_1 = new JList(examList);
		setLocationRelativeTo (null);
		setResizable(false);
		btnCreateExam = new JButton("Create Exam");
		btnCreateExam.addActionListener(new CreateExamActionListener());
	
		btnAddQuestion = new JButton("Add Question");
		btnAddQuestion.addActionListener(new AddQuestionActionListener());
		
		list_1.addListSelectionListener(new AddQuestionActionListener());
	//	list_1.addListSelectionListener(new DeleteExamActionListener());
		
		scrollPane.setViewportView(list_1);
		
		btnEditExam = new JButton("Edit Exam");
		btnEditExam.addActionListener(new EditExamActionListener());
		
		btnDeleteExam = new JButton("Delete Exam");
		btnDeleteExam.addActionListener(new DeleteExamActionListener());
		
		gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCreateExam)
							.addGap(18)
							.addComponent(btnEditExam, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnDeleteExam, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnAddQuestion, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreateExam)
						.addComponent(btnAddQuestion)
						.addComponent(btnDeleteExam)
						.addComponent(btnEditExam))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		
		contentPane.setLayout(gl_contentPane);
		addExamsIntoList();

	}
	static void addExamsIntoList(){
		try {
		 exams = ExamManager.getAllExams();
		 for (int i = 0; i < exams.size(); i++) {
			 examList.addElement(exams.get(i).toString());	
		 }
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	static void updateList(){
		try {
			exams = ExamManager.getAllExams();
			int index = examList.size();
			System.out.println(exams.get(exams.size()-1));
			examList.insertElementAt(exams.get(exams.size()-1), index);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
/*	static void updateList(int ENO){
		try {
			int index = exams.get(exam.)
			System.out.println(exams.get(exams.size()-1));
			examList.insertElementAt(exams.get(exams.size()-1), index);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}*/
	
	class EditExamActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectedENO != 0)
				new CreateExamGUI(selectedENO).setVisible(true);
			else
				JOptionPane.showMessageDialog(ExamGUI.this, "Please Select an Exam");
		}


		
	}
	
	class CreateExamActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			CreateExamGUI ceGUI = new CreateExamGUI();
			ceGUI.setVisible(true);	
		}
	}
	class DeleteExamActionListener implements ActionListener{
		boolean deleted;
		// flag, if any one signed in the exam will be true.
		boolean isSigned;

		@Override
		public void actionPerformed(ActionEvent ae) {
			if(selectedENO == 0)
				JOptionPane.showMessageDialog(ExamGUI.this, "Please Select an Exam");
			else{
				try {
					isSigned = ExamManager.isSignedUp(selectedENO);
					if (!isSigned) {
						int n = JOptionPane.showConfirmDialog(ExamGUI.this,
								"Are You Sure You Want to Delete?",
								"Delete Confirmation",
								JOptionPane.YES_NO_OPTION);
						
						if (n == JOptionPane.YES_OPTION) {
							deleted = ExamManager.deleteExam(selectedENO);
							if (deleted) {
								JOptionPane.showMessageDialog(ExamGUI.this,
										"Exam is Deleted Successfully");
								exams.remove(selectedIndex);
								examList.remove(selectedIndex);
							} else
								JOptionPane.showMessageDialog(ExamGUI.this,
										"Oops, nothing is Deleted");
						}
					} else {
						JOptionPane.showMessageDialog(ExamGUI.this,
								"Some One Signed Up in the Exam",
								"Cannot Delete", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(ExamGUI.this, e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	
	}
	
	class AddQuestionActionListener implements ActionListener, ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent se) {
			selectedIndex = list_1.getSelectedIndex();
			if(selectedIndex != -1)
				selectedENO = exams.get(selectedIndex).getENO();
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(selectedENO != 0)
				new QuestionGUI(selectedENO).setVisible(true);
			else
				JOptionPane.showMessageDialog(ExamGUI.this, "Please Select an Exam", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
