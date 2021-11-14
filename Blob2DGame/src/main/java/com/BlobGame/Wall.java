package com.BlobGame;

import java.io.File;
import java.io.IOException;
import java.awt.Point;

import javax.imageio.ImageIO;

public class Wall extends Entities {
	
	void wall(Point pos) {
		imageName = "Wall.png";
		this.pos = pos;
		
		loadImage();
		
	}
	
	@Override
	public void loadImage() {
		try {
			Image = ImageIO.read(new File("src/main/resources/" + imageName));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
}