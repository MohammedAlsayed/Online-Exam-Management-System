package GUI;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import javax.xml.ws.Dispatch;

import DB.Tables.UserManager;
import DB.beans.User;

/**
 * GUI of the login interface of the admin
 * 
 * @author Mohammed Alsayed, 13-12-2015.
 */


public class Login {
	
private static int HIGHT = 250;
private static int WIDTH = 350;

private JLabel userNameLabel = new JLabel("User Name");
private JLabel passwordLabel = new JLabel("Password");
protected static JTextField  userNameField = new JTextField(30);
protected static JPasswordField passwordField = new JPasswordField(30);
private JRadioButton userRadioButton = new JRadioButton("user");
private JRadioButton adminRadioButton = new JRadioButton("admin");
private JButton login = new JButton("Login");
private JButton back  = new JButton("Back");
static JFrame f;


  

  public void createUI() {
	  f = new JFrame ("Login");
    
    final WaitLayerUI layerUI = new WaitLayerUI();
    JPanel panel = createPanel();
    JLayer<JPanel> jlayer = new JLayer<JPanel>(panel, layerUI);
    
    final Timer stopper = new Timer(2000, new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        layerUI.stop();
      }
    });
    stopper.setRepeats(false);

    login.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          layerUI.start();
          if (!stopper.isRunning()) {
            stopper.start();
          }
        }
      }
    );

    f.add (jlayer);
    
    f.setSize(WIDTH, HIGHT);
    f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo (null);
    f.setVisible (true);
  }

  private JPanel createPanel() {
    JPanel p = new JPanel();
    p.setLayout(null);
    ButtonGroup entreeGroup = new ButtonGroup();
    entreeGroup.add(adminRadioButton);
    entreeGroup.add(userRadioButton);
    
    userNameLabel.setBounds(40, 40, 80, 20);
    p.add(userNameLabel);
    passwordLabel.setBounds(40, 80, 80, 20);
    p.add(passwordLabel);
    
    userNameField.setBounds(140, 40, 130, 20);
    p.add(userNameField);
   
    passwordField.setBounds(140, 80, 130, 20);
    p.add(passwordField);
    
    login.setBounds(170, 150, 130, 20);
    p.add(login);
    
    back.setBounds(30, 150, 130, 20);
    back.addActionListener(
    	      new ActionListener() {
    	        public void actionPerformed(ActionEvent ae) {
    	          new StudentLogin();
    	          f.dispose();
    	        }
    	      }
    	    );
    
    p.add(back);
    
    return p;
  }
}

class WaitLayerUI extends LayerUI<JPanel> implements ActionListener {
  private boolean mIsRunning;
  private boolean mIsFadingOut;
  private Timer mTimer;

  private int mAngle;
  private int mFadeCount;
  private int mFadeLimit = 15;

  @Override
  public void paint (Graphics g, JComponent c) {
    int w = c.getWidth();
    int h = c.getHeight();

    // Paint the view.
    super.paint (g, c);

    if (!mIsRunning) {
      return;
    }

    Graphics2D g2 = (Graphics2D)g.create();

    float fade = (float)mFadeCount / (float)mFadeLimit;
    // Gray it out.
    Composite urComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(
        AlphaComposite.SRC_OVER, .5f * fade));
    g2.fillRect(0, 0, w, h);
    g2.setComposite(urComposite);

    // Paint the wait indicator.
    int s = Math.min(w, h) / 5;
    int cx = w / 2;
    int cy = h / 2;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(
        new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
    g2.setPaint(Color.white);
    g2.rotate(Math.PI * mAngle / 180, cx, cy);
    for (int i = 0; i < 12; i++) {
      float scale = (11.0f - (float)i) / 11.0f;
      g2.drawLine(cx + s, cy, cx + s * 2, cy);
      g2.rotate(-Math.PI / 6, cx, cy);
      g2.setComposite(AlphaComposite.getInstance(
          AlphaComposite.SRC_OVER, scale * fade));
    }

    g2.dispose();
  }

  public void actionPerformed(ActionEvent e) {
    if (mIsRunning) {
      firePropertyChange("tick", 0, 1);
      mAngle += 3;
      if (mAngle >= 360) {
        mAngle = 0;
      }
      if (mIsFadingOut) {
        if (--mFadeCount == 0) {
          mIsRunning = false;
          mTimer.stop();
        }
        User user;
        if(!mIsRunning){
        	try {
				user = UserManager.getLoginedUser(Login.userNameField.getText(), Login.passwordField.getText());
				if(user != null){
					new ExamGUI().setVisible(true);
					Login.f.dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Username and Password are not Matching", "Error", 0);
		//		JOptionPane.QUESTION_MESSAGE
        	} catch (SQLException e1) {
				System.err.println(e1.getMessage());
			}
        }
      }
      else if (mFadeCount < mFadeLimit) {
        mFadeCount++;
      }
    }
  }


public void start() {
    if (mIsRunning) {
      return;
    }
    
    // Run a thread for animation.
    mIsRunning = true;
    mIsFadingOut = false;
    mFadeCount = 0;
    int fps = 24;
    int tick = 1000 / fps;
    mTimer = new Timer(tick, this);
    mTimer.start();
  }

  public void stop() {
    mIsFadingOut = true;
  }

  @Override
  public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
    if ("tick".equals(pce.getPropertyName())) {
      l.repaint();
    }
  }
}
