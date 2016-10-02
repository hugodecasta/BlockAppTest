/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.BlockType;
import blockapptest.BlockManagement.ProcedureType;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.ScreenManagement.VDriver;

/**
 *
 * @author i3mainz
 */
public class LibraryBlock extends ScreenComponent
{
    BlockType type;
    VDriver sizeDriver,imageDriver;
    
    public LibraryBlock(BlockType type)
    {
        this.type = type;
        this.overlayOnGrab = true;
        sizeDriver = addDriver();
        imageDriver = addDriver();
        imageDriver.setGoal(255, 255, 0);
    }
    public BlockType getType()
    {
        return type;
    }
    
    boolean dragged;
    @Override
    public void setBounds(double x,double y,double w,double h)
    {
        super.setBounds(x, y, w, h);
        drawSize = width;
    }
    @Override
    public void initDraw()
    {
        if(!sizeDriver.isDone())
        {
            drawSize = sizeDriver.getValue();
        }
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
    double drawSize;
    private void drawIt()
    {
        int imageBorder = 20;
        Screen.roundRect(width/2.0-drawSize/2.0, width/2.0-drawSize/2.0, drawSize, drawSize,20);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),imageBorder,imageBorder,width-(imageBorder*2),width-(imageBorder*2),imageDriver.getValue());
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
        Screen.translate(Screen.mouseX-width/2,Screen.mouseY-width/2);
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
    public void mouseDrop()
    {
        imageDriver.setGoal(0, 255, 20);
        sizeDriver.setGoal(0, width, 20,false,true);
    }
    
    @Override
    public void mouseDoubleClicked()
    {
        if(type.getClass() == ProcedureType.class)
            Game.setDrawStream(((ProcedureType)type).stream);
    }
    
    @Override
    public void mouseHover()
    {
        Screen.fill(type.getColor().getRed(), type.getColor().getGreen(), type.getColor().getBlue(),200);
    }
}
