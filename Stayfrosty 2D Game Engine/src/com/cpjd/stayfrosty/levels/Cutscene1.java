
package com.cpjd.stayfrosty.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.cpjd.stayfrosty.audio.AudioPlayer;
import com.cpjd.stayfrosty.audio.SKeys;
import com.cpjd.stayfrosty.gamestate.GameState;
import com.cpjd.stayfrosty.gamestate.GameStateManager;
import com.cpjd.stayfrosty.main.GamePanel;

// APROVED
public class Cutscene1 extends GameState {// intro
	
	//private TileMap tileMap; 
	//private Background bg;
	private int counter = 0;
	private int blinker = 0;
	private int storyLevel = 0;
	private int selected = 0;
	private boolean finishedTyping = false;
	private String[] descriptions = new String[3];
	private String[] Narration = new String[10];
	
	public Cutscene1(GameStateManager gsm) {
		super(gsm);
		
		finishedTyping = false;
		init();
	}
	

	public void init() {
		storyLevel = 0;
		Narration[0] = " The DWK Archives: vol 1";
		Narration[1] = " Sometime in the ancient past three great warriors formed a bond, this bond was more powerful than any other known to man.";
		Narration[2] = " They drew upon each other's strengths to outwit, outlast, and overpower their enemies.";
		Narration[3] = " These three great warriors eventually died together in battle. However, their legacy was continued by their followers. Their following formed a union with one purpose, to eradicate the world of injustice.";
		Narration[4] = " This purpose was upheld by the great constitution which held the rules for the perfect society. The followers of this constitution began to be called the illuminati.";
		Narration[5] = " Those under the illuminati experienced great peace and wealth and the constitution was upheld at all costs.";
		Narration[6] = " One day, a young man by the name of Alex Sparker infiltrated the illuminati and attempted to corrupt its followers.";
		Narration[7] = " His plan succeeded, many fell to his sly words and the illuminati began to fall. The days of peace and prosperity seemed to come to an end, the illuminati lost its fame and became only legend.";
		Narration[8] = " Until one day, when three new young heroes joined together to fight the evil plans of Alex Sparker and his new found followers, and to promote justice found in the ideals of the illuminati.";
		Narration[9] = " Here is the tale of the victory of the three united, the DWK.";
		
		descriptions[0] = "A competetive track runner and hobbies enthusiast";
		descriptions[1] = "A dedicated programmer and certified csgo pro";
		descriptions[2] = "A flawless man";
		
		AudioPlayer.loopMusic(SKeys.Epic);

		AudioPlayer.loopSound(SKeys.Type);
		
	}
	
	public void update() {
		counter ++;
		blinker ++;
		if(blinker ==40){
			blinker = 0;
		}
		if(storyLevel <= 9){
			if(counter>Narration[storyLevel].length()*2){
				counter = Narration[storyLevel].length()*2;
			}
			if(counter == Narration[storyLevel].length()*2){
				finishedTyping = true;
				
        	}
		}else if (storyLevel == 10){
			if(counter>90){
				counter = 90;
				finishedTyping  = true;
			}
    	}
		if(finishedTyping){
			AudioPlayer.stopSound(SKeys.Type);
		}

	}
	
	public void draw(Graphics2D g) {

		//bg.draw(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.white);
		
		Font font = new Font("Monospaced", Font.PLAIN, 18);
        g.setFont( font );
		
        boolean cursor;
        if(blinker >= 20){
        	cursor = true;
        }else{
        	cursor = false;
        }
        if(storyLevel <= 9){
        	displayString(Narration[storyLevel].substring(0,counter/2), 100, 100, 50, 20,cursor, g);
        }else if (storyLevel == 10){
        	g.drawString(" Select Your Character",100,100);
        	if(counter>70)
        	g.drawString("   Daniel",100,40+counter);
        	if(counter>40)
        	g.drawString("   Will",100,70+counter);
        	if(counter>10)
        	g.drawString("   Kade",100,100+counter);
        	if(counter==90){
        		g.drawString("[",115,130+selected*30);
        		g.drawString(descriptions[selected],130,300);
        	}
        	
        }
        

		
	}
	
	public static void displayString(String str, int x, int y, int width,int height, boolean cursor, Graphics g){
		while(str.length() > width){
			for(int i = width-1;i>=0;i--){
				if(str.charAt(i)==' '){
					g.drawString(str.substring(0,i), x, y);
					str = str.substring(i,str.length());
					y += height;
					
					break;
					
				}
			}
		}
		g.drawString(str,x,y);
		//int width = g.getFontMetrics().stringWidth(str);
		if (cursor)
			g.drawString("|", x+g.getFontMetrics().stringWidth(str), y);
	}
	
	public void keyPressed(int k) {
		//player.keyPressed(k);
		
		//m.keyPressed(k);
		
		
		
		//if(k == KeyEvent.VK_SPACE) player.setUp(true);
		//if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		//if(k == KeyEvent.VK_DOWN) player.setDown(true);
		//if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		//if(k == KeyEvent.VK_SPACE) player.setJumping(true);
		if(k == KeyEvent.VK_W){
			if (storyLevel == 10){
				selected -= 1;
				if (selected == -1){
					selected = 2;
				}
				AudioPlayer.playSound(SKeys.Change);
			}
		}
		if(k == KeyEvent.VK_S){
			if (storyLevel == 10){
				selected += 1;
				if (selected == 3){
					selected = 0;
				}
				AudioPlayer.playSound(SKeys.Change);
			}
		}
		if(k == KeyEvent.VK_UP){
			if (storyLevel == 10){
				selected -= 1;
				if (selected == -1){
					selected = 2;
				}
				AudioPlayer.playSound(SKeys.Change);
			}
		}
		if(k == KeyEvent.VK_DOWN){
			if (storyLevel == 10){
				selected += 1;
				if (selected == 3){
					selected = 0;
				}
				AudioPlayer.playSound(SKeys.Change);
			}
		}
		
		if(k == KeyEvent.VK_SHIFT){
			storyLevel--;
			counter = 0;
			finishedTyping = false;
		}
		//if(k == KeyEvent.VK_S) player.setSmoking();
		if(k == KeyEvent.VK_ENTER){
			if (storyLevel == 10) {
				gsm.setState(GameStateManager.L1_1);
				AudioPlayer.stopSound(SKeys.Type);
				AudioPlayer.stopMusic(SKeys.Epic);
				return;
			}
			
			if (storyLevel <= 9)
			storyLevel++;
			counter = 0;
			finishedTyping = false;
			AudioPlayer.stopSound(SKeys.Type);
			AudioPlayer.loopSound(SKeys.Type);
			
		}
	}
	
	public void keyReleased(int k) {
		//player.keyReleased(k);

		//if(k == KeyEvent.VK_SPACE) player.setUp(false);
		//if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		//if(k == KeyEvent.VK_DOWN) player.setDown(false);
		//if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		//if(k == KeyEvent.VK_SPACE) player.setJumping(false);
		//if(k == KeyEvent.VK_SHIFT) player.setSprinting(false);
		
	}
	
	
}

