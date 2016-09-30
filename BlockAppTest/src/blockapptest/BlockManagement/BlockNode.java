/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.ScreenManagement.VDriver;


/**
 *
 * @author i3mainz
 */
public class BlockNode extends ScreenComponent
{
    int[]userInputs;
    BlockType type;
    double slideOffset;
    VDriver yDriver,imageDriver;
            
    public BlockNode(BlockType type)
    {
        userInputs = new int[type.getUserInputNeeded()];
        this.type = type;
        this.overlayOnGrab = true;
        yDriver = addDriver();
        imageDriver = addDriver();
    }
    
    @Override
    public void setBounds(double x,double y,double w,double h)
    {
        this.x = x;
        this.width = w;
        this.height = h;
        int speed = 10;
        //this.y = y;
        if(this.y==0)
        {
            this.y = y;
            imageDriver.setGoal(0, 255, 10);
        }
        else
        {
            yDriver.setGoal(this.y, y, speed,false,true);
        }
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
    @Override
    public void initDraw()
    {
        if(!yDriver.isDone())
        {
            this.y = yDriver.getValue();
            Screen.stroke(255);
            Screen.strokeWeight(5);
        }
        Screen.fill(type.getColor());
    }
    double drawSize;

    @Override
    public void draw()
    {
        int imageBorder = 20;
        Screen.translate(x, y-slideOffset);
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
        draw();
        Screen.fill(type.getColor());
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
        Screen.fill(type.getColor().getRed(),type.getColor().getGreen(),type.getColor().getBlue(),100);
    }
}
