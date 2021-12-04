package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.time.Duration;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class GameBoardTest {
	GameBoard board = new GameBoard();
	
	@Test
	void testSpawnWalls() {
		System.out.println("Testing SpawnWalls()");
		Point wallPos = new Point(2,2);
		Wall testWall = new Wall(wallPos);
		Assertions.assertEquals(wallPos, testWall.getPos(), "Expected Wall did not spawn in the correct location...");
		
	}
	
	@Test
	void testHitPunishment() {
		System.out.println("Testing HitPunishment()");
		Point tempPos = new Point(1,1);
		Player tempPlayer = new Player();
		Punishment tempPunishment = new Punishment(tempPos);
		boolean playerHit = false;
		boolean tPort = false;
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
	void testCheckWalls() {
		System.out.println("Testing CheckWalls!");
		ArrayList<Wall> testWalls = new ArrayList<Wall>();
		boolean aWallNorth, aWallSouth, aWallWest, aWallEast;
		aWallNorth = aWallSouth = aWallWest = aWallEast = false;
		Player testPlayer = new Player();
		Point origin = new Point(1,1);
		testPlayer.setPos(origin);
		Assertions.assertEquals(origin, testPlayer.getPos(), "Player did not spawn at origin");
		
		Wall wallN = new Wall(new Point(1,0));
		Wall wallS = new Wall(new Point(1,2));
		Wall wallE = new Wall(new Point(2,1));
		Wall wallW = new Wall(new Point(0,2));
		
		testWalls.add(wallN);
		testWalls.add(wallS);
		testWalls.add(wallE);
		testWalls.add(wallW);
		
		//If it works for one wall should work for all walls
		Assertions.assertEquals(new Point(1,0), wallN.getPos(), "WallNorth spawned incorrectly!");
		
        for(Wall walls : testWalls) {
            Point wallCurrPos = walls.getPos();
            

            if((testPlayer.pos.y - 1 == wallCurrPos.y && testPlayer.pos.x == wallCurrPos.x) || aWallNorth) {
            	testPlayer.wallNorth = true;
                aWallNorth = true;
                Assertions.assertTrue(aWallNorth, "There is no wall North");
                Assertions.assertTrue(testPlayer.wallNorth, "There is no wall North");
                System.out.println("There is a wall North!");
            }
            else {
            	testPlayer.wallNorth = false;
            	Assertions.assertFalse(testPlayer.wallNorth);
            	System.out.println("There is not a wall North!");
            }
            if((testPlayer.pos.y + 1 == wallCurrPos.y && testPlayer.pos.x == wallCurrPos.x) || aWallSouth) {
            	testPlayer.wallSouth = true;
                aWallSouth = true;
                Assertions.assertTrue(aWallSouth, "There is no wall South");
                Assertions.assertTrue(testPlayer.wallSouth, "There is no wall South");
                System.out.println("There is a wall South!");
            }
            else {
            	testPlayer.wallSouth = false;
            	Assertions.assertFalse(testPlayer.wallSouth);
            	System.out.println("There is not a wall South!");
            }
            if((testPlayer.pos.x + 1 == wallCurrPos.x &&testPlayer.pos.y == wallCurrPos.y) || aWallEast) {
            	testPlayer.wallEast = true;
                aWallEast = true;
                Assertions.assertTrue(aWallEast, "There is no wall East");
                Assertions.assertTrue(testPlayer.wallEast, "There is no wall East");
                System.out.println("There is a wall East!");
            }
            else {
            	testPlayer.wallEast = false;
            	Assertions.assertFalse(testPlayer.wallEast);
            	System.out.println("There is not a wall East!");
            }
            if((testPlayer.pos.x - 1 == wallCurrPos.x && testPlayer.pos.y == wallCurrPos.y) || aWallWest) {
            	testPlayer.wallWest = true;
                aWallWest = true;
                Assertions.assertTrue(aWallWest, "There is no wall West");
                Assertions.assertTrue(testPlayer.wallWest, "There is no wall West");
                System.out.println("There is a wall West!");
            }
            else {
            	testPlayer.wallWest = false;
            	Assertions.assertFalse(testPlayer.wallWest, "This should be false!");
            	System.out.println("There is not a wall West!");
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
	
}

