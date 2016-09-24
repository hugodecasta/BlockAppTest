/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GraphixManagement.Drawable;

/**
 *
 * @author i3mainz
 */
public abstract class BlockType implements Drawable
{
    String name;
    int userInputNeeded;
    
    public BlockType(String name,int userInputNeeded,int blockInputNeeded,int streamOutputNeeded)
    {
        this.name = name;
        this.userInputNeeded = userInputNeeded;
    }
    
    public int getUserInputNeeded()
    {
        return userInputNeeded;
    }
    
    public String getName()
    {
        return name;
    }
    
    public abstract String getAsm(int[]userInputs);
}
