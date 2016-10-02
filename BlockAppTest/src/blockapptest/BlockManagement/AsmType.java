/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import java.awt.Color;

/**
 *
 * @author i3mainz
 */
public class AsmType extends BlockType
{
    String instruction;
    
    public AsmType(String name, String image, String instruction, int inputsNeeded)
    {
        super(name,image,new Color(102,36,131),inputsNeeded,0,0);
        this.instruction = instruction;
    }
    
    @Override
    public String getAsm(String[] userInputs)
    {
        String[]parts = instruction.split("\\$");
        String asm = parts[0];
        for(int i=1;i<parts.length;++i)
        {
            int id = Integer.parseInt(parts[i].toCharArray()[0]+"");
            asm+=userInputs[id]+parts[i].substring(1,parts[i].length());
        }
        return asm+"\n";
    }
    
}
