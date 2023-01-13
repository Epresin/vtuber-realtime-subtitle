package cn.mccts.vrts.main;

import cn.mccts.vrts.gui.MainGUI;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


@Slf4j
public class Main {

    public static void main(String[] args) {
        initGUIStyle();
        MainGUI.init();

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
