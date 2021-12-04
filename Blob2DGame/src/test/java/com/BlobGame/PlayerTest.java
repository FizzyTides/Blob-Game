package com.BlobGame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.awt.event.KeyEvent;
import java.awt.Point;

class PlayerTest {

	@Test
	void testFrozenTimer() {
		System.out.println("Testing FrozenTimer()");
		int timeElapsed = 4;
		//Start off frozen for the frozen timer duration
		boolean frozenCheck = true;
		int punishmentFreezeTime = 5; // Freeze duration
		long frozenStartTime = -punishmentFreezeTime; // In actual game, frozenStartTime gets marked upon hitting punishment
		frozenStartTime = 0; // Player gets frozen at the start of the game
		//If the current time passed surpasses the time frozen + duration of freeze, melt
		if(frozenStartTime + punishmentFreezeTime > timeElapsed) {
			System.out.println("Frozen Time not met, FROZEN!");
			frozenCheck = true;
			Assertions.assertTrue(frozenCheck);
			//Time passed 5 seconds simulated, actual using System.millis
			timeElapsed += 5;
			if(timeElapsed > frozenStartTime + punishmentFreezeTime) {
				frozenCheck = false;
				System.out.println("Frozen Time exceeded! UNFREEZING!");
				Assertions.assertFalse(frozenCheck);
			}
		}
		//Asserting false as we want to melt once the set duration has passed currently set to 1000 ms
		Assertions.assertFalse(frozenCheck, "STILL FROZEN!");
	}
	
	@Test
    public void testKeyPressed() {
		System.out.println("Testing KeyPressed()");
        //Not using real timer --> so not testing coolDownTime as it is based on our in game tick
        boolean wallNorth, wallSouth, wallEast, wallWest;
        wallNorth = true; //There is a wall to the North (above)
        wallSouth = false; //No wall South
        wallEast = false; //No wall East
        wallWest = false; //No wall West
        
        boolean pause = false; //Game not paused
        int key = KeyEvent.VK_W; //Pressing W (up)
        
        Point tempPos = new Point(10,7);
        Player tempPlayer = new Player();
        tempPlayer.pos = tempPos;
        
        if(!pause && key == KeyEvent.VK_W && !wallNorth) {
            tempPlayer.pos.translate(0, -1);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_S && !wallSouth) {
            tempPlayer.pos.translate(0, 1);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_A && !wallEast) {
            tempPlayer.pos.translate(-1,0);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_D && !wallWest) {
            tempPlayer.pos.translate(1,0);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        Assertions.assertEquals(tempPlayer.pos, tempPos, "Player moved!");
        
        
        // Now try with no wall to the North 
        wallNorth = false;
        Point finalPos = new Point(10,6);
        
        if(!pause && key == KeyEvent.VK_W && !wallNorth) {
            tempPlayer.pos.translate(0, -1);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_S && !wallSouth) {
            tempPlayer.pos.translate(0, 1);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_A && !wallEast) {
            tempPlayer.pos.translate(-1,0);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        if(!pause && key == KeyEvent.VK_D && !wallWest) {
            tempPlayer.pos.translate(1,0);
            System.out.println("Player Position: " + tempPlayer.pos.x + "," + tempPlayer.pos.y);
        }
        
        Assertions.assertEquals(tempPlayer.pos,finalPos, "Player didn't move North!");
    }

}
