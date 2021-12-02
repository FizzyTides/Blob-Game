package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
		//Move Player to Punishment Tile
		tempPlayer.pos.translate(1,0);
		Assertions.assertEquals(tempPunishment.getPos(), tempPlayer.getPos(), "Player did not hit Punishment Tile");
		
	}
	
	@Test
	void test() {
		
	}
}
