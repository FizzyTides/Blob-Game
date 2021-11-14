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
	
	double lastTime = 0;
	double coolDownInMillis = -400;
	private int score;
	
	public Player() {
		
		this.imageName = "Blob.png";
		this.pos = new Point(0,0);
		score = 0;
		
		
		loadImage();
	}
	
	public void keyPressed(KeyEvent e) {
		
		double now = System.currentTimeMillis();
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W  && (lastTime - now < coolDownInMillis)) {
            pos.translate(0, -1);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_S && (lastTime - now < coolDownInMillis)) {
            pos.translate(0, 1);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_A && (lastTime - now < coolDownInMillis)) {
            pos.translate(-1,0);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(key == KeyEvent.VK_D && (lastTime - now < coolDownInMillis)) {
            pos.translate(1,0);
            System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();

        }
	}

	public void addScore(int amount) {
        score += amount;
    }
}