package com.BlobGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements ActionListener, KeyListener {
	
	private final int DELAY = 500;
	
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	
	public static final int NUM_COINS = 10;
	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
		
		//Background color
		
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		
	}
	
	public void drawBackground(Graphics g) {
		g.setColor(new Color(0,0,0));
		for(int i = 0; i < ROWS * TILE_SIZE; i+=TILE_SIZE) {
			g.drawLine(0, i, TILE_SIZE * COLUMNS, i); // Draws the rows
		}
		
		for(int j = 0; j < COLUMNS * TILE_SIZE; j+=TILE_SIZE) {
			g.drawLine(j, 0, j, TILE_SIZE * COLUMNS); // Draws the columns
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
