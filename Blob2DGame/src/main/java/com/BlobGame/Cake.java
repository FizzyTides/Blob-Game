package com.BlobGame;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class Cake extends Entities {

    protected  int reward;
    protected boolean visibility;

    public Cake (Point position) {

        this.imageName = "Cake.png";
        this.pos =  position;
        this.reward = 100;
        this.visibility = true;

        loadImage();
    }

    public int rewardValue(){

        return reward;
    }

    public boolean isVisible(){

        return visibility;
    }
    


}