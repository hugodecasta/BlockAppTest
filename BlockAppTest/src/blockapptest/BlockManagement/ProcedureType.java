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
public class ProcedureType extends BlockType
{
    String asmProcedure;
    public ProcedureType(String name, String image, String asmProcedure)
    {
        super(name, image, new Color(58,170,53), 0, 0, 0);
        this.asmProcedure = asmProcedure;
    }

    @Override
    public String getAsm(int[] userInputs)
    {
        return asmProcedure+"\n";
    }
    
}
