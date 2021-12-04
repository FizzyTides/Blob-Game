package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void testFrozenTimer() {
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

}
