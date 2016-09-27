/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author i3mainz
 */
public class Button extends ScreenComponent
{
    Color color;
    String name;
    Image image;
    
    public Button(String name,String image,Color color)
    {
        this.name = name;
        this.color = color;
        this.image = Screen.loadImage("src/blockapptest/GameManagement/images/"+image);
    }
    @Override
    public void initDraw()
    {
        Screen.fill(color);
    }

    @Override
    public void draw()
    {
        Screen.rect(x,y,width,height);
        Screen.image(image, x+(width/2-height/2)+5, y+5, height-10, height-10);
        //Screen.image(image, x+(width/2-height/2), y, height, height);
    }
    
    @Override
    public void mouseHover()
    {
        Screen.fill(color.getRed(),color.getGreen(),color.getBlue(),200);
    }
}
