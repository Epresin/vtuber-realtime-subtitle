package cn.mccts.vrts.gui;

import cn.mccts.vrts.recognition.tencent.Recognition;
import com.tencent.SpeechClient;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainGui {


    private static JFrame frame = new JFrame();//创建框架
    private static Subtitle subtitle = new Subtitle();//创建字幕页面
    private static JButton startButton = new JButton("开始识别");
    private static JButton stopButton = new JButton("结束识别");

    public static boolean status = false;



    public static void init() {

        Box buttonBox = Box.createVerticalBox();

        startButton.addActionListener(e -> {
            if (!status) {
                status = true;
                Runnable recognition;
                try {
                    recognition = new Recognition();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Thread thread=new Thread(recognition);
                thread.start();
            }
        });
        stopButton.addActionListener(e -> {
            if (status){
                status=false;
            }
        });

        buttonBox.add(startButton);
        buttonBox.add(stopButton);
        frame.add(buttonBox, BorderLayout.EAST);
        frame.add(subtitle);

        frame.pack();
        frame.setSize(1000, 88);
        frame.setResizable(false);


        frame.setVisible(true);
        frame.setUndecorated(true);
        frame.setOpacity(0.5f);

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
            g2d.drawString(text, 20, 30);
        }

    }

    public static void repaint(String text) {
        subtitle.setText(text);
        subtitle.repaint();
    }

}