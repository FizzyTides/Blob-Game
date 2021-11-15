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
	
	private final int DELAY = 0; //50 by default
	private Timer timer;
	
	boolean wallFound = false;
	
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	private static final int CAKE_REWARD = 100;
	private static final int PUNISHMENT_PENALTY = 100;
	public static final int NUM_CAKES = 10;
	public static final int NUM_PUNISHMENTS = 10;
	
	private Player player;
	private BufferedImage bgImage;
	private ArrayList<Cake> cakes;	
	private ArrayList<Wall> gameWalls;
	private ArrayList<Punishment> punishments;
	private ArrayList<Enemy> enemies;
	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
		
		
		player = new Player(); // Instantiate a player when gameBoard starts
		enemies =  spawnEnemies();
        cakes = spawnCakes();
        gameWalls = spawnWalls();
		punishments = spawnPunishments();

        
        loadBgImage();
        
		timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
		timer.start();
		System.out.println("TIMER: " + timer);

		
	}
	
	void loadBgImage() {
		try { 

			bgImage = ImageIO.read(new File("src/main/resources/Bg.png"));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(bgImage, 0, 0, null);
		
		for (Cake cake : cakes) {
            cake.draw(g, this);
        }
		
		for(Wall wall : gameWalls ) {
			wall.draw(g, this);
		}

		for(Punishment punishment : punishments){
			punishment.draw(g, this);
		}
		
		for(Enemy enemy : enemies) {
			enemy.draw(g, this);
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
				//g.drawImage(tile, i * GameBoard.TILE_SIZE, j * GameBoard.TILE_SIZE , watcher);
			}
		}
	}
	

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
		/*for(Enemy enemy : enemies) {
			enemy.enemyDirection();
		}*/
		
		checkWalls();
		gameBoundary();
		collectCakes();
		hitPunishment();
		enemyDirection();
		repaint();
	}
	
	public void gameBoundary() {
		if(player.pos.x < 0) {
			player.pos.x = 0;
		}
	}
	
	
	public void enemyDirection() {
		for(Enemy enemy : enemies) {
		
			
			Point enemyCurrPos = enemy.getEnemyPos();
			Point playerCurrPos = player.getPlayerPos();
		
			//System.out.println("Enemy Position: " + enemyCurrPos.x + "," + enemyCurrPos.y + " --- " + "Player Position: " + playerCurrPos.x + "," + playerCurrPos.y);
			
			//player is above
			if(playerCurrPos.y < enemyCurrPos.y) {
				enemy.playerAbove = true;
				enemy.playerBelow = false;
				System.out.println("player is above");
			}
			//player is below
			else if(playerCurrPos.y > enemyCurrPos.y) {
				enemy.playerBelow = true;
				enemy.playerAbove = false;
				System.out.println("player is below");
			}
			//player on same row (or switch not 0 --> time to switch)
			else {
				enemy.playerAbove = false;
				enemy.playerBelow = false;
				System.out.println("player is on same row");
			}
			
			//player is to right
			if(playerCurrPos.x > enemyCurrPos.x) {
				enemy.playerRight = true;
				enemy.playerLeft = false;
				System.out.println("player is to right");
			}
			//player is to left
			else if(playerCurrPos.x < enemyCurrPos.x) {
				enemy.playerLeft = true;
				enemy.playerRight = false;
				System.out.println("player is to left");
			}
			//player on same columns (or switch not 1 --> time to switch)
			else {
				enemy.playerRight = false;
				enemy.playerLeft = false;
				System.out.println("player is on same column");
			}
			enemy.enemyMovement();
		}
	}

	public void checkWalls() {
        Point playerCurrPos = player.getPlayerPos();
        boolean aWallNorth, aWallSouth, aWallEast, aWallWest;
        aWallNorth = aWallSouth = aWallEast = aWallWest = false;
        for(Wall walls : gameWalls) {
            Point wallCurrPos = walls.getWallPos();
            

            		

            if((playerCurrPos.y - 1 == wallCurrPos.y && playerCurrPos.x == wallCurrPos.x) || aWallNorth) {
                player.wallNorth = true;
                aWallNorth = true;
                //System.out.println("There is a wall to the North!");
            }
            else {
            	player.wallNorth = false;
            }
            if((playerCurrPos.y + 1 == wallCurrPos.y && playerCurrPos.x == wallCurrPos.x) || aWallSouth) {
                player.wallSouth = true;
                aWallSouth = true;
                //System.out.println("There is a wall to the South!");
            }
            else {
            	player.wallSouth = false;
            }
            if((playerCurrPos.x - 1== wallCurrPos.x && playerCurrPos.y == wallCurrPos.y) || aWallEast) {
                player.wallEast = true;
                aWallEast = true;
                //System.out.println("There is a wall to the East!");
            }
            else {
            	player.wallEast = false;
            }
            if((playerCurrPos.x + 1 == wallCurrPos.x && playerCurrPos.y == wallCurrPos.y) || aWallWest) {
                player.wallWest = true;
                aWallWest = true;
               //System.out.println("There is a wall to the West!");
            }
            else {
            	player.wallWest = false;
            }
        }
	}
	
	private ArrayList<Enemy> spawnEnemies() {
		ArrayList<Enemy> myEnemies = new ArrayList<Enemy>();
		myEnemies.add(new Enemy(new Point(19, 13)));
		return myEnemies;
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

	private ArrayList<Punishment> spawnPunishments() {
        ArrayList<Punishment> punishmentList = new ArrayList<Punishment>();

        for (int i = 0; i < NUM_PUNISHMENTS; i++) {
            Point p = new Point(i+1,i+2);
            punishmentList.add(new Punishment(p));
        }

        return punishmentList;
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

	
	private void hitPunishment() {
		// Fills ArrayList with collectedPunishments
        ArrayList<Punishment> collectedPunishments = new ArrayList<Punishment>();
        for (Punishment punishment : punishments) {
            // if the player is on the same tile as a punishment, hit it
            if (player.getPos().equals(punishment.getPos())) {
                // deduct points from total
                player.deductScore(PUNISHMENT_PENALTY);
                collectedPunishments.add(punishment);
                System.out.println("SCORE: " + player.getScore());
            }
        }
        // remove collected cakes from the board
        punishments.removeAll(collectedPunishments);
    }
}
