package com.BlobGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



@SuppressWarnings("serial")
public class GameBoard extends JPanel implements KeyListener, ActionListener {
	
	private final int DELAY = 50;
	
	private Timer timer;
	
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	private static final int CAKE_REWARD = 100;
	public static final int NUM_CAKES = 10;
	
	private Player player;
	private ArrayList<Cake> cakes;	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
		
		//Background color
		
		player = new Player(); // Instantiate a player when gameBoard starts
        cakes = populateCakes();

		timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
		timer.start();
		System.out.println("TIMER: " + timer);

		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		for (Cake cake : cakes) {
            cake.draw(g, this);
        }
		player.draw(g, this); // Draws player image
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawBackground(Graphics g) {
		g.setColor(new Color(0,0,0));
		for(int i = 0; i < ROWS * TILE_SIZE; i+=TILE_SIZE) {
			g.drawLine(0, i, TILE_SIZE * COLUMNS, i); // Draws the rows
		}
		
		for(int j = 0; j < COLUMNS * TILE_SIZE; j+=TILE_SIZE) {
			g.drawLine(j, 0, j, TILE_SIZE * COLUMNS); // Draws the columns
		}
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
		collectCakes();
		
		repaint();
	}
	

	private ArrayList<Cake> populateCakes() {
        ArrayList<Cake> cakeList = new ArrayList<Cake>();

        // create the given number of coins in random positions on the board.
        // note that there is not check here to prevent two coins from occupying the same
        // spot, nor to prevent coins from spawning in the same spot as the player
        for (int i = 0; i < NUM_CAKES; i++) {
            Point p = new Point(i,i);
            cakeList.add(new Cake(p));
        }

        return cakeList;
    }

	private void collectCakes() {
        // allow player to pickup coins
        ArrayList<Cake> collectedCakes = new ArrayList<Cake>();
        for (Cake cake : cakes) {
            // if the player is on the same tile as a coin, collect it
            if (player.getPos().equals(cake.getPos())) {
                // give the player some points for picking this up
                player.addScore(CAKE_REWARD);
                collectedCakes.add(cake);
            }
        }
        // remove collected coins from the board
        cakes.removeAll(collectedCakes);
    }
	
}
