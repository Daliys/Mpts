package ru.mpts.sprite;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import ru.mpts.map.Location;
import ru.mpts.map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Sprite {
    private HashMap<String, Image> images = new HashMap<>();
    private String[] fileNames;
    private int width;
    private int height;
    private Location location;
    private String pathSprites = "resources/sprites/";

    public Sprite(String[] fileNames, int countFiles, Location location) {
        this.fileNames = new String[countFiles];
        this.location = location;
        this.fileNames = fileNames;

        for (int i = 0; i < countFiles; i++) {
            File imageFile = new File(pathSprites + fileNames[i]);
            Image image;
            if (!imageFile.exists()) {
                System.out.println(pathSprites + fileNames[i]);
                image = new ImageIcon(pathSprites + "default.png").getImage();
            } else {
                image = new ImageIcon(pathSprites + fileNames[i]).getImage();
            }
            width = image.getWidth(null);
            height = image.getHeight(null);
            images.put(fileNames[i], image);
        }
    }

    public Sprite(String fileName, Location location) {
        this.fileNames = new String[1];
        this.location = location;
        this.fileNames[0] = fileName;

        File imageFile = new File(pathSprites + fileName);
        Image image;
        if (!imageFile.exists()) {
            System.out.println(pathSprites + fileName);
            image = new ImageIcon(pathSprites + "default.png").getImage();
        } else {
            image = new ImageIcon(pathSprites + fileName).getImage();
        }
        width = image.getWidth(null);
        height = image.getHeight(null);
        images.put(fileName, image);
    }

    public void draw(String key) {
        float scale = Map.getScale();
        int x = (int) (location.getX() * scale + Map.getIndentX());
        int y = (int) (location.getY() * scale + Map.getIndentY());

        if (Math.round(x - ((width / (64 / scale) - scale) / 2)) <= Display.WIGHT+60 && Math.round(y - ((height / (64 / scale) - scale) / 2)) <= Display.HIGHT+60 && Math.round(x - ((width / (64 / scale) - scale) / 2)) >= -70 && Math.round(y - ((height / (64 / scale) - scale) / 2)) >= -70)
            Engine.graphics2D.drawImage(images.get(key), Math.round(x - ((width / (64 / scale) - scale) / 2)), Math.round(y - ((height / (64 / scale) - scale))), Math.round(width / (64 / scale)), Math.round(height / (64 / scale)), null);
    }

    public void draw() {
        float scale = Map.getScale();
        int x = (int) (location.getX() * scale + Map.getIndentX());
        int y = (int) (location.getY() * scale + Map.getIndentY());

        if (Math.round(x - ((width / (64 / scale) - scale) / 2)) <= Display.WIGHT+60 && Math.round(y - ((height / (64 / scale) - scale) / 2)) <= Display.HIGHT+60 && Math.round(x - ((width / (64 / scale) - scale) / 2)) >= -70 && Math.round(y - ((height / (64 / scale) - scale) / 2)) >= -70)
        Engine.graphics2D.drawImage(images.get(fileNames[0]), Math.round(x - ((width / (64 / scale) - scale) / 2)), Math.round(y - ((height / (64 / scale) - scale))), Math.round(width / (64 / scale)), Math.round(height / (64 / scale)), null);
    }

    public void draw(int x, int y, int width, int height) {
        Engine.graphics2D.drawImage(images.get(fileNames[0]), x, y, width, height, null);
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
