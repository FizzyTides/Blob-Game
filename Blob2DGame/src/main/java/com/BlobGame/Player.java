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
	
	boolean wallNorth, wallSouth, wallEast, wallWest = false;
	double lastTime = 0;
	double coolDownInMillis = -100;
	private int score;
	
	public Player() {
		
		this.imageName = "Blob.png";
		this.pos = new Point(0,1);
		score = 0;
		
		
		loadImage();
	}
	
	public void keyPressed(KeyEvent e) {
		
		double now = System.currentTimeMillis();
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W  && (lastTime - now < coolDownInMillis) && !wallNorth) {
            pos.translate(0, -1);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_S && (lastTime - now < coolDownInMillis) && !wallSouth) {
            pos.translate(0, 1);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_A && (lastTime - now < coolDownInMillis) && !wallEast) {
            pos.translate(-1,0);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_D && (lastTime - now < coolDownInMillis) && !wallWest) {
            pos.translate(1,0);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();

        }
	}

	public void addScore(int amount) {
        score += amount;
    }

    public void deductScore(int amount) {
        score -= amount;
    }
	
	public int getScore() {
		return this.score; 
	}

    
	
	public Point getPlayerPos() {
		return this.pos;
	}
}
