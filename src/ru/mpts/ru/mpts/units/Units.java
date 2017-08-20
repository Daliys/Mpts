package ru.mpts.ru.mpts.units;

import java.util.ArrayList;
import java.util.List;

public class Units {
    public static List<Heroes> heroesList = new ArrayList<>();

    public Units() {
        AddHeroes(17, 17, 0);
        AddHeroes(21, 17, 1);
        AddHeroes(11, 15, 2);
        AddHeroes(0, 0);     /// не безопасный тк при удаление перс может нумирация ебнуться (подумать)
    }

    public static void AddHeroes(int x, int y, int id) {
        heroesList.add(new Heroes(x, y, id));

    }

    public static void AddHeroes(int x, int y) {
        heroesList.add(new Heroes(x, y, heroesList.size()));

    }


    public static void setHeroesTask(int x, int y, int numAction, int ID) {
        heroesList.get(ID).setTask(x, y, numAction);
    }


    public static void removeHeroesTask(int x, int y) {
        for (int i = 0; i < heroesList.size(); i++) {
            if (heroesList.get(i).getTaskX() == x && heroesList.get(i).getTaskY() == y) {
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
