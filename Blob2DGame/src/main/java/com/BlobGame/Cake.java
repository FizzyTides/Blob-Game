package com.BlobGame;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;

/**CAKE CLASS
 * This is a subclass of Entities, which handles solely all objects of type Cakes functionalities
 * 
 * @author mca
 * @author mba
 * @author ketan
 *
 */
public class Cake extends Entities {

    protected  int reward;
    protected boolean visibility;
    protected boolean isBonus;

    /**
     * Cake Constructor
     * @param position
     */
    public Cake (Point position) {

        this.imageName = "CakeSlice.png";
        this.pos =  position;
        this.reward = 100;
        this.visibility = true;
        this.isBonus = false;
        
        loadImage();
    }

    public boolean isVisible(){
        return visibility;
    }
    
    public void setBonusReward(int rewardValue) {
    	this.reward = rewardValue;
    }
    
    public int getValue() {
    	return this.reward;
    }


}