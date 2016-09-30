/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author i3mainz
 */
public class Compiler
{
    HashMap<String,byte[]>compileTable;
    HashMap<String,int[]>procedureAdresses;
    HashMap<String,int[]>toBeAdressed;
    ArrayList<Byte>program;
    
    public Compiler()
    {
        procedureAdresses = new HashMap<>();
        toBeAdressed = new HashMap<>();
        initTable();
    }
    
    private void initTable()
    {
        compileTable = new HashMap<>();
        
        compileTable.put("exit", new byte[]{0x00});
        
        compileTable.put("n", new byte[]{0x01});
        compileTable.put("r", new byte[]{0x02});
        
        compileTable.put("reg", new byte[]{0x11});
        compileTable.put("pin", new byte[]{0x12});
        compileTable.put("ton", new byte[]{0x14});
        
        compileTable.put("call", new byte[]{0x31});
        compileTable.put("jmp", new byte[]{0x32});
        compileTable.put("ret", new byte[]{0x33});
        compileTable.put("wat", new byte[]{0x34});
    }
    
    public byte[]compile(String asm)
    {
        toBeAdressed.clear();
        procedureAdresses.clear();
        
        program = new ArrayList<>();
        
        asm = asm.replace("\r", "");
        String[]lines = asm.split("\n");
        for(String line : lines)
            if(line.length()>0)
                if(!(line.toCharArray()[0] == '#'))
                    parse(line);
        
        for(Map.Entry<String, int[]> pair : toBeAdressed.entrySet())
        {
            String name = pair.getKey();
            int cursor = pair.getValue()[0];
            if(procedureAdresses.containsKey(name))
            {
                int adress = procedureAdresses.get(name)[0];
                ArrayList<Byte>adressByte = createNumber(adress);
                program.set(cursor, adressByte.get(0));
                program.set(cursor+1, adressByte.get(1));
            }
            else
            {
                System.err.println("Unknown command : "+name);
                return null;
            }
        }
        
        byte[] ret = new byte[program.size()];
        for(int i=0;i<ret.length;++i)
        {
            ret[i] = program.get(i);
        }
        
        return ret;
    }
    
    public void drawProgram(byte[]prog)
    {
        
        for(int i=0;i<prog.length;++i)
        {
            System.out.printf("%02X ",prog[i]);
            if((i+1)%8==0)
                if((i+1)%16==0)
                    System.out.print("\n");
                else
                    System.out.print(" ");
        }
        System.out.println();
    }
    
    private void parse(String line)
    {
        String[]characters = line.split(" ");
        String last = "";
        for(String c : characters)
        {
            if(compileTable.containsKey(c))
                program.add(compileTable.get(c)[0]);
            else
            {
                if(isNumber(c))
                    program.addAll(createNumber(Integer.parseInt(c)));
                else if(c.contains(":"))
                {
                    String name = c.replace(":", "");
                    //System.out.println("add new procedure '"+c+"' at adress : "+getCursor());
                    procedureAdresses.put(name, new int[]{getCursor()});
                }
                else if(procedureAdresses.containsKey(c))
                {
                    program.addAll(createNumber(procedureAdresses.get(c)[0]));
                }
                else if(last.equals("call") || last.equals("jmp"))
                {
                    //System.out.println("add wait for '"+c+"' adress : "+getCursor());
                    toBeAdressed.put(c, new int[]{getCursor()});
                    program.addAll(createNumber(0));
                }
                else
                {
                    System.err.println("Unknown : "+c);
                }
            }
            last = c;
        }
    }
    
    private int getCursor()
    {
        return program.size();
    }
    
    private ArrayList<Byte> createNumber(int n)
    {
        ArrayList<Byte>number = new ArrayList<>();
        
        int b1 = n%256;
        int b2 = ((n-b1)/16)/16;
        number.add((byte)b1);
        number.add((byte)b2);
        
        return number;
    }
    
    private boolean isNumber(String c)
    {
        return c.chars().allMatch( Character::isDigit );
    }
}
