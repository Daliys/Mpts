package ru.mpts.listener.Events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == MouseTypeAction.MINE) {
            if (MouseAction.getMouseStage() == MouseTypeAction.MINE) {
                System.out.println(MouseTypeAction.MOUSE);
                MouseAction.setMouseStage(MouseTypeAction.MOUSE);
            } else {
                System.out.println(MouseTypeAction.MINE);
                MouseAction.setMouseStage(MouseTypeAction.MINE);
            }
        }
        if (e.getActionCommand() == MouseTypeAction.CANCEL) {
            if (MouseAction.getMouseStage() == MouseTypeAction.CANCEL) {
                System.out.println(MouseTypeAction.MOUSE);
                MouseAction.setMouseStage(MouseTypeAction.MOUSE);
            } else {
                System.out.println(MouseTypeAction.CANCEL);
                MouseAction.setMouseStage(MouseTypeAction.CANCEL);
            }
        }

    }
}
