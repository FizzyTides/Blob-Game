package com.BlobGame;

import java.awt.Point;

public class TelePunishment extends Punishment{
	
	public TelePunishment(Point pos) {
		super(pos);
		this.pos = pos;
		this.imageName = "TeleportTile.png";
		this.setPenalty(50);
		
		loadImage();
	}
}
