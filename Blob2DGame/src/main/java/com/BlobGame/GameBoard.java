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



/**GAMEBOARD
 * This class includes all the functions we need for our Game Board.
 * This is a subclass of JPanel package so we can implement the KeyListener and Actionlistener to our JPanel board
 * 
 * @author mba
 * @author mca
 * @author ketan
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel implements KeyListener, ActionListener {
	
	
	/**
	 * Variables needed for the program are instantiated/set
	 */
	
	private final int DELAY = 0;
	private Timer timer;
	
	double bonusStayTime = 5;
	double rand = Math.floor(Math.random()*((MAX_GAMETIME-1-bonusStayTime)+1));
	
	boolean wallFound = false;
	private boolean pause = false;
	
    private int gameTimeElapsed = 0;
    private double startRealTime;
    private double pauseTime = 0;
    double pause_begin;
    double pause_end;
    private int cakeCount;
    public int gameState;
    
    private static final int MENU = 0;
    private static final int GAMEPLAY = 1;
    static final int GAMELOSE = 2;
    private static final int GAMEWIN = 3;
    
	public static final int TILE_SIZE = 50;
	public static final int ROWS = 15;
	public static final int COLUMNS = 20;
	public static final int NUM_CAKES = 10;
	public static final int NUM_PUNISHMENTS = 10;
	private static final int MAX_GAMETIME = 30;
	
	private Player player;
	private Gate gate;
	private ExitTile winTile;
	private BufferedImage bgImage, winBgImage, loseBgImage, menuBgImage, pauseText;
	private ArrayList<Cake> rewards; //includes bonus and regular reward	
	private ArrayList<Wall> gameWalls;
	private ArrayList<Punishment> punishments;
	private ArrayList<Enemy> enemies;
	private ArrayList<MovingObjects> mObjects = new ArrayList<MovingObjects>();
	
	JButton startButton= new JButton("START");
	JButton replayButton = new JButton("REPLAY");
	JButton menuButton = new JButton("MENU");
	
	/**
	 * GameBoard JPanel constructor this is instantiated at the beginning of program execution which is added to the JWindow.
	 * This constructor:
	 * sets the layoutmanager
	 * sets starting gameState
	 * loads game assets
	 * loads the game JButtons
	 */
	public GameBoard() {
		setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS + 50));
		setLayout(null);
		gameState = MENU;
		stateSwitch(gameState);
		loadImages();
		buttons();
		
	}
	
	/**
	 * Button method which holds all three menu, game start and replay buttons.
	 * Each button has an individual actionlistener to determine when the buttons are being clicked on, which sets the corresponding buttons visibilities
	 * and swaps the gameState
	 * 
	 * MENU BUTTON:
	 * 	Menu button stamps the current program running time and converts it to seconds which we use to synchronize the program running time and the game running time
	 * 
	 * REPLAY BUTTON:
	 * 	Replay button utilizes similar functions as menu button, however swaps to a different game state
	 * 
	 * START BUTTON:
	 * 	Start button simply stamps the current running time as the game starting time upon clicking the JButton
	 * 
	 * Under the actionlistener implementation for each button, Each button had to be modified for UI placement,
	 * setFont used for selecting font and size
	 * BorderPainted false so we do not have a border outline
	 * setBackgroundcolor and Foreground for button color and text color
	 * Then all buttons are added to the JPanel board (this)
	 */
	private void buttons() {
		replayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == replayButton) {
					gameState = GAMEPLAY;
					stateSwitch(gameState);
					//System.out.println("ReplayButton state switch");
					replayButton.setVisible(false);
					replayButton.setFocusable(false);
					menuButton.setVisible(false);
					startRealTime = System.currentTimeMillis() / 1000;
					player.setScore(0);
					gameTimeElapsed = 0;
					pauseTime = 0;
					player.frozenStartTime = -player.punishmentFreezeTime;
				}
			}
		});
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == menuButton) {
					gameState = MENU;
					stateSwitch(gameState);
					//System.out.println("Menubutton state switch");
					menuButton.setVisible(false);
					menuButton.setFocusable(false);
					replayButton.setVisible(false);
					player.setScore(0);
					startRealTime = System.currentTimeMillis() / 1000;
					gameTimeElapsed = 0;
					pauseTime = 0;
					player.frozenStartTime = -player.punishmentFreezeTime;
					startButton.setVisible(true);
					repaint();
				}
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(event.getSource() == startButton) {
					gameState = GAMEPLAY;
					stateSwitch(gameState);
					//System.out.println("Start button state switch");
					startButton.setVisible(false);
					startButton.setFocusable(false);
					startRealTime = System.currentTimeMillis() / 1000;
				}
			}
		});
		startButton.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
		startButton.setBorderPainted(false);
		startButton.setOpaque(true);
		startButton.setBackground(Color.BLACK);
		startButton.setForeground(Color.WHITE);
		startButton.setBounds(TILE_SIZE * COLUMNS / 2 - 100, TILE_SIZE * ROWS / 2 - 50, 200, 50);
		replayButton.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
		replayButton.setBorderPainted(false);
		replayButton.setOpaque(true);
		replayButton.setBackground(Color.BLACK);
		replayButton.setForeground(Color.WHITE);
		replayButton.setBounds(TILE_SIZE * COLUMNS / 2 - 100, TILE_SIZE * ROWS / 2 + 185, 200, 50);
		menuButton.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
		menuButton.setBorderPainted(false);
		menuButton.setOpaque(true);
		menuButton.setBackground(Color.BLACK);
		menuButton.setForeground(Color.WHITE);
		menuButton.setBounds(TILE_SIZE * COLUMNS / 2 - 100, TILE_SIZE * ROWS / 2 + 245, 200, 50);
		this.add(startButton);
		this.add(replayButton);
		this.add(menuButton);
	}

	/**
	 * STATESWITCH METHOD
	 * Utilizes switch case to control game state
	 */
    private void stateSwitch(int gameState) {
		switch(gameState) {
		case MENU:
			replayButton.setVisible(false);
			menuButton.setVisible(false);
			break;
		case GAMEPLAY:
			timer = new Timer(DELAY, this); // Calls the actionPerformed() function every DELAY
			timer.start();
			gameInit();
			break;
		case GAMELOSE:
			gameReset();
			break;
		case GAMEWIN:
			gameReset();
			break;
		}
		
	}
    
    /**
	 * GAMERESET METHOD
	 * Used to empty all ArrayLists holding entities
	 */
    private void gameReset() {
    	rewards.clear();
    	punishments.clear();
    	gameWalls.clear();
    	enemies.clear();
    	
    }
    
    /**
	 * GAMEINIT METHOD
	 * Spawns all game entities and sets cakecount
	 */
	private void gameInit() {
		player = new Player(); // Instantiate a player when gameBoard starts
		enemies =  spawnEnemies();
		mObjects.add(player);
        rewards = spawnRewards();
        gameWalls = spawnWalls();
		punishments = spawnPunishments();
		winTile = new ExitTile(new Point(19,13));
		cakeCount = 0;

    }
	
	/**
	 * LOSECONDITION METHOD
	 * Checks every actionPerformed to see if loseCondition has been met
	 * 1. Player score < 0
	 * 2. Player Position == Enemy Position (Point variables) by calling another method EnemyKillPlayer(player.getPos());
	 * 3. GameTime has reached maximum time
	 */
	private void loseCondition() {
		if(player.getScore() < 0 || MAX_GAMETIME == gameTimeElapsed) {
			gameEnd(GAMELOSE);
		}
	}
	
	/**
	 * WINCONDITION METHOD
	 * Checks every actionPerformed to see if winCondition has been met
	 * 1. Opens gate when cakeCount reaches to total number of cakes
	 * 2. Checks if player Position == winTile position both x and y
	 * 		- If this is met, calls gameEnd function using GAMEWIN as parameter
	 */
	private void winCondition() {
		if(cakeCount == NUM_CAKES) {
			gameWalls.remove(gate);
		}
		if(player.getPos().x == winTile.getPos().x && player.getPos().y == winTile.getPos().y) {
			gameEnd(GAMEWIN);
		}
	}
	
	/**
	 * TIMEELAPSED METHOD
	 * Calculates the desired game countdown
	 * Stamps the current program running time with currRealTime
	 * Using currRealTime, gameTimeElapsed increments as long as the maximum time has not been reached
	 * currRealTime is the current time, startRealTime is game start Time, gameTimeElapsed is game time passed in seconds and
	 * pauseTime is the amount of time passed while paused
	 */
    private void timeElapsed() {
        double currRealTime = System.currentTimeMillis() / 1000;

        if((currRealTime - (startRealTime + gameTimeElapsed + pauseTime)) == 1 && gameTimeElapsed <= MAX_GAMETIME) {
            gameTimeElapsed++;
        }

    }
    
    /**
     * GAMEEND METHOD
     * @param gameResult
     * Upon calling this method, takes in GAMERESULT parameter which is either GAMEWIN or GAMELOSE
     * sets both replay and menu button to visible to display them in the Window
     * Pauses the player so no movements can be made post game
     */
    public void gameEnd(int gameResult) {
    	player.pause = true;
		replayButton.setVisible(true);
		menuButton.setVisible(true);
    	gameState = gameResult;
    }
	
    /**
     * LOADIMAGES
     * Loads game background images and pause text
     */
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
	
	/**
	 * PAINTCOMPONENT
	 * @param g
	 * All drawings occur in this method
	 * Drawings are revealed based on gameState
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(gameState == GAMEPLAY) {
			g.drawImage(bgImage, 0, 0, this);
			
			g.setFont(new Font("TimesNew Bold", Font.PLAIN, 18));
			g.setColor(Color.WHITE);
			g.drawString("Sugar Level: " + player.getScore(), TILE_SIZE * COLUMNS - 175, TILE_SIZE * ROWS + 30);
			g.drawString("Time Remaining: " + (MAX_GAMETIME - gameTimeElapsed), 50, TILE_SIZE * ROWS + 30);
			g.drawString("Cakes: " + cakeCount + " /" + NUM_CAKES, (TILE_SIZE * COLUMNS / 2) - 50, TILE_SIZE * ROWS + 30);
			
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
	
	/**
	 * KEYPRESSED METHOD
	 * This method is used for all keyboard input
	 * PRESS "P" for pause and unpause
	 * "WASD" to move
	 */
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

	/**
	 * ACTIONPERFORMED METHOD
	 * This method is called based on the timer with DELAY which is currently set to 0 (so no delay in our game)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(gameState == GAMEPLAY && !pause) {
			timeElapsed();
			gameBoundary();
			collectRewards();
			//Added player as parameter to hitPunishment(Player player); for JUnit Testing
			hitPunishment(player, punishments);
			winCondition();
			loseCondition();
			bonusVisibility();
			player.frozenCheck();
			for(Enemy enemy : enemies) {
				enemy.checkEnemies(enemy, enemies);
				enemy.killPlayer(player.getPos(), this);
				enemy.eDirection(player);
			}
			for(MovingObjects object : mObjects) {
				object.checkWalls(gameWalls);
			}
		}
		repaint();
	}
	
	/**
	 * BONUSVISIBILITY METHOD
	 * Handles the timing for the bonus rewards visibility and its sugar value
	 */
	public void bonusVisibility() {
		for (Cake cake : rewards) {
			if((cake.isBonus == true) && (gameTimeElapsed == rand)) {
				cake.setBonusReward((int) (player.getScore() * 1.5 + 200));
				cake.visibility = true; 
			}
			if((cake.isBonus == true) && (gameTimeElapsed == (rand + bonusStayTime)) && cake.visibility == true) {
				cake.visibility = false; 
			}
		}
		
	}
	
	/**
	 * GAMEBOUNDARY METHOD
	 * A simple boundary restriction so player cannot leave the left side of the screen
	 * Right side is not set cause its not possible to reach the right side of screen
	 */
	public void gameBoundary() {
		if(player.pos.x < 0) {
			player.pos.x = 0;
		}
	}
	


	/**
	 * SPAWNENEMIES METHOD
	 * returns arraylist with enemies
	 * @return
	 */
	private ArrayList<Enemy> spawnEnemies() {
		ArrayList<Enemy> myEnemies = new ArrayList<Enemy>();
		Enemy mom = new Enemy(new Point(19, 13), "MomEnemy.png");
		Enemy dad = new Enemy(new Point(1, 14), "DadEnemy.png");
		myEnemies.add(mom);
		myEnemies.add(dad);
		mObjects.add(mom);
		mObjects.add(dad);
		return myEnemies;
	}
	
	/**
	 * SPAWNWALLS METHOD
	 * returns arraylist of walls
	 * @return
	 */
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
	
	
	/**
	 * SPAWNREWARDS METHOD
	 * returns arraylist of cakeslices and cake
	 * @return
	 */
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
        
        Point p7 = new Point(14,3);
        rewardList.add(new Cake(p7));
        
        Point p8 = new Point(4,12);
        rewardList.add(new Cake(p8));
        
        Point p9 = new Point(10,7);
        rewardList.add(new Cake(p9));
        
        Point p10 = new Point(1,7);
        rewardList.add(new Cake(p10));
        
        Point p11 = new Point(15,12);
        rewardList.add(new Cake(p11));

        return rewardList;
    }
	
	/**
	 * SPAWNPUNISHMENTS METHOD
	 * returns arrayList of punishments both Freeze and Teleport tile in one list
	 */
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
        
        Point p7 = new Point(9,2);
        punishmentList.add(new Punishment(p7));
        
        Point p8 = new Point(18,12);
        punishmentList.add(new Punishment(p8));
        
        Point p9 = new Point(1,9);
        punishmentList.add(new Punishment(p9));
        
        Point p10 = new Point(14,7);
        punishmentList.add(new Punishment(p10));
        
        punishmentList.add(new TelePunishment(new Point(18,13)));
        
        return punishmentList;
    }
	
	/**
	 * COLLECTREWARDS METHOD
	 * Checks to see if player picks up a reward
	 * increments cakeCount if player picks one up
	 * Compares player position to reward position and the rewards visibility so that you cannot pickup invisible rewards
	 */
	private void collectRewards() {
		// Fills ArrayList with collectedCakes
        ArrayList<Cake> collectedCakes = new ArrayList<Cake>();
        for (Cake reward : rewards) {
            // if the player is on the same tile as a cake, collect it
            if (player.getPos().equals(reward.getPos()) && (reward.isVisible())) {
                // give the player some points for picking this up
                player.addScore(reward.getValue());
                collectedCakes.add(reward);
                if(reward instanceof BonusReward) {
                	cakeCount+=0;
                }
                else {
                	cakeCount++;
                }
            }
        }
        // remove collected cakes from the board
        rewards.removeAll(collectedCakes);
    }

	/**
	 * HITPUNISHMENT METHOD
	 * Checks to see if player lands on tile with punishment
	 * Same as collectReward, different effects sets player position to start if instanceof Telepunishment otherwise freezes player
	 */
	public void hitPunishment(Player player, ArrayList<Punishment> punishments) {
		// Fills ArrayList with collectedPunishments
        ArrayList<Punishment> collectedPunishments = new ArrayList<Punishment>();
        for (Punishment punishment : punishments) {
            // if the player is on the same tile as a punishment, hit it
            if (player.getPos().equals(punishment.getPos())) {
            	//Echoing when player hits punishment
            	System.out.println("Player hit Punishment at pos: " + punishment.getPos());
                // deduct points from total
                player.deductScore(punishment.getPenalty());
                collectedPunishments.add(punishment);
                //If current index is of class type TelePunishment, change player position
                if(punishment instanceof TelePunishment) {
                	player.setPos(new Point(0, 1));
                }
                else {
                	player.frozenStartTime = System.currentTimeMillis();
                }
            }
        }
        // remove collected cakes from the board
        punishments.removeAll(collectedPunishments);
    }
}
