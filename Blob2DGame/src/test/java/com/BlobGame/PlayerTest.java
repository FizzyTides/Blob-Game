package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest {

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
