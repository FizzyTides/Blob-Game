package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EntitiesTest {

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

}
