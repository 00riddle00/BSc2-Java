package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	private Shop shop;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(Game.gameState == STATE.Menu) {
			// play button
			if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 150, 200, 64)) {
				Game.gameState = STATE.Select;
				
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
			// help button
			else if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 250, 200, 64)) {
				Game.gameState = STATE.Help;
				
				AudioPlayer.getSound("menu_sound").play();
			}
			// quit button
			else if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)) {
				System.exit(1);
				
				AudioPlayer.getSound("menu_sound").play();
			}
		}
		if(Game.gameState == STATE.Select) {
			// normal button
			if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 150, 200, 64)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
				HUD.HEALTH += hud.bounds / 2;
				hud.setScore(0);
				hud.setLevel(1);
				hud.bounds = 0;
				//shop.setB1(shop.initPriceB1);
				//shop.setB2(shop.initPriceB2);
				//shop.setB3(shop.initPriceB3);
				handler.clearEnemies();
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				
				game.diff = 0;
				
				AudioPlayer.getSound("menu_sound").play();
			}
			// hard button
			else if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 250, 200, 64)) {
				Game.gameState = STATE.Game;
				handler.addObject(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				
				game.diff = 1;
				
				AudioPlayer.getSound("menu_sound").play();
			}
			// back button
			else if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)) {
				Game.gameState = STATE.Menu;
				
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
		}
		if(Game.gameState == STATE.Help) {
			// back button for help
			if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)) {
				Game.gameState = STATE.Menu;
				
				AudioPlayer.getSound("menu_sound").play();
				return;
			}
		}
		if(Game.gameState == STATE.Win) {
			// Back to menu button
			if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)) {
				Game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				handler.clearEnemies();				
				AudioPlayer.getSound("menu_sound").play();
				for(int i = 0; i < 15; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.MenuParticle, handler));
				}
			}
		}
		if(Game.gameState == STATE.End) {
			// Back to menu button
			if(mouseOver(mx, my, Game.WIDTH / 2 - 100, 350, 200, 64)) {
				Game.gameState = STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				handler.clearEnemies();				
				AudioPlayer.getSound("menu_sound").play();
				for(int i = 0; i < 15; i++) {
					handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.MenuParticle, handler));
				}
			}
		}
	}
	public void mouseRelease(MouseEvent e) {
		
	}
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public void tick() {
		
	}
	public void render(Graphics g) {
		Font fnt = new Font("Arial", 1, 50);
		Font fnt2 = new Font("Arial", 1, 30);
		Font fnt3 = new Font("Arial", 1, 25);
		
		g.setFont(fnt);
		g.setColor(Color.white);
		
		if(Game.gameState == STATE.Menu) {
			g.drawString("Wave", 255, 70);
			
			g.setFont(fnt2);
			g.drawRect(Game.WIDTH / 2 - 100, 150, 200, 64);
			g.drawString("Play", 290, 190);
			
			g.drawRect(Game.WIDTH / 2 - 100, 250, 200, 64);
			g.drawString("Help", 290, 290);
			
			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Quit", 290, 390);
		}else if(Game.gameState == STATE.Help) {
			g.drawString("Help", 265, 70);
			
			g.setFont(fnt3);
			g.drawString("Use WASD keys", 230, 200);
			g.drawString("Dodge the enemies and survive!", 130, 250);
			
			g.setFont(fnt2);
			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Back", 287, 390);
		}else if(Game.gameState == STATE.End) {
			g.drawString("Game Over", 185, 70);
			
			g.setFont(fnt3);
			g.drawString("You lost with a score of " + hud.getScore(), 150, 250);
			
			g.setFont(fnt2);
			g.drawRect(Game.WIDTH / 2 - 105, 350, 210, 64);
			g.drawString("Back to Menu", 224, 390);								
		} else if (Game.gameState == STATE.Win) {
			// win code
			g.drawString("You WON!", 185, 70);
			g.setFont(fnt3);
			g.drawString("You won with a score of " + hud.getScore(), 150, 250);
			g.setFont(fnt2);
			g.drawRect(Game.WIDTH / 2 - 105, 350, 210, 64);
			g.drawString("Back to Menu", 224, 390);
		}else if(Game.gameState == STATE.Select) {
			g.setFont(fnt);
			g.drawString("Select Difficulty", 130, 70);
			
			g.setFont(fnt2);
			g.drawRect(Game.WIDTH / 2 - 100, 150, 200, 64);
			g.drawString("Normal", 270, 190);
			
			g.drawRect(Game.WIDTH / 2 - 100, 250, 200, 64);
			g.drawString("Hard", 285, 290);
			
			g.drawRect(Game.WIDTH / 2 - 100, 350, 200, 64);
			g.drawString("Back", 285, 390);	
		}
	}	
}