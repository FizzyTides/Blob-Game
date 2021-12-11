package com.BlobGame;

import java.awt.Point;

/**GATE CLASS
 * This is a subclass of Wall, which handles the gate functionalities by inheriting its parents
 * 
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class Gate extends Wall {
	
	/**
	 * Gate Constructor
	 * @param pos
	 */
	public Gate(Point pos) {
		super(pos);
		this.imageName = "Gate.png";
		this.pos = pos;
		
		loadImage();
	}
	
	
}
