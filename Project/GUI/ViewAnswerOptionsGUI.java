package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;

import DB.Tables.AnswerOptionManager;
import DB.Tables.ExamManager;
import DB.beans.AnswerOption;

public class ViewAnswerOptionsGUI extends JFrame {

	private JPanel contentPane;
	static DefaultListModel questionOptionsList = new DefaultListModel();
	private int ENO;
	private int QNO;
	private ArrayList<AnswerOption> ansOps = new ArrayList<AnswerOption>();
	/**
	 * Create the frame.
	 */
	public ViewAnswerOptionsGUI(int ENO, int QNO) {
		this.ENO = ENO;
		this.QNO = QNO;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setLocationRelativeTo (null);
		setResizable(false);
		
		JLabel lblExam = new JLabel("Exam:");
		lblExam.setBounds(23, 11, 46, 14);
		contentPane.add(lblExam);
		
		JLabel ENOLabel = new JLabel(ENO+"");
		ENOLabel.setBounds(79, 11, 71, 14);
		contentPane.add(ENOLabel);
		
		JLabel lblQuestion = new JLabel("Question:");
		lblQuestion.setBounds(167, 11, 61, 14);
		contentPane.add(lblQuestion);
		
		JLabel QNOLabel = new JLabel(QNO+"");
		QNOLabel.setBounds(238, 11, 59, 14);
		contentPane.add(QNOLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 414, 160);
		contentPane.add(scrollPane);
		
		addAnswerOptionsIntoList();
		JList list = new JList(questionOptionsList);
		scrollPane.setViewportView(list);
	}
	private void addAnswerOptionsIntoList(){
		questionOptionsList.removeAllElements();
		try {
			ansOps = AnswerOptionManager.getAnswerOptions(ENO, QNO);
		 for (int i = 0; i < ansOps.size(); i++) {
			 questionOptionsList.addElement(ansOps.get(i).toString());	
		 }
		} catch (Exception e) {
			System.err.println("view: "+e.getMessage());
			}
		}
	}
