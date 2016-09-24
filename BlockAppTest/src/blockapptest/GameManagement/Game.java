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
import blockapptest.GraphixManagement.AndroidFrame;
import blockapptest.GraphixManagement.Drawable;
import blockapptest.GraphixManagement.ScreenManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author i3mainz
 */
public class Game implements Drawable
{
    AndroidFrame frame;
    TypeManager manager;
    Stream mainStream;
    
    public Game()
    {
        manager = new TypeManager();
        mainStream = new Stream("main");
        initTypes();
        
        BlockNode n1 = mainStream.addBlock(manager.get("pin"));
        n1.setUserInput(0, 6);
        n1.setUserInput(1, 255);
        
        BlockNode n2 = mainStream.addBlock(manager.get("wait"));
        n2.setUserInput(0, 1000);
        
        frame = new AndroidFrame(this,.5);
    }
    
    public void run()
    {
        while(true)
        {
            frame.repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    private void initTypes()
    {
        manager.addType(new AsmType("pin","pin", 2));
        manager.addType(new AsmType("wait","wat", 1));
    }

    @Override
    public void draw(double x,double y,double width,double height,ScreenManager d)
    {
        d.noStroke();
        
        d.fill(255,255);
        d.rect(x, y, width, height);
        
        double libraryWidth = 200;
        double buttonHeight = 100;
        
        d.fill(111,111,111);
        d.rect(x,y,libraryWidth,height);
        
        double tx = x+25;
        double ty = y+buttonHeight+25;
        double blockSize = libraryWidth-50;
        
        for(BlockType type : manager)
        {
            type.draw(tx, ty, blockSize, blockSize, d);
            ty += blockSize + 20;
        }
        
        d.fill(230,51,42);
        d.rect(x,y,width,buttonHeight);
        d.rect(x,y+height+1-buttonHeight,width,buttonHeight);
        
        mainStream.draw(x+libraryWidth, y+buttonHeight, width-libraryWidth, height-(buttonHeight*2), d);
    }
}
