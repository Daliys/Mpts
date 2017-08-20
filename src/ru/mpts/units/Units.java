package ru.mpts.units;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ru.mpts.map.Location;

public class Units {
    public static List<Heroes> heroesList = new ArrayList<>();

    public Units() {
        AddHero(new Location(1, 15, 0));
        AddHero(new Location(2, 15, 0));
        AddHero(new Location(3, 15, 0));
    }

    public static void AddHero(Location location, int id) {
        heroesList.add(new Heroes(location, id));
        heroesList.sort(Comparator.comparing(Heroes::getId));
    }

    public static void AddHero(Location location) {
        for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getId() != i) {
                heroesList.add(new Heroes(location, i));
                return;
            }
        }
        heroesList.add(new Heroes(location, heroesList.size()));
    }

    public static void RemoveHero(int id) {
        heroesList.remove(id);
        heroesList.sort(Comparator.comparing(Heroes::getId));
    }

    public static void setHeroesTask(Location location, int numAction, int ID) {
        heroesList.get(ID).setTask(location, numAction);
    }


    public static void removeHeroesTask(Location location) {
        for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getTaskLocation().equals(location)) {
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
