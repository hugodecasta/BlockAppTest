/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GraphixManagement;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author i3mainz
 */
public class MainDrawer
{
    //-----------------------------------------------------------------
    Graphics2D g2d;
    Color fill;
    Color stroke;
    boolean filling,stroking;
    //-----------------------------------------------------------------
    public MainDrawer(Graphics2D g)
    {
        g2d = g;
        fill(255,0,0,255);
        noStroke();
    }
    //-----------------------------------------------------------------
    public void fill(double rgb,double alpha)
    {
        fill(rgb,rgb,rgb,alpha);
    }
    public void fill(double rgb)
    {
        fill(rgb,rgb,rgb,255);
    }
    public void fill(double R,double G,double B)
    {
        fill(R,G,B,255);
    }
    public void fill(double R,double G, double B, double A)
    {
        A = A/255;
        R = R/255;
        G = G/255;
        B = B/255;
        filling = true;
        fill = new Color((float)R, (float)G, (float)B, (float)A);
    }
    public void stroke(double R,double G, double B, double A)
    {
        stroking = true;
        fill = new Color((float)R, (float)G, (float)B, (float)A);
    }
    public void noFill()
    {
        filling = false;
    }
    public void noStroke()
    {
        stroking = false;
    }
    //-----------------------------------------------------------------
    public void rect(double x,double y,double w,double h)
    {
        if(filling)
        {
            g2d.setColor(fill);
            g2d.fillRect((int)x, (int)y, (int)w, (int)h);
        }
        if(stroking)
        {
            g2d.setColor(stroke);
            g2d.drawRect((int)x-1, (int)y-1, (int)w+2, (int)h+2);
        }
    }
}
