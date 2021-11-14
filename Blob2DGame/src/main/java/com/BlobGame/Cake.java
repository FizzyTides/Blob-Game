package com.BlobGame;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.awt.Point;
import java.awt.event.KeyEvent;

public class Cake extends Entities {

    private static final int reward = 10;

    public Cake (Point position) {

        this.imageName = "Cake.png";
        this.pos =  position;

        loadImage();
    }

}
