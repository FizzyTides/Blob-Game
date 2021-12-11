package com.BlobGame;

import java.awt.Point;
import java.util.ArrayList;

/**ENEMY CLASS
 * This is a subclass of Entities which solely handles enemy functionalities
 * 
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class Enemy extends MovingObjects {
	
	boolean otherEnemyNorth, otherEnemySouth, otherEnemyEast, otherEnemyWest = false;
	boolean playerAbove, playerBelow, playerRight, playerLeft = false;
	
	double lastTime = 0;
	double coolDownInMillis = -400;
	//int access_directionSwitchitch = 0; //0 is for up/down access, 1 is for left/right access
	int directionSwitch = 0; //0 is for up/down access, 1 is for left/right access
	
	Enemy(Point pos, String imageName) {
		super();
		this.imageName = imageName;
		this.pos = pos;
		
		loadImage();
	}

	/**
	 * ENEMYKILLPLAYER METHOD
	 * @param playerPos, board
	 * Compares all enemy positions in enemy arraylist to player position, if they are =
	 * gameEnd(GAMELOSE) is called ending the game with a lose
	 */
	public void killPlayer(Point playerPos, GameBoard board) {

		if(playerPos.x == this.getPos().x && playerPos.y == this.getPos().y) {
			board.gameEnd(GameBoard.GAMELOSE);
		}
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
	
	/**
	 * ENEMYCHECKENEMIES METHOD
	 * @param enemy, enemies
	 * Similar to checkwalls logic, except uses it for comparing enemy position to eachother so they cannot move onto
	 * the same tile
	 */
	public void checkEnemies(Enemy enemy, ArrayList<Enemy> enemies) {
		Point currEnemyPos = enemy.getPos();
		boolean aEnemyNorth, aEnemySouth, aEnemyEast, aEnemyWest;
		aEnemyNorth = aEnemySouth = aEnemyEast = aEnemyWest = false;
		for(Enemy otherEnemy : enemies) {
			Point otherEnemyPos = otherEnemy.getPos();


			if((currEnemyPos.y - 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemyNorth) {
				enemy.otherEnemyNorth = true;
				aEnemyNorth = true;
				//System.out.println("There is a wall to the North!");
			}
			else {
				enemy.otherEnemyNorth = false;
			}
			if((currEnemyPos.y + 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemySouth) {
				enemy.otherEnemySouth = true;
				aEnemySouth = true;
				//System.out.println("There is a wall to the South!");
			}
			else {
				enemy.otherEnemySouth = false;
			}
			if((currEnemyPos.x - 1== otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyEast) {
				enemy.otherEnemyEast = true;
				aEnemyEast = true;
				//System.out.println("There is a wall to the East!");
			}
			else {
				enemy.otherEnemyEast = false;
			}
			if((currEnemyPos.x + 1 == otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyWest) {
				enemy.otherEnemyWest = true;
				aEnemyWest = true;
				//System.out.println("There is a wall to the West!");
			}
			else {
				enemy.otherEnemyWest = false;
			}
		}
	}
	
	/**
	 * ENEMYDIRECTION METHOD
	 * @param player
	 */
	public void eDirection(Player player) {

		Point playerCurrPos = player.getPos();

		//player is above
		if(playerCurrPos.y < this.pos.y) {
			this.playerAbove = true;
			this.playerBelow = false;
			//System.out.println("player is above");
		}
		//player is below
		else if(playerCurrPos.y > this.pos.y) {
			this.playerBelow = true;
			this.playerAbove = false;
			//System.out.println("player is below");
		}
		//player on same row (or switch not 0 --> time to switch)
		else {
			this.playerAbove = false;
			this.playerBelow = false;
			//System.out.println("player is on same row");
		}

		//player is to right
		if(playerCurrPos.x > this.pos.x) {
			this.playerRight = true;
			this.playerLeft = false;
			//System.out.println("player is to right");
		}
		//player is to left
		else if(playerCurrPos.x < this.pos.x) {
			this.playerLeft = true;
			this.playerRight = false;
			//System.out.println("player is to left");
		}
		//player on same columns (or switch not 1 --> time to switch)
		else {
			this.playerRight = false;
			this.playerLeft = false;
			//System.out.println("player is on same column");
		}
		this.enemyMovement();
	}
	
}
