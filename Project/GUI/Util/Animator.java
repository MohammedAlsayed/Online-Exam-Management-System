package GUI.Util;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Animator {
	private JPanel panel;
	private JComponent[] components;
	private int firstToGetFocusIndex;
	
	public Animator(JPanel panel, JComponent[] components, int firstToGetFocusIndex) {
		for(int i = 0; i < components.length; i++) {
			JComponent c = components[i];
			
			if(c instanceof JLabel) {
				Color foreground = c.getForeground();
				c.setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), 0));
			}
			else if(c instanceof JTextField) {
				c.setEnabled(false);
				c.setBorder(BorderFactory.createLineBorder(Color.white));
			}
			else if(c instanceof JButton) {
				c.setFocusable(false);
				Color background = c.getBackground();
				c.setBackground(new Color(background.getRed(), background.getGreen(), background.getBlue(), 0));
			}
			else if(c instanceof JRadioButton) {
				c.setFocusable(false);
				c.setForeground(Color.white);
			}
			else
				throw new IllegalArgumentException("Only JLabel, JTextField and JButton are accepted.");
		}
		
		this.panel = panel;
		this.components = components;
		this.firstToGetFocusIndex = firstToGetFocusIndex;
	}
	
	public void showComponents() {
		new Timer().scheduleAtFixedRate(new AnimationTimerTask(true), 0, 16);
	}
	
	public void hideComponents() {
		new Timer().scheduleAtFixedRate(new AnimationTimerTask(false), 0, 16);
	}
	
	private class AnimationTimerTask extends TimerTask {
		private int currentTime = 0;
		private final int DURATION = 1000;
		private final int CHANGE_IN_POSITION = 255;
		private int changeInAlpha = 255;
		private int alphaStart;
		private int[] startPositions = new int[components.length];
		private boolean resetPositionAfterAnimation;
		
		public AnimationTimerTask(boolean show) {
			// Move components CHANGE_IN_POSITION to the left, so they move CHANGE_IN_POSITION again to
			// the right after the animation
			for(int i = 0; i < components.length; i++) {
				startPositions[i] = show? components[i].getX() - CHANGE_IN_POSITION : components[i].getX();
				if(!(components[i] instanceof JLabel)) components[i].setFocusable(show);
				components[i].setEnabled(true);
				components[i].setLocation(show? components[i].getX() - CHANGE_IN_POSITION : components[i].getX(), components[i].getY());
			}
			
			changeInAlpha = show? changeInAlpha : -changeInAlpha;
			alphaStart = show? 0 : 255;
			resetPositionAfterAnimation = !show;
			components[firstToGetFocusIndex].requestFocus();
		}
		
		@Override
		public void run() {
			int newPositionMinusStartPosition = (int)(CHANGE_IN_POSITION * (Math.pow(1.0 * currentTime / DURATION - 1, 3) + 1));
			int newAlpha = (int)(changeInAlpha * (Math.pow(1.0 * currentTime / DURATION - 1, 3) + 1) + alphaStart);
			
			for(int i = 0; i < components.length; i++) {
				if(components[i] instanceof JLabel) {
					Color foreground = components[i].getForeground();
					components[i].setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), newAlpha));
				}
				else if(components[i] instanceof JTextField) {
					Color foreground = components[i].getForeground();
					components[i].setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), newAlpha));
					components[i].setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150, newAlpha)));
				}
				else if(components[i] instanceof JButton) {
					Color foreground = components[i].getForeground();
					components[i].setForeground(new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue(), newAlpha));
					Color background = components[i].getBackground();
					components[i].setBackground(new Color(background.getRed(), background.getGreen(), background.getBlue(), newAlpha));
				}
				components[i].setLocation(newPositionMinusStartPosition + startPositions[i], components[i].getY());
			}
			
			panel.repaint();
			
			if(currentTime == DURATION) {
				if(resetPositionAfterAnimation)
					for(int i = 0; i < components.length; i++)
						components[i].setLocation(startPositions[i], components[i].getY());
				cancel();
			}
			
			currentTime += 16;
			if(currentTime > DURATION)
				currentTime = DURATION;
		}
	}
}
