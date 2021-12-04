package com.BlobGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.awt.Point;

class EnemyTest {
	
	@Test
	public void testEnemyMovement() { 
		System.out.println("Testing EnemyMovement()");
	     //Not using real timer --> so not testing coolDownTime as it is based on our in game tick
		boolean otherEnemyNorth, otherEnemySouth, otherEnemyEast, otherEnemyWest;
		otherEnemyNorth = false;
		otherEnemySouth = false;
		otherEnemyEast = false;
		otherEnemyWest = false;
		
		boolean playerAbove, playerBelow, playerRight, playerLeft;
		playerAbove = false;
		playerBelow = false;
		playerRight = false;
		playerLeft = false;
		
		boolean wallNorth, wallSouth, wallEast, wallWest;
		wallNorth = false;
		wallSouth = false;
		wallEast = false;
		wallWest = false;
		
		int directionSwitch = 0;
		playerAbove = true; //Player is above
		
        Point tempPos = new Point(0,0);
        Player tempPlayer = new Player();
        tempPlayer.pos = tempPos;
		
        Point enemyTempPos = new Point(0,1);
        Enemy tempEnemy = new Enemy(enemyTempPos, "MomEnemy.png");
        	
        //player is above
      	if(playerAbove && !wallNorth && !otherEnemyNorth && directionSwitch==0) {
      		tempEnemy.pos.translate(0, -1);
      		directionSwitch = 1;
      		System.out.println("move north!");
      	}
      	//player is below
      	else if(playerBelow && !wallSouth && !otherEnemySouth && directionSwitch==0) {
      		tempEnemy.pos.translate(0, 1);
      		directionSwitch = 1;
      		System.out.println("move south!");
      	}
      	//player on same row (or directionSwitchitch not 0 --> time to directionSwitchitch)
      	else {
      		directionSwitch = 1;
      	}
      			
      	//player is to right
      	if(playerRight && !wallWest && !otherEnemyWest && directionSwitch==1) {
      		tempEnemy.pos.translate(1, 0);
      		directionSwitch = 0	;
      		System.out.println("move west!");
      	}
      	//player is to left
      	else if(playerLeft && !wallEast && !otherEnemyEast && directionSwitch==1) {
      		tempEnemy.pos.translate(-1, 0);
      		directionSwitch = 0;
      		System.out.println("move east!");
      		}
      	//player on same columns (or directionSwitchitch not 1 --> time to directionSwitchitch)
      	else {
      		directionSwitch = 0;
      	}
        Assertions.assertEquals(tempEnemy.pos, tempPlayer.pos, "Enemy didn't reach player");
	}
	
}
