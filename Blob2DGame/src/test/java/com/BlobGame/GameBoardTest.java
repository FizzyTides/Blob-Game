package com.BlobGame;

import java.awt.Point;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * GameBoardTest class
 * Tests all unit and integration features from GameBoard.java
 * @author Mca
 * @author Mba
 * @author ketan
 *
 */

class GameBoardTest {
	GameBoard board = new GameBoard();
	
	@Test
    void testSpawn() {
        System.out.println("Testing SpawnWalls()");
        Point testPos = new Point(2,2);
        Wall testWall = new Wall(testPos);
        Assertions.assertEquals(testPos, testWall.getPos(), "Expected Wall did not spawn in the correct location...");

        System.out.println("Testing SpawnRewards()");
        testPos.setLocation(1,1);
        Cake testCake = new Cake(testPos);
        Assertions.assertEquals(testPos, testCake.getPos(), "Expected Cake did not spawn in the correct location...");

        System.out.println("Testing SpawnPunishments()");
        testPos.setLocation(3,1);
        Punishment testPunishment = new Punishment(testPos);
        Assertions.assertEquals(testPos, testPunishment.getPos(), "Expected Punishment did not spawn in the correct location...");

        System.out.println("Testing SpawnEnemies()");
        testPos.setLocation(7,1);
        Enemy testEnemy = new Enemy(testPos, "MomEnemy.png");
        Assertions.assertEquals(testPos, testEnemy.getPos(), "Expected Enemy did not spawn in the correct location...");

        System.out.println("Testing SpawnRewards() For Bonus Reward");
        testPos.setLocation(3,8);
        BonusReward testReward = new BonusReward(testPos);
        Assertions.assertEquals(testPos, testReward.getPos(), "Expected Bonus Reward did not spawn in the correct location...");
    }
	
	@Test
	void testPlayerWin() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
		int cakeCount = 9;
		final int NUM_CAKES = 10;
		Player testPlayer = new Player();
		Gate gate = new Gate(new Point(1,1));
		ExitTile winTile = new ExitTile(new Point(0,1));
		boolean win = false;
		cakeCount++;
		walls.add(gate);
		if(cakeCount == NUM_CAKES) {
			System.out.println("YOU GOT ALL THE CAKES! GATE IS REMOVED");
			walls.remove(gate);
			Assertions.assertEquals(0, walls.size(), "Gate not removed from game");
		}
		if(walls.size() == 0 && testPlayer.getPos().x == winTile.getPos().x && testPlayer.getPos().y == winTile.getPos().y) {
			System.out.println("YOU WIN");
			win = true;
			Assertions.assertTrue(win);
		}
	}
	
	@Test
	void testPlayerLose() {
		System.out.println("Testing PlayerLose");
		Player testPlayer = new Player();
		boolean lost = false;
		testPlayer.setScore(-1); //Initialized Player score to simulate lose
		Assertions.assertEquals(-1, testPlayer.getScore());
		
		if(testPlayer.getScore() < 0) {
			lost = true;
			Assertions.assertTrue(lost, "Player did not lose with score < 0");
		}
	}
	
	@Test
	void testEnemyKillPlayer() {
		System.out.println("Testing EnemyKillPlayer");
		Enemy e = new Enemy(new Point(1,1), null);
		Player testPlayer = new Player();
		boolean isDead = false;
		testPlayer.pos.translate(1,0);
		System.out.println("Player moving to (1,1)");
		
		if(testPlayer.pos.x == e.getPos().x && testPlayer.getPos().y == e.getPos().y) {
			Assertions.assertEquals(e.getPos(), testPlayer.getPos(), "Positions do not match!");
			isDead = true;
			System.out.println("GAMEOVER PLAYER IS DEAD");
			Assertions.assertTrue(isDead);
		}
	}
	
	@Test
	void testHitPunishment() {
		System.out.println("Testing HitPunishment()");
		Point tempPos = new Point(1,1);
		Player tempPlayer = new Player();
		Punishment tempPunishment = new Punishment(tempPos);
		boolean playerHit = false;
		//Move Player to Punishment Tile
		tempPlayer.pos.translate(1,0);
		tempPlayer.addScore(300);
		if(tempPlayer.getPos().equals(tempPunishment.getPos())) {
			System.out.println("Player hit Punishment!");
			playerHit = true;
			tempPlayer.deductScore(tempPunishment.getPenalty());
			Assertions.assertEquals(200, tempPlayer.getScore(), "Score not deducted correctly!");
		}
		Assertions.assertTrue(playerHit, "Player did not hit Punishment Tile");
		
	}
	
	@Test
	void testHitTelePunishment() {
		System.out.println("Testing HitTelePunishment()");
		Point tempPos = new Point(1,1);
		Player tempPlayer = new Player();
		Punishment tempPunishment = new TelePunishment(tempPos);
		boolean playerHit = false;
		boolean tPort = false;
		//Move Player to Punishment Tile
		tempPlayer.pos.translate(1,0);
		tempPlayer.addScore(300);
		if(tempPlayer.getPos().equals(tempPunishment.getPos())) {
			System.out.println("Player hit Punishment!");
			playerHit = true;
			tempPlayer.deductScore(tempPunishment.getPenalty());
			Assertions.assertEquals(250, tempPlayer.getScore(), "Score not deducted correctly!");
			if(tempPunishment instanceof TelePunishment) {
				tPort = true;
				Assertions.assertTrue(tPort, "TelePunishment not activated!");
			}
			else {
				tPort = false;
				Assertions.assertFalse(tPort, "Is teleporting");
			}
		}
		Assertions.assertTrue(playerHit, "Player did not hit Punishment Tile");
	}
	
	@Test
    void testEnemyDirection() {
        System.out.println("Testing EnemyDirection()");
        
        Point middlePoint = new Point(10,7);
        
        Point topLeft = new Point(0,0); //i=0 //Player is in top left
        Point topRight = new Point(19,0); //i=1 //Player is in top right
        Point bottomLeft = new Point(0,14); //i=2 //Player is in bottom left
        Point bottomRight = new Point(19,14); //i=3 //Player is in bottom right
        int testPlayerPoints = 4;
        
        Player tempPlayer = new Player();
        Enemy tempEnemy = new Enemy(middlePoint, "MomEnemy.png");
        Point enemyCurrPos = tempEnemy.getPos();
        
        int i=0;
        while(i < testPlayerPoints) {
            Point playerCurrPos = new Point(0,0);
            if(i == 0) {
                //Player is in top left & Enemy in middle
                tempPlayer.pos = topLeft;
                playerCurrPos = tempPlayer.getPos();
            }
            if(i == 1) {
                //Player is in top right & Enemy in middle
                tempPlayer.pos = topRight;
                playerCurrPos = tempPlayer.getPos();
            }
            if(i == 2) {
                //Player is in bottom left & Enemy in middle
                tempPlayer.pos = bottomLeft;
                playerCurrPos = tempPlayer.getPos();
            }
            if(i == 3) {
                //Player is in bottom right & Enemy in middle
                tempPlayer.pos = bottomRight;
                playerCurrPos = tempPlayer.getPos();
            }
            
            //player is above
            if(playerCurrPos.y < enemyCurrPos.y) {
                tempEnemy.playerAbove = true;
                tempEnemy.playerBelow = false;
                System.out.println("player is above");
            }
            //player is below
            else if(playerCurrPos.y > enemyCurrPos.y) {
                tempEnemy.playerBelow = true;
                tempEnemy.playerAbove = false;
                System.out.println("player is below");
            }
            //player on same row (or switch not 0 --> time to switch)
            else {
                tempEnemy.playerAbove = false;
                tempEnemy.playerBelow = false;
                System.out.println("player is on same row");
            }
            
            //player is to right
            if(playerCurrPos.x > enemyCurrPos.x) {
                tempEnemy.playerRight = true;
                tempEnemy.playerLeft = false;
                System.out.println("player is to right");
            }
            //player is to left
            else if(playerCurrPos.x < enemyCurrPos.x) {
                tempEnemy.playerLeft = true;
                tempEnemy.playerRight = false;
                System.out.println("player is to left");
            }
            //player on same columns (or switch not 1 --> time to switch)
            else {
                tempEnemy.playerRight = false;
                tempEnemy.playerLeft = false;
                System.out.println("player is on same column");
            }
            
            if(i == 0) { //Player is in top left
                //Should be true
                Assertions.assertTrue(tempEnemy.playerAbove, "Player is not Above!");
                Assertions.assertTrue(tempEnemy.playerLeft, "Player is not to Left!");
                
                //Should be false
                Assertions.assertFalse(tempEnemy.playerBelow, "Player is Below!");
                Assertions.assertFalse(tempEnemy.playerRight, "Player is to Right!");
            }
            
            if(i == 1) { //Player is in top right
                //Should be true
                Assertions.assertTrue(tempEnemy.playerAbove, "Player is not Above!");
                Assertions.assertTrue(tempEnemy.playerRight, "Player is not to Right!");
                
                //Should be false
                Assertions.assertFalse(tempEnemy.playerBelow, "Player is Below!");
                Assertions.assertFalse(tempEnemy.playerLeft, "Player is to Left!");
            }
            
            if(i == 2) { //Player is in bottom left
                //Should be true
                Assertions.assertTrue(tempEnemy.playerBelow, "Player is not Below!");
                Assertions.assertTrue(tempEnemy.playerLeft, "Player is not to Left!");
                
                //Should be false
                Assertions.assertFalse(tempEnemy.playerAbove, "Player is Above!");
                Assertions.assertFalse(tempEnemy.playerRight, "Player is to Right!");    
            }
            
            if(i == 3) { //Player is in bottom right
                //Should be true
                Assertions.assertTrue(tempEnemy.playerBelow, "Player is not Below!");
                Assertions.assertTrue(tempEnemy.playerRight, "Player is not to Right!");
                
                //Should be false
                Assertions.assertFalse(tempEnemy.playerAbove, "Player is Above!");
                Assertions.assertFalse(tempEnemy.playerLeft, "Player is to Left!");    
            }
            
            i++;
        }
    }
	
	@Test
	void testEnemyCheckEnemy() {
		System.out.println("Testing EnemyCheckEnemy");
		ArrayList<Enemy> testEnemies = new ArrayList<Enemy>();
		Enemy e1 = new Enemy(new Point(1,1), null);
		Enemy e2 = new Enemy(new Point(1,2), null);
		Enemy e3 = new Enemy(new Point(2,2), null);
		testEnemies.add(e1);
		testEnemies.add(e2);
		testEnemies.add(e3);
		
		for(Enemy enemy : testEnemies) {
			Point currEnemyPos = enemy.getPos();
	        boolean aEnemyNorth, aEnemySouth, aEnemyEast, aEnemyWest;
	        aEnemyNorth = aEnemySouth = aEnemyEast = aEnemyWest = false;
	        for(Enemy otherEnemy : testEnemies) {
	            Point otherEnemyPos = otherEnemy.getPos();
	            

	            if((currEnemyPos.y - 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemyNorth) {
	            	enemy.otherEnemyNorth = true;
	                aEnemyNorth = true;
	                Assertions.assertTrue(enemy.otherEnemyNorth);
	                Assertions.assertTrue(aEnemyNorth);
	                System.out.println("There is an enemy North!");
	            }
	            else {
	            	enemy.otherEnemyNorth = false;
	            	Assertions.assertFalse(enemy.otherEnemyNorth);
	            }
	            if((currEnemyPos.y + 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemySouth) {
	            	enemy.otherEnemySouth = true;
	                aEnemySouth = true;
	                Assertions.assertTrue(enemy.otherEnemySouth);
	                Assertions.assertTrue(aEnemySouth);
	                System.out.println("There is an enemy to the South!");
	            }
	            else {
	            	enemy.otherEnemySouth = false;
	            	Assertions.assertFalse(enemy.otherEnemySouth);
	            }
	            if((currEnemyPos.x + 1== otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyEast) {
	            	enemy.otherEnemyEast = true;
	                aEnemyEast = true;
	                Assertions.assertTrue(enemy.otherEnemyEast);
	                Assertions.assertTrue(aEnemyEast);
	                System.out.println("There is an enemy to the East!");
	            }
	            else {
	            	enemy.otherEnemyEast = false;
	            	Assertions.assertFalse(enemy.otherEnemyEast);
	            }
	            if((currEnemyPos.x - 1 == otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyWest) {
	            	enemy.otherEnemyWest = true;
	                aEnemyWest = true;
	                Assertions.assertTrue(enemy.otherEnemyWest);
	                Assertions.assertTrue(aEnemyWest);
	                System.out.println("There is an enemy to the West!");
	            }
	            else {
	            	enemy.otherEnemyWest = false;
	            	Assertions.assertFalse(enemy.otherEnemyWest);
	            }
	        }
		}
	}
	
	@Test
	void testBonusVisibility() {
		System.out.println("Testing BonusVisibility()");
		Point tempPos = new Point(1,1);
		Cake tempBonusReward = new Cake(tempPos);
		int  gameTimeElapsed = 5;
		double rand = 5;
		double bonusStayTime = 5;

		tempBonusReward.isBonus = true;
		//gameTimeElaped == rand --> Cake has spawned (& it is a bonus reward)
		if((tempBonusReward.isBonus == true) && (gameTimeElapsed == rand)) {
			tempBonusReward.visibility = true;
			Assertions.assertTrue(tempBonusReward.visibility, "Bonus Reward not visible!");
		}
		
		gameTimeElapsed += 5; // 5 more seconds has elapsed  --> Cake should now un-spawn
		if((tempBonusReward.isBonus == true) && (gameTimeElapsed == (rand + bonusStayTime)) && tempBonusReward.visibility == true) {
			tempBonusReward.visibility = false;
			Assertions.assertFalse(tempBonusReward.visibility, "Bonus Reward has not disappeared!");
		}
		Assertions.assertTrue(tempBonusReward.isBonus, "Reward was not a Bonus Reward");
	}
	
	
	@Test
    void testCollectRewards() {
        System.out.println("Testing CollectRewards()");
        Point tempPos = new Point(1,1);
        Player tempPlayer = new Player();
        Cake tempCake = new Cake(tempPos);
        boolean playerHit = false;
        //Move Player to Reward Tile
        tempPlayer.pos.translate(1,0);
        if(tempPlayer.getPos().equals(tempCake.getPos())) {
            System.out.println("Player Collected Rewards!");
            playerHit = true;
            tempPlayer.addScore(tempCake.getValue());
            Assertions.assertEquals(100, tempPlayer.getScore(), "Score not deducted correctly!");
        }
        Assertions.assertTrue(playerHit, "Player did not hit Punishment Tile");
    }
}

