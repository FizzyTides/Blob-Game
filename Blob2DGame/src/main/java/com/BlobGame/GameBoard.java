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
	
	double bonusStayTime = 5;
	double rand = Math.floor(Math.random()*((MAX_GAMETIME-1-bonusStayTime)+1)); 
		//get random time (between 0 <-> MAXTIME-1-bonusStayTime)..
	
	boolean wallFound = false;
	private boolean pause = false;
	
    private int punishmentFreezeTime = 1000; //in milliseconds 
    private double frozenStartTime = -punishmentFreezeTime;
    private int gameTimeElapsed = 0;
    private double startRealTime;
    private double pauseTime = 0;
    double pause_begin;
    double pause_end;
    private int cakeCount;
    

    
    public int gameState;
    private static final int MENU = 0;
    private static final int GAMEPLAY = 1;
    private static final int GAMELOSE = 2;
    private static final int GAMEWIN = 3;
    
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	public static final int NUM_CAKES = 10;
	public static final int NUM_PUNISHMENTS = 5;
	private static final int MAX_GAMETIME = 30;
	
	private Player player;
	private Gate gate;
	private ExitTile winTile;
	private BufferedImage bgImage, winBgImage, loseBgImage, menuBgImage, pauseText;
	private ArrayList<Cake> rewards; //includes bunus and regular reward	
	private ArrayList<Wall> gameWalls;
	private ArrayList<Punishment> punishments;
	private ArrayList<Enemy> enemies;
	
	JButton startButton= new JButton("START");
	JButton replayButton = new JButton("REPLAY");
	
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS + 50));
		setLayout(null);
		gameState = MENU;
		stateSwitch(gameState);
		
	}
	
	private void replayButton() {
		replayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				gameState = GAMEPLAY;
				stateSwitch(gameState);
				replayButton.setVisible(false);
				replayButton.setFocusable(false);
				startRealTime = System.currentTimeMillis() / 1000;
				player.setScore(0);
				gameTimeElapsed = 0;
				pauseTime = 0;
				frozenStartTime = -punishmentFreezeTime;
			}
		});
		replayButton.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
		replayButton.setBackground(Color.BLACK);
		replayButton.setForeground(Color.WHITE);
		replayButton.setBounds(TILE_SIZE * COLUMNS / 2 - 100, TILE_SIZE * ROWS / 2 + 185, 200, 50);
		this.add(replayButton);
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
		startButton.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
		startButton.setBackground(Color.BLACK);
		startButton.setForeground(Color.WHITE);
		startButton.setBounds(TILE_SIZE * COLUMNS / 2 - 100, TILE_SIZE * ROWS / 2 - 50, 200, 50);
		this.add(startButton);
	}

    private void stateSwitch(int gameState) {
		switch(gameState) {
		case MENU:
			startButton();
			replayButton();
			replayButton.setVisible(false);
			loadImages();
			break;
		case GAMEPLAY:
			gameInit();
			break;
		case GAMELOSE:
			replayButton();
			break;
		case GAMEWIN:
			replayButton();
			break;
		}
		
	}
    
	private void gameInit() {
		player = new Player(); // Instantiate a player when gameBoard starts
		enemies =  spawnEnemies();
        rewards = spawnRewards();
        gameWalls = spawnWalls();
		punishments = spawnPunishments();
		winTile = new ExitTile(new Point(19,13));

		cakeCount = 0;
        
		timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
		timer.start();
    }
	
	private void loseCondition() {
		if(player.getScore() < 0 || MAX_GAMETIME == gameTimeElapsed) {
			gameEnd(GAMELOSE);
		}
		enemyKillPlayer(player.getPos());
	}
	
	private void winCondition() {
		if(cakeCount == NUM_CAKES) {
			gameWalls.remove(gate);
		}
		if(player.getPos().x == winTile.getPos().x && player.getPos().y == winTile.getPos().y) {
			gameEnd(GAMEWIN);
		}
	}

    private void timeElapsed() {
        double currRealTime = System.currentTimeMillis() / 1000;

        if((currRealTime - (startRealTime + gameTimeElapsed + pauseTime)) == 1 && gameTimeElapsed <= MAX_GAMETIME) {
            gameTimeElapsed++;
        }

    }
    
    public void gameEnd(int gameResult) {
    	player.pause = true;
    	timer.stop();
		replayButton.setVisible(true);
    	gameState = gameResult;
    }
	
	void loadImages() {
		try { 
			bgImage = ImageIO.read(new File("src/main/resources/Bg.png"));
			winBgImage = ImageIO.read(new File("src/main/resources/winBgImage.png"));
			loseBgImage = ImageIO.read(new File("src/main/resources/loseBgImage.png"));
			menuBgImage = ImageIO.read(new File("src/main/resources/menuBgImage.png"));
			pauseText = ImageIO.read(new File("src/main/resources/pauseText.png"));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(gameState == GAMEPLAY) {
			g.drawImage(bgImage, 0, 0, this);
			
			g.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
			g.setColor(Color.WHITE);
			g.drawString("Score: " + player.getScore(), TILE_SIZE * COLUMNS - 100, TILE_SIZE * ROWS + 30);
			g.drawString("Time Remaining: " + (MAX_GAMETIME - gameTimeElapsed), 50, TILE_SIZE * ROWS + 30);
			g.drawString("Cakes: " + cakeCount + " /" + NUM_CAKES, TILE_SIZE * COLUMNS / 2, TILE_SIZE * ROWS + 30);
			
			for (Cake reward : rewards) {
				if(reward.isVisible()){
					reward.draw(g, this);
				}
	  
	        }
			winTile.draw(g, this);
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
			if(pause) {
				g.drawImage(pauseText, TILE_SIZE * COLUMNS / 2 - 150, TILE_SIZE * ROWS / 2 - 50, this);
			}
		}
		else if(gameState == MENU) {
			g.drawImage(menuBgImage, 0, 0, this);
		}

		
		else if(gameState == GAMELOSE) {
			g.drawImage(loseBgImage, 0, 0, this);
		}
		
		else if(gameState == GAMEWIN) {
			g.drawImage(winBgImage, 0, 0, this);
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
			g.drawString("FINAL SUGAR LEVEL: " + player.getScore(), TILE_SIZE * COLUMNS / 2 - 110, TILE_SIZE * ROWS / 2 + 115);
			
			if((MAX_GAMETIME - gameTimeElapsed) != 1) {
				g.drawString("TIME REMAINING: " + (MAX_GAMETIME - gameTimeElapsed) + " seconds", TILE_SIZE * COLUMNS / 2 - 110, TILE_SIZE * ROWS / 2 + 145);
			}
			else {
				g.drawString("TIME REMAINING: " + (MAX_GAMETIME - gameTimeElapsed) + " second", TILE_SIZE * COLUMNS / 2 - 110, TILE_SIZE * ROWS / 2 + 145);
			}
		}
		
		Toolkit.getDefaultToolkit().sync();
	}

	

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        if(key == KeyEvent.VK_P && !pause) {
            pause_begin = System.currentTimeMillis() / 1000;
            pause = true;
            player.pause = true;

        } else if (key == KeyEvent.VK_P && pause) {
            pause_end = System.currentTimeMillis() / 1000;
            pauseTime = pauseTime + (pause_end - pause_begin);
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
		if(gameState == GAMEPLAY && !pause) {
			timeElapsed();
			enemyCheckEnemies();
			enemyCheckWalls();
			checkWalls();
			gameBoundary();
			collectRewards();
			hitPunishment();
			enemyDirection();
			winCondition();
			loseCondition();
			bonusVisibility();
			frozenCheck();
		}
		repaint();
	}

	public void bonusVisibility() {
		//System.out.println("rand: "+rand);
		for (Cake cake : rewards) {
			//System.out.println("gameTimeElapsed: " + (gameTimeElapsed) + "rand: " + rand);
			if((cake.isBonus == true) && (gameTimeElapsed == rand)) {
				//System.out.println("show bonus reward @" + rand);
				cake.setBonusReward((int) (player.getScore() * 1.5 + 200));
				cake.visibility = true; 
			}
			if((cake.isBonus == true) && (gameTimeElapsed == (rand + bonusStayTime)) && cake.visibility == true) {
				//System.out.println("show bonus reward @" + rand);
				cake.visibility = false; 
			}
		}
		
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
            Point wallCurrPos = walls.getPos();
            

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
	
	public void enemyKillPlayer(Point playerPos) {
		for(Enemy enemies : enemies) {
			if(playerPos.x == enemies.getPos().x && playerPos.y == enemies.getPos().y) {
				gameEnd(GAMELOSE);
			}
		}
	}
	
	public void enemyCheckWalls() {
		for(Enemy enemy : enemies) {
			Point currEnemyPos = enemy.getPos();
	        boolean aWallNorth, aWallSouth, aWallEast, aWallWest;
	        aWallNorth = aWallSouth = aWallEast = aWallWest = false;
	        for(Wall walls : gameWalls) {
	            Point wallCurrPos = walls.getPos();
	            

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
		myEnemies.add(new Enemy(new Point(19, 13), "MomEnemy.png"));
		myEnemies.add(new Enemy(new Point(1, 14), "DadEnemy.png"));
		
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
		gate = new Gate(new Point(19, 13));
		myWalls.add(gate); //Adding gate to Walls ArrayList
		
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

        Point p1 = new Point(1,10);
        rewardList.add(new Cake(p1));

        Point p2 = new Point(9,3);
        rewardList.add(new Cake(p2));

        Point p3 = new Point(17,1);
        rewardList.add(new Cake(p3));

        Point p4 = new Point(17,7);
        rewardList.add(new Cake(p4));

        Point p5 = new Point(11,11);
        rewardList.add(new Cake(p5));

        Point p6 = new Point(1,5);
        rewardList.add(new BonusReward(p6));

        return rewardList;
    }

	private ArrayList<Punishment> spawnPunishments() {
        ArrayList<Punishment> punishmentList = new ArrayList<Punishment>();

        Point p1 = new Point(5,2);
        punishmentList.add(new Punishment(p1));

        Point p2 = new Point(9,4);
        punishmentList.add(new Punishment(p2));

        Point p3 = new Point(17,3);
        punishmentList.add(new Punishment(p3));

        Point p4 = new Point(2,5);
        punishmentList.add(new Punishment(p4));

        Point p5 = new Point(9,11);
        punishmentList.add(new Punishment(p5));

        Point p6 = new Point(14,10);
        punishmentList.add(new Punishment(p6));
        
        punishmentList.add(new TelePunishment(new Point(18,13)));
        return punishmentList;
    }

	private void collectRewards() {
		// Fills ArrayList with collectedCakes
        ArrayList<Cake> collectedCakes = new ArrayList<Cake>();
        for (Cake reward : rewards) {
            // if the player is on the same tile as a cake, collect it
            if (player.getPos().equals(reward.getPos()) && (reward.isVisible())) {
                // give the player some points for picking this up
                player.addScore(reward.getValue());
                collectedCakes.add(reward);
                cakeCount++;
                //System.out.println("SCORE: " + player.getScore());
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
                player.deductScore(punishment.getPenalty());
                collectedPunishments.add(punishment);
                //System.out.println("SCORE: " + player.getScore());
                if(punishment instanceof TelePunishment) {
                	player.setPos(new Point(0, 1));
                }
                else {
                	//frozenStartTime = gameTimeElapsed;
                	frozenStartTime = System.currentTimeMillis();
                }
            }
        }
        // remove collected cakes from the board
        punishments.removeAll(collectedPunishments);
    }
	
	public void frozenCheck() {
        //System.out.println(frozen_start_time);
        //if((frozenStartTime + punishmentFreezeTime) > gameTimeElapsed) {
        if((frozenStartTime + punishmentFreezeTime) > System.currentTimeMillis()) {
       
            //still frozen
            //System.out.println(System.currentTimeMillis());
        	player.imageName = "FrozenPlayer.png";
        	player.loadImage();
            player.pause = true;
        }
        //not frozen anymore
        else {
            //System.out.println("un-frozen");
            player.pause = false;
            player.imageName = "Blob.png";
            player.loadImage();
        }
    }
}
