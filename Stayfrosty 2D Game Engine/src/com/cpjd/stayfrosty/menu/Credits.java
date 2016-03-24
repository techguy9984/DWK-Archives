package com.cpjd.stayfrosty.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.files.Load;
import com.cpjd.stayfrosty.files.Save;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;
import com.cpjd.stayfrosty.util.Center;

public class Credits extends GameState {
	
	// Display
	String[] items = {
			"Bryan Simulator",
			"by Cats Pajamas Developers",
			"",
			"Team",
			"Programming & Game Design - Will \"techguy9984\"",
			"",
			"Testers",
			"Jake \"99bacon\"",
			"Isaac \"50_hp\"",
			"Bryan \"MaximumHoney\"",
			"",
			"Art",
			"Ak-47 & Uzi art - clayster2012 (http://opengameart.org/users/clayster2012)",
			"Other gun art - PixelMannen - (http://opengameart.org/users/pixelmannen)",
			"Tileset art - Eris - (http://opengameart.org/users/eris)",
			"Spike tiles - kungfu4000 - (http://opengameart.org/users/kungfu4000)",
			"Keyboard tiles - InanZen - (http://opengameart.org/users/inanzen)",
			"Menu background - PWL - (http://opengameart.org/users/pwl)",
			"Stayfrosty Engine Logo - (http://cooltext.com/Link)",
			"Troll Face - (http://vignette2.wikia.nocookie.net)",
			"Police tile - (http://uxrepo.com/icon/police-station-by-ocha)",
			"Explosion animation - http://opengameart.org/content/explosion-set-2-m484-games",
			"Cooltext - (http://cooltext.com/Link)",
			"",
			"Sounds",
			"Menu Music - A Night Of Dizzy Speels by Eric Skiff",
			"Taking damage - thecheeseman - (http://www.freesound.org/people/thecheeseman/sounds/44428/)",
			"Credits Music - iharman94 - (http://www.freesound.org/people/lharman94/sounds/329597/)",
			"Sniper - qubodup - (http://www.freesound.org/people/qubodup/sounds/182051/)",
			"Shotgun - 18hiltc - ( http://www.freesound.org/people/18hiltc/sounds/186049/)",
			"Pistol - LeMudCrab - (http://www.freesound.org/people/LeMudCrab/sounds/163456/)",
			"Assault Rifle - ryanconway - (http://www.freesound.org/people/ryanconway/sounds/200277/)",
			"Ak47 - Frankie01234 - (http://www.freesound.org/people/Frankie01234/sounds/201669/)",
			"Laser - bubaproducer - (http://www.freesound.org/people/bubaproducer/sounds/151022/)",
			"Other sounds - (http://www.freesound.org/)",
			"",
			"Sounds included in Update 0.11",
			"Select - - http://www.freesound.org/people/Bertrof/sounds/131658/",
			"Enemy laser - http://www.freesound.org/people/jobro/sounds/35685/",
			"Purchased - http://www.freesound.org/people/Zott820/sounds/209578/",
			"Error - http://www.freesound.org/people/distillerystudio/sounds/327737/",
			"Shield - http://www.freesound.org/people/Corsica_S/sounds/107549/",
			"Level complete - http://www.freesound.org/people/jivatma07/sounds/122255/",
			"Various Background Music: ",
			"http://www.freesound.org/people/LittleRobotSoundFactory/sounds/321039/",
			"http://www.freesound.org/people/joshuaempyre/sounds/251461/",
			"http://www.freesound.org/people/FoolBoyMedia/sounds/320232/",
			"http://www.freesound.org/people/tyops/sounds/243435/",
			"http://www.freesound.org/people/dingo1/sounds/174589/",
			"",
			"Thanks to",
			"ForeignGuyMike",
			"Eric Skiff",
			"",
			"This program is not intended for commerical distribution.",
			"",
			"All logos shown or represented in this game",
			"are copyright and or trademark of their respective",
			"corporations.",
			"",
	};
	
	// Technical
	private int y;
	
	BufferedImage cpjd;
	
	public Credits(GameStateManager gsm, Load load, Save save) {
		super(gsm,load,save);

		y = GamePanel.HEIGHT;
		AudioPlayer.playMusic(SKeys.Credits);
		
		try {
			cpjd = ImageIO.read(getClass().getResourceAsStream("/CPJD/cpjdlogosmall.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {
		y--;
		
		if(y + (30 * items.length) < -15) {
			//AudioPlayer.stopAll();
			gsm.setState(GameStateManager.MENU);
		}
	}

	
	public void draw(Graphics2D g) {
		// Rendeirng
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Clear the screen
		g.setColor(new Color(20, 91, 115));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		// Draw items
		g.setColor(Color.BLACK);
		g.setFont(new Font("Comic Sans MS",Font.BOLD,15));
		
		g.drawImage(cpjd, Center.centeri(cpjd.getWidth()), y - cpjd.getHeight(), null);
		
		g.drawString("Press ESC to exit", 5, 15);
		
		for(int i = 0, j = 1; i < items.length; i++) {
			if(i == items.length - 1) g.setColor(Color.MAGENTA);
			g.drawString(items[i], Center.center(g, items[i]), y + (30 * j));
			j++;
		}
		
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ESCAPE) {
			//AudioPlayer.stopAll();
			gsm.setState(GameStateManager.MENU);
		}
		
	}

	
	public void keyReleased(int k) {}
	
}
