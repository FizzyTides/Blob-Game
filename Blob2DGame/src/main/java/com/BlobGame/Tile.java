package com.BlobGame;

import java.awt.Point;

public class Tile extends Entities {
	
	Tile(Point pos) {
		this.imageName = "tile.png";
		this.pos = pos;
		loadImage();
	}

}
