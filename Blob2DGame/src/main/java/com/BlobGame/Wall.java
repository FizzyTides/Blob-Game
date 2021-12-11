package com.BlobGame;

import java.io.IOException;
import java.awt.Point;

import javax.imageio.ImageIO;

/**WALL CLASS
 * This is a subclass of Entities, which handles all wall functionalities
 * 
 * @author mca
 * @author mba
 * @author ketan
 */
public class Wall extends Entities {
	
	Wall(Point pos) {
		imageName = "Wall.png";
		this.pos = pos;
		
		//System.out.println("Spawning Wall at point: " + this.getPos());
		loadImage();
		
	}
	
}