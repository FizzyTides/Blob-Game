package com.BlobGame;

import java.awt.Point;

/**TELEPUNISHMENT CLASS
 * This is a subsubclass of Entities and a subclass of Punishment which handles solely the Teleportation Trap functionalities
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class TelePunishment extends Punishment{
	
	public TelePunishment(Point pos) {
		super(pos);
		this.pos = pos;
		this.imageName = "TeleportTile.png";
		this.setPenalty(50);
		
		loadImage();
	}
}
