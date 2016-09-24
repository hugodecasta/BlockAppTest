/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.BlockManagement;

import blockapptest.GraphixManagement.MainDrawer;

/**
 *
 * @author i3mainz
 */
public class AsmType extends BlockType
{
    String instruction;
    
    public AsmType(String instruction, int inputsNeeded)
    {
        super(instruction,inputsNeeded,0,0);
        this.instruction = instruction;
    }
    
    @Override
    public String getAsm(int[] userInputs)
    {
        return instruction+" $$$\n";
    }

    @Override
    public void draw(double x, double y, double width, double height, MainDrawer d)
    {
        d.fill(102,36,131);
        d.stroke(255,255,255,255);
        d.strokeWeight(5);
        d.rect(x, y, height, width);
        d.noStroke();
        d.fill(255);
        d.text(name,x+10,y+height/2,20);
    }
    
}
