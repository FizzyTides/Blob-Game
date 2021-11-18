package com.BlobGame;

import javax.swing.*;
import java.awt.*;

/**GAME CLASS
 * Handles game Window functionalities
 * 
 * @author mca
 * @author mba
 * @author ketan
 */

public class Game {
	
	/**
	 * INITWINDOW METHOD
	 * initWindow constructor method initilizes a JWindow adding the gameBoard JPanel to it, along with a keylistener
	 * Set resizable to false so window is not resizable
	 * Window is set to visible to display on launch
	 * LocationRelative set to Null to center the window on launch
	 * Window will close on exit
	 */
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
