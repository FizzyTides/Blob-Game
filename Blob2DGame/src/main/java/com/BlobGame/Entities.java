package com.BlobGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**ENTITIES CLASS
 * This class is the superclass for all our entites such as walls, player, enemies, punishments, cakes etc.
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class Entities {
	
	String imageName;
	boolean wallNorth, wallSouth, wallEast, wallWest = false; //Wall detection booleans
	protected BufferedImage Image;
	protected Point pos; // Player position on grid using Point Variable (same as processing PVectors)
	
	public Entities() {
		//ENTITES CONSTRUCTOR AS PLACEHOLDER
	}
	
	/**
	 * LOADIMAGE METHOD
	 * Loads entity image corresponding to its ImageName, inherited to other subclasses
	 */
	public void loadImage() {
		try {
			Image = ImageIO.read(new File("src/main/resources/" + imageName));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	/**
	 * CHECKWALLS METHOD
	 * Handles player wall check by comparing player's x and y coordinates to the indexed wall's x and y coordinate
	 * Triggers corresponding wall boolean to restrict player movement upon attempting to move onto wall with tile
	 */
	
	public void checkWalls(ArrayList<Wall> gameWalls) {
        Point CurrPos = this.getPos();
        boolean aWallNorth, aWallSouth, aWallEast, aWallWest;
        aWallNorth = aWallSouth = aWallEast = aWallWest = false;
        for(Wall walls : gameWalls) {
            Point wallCurrPos = walls.getPos();
            

            if((CurrPos.y - 1 == wallCurrPos.y && CurrPos.x == wallCurrPos.x) || aWallNorth) {
                this.wallNorth = true;
                aWallNorth = true;
                //System.out.println("There is a wall to the North!");
            }
            else {
            	this.wallNorth = false;
            }
            if((CurrPos.y + 1 == wallCurrPos.y && CurrPos.x == wallCurrPos.x) || aWallSouth) {
                this.wallSouth = true;
                aWallSouth = true;
                //System.out.println("There is a wall to the South!");
            }
            else {
            	this.wallSouth = false;
            }
            if((CurrPos.x - 1== wallCurrPos.x && CurrPos.y == wallCurrPos.y) || aWallEast) {
                this.wallEast = true;
                aWallEast = true;
                //System.out.println("There is a wall to the East!");
            }
            else {
            	this.wallEast = false;
            }
            if((CurrPos.x + 1 == wallCurrPos.x && CurrPos.y == wallCurrPos.y) || aWallWest) {
                this.wallWest = true;
                aWallWest = true;
               //System.out.println("There is a wall to the West!");
            }
            else {
            	this.wallWest = false;
            }
        }
	}
	
	/**
	 * DRAW METHOD
	 * Draws image using its position * TILE_SIZE
	 * @param g
	 * @param watcher
	 */
	public void draw(Graphics g, ImageObserver watcher) {
		g.drawImage(Image, pos.x * GameBoard.TILE_SIZE, pos.y * GameBoard.TILE_SIZE , watcher);
	}

	/**
	 * GETPOS METHOD
	 * returns this.pos
	 * @return
	 */
	public Point getPos() {
        return this.pos;
    }
	
	/**
	 * SETPOS
	 * sets this.pos = pos
	 * @param pos
	 */
    public void setPos(Point pos) {
    	this.pos = pos;
    }
}
