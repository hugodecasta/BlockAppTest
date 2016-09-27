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
    
    public AsmType(String name, String image, String instruction, int inputsNeeded, int blockInputsNeeded)
    {
        super(name,image,new Color(102,36,131),inputsNeeded,blockInputsNeeded,0);
        this.instruction = instruction;
    }
    
    @Override
    public String getAsm(int[] userInputs)
    {
        return instruction+"\n";
    }
    
}
