/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.ScreenManagement.Screen;
import blockapptest.ScreenManagement.ScreenComponent;

/**
 *
 * @author i3mainz
 */
public class LevelManager extends ScreenComponent
{
    public int level;
    
    public LevelManager()
    {
        level = 2;
    }

    @Override
    public void initDraw() {
        Screen.fill(192,22,34);
    }

    @Override
    public void draw() {
        Screen.rect(x,y,width,height);
        Screen.fill(255);
        Screen.textSize(50);
        Screen.text(level+"", x+20, y+53);
    }
}
