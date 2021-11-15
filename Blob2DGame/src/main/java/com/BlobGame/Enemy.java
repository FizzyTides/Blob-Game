package com.BlobGame;

import java.awt.Point;

public class Enemy extends Entities {
	
	boolean playerAbove, playerBelow, playerRight, playerLeft = false;
	double lastTime = 0;
	double coolDownInMillis = -400;
	//int access_switch = 0; //0 is for up/down access, 1 is for left/right access
	int sw = 0; //0 is for up/down access, 1 is for left/right access
	
	Enemy(Point pos) {
		this.imageName = "Enemy.png";
		this.pos = pos;
		
		loadImage();
	}
	
	public Point getEnemyPos() {
		return this.pos;	
	}
	
	//int sw = this.access_switch;
	public void enemyMovement() { 
		System.out.println("sw == " + sw);
		double now = System.currentTimeMillis();
		
		//player is above
		if(playerAbove && (lastTime - now < coolDownInMillis) && sw==0) {
			pos.translate(0, -1);
			lastTime = System.currentTimeMillis();
			sw = 1;
		}
		//player is below
		else if(playerBelow && (lastTime - now < coolDownInMillis) && sw==0) {
			pos.translate(0, 1);
			lastTime = System.currentTimeMillis();
			sw = 1;
		}
		//player on same row (or switch not 0 --> time to switch)
		else if(lastTime - now < coolDownInMillis){
			sw = 1;
		}
			
		//player is to right
		if(playerRight && (lastTime - now < coolDownInMillis) && sw==1) {
			pos.translate(1, 0);
			lastTime = System.currentTimeMillis();
			sw = 0	;
		}
		//player is to left
		else if(playerLeft && (lastTime - now < coolDownInMillis) && sw==1) {
			pos.translate(-1, 0);
			lastTime = System.currentTimeMillis();
			sw = 0;
			}
		//player on same columns (or switch not 1 --> time to switch)
		else if(lastTime - now < coolDownInMillis){
			sw = 0;
		}
				
	}
	
	
	

}
