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
	
	@Test // This test uses similar functionality to collectRewards(), 
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
	void testFrozenCheck() {
		//Start off frozen for the frozen timer duration
		boolean frozenCheck = true;
		int punishmentFreezeTime = 1000; // Freeze duration
		long frozenStartTime = -punishmentFreezeTime; // In actual game, frozenStartTime gets marked upon hitting punishment
		frozenStartTime = 0; // Player gets frozen at the start of the game
		//If the current time passed surpasses the time frozen + duration of freeze, melt
		if(frozenStartTime + punishmentFreezeTime > System.currentTimeMillis()) {
			System.out.println("Still Frozen!");
			frozenCheck = true;
			
		}
		else {
			System.out.println("Unfrozen!");
			frozenCheck = false;
		}
		//Asserting false as we want to melt once the set duration has passed currently set to 1000 ms
		Assertions.assertFalse(frozenCheck, "STILL FROZEN!");
	}
	
}
