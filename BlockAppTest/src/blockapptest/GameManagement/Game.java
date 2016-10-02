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
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
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
    static Stream mainStream;
    static Stream actStream;
    static Button sendButton,taskButton;
    Compiler compiler;
    SendModule connector;
    LevelManager levels;
    Trash trash;
    
    static int width,height;
    static double buttonHeight;
    static double trashHeight;
    static double libraryWidth;
    
    public Game()
    {
        library = new BlockLibrary();
        mainStream = new Stream("main");
        compiler = new Compiler();
        connector = new UsbPortConnector();
        trash = new Trash(this);
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
        
        buttonHeight = 70;
        trashHeight = 100;
        libraryWidth = 100;
        
        sendButton = new Button("Send", "check.png", new Color(230,51,42,255),this);
        taskButton = new Button("Tasks", "", new Color(230,51,42,255),this);
        
        mainStream.setBounds(libraryWidth, buttonHeight, width-libraryWidth, height-(2*buttonHeight));
        library.setBounds(0, buttonHeight, libraryWidth, height-(2*buttonHeight));
        sendButton.setBounds(0, height-buttonHeight, width, buttonHeight);
        taskButton.setBounds(0, 0, width-buttonHeight, buttonHeight);
        levels.setBounds(width-buttonHeight, 0, buttonHeight, buttonHeight);
        trash.setBounds(libraryWidth, height-(buttonHeight+trashHeight), trashHeight, trashHeight);
        
        Screen.addLayer("stream");
        Screen.addLayer("library");
        Screen.addLayer("gui");
        
        Screen.addComponent(trash,"library");
        Screen.addComponent(library,"library");
        Screen.addComponent(sendButton,"gui");
        Screen.addComponent(taskButton,"gui");
        Screen.addComponent(levels,"gui");
        setDrawStream(mainStream);
    }
    
    public static void setDrawStream(Stream stream)
    {
        if(actStream==stream)
            return;
        if(actStream!=null)
            actStream.unDrawStream();
        actStream = stream;
        actStream.setBounds(libraryWidth, buttonHeight, width-libraryWidth, height-(2*buttonHeight));
        actStream.drawStream("stream");
        updateSendButton();
    }
    
    public static void updateSendButton()
    {
        if(actStream!=mainStream)
            sendButton.image = Screen.loadImage("images/back.png");
        else
            sendButton.image = Screen.loadImage("images/check.png");
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
        Stream.addToParse(mainStream);
        String asm = "call main\njmp end\n\n"+Stream.getFullAsm()+":end:\nexit";
        System.out.println(asm);
        byte[]program = compiler.compile(asm);
        System.out.println("Sending "+program.length+"_B code:");
        compiler.drawProgram(program);
        
        if(!connector.isConnected())
        {
            System.err.println("Serial port is not connected");
            return;
        }
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
        library.addType(new AsmType("pin", "pin.png", "pin $0 $1",2));
        library.addType(new AsmType("wat", "timer.png", "wat $0",1));
        library.addType(new AsmType("reg", "register.png", "reg $0 $1",2));
        library.addType(new AsmType("ton", "buzzer.png", "ton $0 $1",2));
        ProcedureType forward = new ProcedureType("forward","up-arrow.png",new Color(58,170,53));
        ProcedureType turn_right = new ProcedureType("turn-right","turn-right.png",new Color(58,170,53));
        ProcedureType turn_left = new ProcedureType("turn-left","turn-left.png",new Color(58,170,53));
        ProcedureType music = new ProcedureType("music","musical-note.png",new Color(243,146,0));
        library.addType(forward);
        library.addType(turn_right);
        library.addType(turn_left);
        library.addType(music);
        
        generateProcedure(forward,"pre_built_forward.asm");
        generateProcedure(turn_right,"pre_built_right.asm");
        generateProcedure(turn_left,"pre_built_left.asm");
        generateProcedure(music,"pre_built_music.asm");
        
    }
    
    private void generateProcedure(ProcedureType procedure,String file)
    {
        String asmFile = "";
        try {
            asmFile = new Scanner(new File("images/"+file)).useDelimiter("\\Z").next();
        } catch (FileNotFoundException ex) {
            return;
        }
        asmFile = asmFile.replace("\r", "");
        String[]lines = asmFile.split("\n");
        //procedure.stream.setBounds(libraryWidth, buttonHeight, width-libraryWidth, height-(2*buttonHeight));
        for(String line : lines)
        {
            if(!line.equals("") && !line.contains("#"))
            {
                String[]parts = line.split(" ");
                BlockNode input = procedure.stream.addBlock(library.get(parts[0]));
                for(int i=1;i<parts.length;++i)
                {
                    input.setUserInput(i-1, parts[i].replace("_", " "));
                }
            }
        }
    }

    @Override
    public void backClick(ScreenComponent c)
    {
        if(c==sendButton)
            if(actStream==mainStream)
                build();
            else
                setDrawStream(mainStream);
    }
}
