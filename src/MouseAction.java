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
                e.getX() <= (Map.WightMap * Map.scale + 5) && e.getY() <= (Map.HeightMap * Map.scale + 5)) {
            int x = (int) ((e.getX() - Map.IndentX) / Map.scale);
            int y = (int) ((e.getY() - Map.IndentY) / Map.scale);
            System.out.println("Mouse x:" + x + "  Mouse y:" + y);
            Display.MenutextLabel.setText("x:" + x + "  y:" + y);
            if (MouseStage == "mine") {
                TaskPlayers.AddTask(x, y, "mine");
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
