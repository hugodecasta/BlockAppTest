/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

/**
 *
 * @author i3mainz
 */
public abstract class BlockType
{
    String name;
    public BlockType(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}
