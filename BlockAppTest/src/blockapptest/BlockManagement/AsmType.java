/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GraphixManagement.ScreenManager;

/**
 *
 * @author i3mainz
 */
public class AsmType extends BlockType
{
    String instruction;
    
    public AsmType(String name, String instruction, int inputsNeeded)
    {
        super(name,inputsNeeded,0,0);
        this.instruction = instruction;
    }
    
    @Override
    public String getAsm(int[] userInputs)
    {
        return instruction+" $$$\n";
    }

    @Override
    public void draw(double x, double y, double width, double height, ScreenManager d)
    {
        d.fill(102,36,131);
        d.rect(x, y, height, width);
        d.fill(255);
        d.textSize(50);
        d.text(name,x+20,y+height/1.7);
    }
    
}
