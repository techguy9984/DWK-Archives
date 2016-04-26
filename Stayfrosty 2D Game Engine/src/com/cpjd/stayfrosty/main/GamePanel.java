package com.cpjd.stayfrosty.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.cpjd.input.Keys;
import com.cpjd.input.Mouse;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.tools.Log;
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	//////////////////// GAME SYSTEM VARIABLES////////////////////
	public static int WIDTH;
	public static int HEIGHT;
	public static int SCALE = 2;
	public static String GAME_TITLE;
	public static String VERSION;
	public static int VERSION_CODE;
	public static boolean QUALITY;
	public static boolean DEBUG;
	//////////////////// GAME SYSTEM VARIABLES////////////////////
	
	// Thread
	private Thread thread;
	private volatile boolean running;
	public static int FPS = 60;
	public static long targetTime = 1000 / FPS;
	
	// Image
	private BufferedImage image;
	private Graphics2D g;

	// Game State Manager
	private GameStateManager gsm;  
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			requestFocus();
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		//if(QUALITY) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		running = true;
		
		gsm = new GameStateManager();
		
		Log.log("Thread started successfully", 5);
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running) {
			start = System.nanoTime();
			{
				update();
				draw();
				drawToScreen();
			}
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 0;
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				Log.logError(e, Log.THREAD_ERROR);
			}
		}
	}
	
	private void update() {
		gsm.update();
	}
	
	private void draw() { 
		gsm.draw(g);
		
		//if(QUALITY) Toolkit.getDefaultToolkit().sync(); // Refreshes the display on some systems
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	/* Input Handlers */
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());

		Keys.keySet(key.getKeyCode(), true);

		// Debugging
		if (key.getKeyCode() == KeyEvent.VK_F6) {
			DEBUG = !DEBUG;
		}
	}

	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
		Keys.keySet(key.getKeyCode(), false);
	}
	public void keyTyped(KeyEvent key) {}

	public void mousePressed(MouseEvent mouse) {
		if(mouse.getButton() == MouseEvent.BUTTON1) {
			Mouse.leftPressed = true;
		}
	}

	public void mouseReleased(MouseEvent mouse) {
		if(mouse.getButton() == MouseEvent.BUTTON1) {
			Mouse.leftPressed = false;
		}
	}

	public void mouseMoved(MouseEvent mouse) {
		Mouse.x = mouse.getX();
		Mouse.y = mouse.getY();
	}
	public void mouseDragged(MouseEvent mouse) {}
	public void mouseClicked(MouseEvent mouse) {}
	public void mouseEntered(MouseEvent mouse) {}
	public void mouseExited(MouseEvent mouse) {}	
}
