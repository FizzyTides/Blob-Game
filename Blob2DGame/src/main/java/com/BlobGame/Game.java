package com.BlobGame;

import javax.swing.*;
import java.awt.*;


public class Game {
	
	private static void initWindow() {
		JFrame window = new JFrame("Cake Blob"); // New JFrame with game title "Cake Blob"
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Stops game on game window close

		GameBoard board = new GameBoard(); //JPanel to draw our game
		
		window.add(board); // Adding JPanel to the game Window
		window.addKeyListener(board); // Adding Keyboard listener so we can transfer inputs to character

		window.setResizable(false); // User cannot resize the window
		window.pack();
		
		window.setLocationRelativeTo(null); // Opening game window in center of display
		window.setFocusable(true);
		window.setVisible(true); //Show the game window
	}
	
	public static void main(String[] args) {
		initWindow();
	}
}
