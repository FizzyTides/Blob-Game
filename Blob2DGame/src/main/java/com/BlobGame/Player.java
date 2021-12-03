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

/**
 * This class includes all the functions we need for our Player.
 * 
 * @author mba
 * @author mca
 * @author ketan
 */
public class Player extends Entities {
	/**
     * Variables needed for the class are instantiated/set
     * 
     */
	double lastTime = 0;
	double coolDownInMillis = -100;
	private int score;
	public boolean pause;
    public int punishmentFreezeTime = 1000; //in milliseconds 
    public double frozenStartTime = -punishmentFreezeTime;
	
	public Player() {
		
		this.imageName = "Blob.png";
		this.pos = new Point(0,1);
		score = 0;
		
		
		loadImage();
	}
	
	/**
	 * KEYPRESSED METHOD FOR PLAYER
     * Takes the users keyboard input and moves the player accordingly:
     * W key moves the player one tile North.
     * S key moves the player one tile South.
     * A key moves the player one tile West.
     * D key moves the player one tile East.
     *
     * The following restricts how the player can move:
     * 1. The player cannot be paused (by either the pause feature or the freeze feature).
     * 2. The player cannot be blocked by a wall.
     * 3. The player cannot move 'again' until the cool-down time passes.
     * 
     */
	public void keyPressed(KeyEvent e) {
		
		double now = System.currentTimeMillis();
        int key = e.getKeyCode();

        if(!pause && key == KeyEvent.VK_W  && (lastTime - now < coolDownInMillis) && !wallNorth) {
            pos.translate(0, -1);
            //System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(!pause && key == KeyEvent.VK_S && (lastTime - now < coolDownInMillis) && !wallSouth) {
            pos.translate(0, 1);
           // System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(!pause && key == KeyEvent.VK_A && (lastTime - now < coolDownInMillis) && !wallEast) {
            pos.translate(-1,0);
           // System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
        if(!pause && key == KeyEvent.VK_D && (lastTime - now < coolDownInMillis) && !wallWest) {
            pos.translate(1,0);
         //   System.out.println("Player Position: " + pos.x + "," + pos.y);
            lastTime = System.currentTimeMillis();
        }
	}
	
	public void frozenCheck() {
        if((frozenStartTime + punishmentFreezeTime) > System.currentTimeMillis()) {
       
            //still frozen
        	this.imageName = "FrozenPlayer.png";
        	this.loadImage();
            this.pause = true;
        }
        //not frozen anymore
        else {
            this.pause = false;
            this.imageName = "Blob.png";
            this.loadImage();
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

    public void setScore(int value) {
    	this.score = value;
    }
	
}
