package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas {
	
	// generated serial ID
	// dont know what its for !!
	private static final long serialVersionUID = -240840600533728354L;
	
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// null starts the window in the middle of the screen
		// not in the left upper corner
		frame.setLocationRelativeTo(null);
		// need to have an instance of game itself inside this class
		// it was passed in the constructor's args
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
}
