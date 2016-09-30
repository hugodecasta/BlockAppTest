/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GameManagement.LibraryBlock;
import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;
import blockapptest.old_GraphixManagement.Drawable;
import blockapptest.old_GraphixManagement.ScreenManager;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author i3mainz
 */
public class Stream extends ScreenComponent
{
    static HashMap<String,Integer>nameOccurences;
    static ArrayList<Stream>toParse;
    
    //------------------------------------------------------- STANDALONE PART
    String name;
    ArrayList<BlockNode> nodes;
    
    public Stream(String name)
    {
        this.name = createName(name);
        nodes = new ArrayList<>();
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAsm()
    {
        String asm = ":"+name+":\n";
        for(BlockNode n : nodes)
            asm += n.getAsm();
        asm += "ret\n";
        return asm;
    }
    
    public BlockNode addBlock(BlockType type)
    {
        return addBlock(type,nodes.size());
    }
    public BlockNode addBlock(BlockType type,int pos)
    {
        BlockNode node = new BlockNode(type);
        nodes.add(pos,node);
        node.mouseGrab();
        Screen.addComponent(node,"stream");
        resetPosition();
        //resetPosition();
        return node;
    }
    public void replaceBlock(BlockNode node, int newPlace)
    {
        int actualPlace = nodes.indexOf(node);
        if(newPlace>actualPlace)
        {
            newPlace--;
            for(int i=actualPlace+1;i<=newPlace;++i)
            {
                nodes.set(i-1, nodes.get(i));
            }
        }
        else
        {
            for(int i=actualPlace;i>newPlace;i--)
            {
                nodes.set(i, nodes.get(i-1));
            }
        }
        nodes.set(newPlace, node);
        /*
        nodes.set(actualPlace, save);*/
        resetPosition();
    }
    //------------------------------------------------------- STATIC PART
    public static String getFullAsm()
    {
        String asm = "";
        while(toParse.size()>0)
        {
            asm += toParse.get(0).getAsm()+"\n";
            toParse.remove(0);
        }
        return asm;
    }
    
    public static void addToParse(Stream stream)
    {
        if(toParse == null)
            toParse = new ArrayList<>();
        if(!toParse.contains(stream))
            toParse.add(stream);
    }
    public static String createName(String name)
    {
        if(nameOccurences == null)
            nameOccurences = new HashMap<>();
        
        int occurence = 0;
        if(nameOccurences.containsKey(name))
            occurence = nameOccurences.get(name);
        
        if(occurence>0)
            name += "_"+occurence;
        
        ++occurence;
        nameOccurences.put(name, occurence);
        return name;
    }
    //----------------------------------------
    double slide = 0;
    double slideAx = 0;
    int mayPos = -1;
    
    double squareSize = 100;
    double separate = 30;
    
    private void resetPosition()
    {
        double offsetX = x+width/2;
        for(int i=0;i<nodes.size();++i)
        {
            double offsetY = getSquarePositionNoSlide(i);
            nodes.get(i).setBounds(offsetX-squareSize/2, offsetY, squareSize, squareSize);
            nodes.get(i).setSlideOffset(slide);
        }
    }
    private double getSquarePositionNoSlide(int i)
    {
        return (y+separate)+((squareSize+separate)*i);
    }
    private double getSquarePosition(int i)
    {
        return (y+separate)+((squareSize+separate)*i)-slide;
    }
    
    @Override
    public void initDraw()
    {
        Screen.fill(255);
    }

    @Override
    public void draw()
    {
        Screen.fill(255);
        Screen.rect(x, y, width, height);
        if(mayPos>-1)
            drawIndiquator();
    }
    
    private void drawIndiquator()
    {
        double indiqWidth = squareSize;
        double indiqHeight = separate / 2;
        double indiquSeparate = (separate-indiqHeight)/2;
        
        Screen.fill(111,200);
        if(mayPos==nodes.size())
        {
            indiqWidth = squareSize;
            indiqHeight = squareSize;
            indiquSeparate = separate;
        }
        double offsetX = x+width/2;
        double offsetY = getSquarePosition(mayPos)-separate+(indiquSeparate+indiqHeight/2);
        Screen.roundRect(offsetX-indiqWidth/2, offsetY-indiqHeight/2, indiqWidth, indiqHeight,20);
    }
    
    @Override
    public void mouseMoveOverWidth(ScreenComponent c)
    {
        double posArea = separate + squareSize;
        
        if(c.getClass() == LibraryBlock.class || c.getClass() == BlockNode.class)
        {
            double dMayPos = ((Screen.mouseY-y)+(squareSize/2)+slide)/posArea;
            mayPos = (int)Math.floor(dMayPos);
            
            mayPos = mayPos>nodes.size()?nodes.size():mayPos;
            
        }
    }
    @Override
    public void mouseGrab()
    {
        slide += Screen.pmouseY-Screen.mouseY;
        double listHeight = separate+(nodes.size()*(squareSize+separate))+separate;
        if(slide<0)
            slide = 0;
        else if(slide > (listHeight-height))
            slide = (listHeight>height)?listHeight-height:0;
        for(BlockNode n : nodes)
            n.setSlideOffset(slide);
        //resetPosition();
    }
    
    @Override
    public void mouseDropObject(ScreenComponent c)
    {
        if(c.getClass() == LibraryBlock.class)
        {
            this.addBlock(((LibraryBlock)c).getType(),mayPos);
            mayPos = -1;
        }
        if(c.getClass() == BlockNode.class)
        {
            ((BlockNode)c).mouseDrop();
            this.replaceBlock((BlockNode)c,mayPos);
            mayPos = -1;
        }
    }
}
