package com.BlobGame;

import java.awt.Point;

/**EXITTILE CLASS 
 * This class is a subclass of Entities, it handles solely the exit tile functionalities
 * @author mca
 * @author mba
 * @author ketan
 *
 */
class ExitTile extends Entities {
	
	/**
	 * EXITTILE CONSTRUCTOR
	 * @param pos
	 * ExitTile subclass of Entities spawns a tile at Point Pos
	 * and loads its corresponding image "ExitTile.png"
	 */
	public ExitTile(Point pos) {
		this.imageName = "ExitTile.png";
		this.pos = pos;
		
		loadImage();
		
	}
	
	
}



