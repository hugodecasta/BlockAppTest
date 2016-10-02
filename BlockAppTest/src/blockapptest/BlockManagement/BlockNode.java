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
import java.awt.event.KeyEvent;


/**
 *
 * @author i3mainz
 */
public class BlockNode extends ScreenComponent
{
    String[]userInputs;
    BlockType type;
    double slideOffset;
    VDriver yDriver,xDriver,imageDriver;
            
    public BlockNode(BlockType type)
    {
        userInputs = new String[type.getUserInputNeeded()];
        this.type = type;
        this.overlayOnGrab = true;
        this.recieveGlobalPressed = true;
        yDriver = addDriver();
        xDriver = addDriver();
        imageDriver = addDriver();
        imageDriver.setGoal(0, 255, 20);
        d_overlaySize = addDriver();
    }
    
    @Override
    public void setBounds(double x,double y,double w,double h)
    {
        int duration = 20;
        if(dropped)
        {
            xDriver.setGoal(Screen.mouseX-width/2, x, duration,false,true);
            yDriver.setGoal(Screen.mouseY-width/2, y, duration,false,true);
        }
        else
        {
            xDriver.setGoal(this.x, x, duration,false,true);
            yDriver.setGoal(this.y, y, duration,false,true);
        }
        super.setBounds(x, y, w, h);
        d_overlaySize.setGoal(w, w, 0);
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
    
    public void setUserInput(int id,String value)
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
    int imageBorder = 20;

    @Override
    public void draw()
    {
        double drawWidth = width;
        if(userInputs.length>0)
            drawWidth *= 2;
        Screen.translate(dx+width/2-drawWidth/2, dy-slideOffset);
        Screen.roundRect(0, 0, drawWidth, height,20);
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
        drawInputs();
    }
    int userInputEdit = -1;
    private void drawInputs()
    {
        double xOffset = dx+width/2;
        double border = 5;
        double inputHeight = (height-2*border)/userInputs.length;
        for(int i=0;i<userInputs.length;++i)
        {
            Screen.fill(255,150);
            if(userInputEdit==i)
            {
                Screen.fill(255);
            }
            double yOffset = dy+border/2+(i*(inputHeight+border/2))-slideOffset+border/2;
            Screen.roundRect(xOffset, yOffset, width-border, inputHeight-border/2, 20);
            Screen.textSize(30);
            Screen.fill(0);
            if(userInputEdit==i)
                Screen.text(userInputs[i]+"|", xOffset+border, yOffset+(inputHeight-border/2)/1.5);
            else
                Screen.text(userInputs[i], xOffset+border, yOffset+(inputHeight-border/2)/1.5);
                
            Screen.noStroke();
        }
    }
    VDriver d_overlaySize;
    @Override
    public void initDrawOverlay()
    {
        initDraw();
    }
    @Override
    public void drawOverlay()
    {
        double overlaySize = d_overlaySize.getValue();
        Screen.fill(type.getColor());
        if(deleting)
            Screen.fill(255,100,100,200);
        deleting = false;
        Screen.translate(Screen.mouseX, Screen.mouseY);
        Screen.rotate(10);
        Screen.roundRect(-overlaySize/2, -overlaySize/2, overlaySize, overlaySize,20);
        if(type.getImage()!=null)
        {
            Screen.image(type.getImage(),imageBorder-overlaySize/2,
                    imageBorder-overlaySize/2,overlaySize-(imageBorder*2),overlaySize-(imageBorder*2),imageDriver.getValue());
        }
        else
        {
            Screen.fill(255);
            Screen.text(type.getName(), 30, 30);
        }
        Screen.resetTransform();
    }
    @Override
    public void mouseClicked()
    {
        double xOffset = dx+width/2;
        double border = 5;
        double inputHeight = (height-2*border)/userInputs.length;
        for(int i=0;i<userInputs.length;++i)
        {
            double yOffset = dy+border/2+(i*(inputHeight+border/2))-slideOffset+border/2;
            if(Screen.mouseX>xOffset && Screen.mouseX<xOffset+(width-border) && Screen.mouseY>yOffset && Screen.mouseY<yOffset+inputHeight-border/2)
            {
                userInputEdit = i;
                Screen.showKeyboard();
            }
        }        
    }
    @Override
    public void mouseGrab()
    {
        dropped = true;
        Screen.fill(type.getColor().getRed(),type.getColor().getGreen(),type.getColor().getBlue(),100);
    }
    boolean deleting = false;
    boolean enterTrash;
    @Override
    public void mouseMoveOn(ScreenComponent c)
    {
        if(c.getClass() == Trash.class)
        {
            if(!enterTrash)
            {
                enterTrash = true;
                d_overlaySize.setGoal(width, width/1.5, 20,false,true);
            }
            deleting = true;
        }
        else if(enterTrash)
        {
            enterTrash = false;
            d_overlaySize.setGoal(width/1.5, width, 20,false,true);
        }
    }
    @Override
    public void mouseGlobalPressed()
    {
        userInputEdit = -1;
    }
    @Override
    public void keyPressed(KeyEvent k)
    {
        if(userInputEdit==-1)
            return;
        if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            userInputs[userInputEdit] = userInputs[userInputEdit].substring(0,userInputs[userInputEdit].length()-1);
        else
            userInputs[userInputEdit] += k.getKeyChar();
    }
}
