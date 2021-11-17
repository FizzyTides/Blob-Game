package com.BlobGame;

import java.awt.Point;

public class BonusReward extends Cake{

    BonusReward(Point position){

        super(position);
        this.imageName = "Cake.png"; //TODO change image
        this.reward = 500;
        this.visibility = false;
        this.isBonus = true;

        loadImage();

    }
}
