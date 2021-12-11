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
