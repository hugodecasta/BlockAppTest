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
    public ProcedureStream stream;
    public ProcedureType(String name, String image, Color color)
    {
        super(name, image, color, 0, 0, 0);
        stream = new ProcedureStream(name+"_stream", color,this.image);
    }

    @Override
    public String getAsm(String[] userInputs)
    {
        Stream.addToParse(stream);
        return "call "+stream.name+"\n";
    }
    
}
