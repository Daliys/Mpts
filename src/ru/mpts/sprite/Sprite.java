package ru.mpts.sprite;

import ru.mpts.engine.Engine;

import ru.mpts.map.Location;
import ru.mpts.map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sprite {
    Image image;
    private int width;
    private int height;
    private Location location;
    private String pathSprites = "resources/sprites/";

    public Sprite(String fileName, Location location) {
        this.location = location;

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

    public void draw() {
        float scale = Map.getScale();
        int x = (int) (location.getX() * scale + Map.getIndentX());
        int y = (int) (location.getY() * scale + Map.getIndentY());

        Engine.graphics2D.drawImage(image, Math.round(x - ((width / (64 / scale) - scale) / 2)), Math.round(y - ((height / (64 / scale) - scale))), Math.round(width / (64 / scale)), Math.round(height / (64 / scale)), null);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
