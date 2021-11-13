package com.BlobGame;


import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class Player {
	
	private BufferedImage playerImage;
	private Point pos; // Player position on grid
	private int score;
	
	public Player() {
		loadImage();
		pos = new Point(0,0);
		score = 0;

	}
	
	private void loadImage() {
		try {
			playerImage = ImageIO.read(new File("src/main/resources/Blob.png"));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	public void drawPlayer(Graphics g, ImageObserver watcher) {
		g.drawImage(playerImage, pos.x * GameBoard.TILE_SIZE, pos.y * GameBoard.TILE_SIZE, watcher);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.)
	}
}
