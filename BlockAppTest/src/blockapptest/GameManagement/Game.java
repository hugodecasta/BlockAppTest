/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.ScreenManagement.ClickBackable;
import blockapptest.BlockManagement.AsmType;
import blockapptest.BlockManagement.BlockNode;
import blockapptest.BlockManagement.BlockType;
import blockapptest.BlockManagement.ProcedureType;
import blockapptest.BlockManagement.Stream;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.ScreenManagement.SendModule;
import blockapptest.ScreenManagement.UsbPortConnector;
import blockapptest.old_GraphixManagement.AndroidFrame;
import blockapptest.old_GraphixManagement.Drawable;
import blockapptest.old_GraphixManagement.ScreenManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author i3mainz
 */
public class Game implements ClickBackable
{
    BlockLibrary library;
    Stream mainStream;
    Button sendButton,taskButton;
    Compiler compiler;
    SendModule connector;
    LevelManager levels;
    
    int width,height;
    
    public Game()
    {
        library = new BlockLibrary();
        mainStream = new Stream("main");
        compiler = new Compiler();
        connector = new UsbPortConnector();
        initConnection();
        levels = new LevelManager();
        initGraphix();
        initTypes();
        
        /*BlockNode n1 = mainStream.addBlock(library.get("pin"));
        n1.setUserInput(0, 6);
        n1.setUserInput(1, 255);
        
        BlockNode n2 = mainStream.addBlock(library.get("wait"));
        n2.setUserInput(0, 1000);*/
    }
    
    private void initConnection()
    {
        String[]portsNames = connector.getPorts();
        if(portsNames.length<=0)
        {
            System.err.println("No port connected to the app...");
            return;
        }
        if(connector.connect(portsNames[0]))
            System.out.println("Success connect to port "+portsNames[0]);
        else
            System.err.println("Could not connect to port "+portsNames[0]);
        
    }
    
    private void initGraphix()
    {
        width = 480;
        height = 740;
        
        double buttonHeight = 70;
        double libraryWidth = 100;
        
        sendButton = new Button("Send", "check.png", new Color(230,51,42,255),this);
        taskButton = new Button("Tasks", "", new Color(230,51,42,255),this);
        
        mainStream.setBounds(libraryWidth, buttonHeight, width-libraryWidth, height-(2*buttonHeight));
        library.setBounds(0, buttonHeight, libraryWidth, height-(2*buttonHeight));
        sendButton.setBounds(0, height-buttonHeight, width, buttonHeight);
        taskButton.setBounds(0, 0, width-buttonHeight, buttonHeight);
        levels.setBounds(width-buttonHeight, 0, buttonHeight, buttonHeight);
        
        Screen.addLayer("stream");
        Screen.addLayer("library");
        Screen.addLayer("gui");
        
        Screen.addComponent(mainStream,"stream");
        Screen.addComponent(library,"library");
        Screen.addComponent(sendButton,"gui");
        Screen.addComponent(taskButton,"gui");
        Screen.addComponent(levels,"gui");
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
        String asm = ""+mainStream.getAsm()+"exit";
        System.out.println(asm);
        byte[]program = compiler.compile(asm);
        System.out.println("Sending "+program.length+"_B code:");
        compiler.drawProgram(program);
        
        /*if(!connector.isConnected())
        {
            System.err.println("Serial port is not connected");
            return;
        }*/
        boolean success = true;
        success &= connector.sendByte((byte)0x01);
        for(int i=0;i<program.length;++i)
        {
            success &= connector.sendByte((byte)0x00);
            success &= connector.sendByte(program[i]);
        }
        success &= connector.sendByte((byte)0x02);
        if(success)
            System.out.println("Code sent success");
        else
            System.err.println("Error while sending code");
            
        //System.out.println(connector.disconnect());
    }
    
    private void initTypes()
    {
        /*library.addType(new AsmType("pin","led.png","pin $0 #1", 2,0));
        library.addType(new AsmType("wait","timer.png","wat $0", 1,0));*/
        int leftWheelPin = 6;
        int RightWheelPin = 7;
        int timeMsForward = 1000;
        int timeMsTurn90 = 500;
        try
        {
            library.addType(new ProcedureType("forward","up-arrow.png",new Color(58,170,53),
                    new Scanner(new File("src/blockapptest/GameManagement/images/pre_built_forward.asm")).useDelimiter("\\Z").next()));
            library.addType(new ProcedureType("turn-left","turn-left.png",new Color(58,170,53),
                    new Scanner(new File("src/blockapptest/GameManagement/images/pre_built_left.asm")).useDelimiter("\\Z").next()));
            library.addType(new ProcedureType("turn-right","turn-right.png",new Color(58,170,53),
                    new Scanner(new File("src/blockapptest/GameManagement/images/pre_built_right.asm")).useDelimiter("\\Z").next()));
            library.addType(new ProcedureType("music","musical-note.png",new Color(243,146,0),
                    new Scanner(new File("src/blockapptest/GameManagement/images/pre_built_music.asm")).useDelimiter("\\Z").next()));
        }
        catch(Exception e)
        {
            
        }
    }

    @Override
    public void backClick(ScreenComponent c)
    {
        if(c==sendButton)
            build();
    }
}
