package com.BlobGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
	private boolean pause = false;
	private boolean gameEnd = false;
	
    private int gameTimeElapsed = 0;
    private double startRealTime;
    private int pauseTime = 0;
	
    public int gameState;
    private static final int MENU = 0;
    private static final int GAMEPLAY = 1;
    private static final int LOSEGAME = 2;
    private static final int WINGAME = 3;
    
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	private static final int CAKE_REWARD = 100;
	private static final int BONUS_REWARD = 500;
	private static final int PUNISHMENT_PENALTY = 100;
	public static final int NUM_CAKES = 5;
	public static final int NUM_PUNISHMENTS = 5;
	private static final int MAX_GAMETIME = 20;
	
	private Player player;
	private BufferedImage bgImage;
	private ArrayList<Cake> rewards; //includes bunus and regular reward	
	private ArrayList<Wall> gameWalls;
	private ArrayList<Punishment> punishments;
	private ArrayList<Enemy> enemies;
	
	JButton startButton = new JButton("START");
	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS + 50));
		setLayout(new GridBagLayout());
		gameState = MENU;
		
		stateSwitch(gameState);
		
	}
	
	private void startButton() {
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameState = GAMEPLAY;
				stateSwitch(gameState);
				startButton.setVisible(false);
				startButton.setFocusable(false);
				startRealTime = System.currentTimeMillis() / 1000;
			}
		});
		
		startButton.setPreferredSize(new Dimension(100, 50));
		this.add(startButton, new GridBagConstraints());
		
	}

    private void stateSwitch(int gameState) {
		switch(gameState) {
		case MENU:
			displayMenu();
			break;
		case GAMEPLAY:
			gamePlay();
			break;
		case LOSEGAME:
			
			break;
		case WINGAME:
			
			break;
		}
		
	}
    
    private void gamePlay() {
		player = new Player(); // Instantiate a player when gameBoard starts
		enemies =  spawnEnemies();
        rewards = spawnRewards();
        gameWalls = spawnWalls();
		punishments = spawnPunishments();

        
        loadBgImage();
        
		timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
		timer.start();
    }

	private void displayMenu() {
		
		startButton();
		repaint();
	}

    private void timeElapsed() {
        double currRealTime = System.currentTimeMillis() / 1000;

        if((currRealTime - (startRealTime + gameTimeElapsed + pauseTime)) == 1 && gameTimeElapsed <= MAX_GAMETIME) {
            //System.out.println(gameTimeElapsed);
            int gameTimeCountdown = MAX_GAMETIME - gameTimeElapsed;
            System.out.println(gameTimeCountdown); // stop at 0...
            gameTimeElapsed++;
        }
        else if(MAX_GAMETIME + 1 == gameTimeElapsed) {
            gameEnd();
        }
    }
    
    public void gameEnd() {
    	player.pause = true;
    	timer.stop();
    	gameEnd = true;
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
		
		if(gameState == GAMEPLAY) {
			g.drawImage(bgImage, 0, 0, null);
			g.drawString("Score: " + player.getScore(), TILE_SIZE * COLUMNS - 100, TILE_SIZE * ROWS + 30);
			g.drawString("Time Remaining: " + (MAX_GAMETIME + 1 - gameTimeElapsed), 50, TILE_SIZE * ROWS + 30);
			
			for (Cake reward : rewards) {
				if(reward.isVisible()){
					reward.draw(g, this);
				}
	  
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
		}
		else if(gameState == MENU) {
			g.setColor(Color.RED);
			g.fillRect(0, 0, COLUMNS * TILE_SIZE, ROWS * TILE_SIZE + 50);
		}

		
		if(gameEnd) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, COLUMNS * TILE_SIZE, ROWS * TILE_SIZE);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}

	

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_P && !pause) {
			timer.stop();
			pause = true;
			player.pause = true;
		} else if (key == KeyEvent.VK_P && pause) {
			timer.start();
			pause = false;
			player.pause = false;
		}

		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		
		/*for(Enemy enemy : enemies) {
			enemy.enemyDirection();
		}*/
		if(gameState == GAMEPLAY) {
			timeElapsed();
			enemyCheckEnemies();
			enemyCheckWalls();
			checkWalls();
			gameBoundary();
			collectRewards();
			hitPunishment();
			enemyDirection();
		}
		repaint();
	}
	


	public void gameBoundary() {
		if(player.pos.x < 0) {
			player.pos.x = 0;
		}
	}
	
	
	public void enemyDirection() {
		for(Enemy enemy : enemies) {
		
			
			Point enemyCurrPos = enemy.getPos();
			Point playerCurrPos = player.getPos();
		
			//System.out.println("Enemy Position: " + enemyCurrPos.x + "," + enemyCurrPos.y + " --- " + "Player Position: " + playerCurrPos.x + "," + playerCurrPos.y);
			
			//player is above
			if(playerCurrPos.y < enemyCurrPos.y) {
				enemy.playerAbove = true;
				enemy.playerBelow = false;
				//System.out.println("player is above");
			}
			//player is below
			else if(playerCurrPos.y > enemyCurrPos.y) {
				enemy.playerBelow = true;
				enemy.playerAbove = false;
				//System.out.println("player is below");
			}
			//player on same row (or switch not 0 --> time to switch)
			else {
				enemy.playerAbove = false;
				enemy.playerBelow = false;
				//System.out.println("player is on same row");
			}
			
			//player is to right
			if(playerCurrPos.x > enemyCurrPos.x) {
				enemy.playerRight = true;
				enemy.playerLeft = false;
				//System.out.println("player is to right");
			}
			//player is to left
			else if(playerCurrPos.x < enemyCurrPos.x) {
				enemy.playerLeft = true;
				enemy.playerRight = false;
				//System.out.println("player is to left");
			}
			//player on same columns (or switch not 1 --> time to switch)
			else {
				enemy.playerRight = false;
				enemy.playerLeft = false;
				//System.out.println("player is on same column");
			}
			enemy.enemyMovement();
		}
	}

	public void checkWalls() {
        Point playerCurrPos = player.getPos();
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
	
	public void enemyCheckWalls() {
		for(Enemy enemy : enemies) {
			Point currEnemyPos = enemy.getPos();
	        boolean aWallNorth, aWallSouth, aWallEast, aWallWest;
	        aWallNorth = aWallSouth = aWallEast = aWallWest = false;
	        for(Wall walls : gameWalls) {
	            Point wallCurrPos = walls.getWallPos();
	            

	            if((currEnemyPos.y - 1 == wallCurrPos.y && currEnemyPos.x == wallCurrPos.x) || aWallNorth) {
	            	enemy.wallNorth = true;
	                aWallNorth = true;
	                //System.out.println("There is a wall to the North!");
	            }
	            else {
	            	enemy.wallNorth = false;
	            }
	            if((currEnemyPos.y + 1 == wallCurrPos.y && currEnemyPos.x == wallCurrPos.x) || aWallSouth) {
	            	enemy.wallSouth = true;
	                aWallSouth = true;
	                //System.out.println("There is a wall to the South!");
	            }
	            else {
	            	enemy.wallSouth = false;
	            }
	            if((currEnemyPos.x - 1== wallCurrPos.x && currEnemyPos.y == wallCurrPos.y) || aWallEast) {
	            	enemy.wallEast = true;
	                aWallEast = true;
	                //System.out.println("There is a wall to the East!");
	            }
	            else {
	            	enemy.wallEast = false;
	            }
	            if((currEnemyPos.x + 1 == wallCurrPos.x && currEnemyPos.y == wallCurrPos.y) || aWallWest) {
	            	enemy.wallWest = true;
	                aWallWest = true;
	               //System.out.println("There is a wall to the West!");
	            }
	            else {
	            	enemy.wallWest = false;
	            }
	        }
		}
	}
	
	public void enemyCheckEnemies() {
		for(Enemy enemy : enemies) {
			Point currEnemyPos = enemy.getPos();
	        boolean aEnemyNorth, aEnemySouth, aEnemyEast, aEnemyWest;
	        aEnemyNorth = aEnemySouth = aEnemyEast = aEnemyWest = false;
	        for(Enemy otherEnemy : enemies) {
	            Point otherEnemyPos = otherEnemy.getPos();
	            

	            if((currEnemyPos.y - 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemyNorth) {
	            	enemy.otherEnemyNorth = true;
	                aEnemyNorth = true;
	                //System.out.println("There is a wall to the North!");
	            }
	            else {
	            	enemy.otherEnemyNorth = false;
	            }
	            if((currEnemyPos.y + 1 == otherEnemyPos.y && currEnemyPos.x == otherEnemyPos.x) || aEnemySouth) {
	            	enemy.otherEnemySouth = true;
	                aEnemySouth = true;
	                //System.out.println("There is a wall to the South!");
	            }
	            else {
	            	enemy.otherEnemySouth = false;
	            }
	            if((currEnemyPos.x - 1== otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyEast) {
	            	enemy.otherEnemyEast = true;
	                aEnemyEast = true;
	                //System.out.println("There is a wall to the East!");
	            }
	            else {
	            	enemy.otherEnemyEast = false;
	            }
	            if((currEnemyPos.x + 1 == otherEnemyPos.x && currEnemyPos.y == otherEnemyPos.y) || aEnemyWest) {
	            	enemy.otherEnemyWest = true;
	                aEnemyWest = true;
	               //System.out.println("There is a wall to the West!");
	            }
	            else {
	            	enemy.otherEnemyWest = false;
	            }
	        }
		}
	}
	

	
	private ArrayList<Enemy> spawnEnemies() {
		ArrayList<Enemy> myEnemies = new ArrayList<Enemy>();
		myEnemies.add(new Enemy(new Point(19, 13)));
		myEnemies.add(new Enemy(new Point(1, 14)));
		
		return myEnemies;
	}
	

	private ArrayList<Wall> spawnWalls() { 
		ArrayList<Wall> myWalls = new ArrayList<Wall>();
		
		myWalls.add(new Wall(new Point(2, 2)));
		myWalls.add(new Wall(new Point(3, 2)));
		myWalls.add(new Wall(new Point(4, 2)));
		
		myWalls.add(new Wall(new Point(2, 12)));
		myWalls.add(new Wall(new Point(2, 11)));
		myWalls.add(new Wall(new Point(2, 10)));
		myWalls.add(new Wall(new Point(2, 9)));
		
		myWalls.add(new Wall(new Point(4, 11)));
		myWalls.add(new Wall(new Point(5, 11)));

		myWalls.add(new Wall(new Point(1, 6)));
		myWalls.add(new Wall(new Point(2, 6)));
		myWalls.add(new Wall(new Point(3, 6)));
		
		myWalls.add(new Wall(new Point(16, 1)));
		myWalls.add(new Wall(new Point(16, 2)));
		myWalls.add(new Wall(new Point(17, 2)));

		myWalls.add(new Wall(new Point(6, 7)));
		myWalls.add(new Wall(new Point(8, 7)));
		myWalls.add(new Wall(new Point(9, 7)));
		
		myWalls.add(new Wall(new Point(11, 7)));
		myWalls.add(new Wall(new Point(12, 7)));
		
		myWalls.add(new Wall(new Point(16, 5)));
		myWalls.add(new Wall(new Point(16, 6)));
		myWalls.add(new Wall(new Point(16, 7)));
		myWalls.add(new Wall(new Point(16, 8)));
		
		myWalls.add(new Wall(new Point(8, 2)));
		myWalls.add(new Wall(new Point(7, 2)));
		myWalls.add(new Wall(new Point(7, 3)));
		myWalls.add(new Wall(new Point(7, 4)));
		myWalls.add(new Wall(new Point(8, 4)));
		
		myWalls.add(new Wall(new Point(10, 2)));
		myWalls.add(new Wall(new Point(11, 2)));
		myWalls.add(new Wall(new Point(11, 3)));
		myWalls.add(new Wall(new Point(11, 4)));
		myWalls.add(new Wall(new Point(10, 4)));
		
		myWalls.add(new Wall(new Point(16, 12)));
		myWalls.add(new Wall(new Point(14, 12)));
		myWalls.add(new Wall(new Point(13, 12)));
		
		myWalls.add(new Wall(new Point(7, 9)));
		myWalls.add(new Wall(new Point(11, 9)));
		myWalls.add(new Wall(new Point(10, 13)));
		myWalls.add(new Wall(new Point(7, 12)));

		
		
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
	
	

	private ArrayList<Cake> spawnRewards() {
        ArrayList<Cake> rewardList = new ArrayList<Cake>();

        for (int i = 0; i < NUM_CAKES; i++) {
            Point p = new Point(i,i);
            rewardList.add(new Cake(p));
        }

		Random rand = new Random(); //TODO hardcode
		
		Point p = new Point(rand.nextInt(COLUMNS),rand.nextInt(ROWS));
		rewardList.add(new BonusReward(p));

        return rewardList;
    }

	private ArrayList<Punishment> spawnPunishments() {
        ArrayList<Punishment> punishmentList = new ArrayList<Punishment>();

        for (int i = 0; i < NUM_PUNISHMENTS; i++) {
            Point p = new Point(i+1,i+2);
            punishmentList.add(new Punishment(p));
        }

        return punishmentList;
    }

	private void collectRewards() {
		// Fills ArrayList with collectedCakes
        ArrayList<Cake> collectedCakes = new ArrayList<Cake>();
        for (Cake reward : rewards) {
            // if the player is on the same tile as a cake, collect it
            if (player.getPos().equals(reward.getPos()) && (reward.isVisible())) {
                // give the player some points for picking this up
                player.addScore(reward.rewardValue());
                collectedCakes.add(reward);
                System.out.println("SCORE: " + player.getScore());
            }
        }
        // remove collected cakes from the board
        rewards.removeAll(collectedCakes);
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
