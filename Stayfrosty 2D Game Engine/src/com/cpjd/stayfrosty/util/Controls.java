package com.cpjd.stayfrosty.util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class Controls extends JFrame implements FocusListener, ActionListener {
	
	private static final long serialVersionUID = 6568543362244554364L;
	
	// Defaults
	private static int DLEFT = 37;
	private static int DRIGHT = 39;
	private static int DSPRINT = 16;
	private static int DJUMP = 32;
	private static int DSMOKE = 83;
	private static int DINV = 73;
	private static int DSHOP = 79;
	private static int DPAUSE = 27;
	private static int DFIRE = 88;
	private static int DRELOAD = 68;
	
	// Characters for defaults
	private static String[] display = {"left","right","shift","space","s","i","o","esc","x","d"};
	
	// To set
	private static String[] set = new String[10];
	
	// Whatever key codes they are set to
	public static int LEFT;
	public static int RIGHT;
	public static int SPRINT;
	public static int JUMP;
	public static int SMOKE;
	public static int INV;
	public static int SHOP;
	public static int PAUSE;
	public static int FIRE;
	public static int RELOAD;
	
	// Labels
	JLabel left;
	JLabel right;
	JLabel sprint;
	JLabel jump;
	JLabel smoke;
	JLabel inv;
	JLabel shop;
	JLabel pause;
	JLabel fire;
	JLabel reload;
	
	// Text fields
	JTextField[] fields = new JTextField[10];
	
	// Buttons
	JButton apply;
	
	public void init() {
		left = new JLabel("Arrow keys & space to move");
		right = new JLabel("Shift - Sprint");
		sprint = new JLabel("S - Use powerup: ");
		jump = new JLabel("X - Fire weapon");
		smoke = new JLabel("D - Reload");
		inv = new JLabel("I - Inventory / O - Shop");
		shop = new JLabel("M - Minimap");
		pause = new JLabel("ESC - Pause");
		fire = new JLabel("");
		reload = new JLabel("F6 - Debug");
		
		apply = new JButton("let's go");
		apply.setSize(200,30);
		apply.setLocation(80,325);
		apply.setFocusable(false);
		apply.setBorderPainted(false);
		apply.setBackground(Color.DARK_GRAY);
		apply.setForeground(Color.WHITE);
		apply.addActionListener(this);
		add(apply);
		
		left.setSize(250,20);
		right.setSize(150,20);
		sprint.setSize(150,20);
		jump.setSize(150,20);
		smoke.setSize(150,20);
		inv.setSize(150,20);
		shop.setSize(150,20);
		pause.setSize(150,20);
		fire.setSize(150,20);
		reload.setSize(150,20);
		
		left.setLocation(50,30);
		right.setLocation(50,60);
		sprint.setLocation(50,90);
		jump.setLocation(50,120);
		smoke.setLocation(50,150);
		inv.setLocation(50,180);
		shop.setLocation(50,210);
		pause.setLocation(50,240);
		fire.setLocation(50,270);
		reload.setLocation(50,300);
		
		add(left);
		add(right);
		add(sprint);
		add(jump);
		add(smoke);
		add(inv);
		add(shop);
		add(pause);
		add(fire);
		add(reload);
		
		for(int i = 0, j = 1; i < fields.length; i++) {
			fields[i] = new JTextField();
			fields[i].setSize(50,20);
			fields[i].setLocation(200,j * 30);
			fields[i].setText(display[i]);
			fields[i].addFocusListener(this);
			fields[i].addActionListener(this);
			//add(fields[i]);
			j++;
		}
		
		
	}
	
	public Controls() {
		super("Controls");

		// JFrame settings
		setSize(300,400);
		//setUndecorated(true);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		init();
	}
	
	private void setKeys() {
		
		
		//KeyStroke ks = KeyStroke.getKeyStroke(set[0],0);
	}
	
	public void focusGained(FocusEvent focus) {
		for(int i = 0; i < fields.length; i++) {
			if(focus.getSource() == fields[i]) {
				fields[i].selectAll();
			}
		}
	}
	public void focusLost(FocusEvent focus) {
		
	}


	public void actionPerformed(ActionEvent e) {
		for(int i = 0; i < fields.length; i++) {
			if(e.getSource() == fields[i]) {
				System.out.println(fields[i].getText());
			}
		}
		if(e.getSource() == apply) {
			for(int i = 0; i < fields.length; i++) {
				StringTokenizer st = new StringTokenizer(fields[i].getText());
				set[i] = st.nextToken();
			}
			dispose();
		}
		
	}
	
	
}
