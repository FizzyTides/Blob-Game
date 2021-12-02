package com.BlobGame;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class GameTest {

	@Test
	void testInitWindow() {
		JFrame window = new JFrame("Cake Blob");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameBoard board = new GameBoard();
		
		window.add(board);
		
	}

}
