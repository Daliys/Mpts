package ru.mpts.engine;

import ru.mpts.listener.Events.KeyActionListener;
import ru.mpts.listener.Events.MenuActionListener;
import ru.mpts.listener.Events.MouseAction;
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
    public static int HIGHT = 600;
    public static int WIGHT = 600;
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

    public static JButton MenuButtonMine;


    public static void CreateBuffer(int _color, int numBuffer) {
        frame = new JFrame("Mpts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        content = new Canvas();
        Dimension size = new Dimension(WIGHT, HIGHT);
        content.setPreferredSize(size);
        content.addMouseListener(new MouseAction());     // cлушатель мыши
        content.addKeyListener(new KeyActionListener());
        content.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    Map.setScale(Map.getScale() + 15);
                } else {
                    if (Map.getScale() > 30) {
                        Map.setScale(Map.getScale() - 15);
                    } else if (Map.getScale() > 10) {
                        Map.setScale(Map.getScale() - 2);
                    } else {
                        Map.setScale(Map.getScale() - 1);
                    }
                }
                System.out.println("Scale " + Map.getScale());
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

        JPanel panelMenuAction = new JPanel();
        Dimension MenuSizeButtonMine = new Dimension(75, 25);
        MenuButtonMine = new JButton("Mine");
        MenuButtonMine.setPreferredSize(MenuSizeButtonMine);
        MenuButtonMine.setActionCommand("Mine");
        MenuButtonMine.addActionListener(new MenuActionListener());     // cлушатель кнопкпи


        //панель информации
        panelMenuInformation.add(MenutextLabel);
        panelMenuInformation.add(MenuTextStageMouse);
        panelMenuInformation.add(MenuTextTask);

        // панель кнопак
        panelMenuAction.add(MenuButtonMine);


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
        frame.setTitle("Mpts   " + title);
    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }
}
