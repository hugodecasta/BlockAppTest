/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.AsmType;
import blockapptest.BlockManagement.BlockNode;
import blockapptest.BlockManagement.BlockType;
import blockapptest.BlockManagement.Stream;
import blockapptest.ScreenManagement.Screen;
import blockapptest.old_GraphixManagement.AndroidFrame;
import blockapptest.old_GraphixManagement.Drawable;
import blockapptest.old_GraphixManagement.ScreenManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author i3mainz
 */
public class Game
{
    BlockLibrary library;
    Stream mainStream;
    Button sendButton,taskButton;
    
    int width,height;
    
    public Game()
    {
        library = new BlockLibrary();
        mainStream = new Stream("main");
        initGraphix();
        initTypes();
        
        BlockNode n1 = mainStream.addBlock(library.get("pin"));
        n1.setUserInput(0, 6);
        n1.setUserInput(1, 255);
        
        BlockNode n2 = mainStream.addBlock(library.get("wait"));
        n2.setUserInput(0, 1000);
    }
    
    private void initGraphix()
    {
        width = 480;
        height = 740;
        
        double buttonHeight = 70;
        double libraryWidth = 100;
        
        sendButton = new Button("Send", "check.png", new Color(230,51,42,255));
        taskButton = new Button("Tasks", "", new Color(230,51,42,255));
        
        mainStream.setBounds(libraryWidth, buttonHeight, width-libraryWidth, height-(2*buttonHeight));
        library.setBounds(0, buttonHeight, libraryWidth, height-(2*buttonHeight));
        sendButton.setBounds(0, height-buttonHeight, width, buttonHeight);
        taskButton.setBounds(0, 0, width-buttonHeight, buttonHeight);
        
        Screen.addComponent(mainStream);
        Screen.addComponent(library);
        Screen.addComponent(sendButton);
        Screen.addComponent(taskButton);
    }
    
    public void run()
    {
        Screen.initScreen(width, height,1);
        Screen.launch();
        
        while(true)
        {
            Screen.draw();
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void build()
    {
        String asm = "call main\njmp end\n"+mainStream.getAsm()+":end:";
        System.out.println(asm);
    }
    
    private void initTypes()
    {
        library.addType(new AsmType("pin","led.png","pin $0 #1", 2,0));
        library.addType(new AsmType("wait","timer.png","wat $0", 1,0));
    }
}
