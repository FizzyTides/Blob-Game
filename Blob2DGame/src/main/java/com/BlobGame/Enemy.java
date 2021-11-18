package com.BlobGame;

import java.awt.Point;

/**ENEMY CLASS
 * This is a subclass of Entities which solely handles enemy functionalities
 * 
 * @author mca
 * @author mba
 * @author ketan
 *
 */
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
	
	/**
     * ENEMY MOVEMENT 
     * This uses the booleans instantiated above (and constantly updated in GameBoard) to determines where on the
     * board the player is located in relation to the enemies.
     * The enemies also know the direction of the other enemies.
     * 
     * The the enemies move 1 tile in direction of the player with the following restrictions:
     * 1. There is no wall blocking them.
     * 2. There is no other enemy blocking them
     * 3. Cannot move 'again' until the cool-down time passes.
     * 4. If the enemy moved in a different direction last movement, try to move in a different direction if possible
     * (using the directionSwitch boolean0.
     * 
     */
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
