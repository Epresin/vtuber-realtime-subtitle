package cn.mccts.vrts.gui;

import cn.mccts.vrts.main.Main;
import cn.mccts.vrts.recognition.tencent.Recognition;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TestGUI {

    static Point origin = new Point();  //全局的位置变量，用于表示鼠标在窗口上的位置

    static JFrame frame = new JFrame();//框架

    static Subtitle subtitle = new Subtitle();//字幕绘制


    static PanelButton closeButton = new PanelButton();//关闭按钮
    static PanelButton startButton = new PanelButton();//开始录制按钮
    static PanelButton stopButton = new PanelButton();//结束录制按钮

    public static void init() {
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 50));
        frame.setSize(900, 100);
        frame.setLayout(null);

        subtitle.setBackground(new Color(0, 0, 0, 0));
        subtitle.setSize(900, 100);

        closeButton.setBackground(new Color(0, 0, 0, 30));
        closeButton.setBounds(884, 0, 16, 16);

        startButton.setBackground(new Color(0,100,0,30));
        startButton.setBounds(884,16,16,42);

        stopButton.setBackground(new Color(100,0,0,30));
        stopButton.setBounds(884,58,16,42);


        frame.add(subtitle);
        frame.add(closeButton);
        frame.add(startButton);
        frame.add(stopButton);

        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
                origin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
                origin.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {  //拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）

                Point p = frame.getLocation();  //当鼠标拖动时获取窗口当前位置
                //设置窗口的位置
                //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(false);
                System.exit(0);

            }
        });
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Main.status) {
                    Main.status = true;
                    Runnable recognition;
                    try {
                        recognition = new Recognition();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Thread thread=new Thread(recognition);
                    thread.start();
                }
            }
        });
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Main.status){
                   Main.status=false;
                }
            }
        });


    }

    static class Subtitle extends JPanel {
        @Setter
        String text = "";

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//抗锯齿
            g2d.setFont(new Font("楷体", Font.BOLD, 20));
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, 20, 60);
        }
    }

    static class PanelButton extends JPanel {
    }
    public static void repaint(String text) {
        subtitle.setText(text);
        subtitle.repaint();
        frame.repaint();
    }

}