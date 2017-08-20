package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.TaskType;

import java.applet.Applet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseAction extends Applet implements MouseListener {

    public static String MouseStage = "mouse";

    public static void setMouseStage(String stage) {
        MouseStage = stage;
        Display.MenuTextStageMouse.setText(stage);
    }

    public static String getMouseStage() {
        return MouseStage;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() >= 5 && e.getY() >= 5 &&
                e.getX() <= (Map.getWightMap() * Map.getScale() + 5) && e.getY() <= (Map.getHeightMap() * Map.getScale() + 5)) {
            int x = (int) ((e.getX() - Map.getIndentX()) / Map.getScale());
            int y = (int) ((e.getY() - Map.getIndentY()) / Map.getScale());
            System.out.println("Mouse x:" + x + "  Mouse y:" + y);
            Display.MenutextLabel.setText("x:" + x + "  y:" + y);
            if (MouseStage == "mine") {
                TaskPlayers.AddTask(new Location(x, y, 0), TaskType.MINE);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
