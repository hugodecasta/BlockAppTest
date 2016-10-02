/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.VDriver;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author i3mainz
 */
public class ProcedureStream extends Stream
{
    Color color;
    Image image;
    public ProcedureStream(String name,Color color,Image image)
    {
        super(name);
        this.color = color;
        this.image = image;
        separateYoffset = 100;
        heightDriver = addDriver();
    }
    
    VDriver heightDriver;
    
    @Override
    public BlockNode addBlock(BlockType type,int pos)
    {
        double saveHeight = getRelativeStreamHeight();
        BlockNode n = super.addBlock(type,pos);
        heightDriver.setGoal(saveHeight, getRelativeStreamHeight(), 20,false,true);
        return n;
    }
    @Override
    public void remove(BlockNode node)
    {
        double saveHeight = getRelativeStreamHeight();
        super.remove(node);
        heightDriver.setGoal(saveHeight, getRelativeStreamHeight(), 20,false,true);
    }
    @Override
    public void draw()
    {
        Screen.noStroke();
        double border = 20;
        double imageBorder = 10;
        double nameHeight = 50;
        Screen.fill(color.getRed(),color.getGreen(),color.getBlue(),200);
        Screen.rect(x+border, y+border-slide, width-border*2, heightDriver.getValue()-separate);
        Screen.fill(color);
        Screen.rect(x+border, y+border-slide, width-border*2, nameHeight);
        Screen.image(image,x+border+imageBorder,y+border+imageBorder-slide,nameHeight-2*imageBorder,nameHeight-2*imageBorder);
        super.draw();
    }
    
}
