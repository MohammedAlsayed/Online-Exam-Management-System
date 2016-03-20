package GUI;

import javax.swing.UIManager;

import GUI.Login;

public class AdminMain {

	public static void main(String[] args) {
		 try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch(Exception e) {
		        System.out.println("Error setting native LAF: " + e);
		    }
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        new Login().createUI();
		      }
		  });
	}
}
