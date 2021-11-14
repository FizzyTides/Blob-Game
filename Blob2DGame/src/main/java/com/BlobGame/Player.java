package com.BlobGame;


import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;


import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;


public class Player extends Entities {
	
	private int score;
	
	public Player() {
		
		this.imageName = "Blob.png";
		this.pos = new Point(0,0);
		score = 0;
		
		
		loadImage();
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W) {
			pos.translate(0, -1);
			System.out.println("Player Position: " + pos.x + "," + pos.y);
		}
		if(key == KeyEvent.VK_S) {
			pos.translate(0, 1);
			System.out.println("Player Position: " + pos.x + "," + pos.y);
		}
		if(key == KeyEvent.VK_A) {
			pos.translate(-1,0);
			System.out.println("Player Position: " + pos.x + "," + pos.y);
		}
		if(key == KeyEvent.VK_D) {
			pos.translate(1,0);
			System.out.println("Player Position: " + pos.x + "," + pos.y);
				
		}
	}

	public void addScore(int amount) {
        score += amount;
    }
}
