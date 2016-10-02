/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.ScreenManagement.Screen;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author i3mainz
 */
public abstract class BlockType
{
    String name;
    Image image;
    Color color;
    int userInputNeeded;
    
    public BlockType(String name,String image,Color color,int userInputNeeded,int blockInputNeeded,int streamOutputNeeded)
    {
        this.name = name;
        this.color = color;
        this.userInputNeeded = userInputNeeded;
        this.image = Screen.loadImage("images/"+image);
    }
    
    public int getUserInputNeeded()
    {
        return userInputNeeded;
    }
    
    public Image getImage()
    {
        return image;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public abstract String getAsm(String[]userInputs);
}
