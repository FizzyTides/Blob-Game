package com.BlobGame;

import java.awt.Point;

public class Enemy extends Entities {
	
	boolean otherEnemyNorth, otherEnemySouth, otherEnemyEast, otherEnemyWest = false;
	boolean playerAbove, playerBelow, playerRight, playerLeft = false;
	
	double lastTime = 0;
	double coolDownInMillis = -400;
	//int access_directionSwitchitch = 0; //0 is for up/down access, 1 is for left/right access
	int directionSwitch = 0; //0 is for up/down access, 1 is for left/right access
	
	Enemy(Point pos, String imageName) {
		this.imageName = imageName;
		this.pos = pos;
		
		loadImage();
	}
	
	public Point getPos() {
		return this.pos;	
	}
	
	//int directionSwitch = this.access_directionSwitchitch;
	public void enemyMovement() { 
		//System.out.println("directionSwitch == " + directionSwitch);
		double now = System.currentTimeMillis();
		
		//player is above
		if(playerAbove && !wallNorth && !otherEnemyNorth && (lastTime - now < coolDownInMillis) && directionSwitch==0) {
			pos.translate(0, -1);
			lastTime = System.currentTimeMillis();
			directionSwitch = 1;
		}
		//player is below
		else if(playerBelow && !wallSouth && !otherEnemySouth && (lastTime - now < coolDownInMillis) && directionSwitch==0) {
			pos.translate(0, 1);
			lastTime = System.currentTimeMillis();
			directionSwitch = 1;
		}
		//player on same row (or directionSwitchitch not 0 --> time to directionSwitchitch)
		else if(lastTime - now < coolDownInMillis){
			directionSwitch = 1;
		}
			
		//player is to right
		if(playerRight && !wallWest && !otherEnemyWest && (lastTime - now < coolDownInMillis) && directionSwitch==1) {
			pos.translate(1, 0);
			lastTime = System.currentTimeMillis();
			directionSwitch = 0	;
		}
		//player is to left
		else if(playerLeft && !wallEast && !otherEnemyEast && (lastTime - now < coolDownInMillis) && directionSwitch==1) {
			pos.translate(-1, 0);
			lastTime = System.currentTimeMillis();
			directionSwitch = 0;
			}
		//player on same columns (or directionSwitchitch not 1 --> time to directionSwitchitch)
		else if(lastTime - now < coolDownInMillis){
			directionSwitch = 0;
		}
				
	}
	
	
	

}
