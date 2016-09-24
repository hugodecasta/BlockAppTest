/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GraphixManagement.Drawable;
import blockapptest.GraphixManagement.MainDrawer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author i3mainz
 */
public class Stream implements Drawable
{
    static HashMap<String,Integer>nameOccurences;
    static ArrayList<Stream>toParse;
    
    //------------------------------------------------------- STANDALONE PART
    String name;
    BlockNode firstNode;
    
    public Stream(String name)
    {
        this.name = createName(name);
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAsm()
    {
        String asm = ":"+name+":\n";
        asm += firstNode.getAsm();
        asm += "ret\n";
        return asm;
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

    @Override
    public void draw(double x, double y, double width, double height, MainDrawer d)
    {
        d.fill(0,255,0);
        d.rect(x, y, width, height);
    }
}
