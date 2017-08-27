package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.units.Units;

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
        int x = (int) ((e.getX() - Map.getIndentX()) / Map.getScale());
        int y = (int) ((e.getY() - Map.getIndentY()) / Map.getScale());
        if(Units.getHero(new Location(x,y,0)) != null){

                HandlingMouseEvent.followTheHeroID = Units.getHero(new Location(x,y,0)).getId();
                System.out.println(HandlingMouseEvent.followTheHeroID);


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        HandlingMouseEvent.followTheHeroID = -1;
        if(isMouseBeyundBorders(new Location(e.getX(),e.getY(),0))) {
            handlingMouseEvent.getLocationStartSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
            handlingMouseEvent.getLocationStartSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
            handlingMouseEvent.getLocationNowSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
            handlingMouseEvent.getLocationNowSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
            handlingMouseEvent.setIsSelect(true);
            handlingMouseEvent.setIsPressMouse(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isMouseBeyundBorders(new Location(e.getX(),e.getY(),0))) {
            handlingMouseEvent.getLocationNowSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
            handlingMouseEvent.getLocationNowSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
        }
        if(handlingMouseEvent.isSelect()) {
            handlingMouseEvent.setIsSelect(false);
            handlingMouseEvent.setIsPressMouse(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        if(isMouseBeyundBorders(new Location(e.getX(),e.getY(),0))) {
            if (handlingMouseEvent.isSelect()) {

                handlingMouseEvent.getLocationNowSelect().setX((int) ((e.getX() - Map.getIndentX()) / Map.getScale()));
                handlingMouseEvent.getLocationNowSelect().setY((int) ((e.getY() - Map.getIndentY()) / Map.getScale()));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private boolean isMouseBeyundBorders(Location location){
        if ((location.getX() >= Map.getIndentX()) && (location.getY() >= Map.getIndentY())
                && (location.getX() <= (Map.getWightMap() * Map.getScale() + Map.getIndentX() - 2))
                && (location.getY() <= (Map.getHeightMap() * Map.getScale() + Map.getIndentY())-2)) {
            return true;
        }
        return false;
    }
}
