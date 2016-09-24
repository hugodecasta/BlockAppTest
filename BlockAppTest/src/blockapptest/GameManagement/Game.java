/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GameManagement;

import blockapptest.GraphixManagement.AndroidFrame;
import blockapptest.GraphixManagement.Drawable;
import java.awt.Color;
import java.awt.Graphics2D;


/**
 *
 * @author i3mainz
 */
public class Game implements Drawable
{
    AndroidFrame frame;
    
    public Game()
    {
        frame = new AndroidFrame(this);
    }

    @Override
    public void draw(int x, int y, int width, int height, Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        
        int libSize = 60;
        g.setColor(Color.BLUE);
        g.fillRect(x, y, libSize, height);
        
        int buttonSize = 50;
        g.setColor(Color.red);
        g.fillRect(x, y, width, buttonSize);
        g.fillRect(x, (height-buttonSize)+y, width, buttonSize);
    }
}
