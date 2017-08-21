package ru.mpts.units;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.mpts.inventory.Inventory;
import ru.mpts.map.Location;

public class Units {
    public static List<Hero> heroesList = new ArrayList<>();

    public Units() {
        AddHero(new Location(1, 15, 0), 100.0f, 100);
        AddHero(new Location(2, 15, 0), 100.0f, 100);
        AddHero(new Location(3, 15, 0), 100.0f, 100);
    }

    public static void AddHero(Location location, int id) {
        heroesList.add(new Hero(id, location, new Inventory(1), 1.0f, 100));
        heroesList.sort(Comparator.comparing(Hero::getId));
    }

    public static void AddHero(Location location, float speedMove, int healthPoints) {
        for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getId() != i) {
                heroesList.add(new Hero(i, location, new Inventory(1), speedMove, healthPoints));
                heroesList.sort(Comparator.comparing(Hero::getId));
                return;
            }
        }
        heroesList.add(new Hero(heroesList.size(), location, new Inventory(1), 1.0f, 100));
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
            System.out.println(heroesList.get(i).getId()+" "+i);
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
