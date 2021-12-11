package com.BlobGame;

import java.awt.Point;
import java.util.ArrayList;

public class MovingObjects extends Entities {
	boolean wallNorth, wallSouth, wallEast, wallWest = false; //Wall detection booleans
	
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
}
