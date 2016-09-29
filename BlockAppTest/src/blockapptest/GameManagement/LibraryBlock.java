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
        this.overlayOnGrab = true;
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
        Screen.stroke(255,255,255,255);
        Screen.strokeWeight(5);
    }

    @Override
    public void draw()
    {
        Screen.translate(x, y);
        drawIt();
        Screen.resetTransform();
    }
    
    private void drawIt()
    {
        int imageBorder = 20;
        Screen.roundRect(0, 0, width, height,20);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),imageBorder,imageBorder,width-(imageBorder*2),height-(imageBorder*2));
        }
        else
        {
            Screen.fill(255);
            Screen.text(type.getName(), 30, 30);
        }
        Screen.noStroke();
    }
    
    @Override
    public void initDrawOverlay()
    {
        initDraw();
    }
    
    @Override
    public void drawOverlay()
    {
        Screen.stroke(0,0,0,100);
        Screen.strokeWeight(5);
        Screen.translate(Screen.mouseX-width/2,Screen.mouseY-height/2);
        Screen.rotate(5);

        drawIt();

        Screen.resetTransform();
        Screen.noStroke();
    }
    
    @Override
    public void mouseGrab()
    {
        dragged = true;
    }
    
    @Override
    public void mouseHover()
    {
        Screen.fill(type.getColor().getRed(), type.getColor().getGreen(), type.getColor().getBlue(),200);
    }
}
