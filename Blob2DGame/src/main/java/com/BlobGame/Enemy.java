package com.BlobGame;

import java.awt.Point;

public class Enemy extends Entities {
	
	Enemy(Point pos) {
		this.imageName = "Enemy.png";
		this.pos = pos;
		
		loadImage();
	}
	
	public void enemyMovement() {
		
	}

}
