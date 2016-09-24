/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

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
    
    public Game()
    {
        frame = new AndroidFrame(this,.5);
    }

    @Override
    public void draw(double x,double y,double width,double height,MainDrawer d)
    {
        d.noStroke();
        
        d.fill(255,255);
        d.rect(x, y, width, height);
        
        double libraryWidth = (width/770)*200;
        d.fill(255,100,255);
        d.rect(x,y,libraryWidth,height);
        
        double buttonHeight = (height/770)*70;
        d.fill(255,100,100);
        d.rect(x,y,width,buttonHeight);
        d.rect(x,y+height+1-buttonHeight,width,buttonHeight);
    }
}
