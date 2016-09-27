/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.BlockType;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import java.awt.Color;

/**
 *
 * @author i3mainz
 */
public class LibraryBlock extends ScreenComponent
{
    BlockType type;
    public LibraryBlock(BlockType type)
    {
        this.type = type;
        this.overlay = true;
    }
    public BlockType getType()
    {
        return type;
    }
    
    boolean dragged;
    @Override
    public void initDraw()
    {
        dragged = false;
        Screen.fill(type.getColor());
        Screen.noStroke();
    }

    @Override
    public void draw()
    {
        Screen.translate(x, y);
        drawIt();
        Screen.resetTransform();
        
        if(dragged)
        {
            Screen.stroke(0,0,0,255);
            Screen.strokeWeight(5);
            Screen.translate(Screen.mouseX-width/2,Screen.mouseY-height/2);
            Screen.rotate(5);
            
            drawIt();
            
            Screen.resetTransform();
            Screen.noStroke();
        }
    }
    
    private void drawIt()
    {
        Screen.roundRect(0, 0, width, height,10);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),10,10,width-20,height-20);
        }
        else
        {
            Screen.fill(255);
            Screen.text(type.getName(), 30, 30);
        }
    }
    
    @Override
    public void mouseGrab()
    {
        dragged = true;
    }
}
