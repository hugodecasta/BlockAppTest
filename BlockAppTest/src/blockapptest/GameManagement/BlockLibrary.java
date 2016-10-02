/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.BlockManagement.AsmType;
import blockapptest.BlockManagement.BlockType;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author i3mainz
 */
public class BlockLibrary extends ScreenComponent implements Iterable<BlockType>, Iterator<BlockType>
{
    HashMap<String, BlockType>types;
    ArrayList<LibraryBlock>libBlocks;
    int asmTypes;
    public BlockLibrary()
    {
        types = new HashMap<>();
        libBlocks = new ArrayList<>();
        asmTypes = 0;
    }
    
    public void addType(BlockType type)
    {
        types.put(type.getName(), type);
        LibraryBlock libBlock = new LibraryBlock(type);
        libBlocks.add(libBlock);
        if(type.getClass() != AsmType.class)
        {
            Screen.addComponent(libBlock,"library");
        }
        else
            asmTypes++;
        resetBounds();
    }
    
    private void resetBounds()
    {
        double border = 30;
        double libBlockSize = width-border;
        double separate = border/2;
        double leftOffset = x+separate;
        double topOffset = y+separate;
        
        for(int i=0;i<libBlocks.size();++i)
        {
            libBlocks.get(i).setBounds(leftOffset, topOffset+((i-asmTypes)*(libBlockSize+separate)), libBlockSize, libBlockSize);
        }
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
        boolean ret = index < types.size();
        if(!ret)
            index = 0;
        return ret;
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

    @Override
    public void initDraw() {
        Screen.fill(111);
    }

    @Override
    public void draw() {
        Screen.rect(x, y, width, height);
    }
}
