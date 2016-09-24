/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.AsmType;
import blockapptest.BlockManagement.BlockType;
import blockapptest.BlockManagement.Stream;
import blockapptest.GraphixManagement.AndroidFrame;
import blockapptest.GraphixManagement.Drawable;
import blockapptest.GraphixManagement.MainDrawer;
import java.awt.Color;
import java.awt.Graphics2D;


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
        
        frame = new AndroidFrame(this,.5);
    }
    
    private void initTypes()
    {
        manager.addType(new AsmType("pin", 2));
    }

    @Override
    public void draw(double x,double y,double width,double height,MainDrawer d)
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
            tx += blockSize + 5;
        }
        
        d.fill(230,51,42);
        d.rect(x,y,width,buttonHeight);
        d.rect(x,y+height+1-buttonHeight,width,buttonHeight);
    }
}
