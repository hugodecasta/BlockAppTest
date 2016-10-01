/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GameManagement.Trash;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.ScreenManagement.VDriver;
import java.awt.Color;


/**
 *
 * @author i3mainz
 */
public class BlockNode extends ScreenComponent
{
    int[]userInputs;
    BlockType type;
    double slideOffset;
    VDriver yDriver,xDriver,imageDriver;
            
    public BlockNode(BlockType type)
    {
        userInputs = new int[type.getUserInputNeeded()];
        this.type = type;
        this.overlayOnGrab = true;
        yDriver = addDriver();
        xDriver = addDriver();
        imageDriver = addDriver();
        imageDriver.setGoal(0, 255, 20);
    }
    
    @Override
    public void setBounds(double x,double y,double w,double h)
    {
        int duration = 20;
        if(dropped)
        {
            xDriver.setGoal(Screen.mouseX, x, duration,false,true);
            yDriver.setGoal(Screen.mouseY, y, duration,false,true);
        }
        else
        {
            xDriver.setGoal(this.x, x, duration,false,true);
            yDriver.setGoal(this.y, y, duration,false,true);
        }
        super.setBounds(x, y, w, h);
        dropped = false;
    }
    boolean dropped;
    @Override
    public void mouseDrop()
    {
        /*System.out.println(type.name);
        dropped = true;*/
    }
    
    public String getAsm()
    {
        return type.getAsm(userInputs);
    }
    
    public void setUserInput(int id,int value)
    {
        userInputs[id] = value;
    }

    public void setSlideOffset(double offset)
    {
        slideOffset = offset;
    }
    
    @Override
    public boolean touched(double mx,double my)
    {
        return mx>x && mx<x+width && my>y-slideOffset && my<y-slideOffset+height;
    }
    
    double dx,dy;
    @Override
    public void initDraw()
    {
        if(!yDriver.isDone())
        {
            dy = yDriver.getValue();
            Screen.stroke(255);
            Screen.strokeWeight(5);
        }
        if(!xDriver.isDone())
        {
            dx = xDriver.getValue();
        }
        Screen.fill(type.getColor());
    }
    double drawSize;

    @Override
    public void draw()
    {
        int imageBorder = 20;
        Screen.translate(dx, dy-slideOffset);
        Screen.roundRect(0, 0, width, height,20);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),imageBorder,imageBorder,width-(imageBorder*2),height-(imageBorder*2),imageDriver.getValue());
        }
        else
        {
            Screen.fill(255);
            Screen.text(type.getName(), 30, 30);
        }
        Screen.resetTransform();
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
        if(!deleting)
            draw();
        Screen.fill(type.getColor());
        if(deleting)
            Screen.fill(255,100,100,200);
        deleting = false;
        Screen.translate(Screen.mouseX, Screen.mouseY);
        Screen.rotate(10);
        Screen.roundRect(-width/2, -width/2, width, height,20);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),-width/2+10,-width/2+10,width-20,width-20);
        }
        else
        {
            Screen.fill(255);
            Screen.text(type.getName(), 30, 30);
        }
        Screen.resetTransform();
    }
    
    @Override
    public void mouseGrab()
    {
        dropped = true;
        Screen.fill(type.getColor().getRed(),type.getColor().getGreen(),type.getColor().getBlue(),100);
    }
    boolean deleting = false;
    @Override
    public void mouseMoveOn(ScreenComponent c)
    {
        if(c.getClass() == Trash.class)
            deleting = true;
    }
}
