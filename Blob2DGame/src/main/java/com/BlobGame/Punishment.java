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
    
    public static final int penalty = 100;

    public Punishment (Point position) {

        this.imageName = "Punishment.png";
        this.pos =  position;

        loadImage();
    } 
    
}
