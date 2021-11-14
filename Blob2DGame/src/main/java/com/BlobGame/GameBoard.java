package com.BlobGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.File;



@SuppressWarnings("serial")
public class GameBoard extends JPanel implements KeyListener, ActionListener {
	
	private final int DELAY = 50;
	
	private Timer timer;
	
	boolean wallFound = false;
	
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	private static final int CAKE_REWARD = 100;
	public static final int NUM_CAKES = 10;
	
	private Player player;
	private BufferedImage tile;
	private ArrayList<Cake> cakes;	
	private ArrayList<Wall> gameWalls;
	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
		
		//Background color
		
		player = new Player(); // Instantiate a player when gameBoard starts
        cakes = spawnCakes();
        gameWalls = spawnWalls();
        
        loadTileImage();
        
		timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
		timer.start();
		System.out.println("TIMER: " + timer);

		
	}
	
	void loadTileImage() {
		try { 
			tile = ImageIO.read(new File("src/main/resources/Tile.png"));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g, this);
		for (Cake cake : cakes) {
            cake.draw(g, this);
        }
		
		for(Wall wall : gameWalls ) {
			wall.draw(g, this);
		}
		
		player.draw(g, this); // Draws player image
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawBackground(Graphics g, ImageObserver watcher) {
//		g.setColor(new Color(0,0,0));
//		for(int i = 0; i < ROWS * TILE_SIZE; i+=TILE_SIZE) {
//			g.drawLine(0, i, TILE_SIZE * COLUMNS, i); // Draws the rows
//		}
//		
//		for(int j = 0; j < COLUMNS * TILE_SIZE; j+=TILE_SIZE) {
//			g.drawLine(j, 0, j, TILE_SIZE * COLUMNS); // Draws the columns
//		}
		for(int i = 0; i < ROWS * TILE_SIZE; i++) {
			for(int j = 0; j < COLUMNS * TILE_SIZE; j++) {
				g.drawImage(tile, i * GameBoard.TILE_SIZE, j * GameBoard.TILE_SIZE , watcher);
			}
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
		
		checkWalls();
		collectCakes();
		repaint();
	}
	
	public void checkWalls() {
        Point playerCurrPos = player.getPlayerPos();
        boolean aWallNorth, aWallSouth, aWallEast, aWallWest;
        aWallNorth = aWallSouth = aWallEast = aWallWest = false;
        for(Wall walls : gameWalls) {
            Point wallCurrPos = walls.getWallPos();
            
            //System.out.println("Player position: ");
            
            		

            if((playerCurrPos.y - 1 == wallCurrPos.y && playerCurrPos.x == wallCurrPos.x) || aWallNorth) {
                player.wallNorth = true;
                aWallNorth = true;
                System.out.println("There is a wall to the North!");
            }
            else {
            	player.wallNorth = false;
            }
            if((playerCurrPos.y + 1 == wallCurrPos.y && playerCurrPos.x == wallCurrPos.x) || aWallSouth) {
                player.wallSouth = true;
                aWallSouth = true;
                System.out.println("There is a wall to the South!");
            }
            else {
            	player.wallSouth = false;
            }
            if((playerCurrPos.x - 1== wallCurrPos.x && playerCurrPos.y == wallCurrPos.y) || aWallEast) {
                player.wallEast = true;
                aWallEast = true;
                System.out.println("There is a wall to the East!");
            }
            else {
            	player.wallEast = false;
            }
            if((playerCurrPos.x + 1 == wallCurrPos.x && playerCurrPos.y == wallCurrPos.y) || aWallWest) {
                player.wallWest = true;
                aWallWest = true;
                System.out.println("There is a wall to the West!");
            }
            else {
            	player.wallWest = false;
            }
        }
	}
	
	
	private ArrayList<Wall> spawnWalls() { 
		ArrayList<Wall> myWalls = new ArrayList<Wall>();
		
		for(int i = 0; i < COLUMNS; i++) {
			Point upperWalls = new Point(i, 0);
			myWalls.add(new Wall(upperWalls));
			Point lowerWalls = new Point(i, 14);
			myWalls.add(new Wall(lowerWalls));
		}
		
		for(int j = 2; j < ROWS - 1; j++) {
			Point leftWalls = new Point(0,j);
			myWalls.add(new Wall(leftWalls));
			Point rightWalls = new Point(19, j - 1);
			myWalls.add(new Wall(rightWalls));
		}
		
		
		return myWalls;
	}
	

	private ArrayList<Cake> spawnCakes() {
        ArrayList<Cake> cakeList = new ArrayList<Cake>();

        for (int i = 0; i < NUM_CAKES; i++) {
            Point p = new Point(i,i);
            cakeList.add(new Cake(p));
        }

        return cakeList;
    }

	private void collectCakes() {
		// Fills ArrayList with collectedCakes
        ArrayList<Cake> collectedCakes = new ArrayList<Cake>();
        for (Cake cake : cakes) {
            // if the player is on the same tile as a cake, collect it
            if (player.getPos().equals(cake.getPos())) {
                // give the player some points for picking this up
                player.addScore(CAKE_REWARD);
                collectedCakes.add(cake);
                System.out.println("SCORE: " + player.getScore());
            }
        }
        // remove collected cakes from the board
        cakes.removeAll(collectedCakes);
    }
	
}
