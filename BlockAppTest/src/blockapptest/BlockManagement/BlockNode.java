/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;


/**
 *
 * @author i3mainz
 */
public class BlockNode extends ScreenComponent
{
    int[]userInputs;
    BlockType type;
            
    public BlockNode(BlockType type)
    {
        userInputs = new int[type.getUserInputNeeded()];
        this.type = type;
        this.overlayOnGrab = true;
    }
    
    public String getAsm()
    {
        return type.getAsm(userInputs);
    }
    
    public void setUserInput(int id,int value)
    {
        userInputs[id] = value;
    }

    @Override
    public void initDraw()
    {
        Screen.fill(type.getColor());
    }

    @Override
    public void draw()
    {
        int imageBorder = 20;
        Screen.translate(x, y);
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
        Screen.resetTransform();
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
            Screen.image(type.getImage(),-width/2+10,-width/2+10,width-20,height-20);
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
