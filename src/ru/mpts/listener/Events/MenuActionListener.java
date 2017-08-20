package ru.mpts.listener.Events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Mine") {
            if (MouseAction.getMouseStage() == "mine") {
                MouseAction.setMouseStage("mouse");
            } else {
                System.out.println("Mine");
                MouseAction.setMouseStage("mine");
            }
        }
    }
}
