package ru.mpts.units;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.mpts.inventory.Inventory;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapGroundType;
import ru.mpts.map.MapObjectType;

public class Units {
    public static List<Hero> heroesList = new ArrayList<>();

    public Units() {
        int units = 2;
        end:
        while (units > 0) {
            for (int x = 0; x < Map.getWightMap(); x++) {
                for (int y = 0; y < Map.getHeightMap(); y++) {
                    if (Map.getObject(new Location(x, y, 0)).getType() == MapObjectType.AIR && (int)(Math.random() * 257) == 1) {
                        AddHero(new Location(x, y, 0), 1.0f, 100);
                        units--;

                        if (units <= 0) break end;
                    }
                }
            }
        }
    }

    public static void AddHero(Location location, int id) {
        heroesList.add(new Hero(id, location, new Inventory(1), 1.0f, 100));
        heroesList.sort(Comparator.comparing(Hero::getId));
    }

    public static Hero getHero(Location location){
        for(int a = 0; a < heroesList.size(); a++) {
            if (heroesList.get(a).getLocation().getX() == location.getX() && heroesList.get(a).getLocation().getY() == location.getY()){
                return heroesList.get(a);
            }
        }
        return null;
    }

    public static Hero getHero(int Id){
        if(Id <= heroesList.size())
        return heroesList.get(Id);
        return null;
    }

    public static void AddHero(Location location, float speedMove, int healthPoints) {
        for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getId() != i) {
                heroesList.add(new Hero(i, location, new Inventory(1), speedMove, healthPoints));
                heroesList.sort(Comparator.comparing(Hero::getId));
                return;
            }
        }
        heroesList.add(new Hero(heroesList.size(), location, new Inventory(1), speedMove, 100));
        heroesList.sort(Comparator.comparing(Hero::getId));
    }

    public static void RemoveHero(int id) {
        heroesList.remove(id);
        heroesList.sort(Comparator.comparing(Hero::getId));
    }

    public static void setHeroesTask(Location location, int numAction, int ID) {
        heroesList.get(ID).setTask(location, numAction);

    }


    public static void removeHeroesTask(Location location) {
       for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getTaskLocation().getX() == location.getX() && heroesList.get(i).getTaskLocation().getY() == location.getY()) {
                heroesList.get(i).removeTask();
                
                return;
            }
        }
    }

    public static void update() {
        for (int i = 0; i < heroesList.size(); i++) {
            heroesList.get(i).update();
        }
    }

    public static void render() {
        for (int i = 0; i < heroesList.size(); i++) {
            heroesList.get(i).render();
        }
    }

}
