package com.BlobGame;

import java.awt.Point;

/**BONUSREWARD CLASS
 * This is a subsubclass of Entities and a subclass of Cake, which handles solely the functionalities of the Bonus Reward
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class BonusReward extends Cake{

	/**
	 * BonusReward Constructor
	 * @param position
	 */
    BonusReward(Point position){

        super(position);
        this.imageName = "Cake.png"; //TODO change image
        this.visibility = false;
        this.isBonus = true;

        loadImage();

    }
    

}
