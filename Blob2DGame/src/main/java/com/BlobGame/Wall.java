package com.BlobGame;

import java.io.IOException;
import java.awt.Point;

import javax.imageio.ImageIO;

public class Wall extends Entities {
	
	Wall(Point pos) {
		imageName = "Wall.png";
		this.pos = pos;
		
		loadImage();
		
	}
	
	public Point getWallPos() {
		return this.pos;
	}
	
}