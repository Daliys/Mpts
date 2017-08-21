package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.timers.Timer;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.TaskType;

import java.applet.Applet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseAction extends Applet implements MouseListener, MouseMotionListener {

    private static HandlingMouseEvent handlingMouseEvent;

    public MouseAction() {
        addMouseListener(this);
        addMouseMotionListener(this);

        handlingMouseEvent = new HandlingMouseEvent();
    }

    public static void setMouseStage(String stage) {
        handlingMouseEvent.setMouseStage(stage);
        Display.MenuTextStageMouse.setText(stage);
    }

    public static String getMouseStage() {
        return handlingMouseEvent.getMouseStage();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*if (e.getX() >= 5 && e.getY() >= 5 &&
                e.getX() <= (Map.getWightMap() * Map.getScale() + 5) && e.getY() <= (Map.getHeightMap() * Map.getScale() + 5)) {
            int x = (int) ((e.getX() - Map.getIndentX()) / Map.getScale());
            int y = (int) ((e.getY() - Map.getIndentY()) / Map.getScale());
            System.out.println("Mouse x:" + x + "  Mouse y:" + y);
            Display.MenutextLabel.setText("x:" + x + "  y:" + y);
            if (MouseStage == "mine") {
                TaskPlayers.AddTask(new Location(x, y, 0), TaskType.MINE);
            }
        }*/
        handlingMouseEvent.getLocationStartSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
        handlingMouseEvent.getLocationStartSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
        handlingMouseEvent.setIsSelect(true);
        handlingMouseEvent.setIsPressMouse(true);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        handlingMouseEvent.getLocationNowSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
        handlingMouseEvent.getLocationNowSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
        handlingMouseEvent.setIsSelect(false);
        handlingMouseEvent.setIsPressMouse(false);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (handlingMouseEvent.isSelect()) {
            handlingMouseEvent.getLocationNowSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
            handlingMouseEvent.getLocationNowSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
        }
    }
}
