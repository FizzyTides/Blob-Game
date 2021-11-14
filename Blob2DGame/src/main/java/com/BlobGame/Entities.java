package com.BlobGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entities {
	
	String imageName;
	protected BufferedImage Image;
	protected Point pos; // Player position on grid using Point Variable (same as processing PVectors)
	
	public Entities() {

	}

	public void loadImage() {
		try {
			Image = ImageIO.read(new File("C:\\Users\\dhing\\OneDrive\\Desktop\\Family\\Ketan\\UNI\\Fall2021\\cmpt276\\project\\Blob2DGame\\src\\main\\resources\\" + imageName));
		} catch (IOException ex) {
			System.out.println("Cannot open this file: " + ex.getMessage());
		}
	}
	
	public void draw(Graphics g, ImageObserver watcher) {
		g.drawImage(Image, pos.x * GameBoard.TILE_SIZE, pos.y * GameBoard.TILE_SIZE , watcher);
	}

	public Point getPos() {
        return pos;
    }
}
