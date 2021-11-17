package com.BlobGame;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class Punishment extends Entities{
    
    private int penalty;

    public Punishment (Point position) {

        this.imageName = "FreezeTile.png";
        this.pos =  position;
        this.setPenalty(100);

        loadImage();
    }
    
    public void setPenalty(int value) {
    	this.penalty = value;
    }
    
    public int getPenalty() {
    	return this.penalty;
    }
}
