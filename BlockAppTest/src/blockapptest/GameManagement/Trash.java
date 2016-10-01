/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.BlockNode;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.ScreenManagement.VDriver;
import java.awt.Image;

/**
 *
 * @author i3mainz
 */
public class Trash extends ScreenComponent
{
    Image bin,open;
    Game g;
    VDriver xDriver;
    public Trash(Game g)
    {
        this.g = g;
        bin = Screen.loadImage("images/bin.png");
        open = Screen.loadImage("images/bin_open.png");
        xDriver = addDriver();
    }
    @Override
    public void setBounds(double x,double y,double w,double h)
    {
        xDriver.setGoal(0, x, 50);
        this.y = y;
        this.height = h;
        this.width = w;
    }
    Image show;
    @Override
    public void initDraw()
    {
        show = bin;
        if(!xDriver.isDone())
            this.x = xDriver.getValue();
    }

    @Override
    public void draw()
    {
        Screen.image(show, x, y, width, height);
    }
    @Override
    public void mouseMoveOverWidth(ScreenComponent c)
    {
        if(c.getClass() == BlockNode.class)
            show = open;
    }
    @Override
    public void mouseDropObject(ScreenComponent c)
    {
        if(c.getClass() == BlockNode.class)
            g.mainStream.remove(((BlockNode)c));
    }
}
