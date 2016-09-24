/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GraphixManagement.Drawable;
import blockapptest.GraphixManagement.ScreenManager;

/**
 *
 * @author i3mainz
 */
public class BlockNode implements Drawable
{
    int[]userInputs;
    BlockType type;
    BlockNode nextNode;
            
    public BlockNode(BlockType type)
    {
        userInputs = new int[type.getUserInputNeeded()];
        this.type = type;
    }
    
    public String getAsm()
    {
        return type.getAsm(userInputs);
    }
    
    public BlockNode addBlock(BlockType type)
    {
        if(nextNode != null)
        {
            return nextNode.addBlock(type);
        }
        else
            return nextNode = new BlockNode(type);
    }
    
    public void setUserInput(int id,int value)
    {
        userInputs[id] = value;
    }

    @Override
    public void draw(double x, double y, double width, double height, ScreenManager d)
    {
        type.draw(x, y, width, height, d);
        
        double iX = x+10;
        double inputHeight = 50;
        double inputwidth = (width-20)/(userInputs.length)-((userInputs.length-1)*10);
        for(int i : userInputs)
        {
            d.fill(255);
            d.rect(iX, y+height-inputHeight-10, inputwidth, inputHeight);
            d.textSize(40);
            d.fill(0);
            d.text(i+"", iX+10, y+height-inputHeight-10+40);
            iX += inputwidth+10;
        }
        if(nextNode!=null)
        {
            nextNode.draw(x, y+height+10, width, height, d);
            return;
        }
        d.fill(0,100);
        d.rect(x+20, y+height+20, width-40, height-40);
    }
}
