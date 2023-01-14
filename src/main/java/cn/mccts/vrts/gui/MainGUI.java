package cn.mccts.vrts.gui;

import cn.hutool.core.thread.ThreadUtil;
import cn.mccts.vrts.main.Main;
import cn.mccts.vrts.recognition.tencent.Recognition;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainGUI {

    static Point origin = new Point();  //全局的位置变量，用于表示鼠标在窗口上的位置

    static JFrame frame = new JFrame();//框架

    static Subtitle subtitle = new Subtitle();//字幕绘制


    static PanelButton closeButton = new PanelButton();//关闭按钮
    static PanelButton startButton = new PanelButton();//开始录制按钮
    static PanelButton stopButton = new PanelButton();//结束录制按钮

    public static void init() {
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 60));
        frame.setBounds(300, 500, 900, 100);
        frame.setLayout(null);

        subtitle.setBackground(new Color(0, 0, 0, 0));
        subtitle.setSize(900, 100);

        closeButton.setBackground(new Color(0, 0, 0, 30));
        closeButton.setBounds(884, 0, 16, 16);

        startButton.setBackground(new Color(0, 100, 0, 30));
        startButton.setBounds(884, 16, 16, 42);

        stopButton.setBackground(new Color(100, 0, 0, 30));
        stopButton.setBounds(884, 58, 16, 42);


        frame.add(subtitle);
        frame.add(closeButton);
        frame.add(startButton);
        frame.add(stopButton);

        frame.setVisible(true);

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {

                Point p = frame.getLocation();
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
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                closeButton.addShadow();
                frame.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                closeButton.removeShadow();
                frame.repaint();
            }
        });
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!Main.status) {
                    Main.status = true;
                    try {
                        Recognition recognition = new Recognition();
                        ThreadUtil.execAsync(recognition);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                startButton.addShadow();
                frame.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                startButton.removeShadow();
                frame.repaint();
            }
        });
        stopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Main.status) {
                    Main.status = false;
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                stopButton.addShadow();
                frame.repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                stopButton.removeShadow();
                frame.repaint();
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
        private void addShadow() {
            Color color = getBackground();
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int alpha = color.getAlpha();
            alpha = alpha + 30;
            Color newColor = new Color(red, green, blue, alpha);
            setBackground(newColor);
            repaint();
        }
        private void removeShadow() {
            Color color = getBackground();
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();
            int alpha = color.getAlpha();
            alpha = alpha - 30;
            Color newColor = new Color(red, green, blue, alpha);
            setBackground(newColor);
            repaint();
        }
    }

    public static void repaint(String text) {
        subtitle.setText(text);
        subtitle.repaint();
        frame.repaint();
    }

}