package GUI.Util;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class ThemeSetter {
	public static void setToTheme(JLabel label) {
		label.setFont(new Font("Calibri", Font.PLAIN, 20));
		label.setBackground(Color.white);
		label.setSize(label.getPreferredSize());
	}
	
	public static void setToTheme(JTextField text) {
		text.setFont(new Font("Calibri", Font.PLAIN, 20));
		text.setSize(text.getPreferredSize());
	}
	
	public static void setToTheme(JButton button) {
		button.setFont(new Font("Calibri", Font.BOLD, 20));
		button.setSize(button.getPreferredSize());
		button.setBackground(new Color(0, 128, 255));
		button.setForeground(Color.white);
		button.setBorder(BorderFactory.createLineBorder(Color.white));
	}
	
	public static void setToTheme(JRadioButton radio) {
		radio.setFont(new Font("Calibri", Font.PLAIN, 20));
		radio.setBackground(Color.white);
		
		radio.setIcon(new Icon() {
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.black);
				g2d.drawOval(x - 2, y - 2, getIconWidth(), getIconHeight());
			}
			
			@Override
			public int getIconWidth() {
				return 16;
			}
			
			@Override
			public int getIconHeight() {
				return 16;
			}
		});
		
		radio.setSelectedIcon(new Icon() {
			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.black);
				g2d.drawOval(x - 2, y - 2, getIconWidth(), getIconHeight());
				g2d.setColor(new Color(0, 128, 255));
				g2d.fillOval(x + 1, y + 1, getIconWidth() - 5, getIconHeight() - 5);
			}
			
			@Override
			public int getIconWidth() {
				return 16;
			}
			
			@Override
			public int getIconHeight() {
				return 16;
			}
		});
		
		radio.setSize(radio.getPreferredSize());
	}
	
	public static void setToBigCyan(JLabel label) {
		label.setBackground(Color.white);
		label.setForeground(Color.cyan);
		label.setFont(new Font("Calibri", Font.BOLD, 100));
		label.setSize(label.getPreferredSize());
	}
	
	public static void setToSmallBlue(JLabel label) {
		label.setForeground(Color.blue);
		label.setBackground(Color.white);
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.setSize(label.getPreferredSize());
	}
	
	public static void setToSmallBlack(JLabel label) {
		label.setFont(new Font("Calibri", Font.PLAIN, 14));
		label.setBackground(Color.white);
		label.setSize(label.getPreferredSize());
	}
	
	public static void setToBold(JLabel label) {
		label.setFont(new Font("Calibri", Font.BOLD, 20));
		label.setBackground(Color.white);
		label.setSize(label.getPreferredSize());
	}
	
	public static void setToGreyButton(JButton button) {
		button.setFont(new Font("Calibri", Font.BOLD, 20));
		button.setSize(button.getPreferredSize());
		button.setBackground(new Color(224, 224, 224));
		button.setForeground(Color.black);
		button.setBorder(BorderFactory.createLineBorder(Color.white));
	}
	
	public static void setToMediumBlack(JLabel label) {
		label.setFont(new Font("Calibri", Font.BOLD, 50));
		label.setBackground(Color.white);
		label.setSize(label.getPreferredSize());
	}
}
