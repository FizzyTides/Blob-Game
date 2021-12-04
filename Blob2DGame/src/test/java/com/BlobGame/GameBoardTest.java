package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.time.Duration;

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
	
	/*@Test
	public void testBonusVisibility() {
		System.out.println("Testing BonusVisibility()");
		for (Cake cake : rewards) {
			if((cake.isBonus == true) && (gameTimeElapsed == rand)) {
				cake.setBonusReward((int) (player.getScore() * 1.5 + 200));
				cake.visibility = true; 
			}
			if((cake.isBonus == true) && (gameTimeElapsed == (rand + bonusStayTime)) && cake.visibility == true) {
				cake.visibility = false; 
			}
		}
		
	}*/
	
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

