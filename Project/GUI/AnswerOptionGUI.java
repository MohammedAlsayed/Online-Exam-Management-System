package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import DB.Tables.AnswerOptionManager;

/**
 * GUI of the answer option interface
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */

public class AnswerOptionGUI extends JFrame {

	private JPanel contentPane;
	private JTextField qA;
	private JTextField qB;
	private JTextField qC;
	private JTextField qD;
	private JTextField qE;
	private int ENO;
	private int QNO;

	
	/**
	 * Create the frame.
	 */
	public AnswerOptionGUI(int ENO, int QNO) {
		super("Answer Option");
		this.ENO = ENO;
		this.QNO = QNO;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 411, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setLocationRelativeTo (null);
		setResizable(false);
		
		JLabel lblNewLabel = new JLabel("Option: A");
		lblNewLabel.setBounds(10, 37, 66, 14);
		contentPane.add(lblNewLabel);
		
		qA = new JTextField();
		qA.setBounds(83, 59, 289, 20);
		contentPane.add(qA);
		qA.setColumns(10);
		qA.getDocument().putProperty("name", "qA");
		qA.getDocument().addDocumentListener(new textFieldsDocumentListener());
		
		
		JLabel lblOpti = new JLabel("Option Text");
		lblOpti.setBounds(10, 62, 66, 14);
		contentPane.add(lblOpti);
		
		qB = new JTextField();
		qB.setColumns(10);
		qB.setBounds(83, 112, 289, 20);
		contentPane.add(qB);
		qB.setEditable(false);
		qB.getDocument().putProperty("name", "qB");
		qB.getDocument().addDocumentListener(new textFieldsDocumentListener());
		
		JLabel label = new JLabel("Option Text");
		label.setBounds(10, 115, 66, 14);
		contentPane.add(label);
		
		JLabel lblOptionB = new JLabel("Option: B");
		lblOptionB.setBounds(10, 90, 66, 14);
		contentPane.add(lblOptionB);
		
		qC = new JTextField();
		qC.setColumns(10);
		qC.setBounds(83, 165, 289, 20);
		contentPane.add(qC);
		qC.setEditable(false);
		qC.getDocument().putProperty("name", "qC");
		qC.getDocument().addDocumentListener(new textFieldsDocumentListener());
		
		JLabel label_1 = new JLabel("Option Text");
		label_1.setBounds(10, 168, 66, 14);
		contentPane.add(label_1);
		
		JLabel lblOptionC = new JLabel("Option: C");
		lblOptionC.setBounds(10, 143, 66, 14);
		contentPane.add(lblOptionC);
		
		qD = new JTextField();
		qD.setColumns(10);
		qD.setBounds(83, 230, 289, 20);
		contentPane.add(qD);
		qD.setEditable(false);
		qD.getDocument().putProperty("name", "qD");
		qD.getDocument().addDocumentListener(new textFieldsDocumentListener());
		
		JLabel label_2 = new JLabel("Option Text");
		label_2.setBounds(10, 233, 66, 14);
		contentPane.add(label_2);
		
		JLabel lblOptionD = new JLabel("Option: D");
		lblOptionD.setBounds(10, 208, 66, 14);
		contentPane.add(lblOptionD);
		
		qE = new JTextField();
		qE.setColumns(10);
		qE.setBounds(83, 294, 289, 20);
		contentPane.add(qE);
		qE.setEditable(false);
		qE.getDocument().putProperty("name", "qE");
		qE.getDocument().addDocumentListener(new textFieldsDocumentListener());
		
		JLabel label_3 = new JLabel("Option Text");
		label_3.setBounds(10, 297, 66, 14);
		contentPane.add(label_3);
		
		JLabel lblOptionE = new JLabel("Option: E");
		lblOptionE.setBounds(10, 272, 66, 14);
		contentPane.add(lblOptionE);
		
		JButton btnAddAnswers = new JButton("Add Answer Options");
		btnAddAnswers.setBounds(128, 329, 150, 23);
		contentPane.add(btnAddAnswers);
		
		JLabel lblNoteOptions = new JLabel("Note : Option texts not filled will not be added");
		lblNoteOptions.setBounds(163, 11, 295, 14);
		contentPane.add(lblNoteOptions);
		
		JLabel lblQno = new JLabel("QNO:");
		lblQno.setBounds(10, 11, 46, 14);
		contentPane.add(lblQno);
		
		JLabel lblNewLabel_1 = new JLabel(QNO+"");
		lblNewLabel_1.setBounds(54, 11, 83, 14);
		contentPane.add(lblNewLabel_1);
		
		btnAddAnswers.addActionListener(new AddAnswerOptionsBtnActionListener());
		
	}
	
	// The class implements the action listener of the add answer option button
	class AddAnswerOptionsBtnActionListener implements ActionListener{
		int count = 0;
		// flags to check whether all answer options were inserted successfully
		boolean answerOption1, answerOption2, answerOption3, answerOption4, answerOption5;
		@Override
		public void actionPerformed(ActionEvent ae) {
			try{
			if(qA.isEditable()){
				answerOption1 = AnswerOptionManager.addAnswerOption(ENO, QNO,"A" , qA.getText());
				count++;
			}
			if(qB.isEditable()){
				answerOption2 = AnswerOptionManager.addAnswerOption(ENO, QNO,"B" , qB.getText());
				count++;
			}	
			if(qC.isEditable()){
				answerOption3 = AnswerOptionManager.addAnswerOption(ENO, QNO,"C" , qC.getText());
				count++;
			}	
			if(qD.isEditable()){
				answerOption4 = AnswerOptionManager.addAnswerOption(ENO, QNO,"D" , qD.getText());
				count++;
			}	
			if(qE.isEditable() && !qE.getText().isEmpty()){
				answerOption5 = AnswerOptionManager.addAnswerOption(ENO, QNO,"E" , qE.getText());
				count++;
			}	
			if(answerOption1 | answerOption2 | answerOption3 | answerOption4 | answerOption5)
				JOptionPane.showMessageDialog(AnswerOptionGUI.this, count+" Answer Option(s) are Inserted Successfully");	
			}catch(SQLException e){
				JOptionPane.showMessageDialog(AnswerOptionGUI.this, e.getMessage());
			}
		}
	}
	class textFieldsDocumentListener implements DocumentListener{

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if(e.getDocument().getProperty("name").equals("qA"))
				qB.setEditable(true);
			if(e.getDocument().getProperty("name").equals("qB"))
				qC.setEditable(true);
			if(e.getDocument().getProperty("name").equals("qC"))
				qD.setEditable(true);
			if(e.getDocument().getProperty("name").equals("qD"))
				qE.setEditable(true);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(e.getDocument().getProperty("name").equals("qA") && qA.getText().isEmpty()){
				qB.setEditable(false);
				qB.setText("");
			}
			if(e.getDocument().getProperty("name").equals("qB") && qB.getText().isEmpty()){
				qC.setEditable(false);
				qC.setText("");
			}
			if(e.getDocument().getProperty("name").equals("qC")  && qC.getText().isEmpty()){
				qD.setEditable(false);
				qD.setText("");
			}
			if(e.getDocument().getProperty("name").equals("qD") && qD.getText().isEmpty()){
				qE.setEditable(false);
				qE.setText("");
			}
		}
		
	}
}
