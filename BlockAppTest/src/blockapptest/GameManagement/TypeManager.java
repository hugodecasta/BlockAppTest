/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.BlockType;
import java.util.HashMap;

/**
 *
 * @author i3mainz
 */
public class TypeManager
{
    HashMap<String, BlockType>types;
    public TypeManager()
    {
        initTypes();
    }
    
    private void initTypes()
    {
        types = new HashMap<>();
    }
    
    public void addType(BlockType type)
    {
        types.put(type.getName(), type);
    }
}
