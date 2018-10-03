package com.tutorial.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	public int spd = 5;
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			// creates a temporary object which is grabbed from the list
			GameObject tempObject = object.get(i);
			// this tick() is from objects own class'es method
			tempObject.tick();
		}
	}
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			// this render() is from object's own methods
			tempObject.render(g);
		}
	}
	
	public void clearEnemies() {

		if(Game.gameState == Game.STATE.End || Game.gameState == Game.STATE.Win) {
			object.clear();
		}		
		else {
			for(int i = 0; i < object.size(); i++) {
				GameObject tempObject = object.get(i);
			
				if (tempObject.getId() != ID.Player) {
					removeObject(tempObject);
					i--;
				}
			}
		}
	}
	
	// IDK why you need separate methods for this...
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
