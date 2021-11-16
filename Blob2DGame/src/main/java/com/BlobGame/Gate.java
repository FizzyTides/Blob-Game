package com.BlobGame;

import java.awt.Point;

public class Gate extends Wall {
	
	public Gate(Point pos) {
		super(pos);
		this.imageName = "Gate.png";
		this.pos = pos;
		
		loadImage();
	}
	
	
}
