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
public class BlockNode
{
    int[]userInputs;
    BlockType type;
            
    public BlockNode(BlockType type)
    {
        userInputs = new int[type.getUserInputNeeded()];
        this.type = type;
    }
    
    public String getAsm()
    {
        return type.getAsm(userInputs);
    }
    
    public void setUserInput(int id,int value)
    {
        userInputs[id] = value;
    }
}
