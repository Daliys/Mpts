package ru.mpts.engine;

import ru.mpts.listener.Events.*;
import ru.mpts.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Display {
    //public static int WIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
   // public static int HIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static int WIGHT = (int)800; // тестровые значения
    public static int HIGHT = (int)800; // тестровые значения
    public static JFrame frame;
    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor = 0xff000000;
    private static Canvas content;

    private static BufferStrategy bufferStrategy;
    ///for PanelMenu
    public static JLabel MenutextLabel;
    public static JLabel MenuTextStageMouse;
    public static JLabel MenuTextTask;
    public static JLabel MenuTextSelect;
    public static JLabel MenuTextInformationHero;

    public static JButton MenuButtonMine;
    public static JButton MenuButtonCancel;
    public static JButton MenuButtonBuild;


    public static void CreateBuffer(int _color, int numBuffer) {
        frame = new JFrame("Mpts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content = new Canvas();
        Dimension size = new Dimension(WIGHT, HIGHT);
        content.setPreferredSize(size);
        content.addMouseListener(new MouseAction());     // cлушатель мыши
        content.addMouseMotionListener(new MouseAction());     // cлушатель мыши
        content.addKeyListener(new KeyActionListener());
        content.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    if(Map.getScale() < 64) {
                        double CellsOfCenterX = (((Display.WIGHT/2) - Map.getIndentX()) / Map.getScale());     // находим количество клеток до центра
                        double changeIndentX = (CellsOfCenterX * (Map.getScale() + 3) - (Display.WIGHT/2));        // находим на сколько нужно подвинуть что бы цетр был == клеток центру
                        changeIndentX *= -1;        // инверсируем

                        Map.setIndentX((int)(changeIndentX));

                        double CellsOfCenterY = (((Display.HIGHT/2) - Map.getIndentY()) / Map.getScale());     // находим количество клеток до центра
                        double changeIndentY = (CellsOfCenterY * (Map.getScale() + 3) - (Display.HIGHT/2));        // находим на сколько нужно подвинуть что бы цетр был == клеток центру
                        changeIndentY *= -1;        // инверсируем

                        Map.setIndentY((int)(changeIndentY));

                        Map.setScale(Map.getScale() + 3);

                    }
                } else {
                    if(Map.getScale() > 9) {
                        double CellsOfCenterX = (((Display.WIGHT/2) - Map.getIndentX()) / Map.getScale());     // находим количество клеток до центра
                        double changeIndentX = (CellsOfCenterX * (Map.getScale() - 3) - (Display.WIGHT/2));        // находим на сколько нужно подвинуть что бы цетр был == клеток центру
                        changeIndentX *= -1;        // инверсируем

                        Map.setIndentX((int)(changeIndentX));

                        double CellsOfCenterY = (((Display.HIGHT/2) - Map.getIndentY()) / Map.getScale());     // находим количество клеток до центра
                        double changeIndentY = (CellsOfCenterY * (Map.getScale() - 3) - (Display.HIGHT/2));        // находим на сколько нужно подвинуть что бы цетр был == клеток центру
                        changeIndentY *= -1;        // инверсируем

                        Map.setIndentY((int)(changeIndentY));

                        Map.setScale(Map.getScale() - 3);

                    }
                }

            }
        });

        // обьявления основных панелей
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelCanvas = new JPanel();
        JPanel panelMenu = new JPanel();
        // ------------


        JPanel panelMenuInformation = new JPanel();
        MenutextLabel = new JLabel("x:0  y:0");
        MenuTextStageMouse = new JLabel("mouse");
        MenuTextTask = new JLabel("Task: 0");
        MenuTextSelect = new JLabel("Select: 0");
        MenuTextInformationHero = new JLabel("Hero /");
        Dimension dimensionMenuTextInformationHero = new Dimension(200, 600);
        MenuTextInformationHero.setPreferredSize(dimensionMenuTextInformationHero);


        JPanel panelMenuAction = new JPanel();
        Dimension MenuSizeButtonMine = new Dimension(75, 25);



        MenuButtonMine = new JButton("Mine");
        MenuButtonMine.setPreferredSize(MenuSizeButtonMine);
        MenuButtonMine.setActionCommand(MouseTypeAction.MINE);
        MenuButtonMine.addActionListener(new MenuActionListener());     // cлушатель кнопкпи

        MenuButtonCancel = new JButton("Cancel");
        MenuButtonCancel.setPreferredSize(MenuSizeButtonMine);
        MenuButtonCancel.setActionCommand(MouseTypeAction.CANCEL);
        MenuButtonCancel.addActionListener(new MenuActionListener());     // cлушатель кнопкпи

        MenuButtonBuild = new JButton("Build");
        MenuButtonBuild.setPreferredSize(MenuSizeButtonMine);
        MenuButtonBuild.setActionCommand(MouseTypeAction.BUILD);
        MenuButtonBuild.addActionListener(new MenuActionListener());     // cлушатель кнопкпи



        //панель информации
        panelMenuInformation.add(MenutextLabel);
        panelMenuInformation.add(MenuTextStageMouse);
        panelMenuInformation.add(MenuTextTask);
        panelMenuInformation.add(MenuTextSelect);
        panelMenuInformation.add(MenuTextInformationHero);

        // панель кнопак
        panelMenuAction.add(MenuButtonMine);
        panelMenuAction.add(MenuButtonCancel);
        panelMenuAction.add(MenuButtonBuild);


        // панель меню с кнопками и информацией
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.add(panelMenuInformation);
        panelMenu.add(panelMenuAction);

        Dimension sizeMenu = new Dimension(200, HIGHT);
        panelMenu.setPreferredSize(sizeMenu);
        panelMenu.setBackground(new Color(0x4D4E4F));

        panelCanvas.setBackground(Color.BLACK);
        panelCanvas.add(content);

        panel.add(panelCanvas, BorderLayout.CENTER);
        panel.add(panelMenu, BorderLayout.EAST);

        //  frame.setSize(WIGHT,HIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addMouseListener(new MouseAction());

        buffer = new BufferedImage(WIGHT, HIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);       //сглаживание
        clearColor = _color;

        content.createBufferStrategy(numBuffer);
        bufferStrategy = content.getBufferStrategy();
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }       // закрашивает экран белым цветом

    public static void swapBuffer() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static void setTitle(String title) {
        frame.setTitle("The World of Tasks   " + title);
    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }
}
