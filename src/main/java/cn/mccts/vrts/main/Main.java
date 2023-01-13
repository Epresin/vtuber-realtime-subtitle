package cn.mccts.vrts.main;

import cn.mccts.vrts.gui.JsonAnalysis;
import cn.mccts.vrts.gui.MainGui;
import cn.mccts.vrts.gui.TestGUI;
import cn.mccts.vrts.recognition.tencent.Recognition;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.io.IOException;



@Slf4j
public class Main {

    public static void main(String[] args) {
        initGUIStyle();
        TestGUI.init();

    }
    public static boolean status = false;//记录系统状态

    private static void initGUIStyle(){
        UIManager.put( "Button.arc", 0 );
        UIManager.put( "Component.arc", 0 );
        UIManager.put( "CheckBox.arc", 0 );
        UIManager.put( "ProgressBar.arc", 0 );
        FlatArcOrangeIJTheme.setup();
    }
}
