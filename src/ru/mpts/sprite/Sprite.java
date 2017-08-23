package ru.mpts.sprite;

import ru.mpts.engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sprite {
    private int width;
    private int height;
    private int x = -1;
    private int y = -1;
    private float scale = -1;
    private String pathSprites = "resources/sprites/";
    Image image;
    Graphics2D graphics2D;

    public Sprite(String fileName, Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        File imageFile = new File(pathSprites + fileName);
        if (!imageFile.exists()) {
            image = new ImageIcon(pathSprites + "default.png").getImage();
        } else {
            image = new ImageIcon(pathSprites + fileName).getImage();
        }
        image = new ImageIcon(pathSprites + fileName).getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void draw(int x, int y, float scale)  {
        //graphics2D.drawImage(image, 0,0, 64, 64, null);
        //if (this.x != x || this.y != y || this.scale != scale) {
            Engine.graphics2D.drawImage(image, Math.round(x - ((width / (64 / scale) - scale) / 2)), Math.round(y - ((height / (64 / scale) - scale))), Math.round(width / (64 / scale)), Math.round(height / (64 / scale)), null);
        //}
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
