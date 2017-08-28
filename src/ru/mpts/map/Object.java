package ru.mpts.map;

import ru.mpts.sprite.Sprite;

public class Object {
    private Location location;
    private int type;
    private int durability;
    private Sprite sprite;

    public Object(Location location, int type) {
        this.location = location;
        this.type = type;
        durability = 100;

        switch (type) {
            case MapObjectType.AIR: {
                sprite = new Sprite("air.png", location);
                break;
            }
            case MapObjectType.GRASS: {
                sprite = new Sprite("grass(1).png", location);
                break;
            }
            case MapObjectType.HERO: {
                sprite = new Sprite("hero(3).png", location);
                break;
            }
            case MapObjectType.IRON_ORE: {
                sprite = new Sprite("ironOreBlockCenter(1).png", location);
                break;
            }
            case MapObjectType.STONE: {
                int counts = 16;
                String[] paths = new String[counts];

                paths[0] = "stoneBlock(Center).png";
                paths[1] = "stoneBlock(Up).png";
                paths[2] = "stoneBlock(Down).png";
                paths[3] = "stoneBlock(Left).png";
                paths[4] = "stoneBlock(Right).png";
                paths[5] = "stoneBlock(UpLeft).png";
                paths[6] = "stoneBlock(UpRight).png";
                paths[7] = "stoneBlock(DownLeft).png";
                paths[8] = "stoneBlock(DownRight).png";
                paths[9] = "stoneBlock(UpDownLeft).png";
                paths[10] = "stoneBlock(UpDownRight).png";
                paths[11] = "stoneBlock(UpLeftRight).png";
                paths[12] = "stoneBlock(DownLeftRight).png";
                paths[13] = "stoneBlock(Only).png";
                paths[14] = "stoneBlock(UpDown).png";
                paths[15] = "stoneBlock(LeftRight).png";
                sprite = new Sprite(paths, counts, location);
                break;
            }
            default: {
                sprite = new Sprite("default.png", location);
                break;
            }
        }
    }

    public void draw() {
        int x = location.getX();
        int y = location.getY();

        switch (type) {
            case MapObjectType.STONE: {
                if (Map.getObject(x, y-1).getType() == MapObjectType.AIR || Map.getObject(x, y-1).getType() == MapObjectType.HERO) {
                    if (Map.getObject(x, y+1).getType() == MapObjectType.AIR || Map.getObject(x, y+1).getType() == MapObjectType.HERO) {
                        if (Map.getObject(x-1, y).getType() == MapObjectType.AIR || Map.getObject(x-1, y).getType() == MapObjectType.HERO) {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(Only).png");
                            } else {
                                sprite.draw("stoneBlock(UpDownLeft).png");
                            }
                        } else {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(UpDownRight).png");
                            } else {
                                sprite.draw("stoneBlock(UpDown).png");
                            }
                        }
                    } else {
                        if (Map.getObject(x-1, y).getType() == MapObjectType.AIR || Map.getObject(x-1, y).getType() == MapObjectType.HERO) {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(UpLeftRight).png");
                            } else {
                                sprite.draw("stoneBlock(UpLeft).png");
                            }
                        } else {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(UpRight).png");
                            } else {
                                sprite.draw("stoneBlock(Up).png");
                            }
                        }
                    }
                } else {
                    if (Map.getObject(x, y+1).getType() == MapObjectType.AIR || Map.getObject(x, y+1).getType() == MapObjectType.HERO) {
                        if (Map.getObject(x-1, y).getType() == MapObjectType.AIR || Map.getObject(x-1, y).getType() == MapObjectType.HERO) {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(DownLeftRight).png");
                            } else {
                                sprite.draw("stoneBlock(DownLeft).png");
                            }
                        } else {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(DownRight).png");
                            } else {
                                sprite.draw("stoneBlock(Down).png");
                            }
                        }
                    } else {
                        if (Map.getObject(x-1, y).getType() == MapObjectType.AIR || Map.getObject(x-1, y).getType() == MapObjectType.HERO) {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(LeftRight).png");
                            } else {
                                sprite.draw("stoneBlock(Left).png");
                            }
                        } else {
                            if (Map.getObject(x+1, y).getType() == MapObjectType.AIR || Map.getObject(x+1, y).getType() == MapObjectType.HERO) {
                                sprite.draw("stoneBlock(Right).png");
                            } else {
                                sprite.draw("stoneBlock(Center).png");
                            }
                        }
                    }
                }
                break;
            }
            case MapObjectType.AIR: {
                break;
            }
            default: {
                sprite.draw();
                break;
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
