package com.BlobGame;

import java.io.IOException;
import java.awt.Point;

import javax.imageio.ImageIO;

public class Wall extends Entities {
	
	void wall(Point pos) {
		imageName = "Wall.png";
		this.pos = pos;
		
		loadImage();
		
	}
	
}