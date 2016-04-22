package com.cpjd.stayfrosty.launcher;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.cpjd.input.Keymap;
import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.main.Game;
import com.cpjd.stayfrosty.save.Head;
import com.cpjd.stayfrosty.save.Load;
import com.cpjd.stayfrosty.save.Save;
import com.cpjd.tools.Log;

@SuppressWarnings("serial")
public class Launcher extends JFrame implements ActionListener, MouseMotionListener, MouseListener {
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/* MODIFY THIS PARAMTERS, NOT ANY OTHERS */
	public static String TITLE = "DWK Archives";
	private static String version = "Alpha 2.0";
	private static int versionCode  = 4;
	
	/*
	 * Technical paramaters
	 */
	
	// Mouse tracking
	private boolean foundStart;
	private int xstart;
	private int ystart;
	
	// Confirm settings
	private JButton apply;
	private JButton quit;
	
	// Game settings
	private JButton reset;
	private JCheckBox quality;
	private JLabel resInfo;
	private JLabel options;
	private JComboBox<String> resolution;
	private JButton controls;
	private JButton bug;
	private JButton cpjdLogo;
	private JCheckBox noSound;
	
	// Scaling options available
	String[] resDisplay = {"640 x 480","1280 x 720","1360 x 1024","1600 x 900","Fullscreen"}; // The resolutions that display 
	
	// The actual resolutions
	private int[][] resolutions = {
			{640,480},
			{1280,720},
			{1360,1024},
			{1600,900},
			{(int)screenSize.getWidth(),(int)screenSize.getHeight()}
	};

	// Entry point of launcher
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Launcher().setVisible(true);
			}
		});
	}

	Load load;
	
	public Launcher() {
		super(TITLE);

		// JFrame settings
		setSize(500,320);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		addMouseMotionListener(this);
		addMouseListener(this);
		
		// Initialize file loading
		Keymap.setDefaults();
		Log.setLogDir(TITLE);
		Head.init();
		load = new Load();
		
		// Initialize components
		apply = new JButton("Play");
		quit = new JButton("Quit");
		reset = new JButton("Delete all game data");
		quality = new JCheckBox("High quality");
		resInfo = new JLabel("Resolution: ");
		resolution = new JComboBox<>(resDisplay);
		options = new JLabel("Options: ");
		controls = new JButton("Controls...");
		bug = new JButton("Report a bug...");
		noSound = new JCheckBox("-nosound");
		
		// Set unique attributes
		bug.setSize(130,40);
		bug.setLocation(110,220);
		bug.setFocusable(false);
		bug.setBorderPainted(false);
		bug.setBackground(Color.DARK_GRAY);
		bug.setForeground(Color.WHITE);
		bug.addActionListener(this);
		add(bug);
		
		reset.setSize(180,40);
		reset.setLocation(5,270);
		reset.setFocusable(false);
		reset.setBorderPainted(false);
		reset.setBackground(Color.DARK_GRAY);
		reset.setForeground(Color.RED);
		reset.addActionListener(this);
		add(reset);
		
		controls.setSize(100,40);
		controls.setLocation(5,220);
		controls.setFocusable(false);
		controls.setBorderPainted(false);
		controls.setBackground(Color.DARK_GRAY);
		controls.setForeground(Color.WHITE);
		controls.addActionListener(this);
		add(controls);
		
		resInfo.setSize(100,15);
		resInfo.setLocation(5,105);
		resInfo.setFocusable(false);
		resInfo.setForeground(Color.DARK_GRAY);
		add(resInfo);
		
		resolution.setSize(100,25);
		resolution.setLocation(5, 125);
		resolution.setFocusable(false);
		resolution.setBackground(Color.DARK_GRAY);
		resolution.setForeground(Color.WHITE);
		resolution.setSelectedIndex(load.getResolution());
		resolution.addActionListener(this);
		resolution.setToolTipText("Sets the resolution off the display. Fullscreen is recommended and fits automically to your screen");
		add(resolution);
		
		quality.setSize(100,20);
		quality.setLocation(5, 160);
		quality.setFocusable(false);
		quality.setBorderPainted(false);
		quality.setForeground(Color.DARK_GRAY);
		quality.setSelected(load.getQuality());
		quality.setToolTipText("<html> Enable high-quality for deeper calculations <br>"
				+ "and rendering procedures</html>");
		quality.addActionListener(this);
		add(quality);
		
		noSound.setSize(90,20);
		noSound.setLocation(110, 160);
		noSound.setFocusable(false);
		noSound.setBorderPainted(false);
		noSound.setForeground(Color.DARK_GRAY);
		noSound.setSelected(load.getSound());
		noSound.addActionListener(this);
		noSound.setToolTipText("Disables all music and SFX effects. Can be reenabled in game");
		add(noSound);
		
		apply.setForeground(Color.GREEN);
		quit.setForeground(Color.RED);
		
		quit.setSize(100,40);
		apply.setSize(100,40);
		
		options.setSize(100,25);
		options.setFont(new Font("Arial",Font.BOLD,20));
		options.setLocation(3, 75);
		add(options);
		
		// Set default attributes
		buttonDefaults(apply);
		buttonDefaults(quit);
		
		// Set locations
		apply.setLocation(285, 270);
		quit.setLocation(390, 270);
		
		// Add images to the launcher
		ImageIcon titleImage = new ImageIcon(getClass().getResource("/CPJD/enginesmall.png"));
		JLabel title = new JLabel(titleImage);
		title.setLocation(0,0);
		title.setSize(276,39);
		add(title);
		
		ImageIcon cpjdImage = new ImageIcon(getClass().getResource("/CPJD/cpjdlogosmall.png"));
		cpjdLogo = new JButton(cpjdImage);
		cpjdLogo.addActionListener(this);
		cpjdLogo.setSize(50,50);
		cpjdLogo.setLocation(450,0);
		add(cpjdLogo);
		
		// Icon
		ImageIcon icon = new ImageIcon(getClass().getResource("/CPJD/launch.png"));
		setIconImage(icon.getImage());
		
		// Version label
		JLabel version = new JLabel(Launcher.version);
		version.setSize(600,15);
		version.setLocation(3,45);
		add(version);
		
	}
	
	/* Attributes that are similar for all JButtons */
	private void buttonDefaults(JButton b) {
		b.setFocusable(false);
		b.setBorderPainted(false);
		b.setBackground(Color.DARK_GRAY);
		b.addActionListener(this);
		add(b);
	}
	
	private boolean isFullscreen() {
		if(resolutions[resolution.getSelectedIndex()][0] == screenSize.getWidth() &&
				resolutions[resolution.getSelectedIndex()][1] == screenSize.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == reset) {
			try {
				Desktop.getDesktop().open(new File(System.getenv("APPDATA") + "//" + TITLE + "//"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		if(e.getSource() == quit) {
			dispose();
			System.exit(0);
			return;
		}
		if(e.getSource() == apply) {
			String[] saveTokens = {
					"//Resolution",
					String.valueOf(resolution.getSelectedIndex()),
					"//Quality",
					String.valueOf(quality.isSelected()),
					"//Sound",
					String.valueOf(noSound.isSelected())
			};
			
			new Save().saveLauncher(saveTokens);
			
			dispose();
			AudioPlayer.mute = noSound.isSelected();
			Dimension screen = new Dimension(resolutions[resolution.getSelectedIndex()][0],resolutions[resolution.getSelectedIndex()][1]);
			new Game(TITLE,version,versionCode,screen,isFullscreen(),true);
			
			return;
		}
		if(e.getSource() == bug) {
			try {
				URI url = new URI("http://catspjsdevelopers.weebly.com/bug-reporter.html");
				Desktop.getDesktop().browse(url);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == cpjdLogo) {
			try {
				URI url = new URI("http://catspjsdevelopers.weebly.com");
				Desktop.getDesktop().browse(url);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	} 

	/* Input Handlers - for movement of the launcher window*/
	public void mouseDragged(MouseEvent e) {
		// Calculates new position of screen
		if(!foundStart) {
			xstart = e.getX();
			ystart = e.getY();
			foundStart = true;
		}
		int xdist = e.getX() - xstart;
		int ydist = e.getY() - ystart;
		if(xdist < 0 || ydist < 0) setLocation(this.getX() - Math.abs(xdist), this.getY() - Math.abs(ydist));
		else setLocation(this.getX() + xdist, this.getY() + ydist);
	}
	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
		foundStart = false;
	}
}

