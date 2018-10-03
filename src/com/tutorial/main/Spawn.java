package com.tutorial.main;

import java.util.Random;

import org.lwjgl.opengl.GREMEDYStringMarker;

import com.tutorial.main.Game.STATE;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private int scoreKeep = 0;
	private Random r = new Random();
	private Game game;
	
	public Spawn(Handler handler, HUD hud, Game game) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
	}
	
	public void tick() {
		scoreKeep++;
		if(scoreKeep > 250) {
			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			if(game.diff == 0) {
				if(hud.getLevel() == 2) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				}
				else if(hud.getLevel() == 3) {
					handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				}
				else if(hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.SmartEnemy, handler));
				}
				else if(hud.getLevel() == 6) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 7) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 10) {
					handler.clearEnemies();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 48, -120, ID.EnemyBoss, handler));
				} else if(hud.getLevel() == 8) {
					handler.clearEnemies();
					Game.gameState = STATE.End;
				}
				
			}else if(game.diff == 1) {
				if(hud.getLevel() == 2) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				}
				else if(hud.getLevel() == 3) {
					handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.BasicEnemy, handler));
				}
				else if(hud.getLevel() == 4) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 5) {
					handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.SmartEnemy, handler));
				}
				else if(hud.getLevel() == 6) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 7) {
					handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 38), r.nextInt(Game.HEIGHT - 62), ID.FastEnemy, handler));
				}
				else if(hud.getLevel() == 10) {
					handler.clearEnemies();
					handler.addObject(new EnemyBoss((Game.WIDTH / 2) - 48, -120, ID.EnemyBoss, handler));
				}
			}
		}
	}
}
