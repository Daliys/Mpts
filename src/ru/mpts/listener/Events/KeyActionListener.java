package ru.mpts.listener.Events;

import ru.mpts.map.Map;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyActionListener implements KeyListener {
    private static boolean pressKey[] = new boolean[4];       //0 - w, 1 - s , 2 - a, 3 - d
    private static int speedScrollMap = 3;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == e.VK_W) {
            if (!pressKey[0])
                pressKey[0] = true;
        }
        if (e.getKeyCode() == e.VK_S) {
            if (!pressKey[1])
                pressKey[1] = true;
        }
        if (e.getKeyCode() == e.VK_A) {
            if (!pressKey[2])
                pressKey[2] = true;
        }
        if (e.getKeyCode() == e.VK_D) {
            if (!pressKey[3])
                pressKey[3] = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == e.VK_W) {
            if (pressKey[0])
                pressKey[0] = false;
        }
        if (e.getKeyCode() == e.VK_S) {
            if (pressKey[1])
                pressKey[1] = false;
        }
        if (e.getKeyCode() == e.VK_A) {
            if (pressKey[2])
                pressKey[2] = false;
        }
        if (e.getKeyCode() == e.VK_D) {
            if (pressKey[3])
                pressKey[3] = false;
        }

    }

    public static void render() {
        if (pressKey[0]) {
            Map.setIndentY(Map.getIndentY()-speedScrollMap);
        }
        if (pressKey[1]) {
            Map.setIndentY(Map.getIndentY()+speedScrollMap);
        }
        if (pressKey[2]) {
            Map.setIndentX(Map.getIndentX()-speedScrollMap);
        }
        if (pressKey[3]) {
            Map.setIndentX(Map.getIndentX()+speedScrollMap);
        }
    }
}
