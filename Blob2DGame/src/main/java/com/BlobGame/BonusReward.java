package com.BlobGame;

import java.awt.Point;

public class BonusReward extends Cake{

    BonusReward(Point position){

        super(position);
        this.imageName = "Cake.png"; //TODO change image
        this.visibility = false;
        this.isBonus = true;

        loadImage();

    }
    

}
