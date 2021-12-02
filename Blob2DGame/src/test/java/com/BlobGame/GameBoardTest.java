package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameBoardTest {
	Point wallPos = new Point(2,2);
	
	@Test
	void testSpawnWalls() {
		System.out.println("Testing SpawnWalls()");
		Wall testWall = new Wall(wallPos);
		Assertions.assertEquals(wallPos, testWall.getPos(), "Expected Wall did not spawn in the correct location...");
		
	}
}
