package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public int bounds = 0;
	public static float HEALTH = 100;
	private float greenValue = 255;
	
	private int score = 0;
	private int level = 1;
	
	public void tick() {

		HEALTH = Game.clamp(HEALTH, 0, 100 + bounds / 2);
		
		greenValue = 255 * HEALTH / 100;
		greenValue = Game.clamp(greenValue, 0, 255);
		
		score++;
	}
	
	public void render(Graphics g) {
		// empty bar
		g.setColor(Color.gray);
		g.fillRect(15, 15, 200 + bounds, 32);
		// currently have bar
		g.setColor(new Color(150, (int)greenValue, 0));
		g.fillRect(15, 15, (int)HEALTH * 2, 32);
		// border
		g.setColor(Color.white);
		g.drawRect(15, 15, 200 + bounds, 32);
		
		// drawing score
		g.drawString("Score: " + score, 15, 64);
		// drawing level
		g.drawString("Level: " + level, 15, 80);
		// drawing shop label
		g.drawString("Space for Shop", 15, 94);
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
}
