/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.BlockType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author i3mainz
 */
public class TypeManager implements Iterable<BlockType>, Iterator<BlockType>
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
    
    public ArrayList<BlockType>getTypes()
    {
        ArrayList<BlockType>allTypes = new ArrayList<>();
        for(Entry<String, BlockType> entry : types.entrySet()) 
            allTypes.add(entry.getValue());
        return allTypes;
    }

    private int index = 0;
    @Override
    public Iterator<BlockType> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return index < types.size();
    }

    @Override
    public BlockType next() {
        int id = 0;
        index++;
        for(Entry<String, BlockType> entry : types.entrySet()) 
        {
            if(id==index-1)
                return entry.getValue();
            ++id;
        }
        return null;
    }
    
    public BlockType get(String typeName)
    {
        return types.get(typeName);
    }
}
