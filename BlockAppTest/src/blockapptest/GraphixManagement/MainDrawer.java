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
    double size;
    double strokeWeight;
    //-----------------------------------------------------------------
    public MainDrawer(Graphics2D g,double size)
    {
        this.size = size;
        g2d = g;
        fill(255,0,0,255);
        noStroke();
        strokeWeight(1);
    }
    //-----------------------------------------------------------------
    public void strokeWeight(double weight)
    {
        strokeWeight = weight;
    }
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
        A = A/255;
        R = R/255;
        G = G/255;
        B = B/255;
        stroking = true;
        stroke = new Color((float)R, (float)G, (float)B, (float)A);
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
        x = x*size;
        y = y*size;
        w = w*size;
        h = h*size;
        double sizedSroke = strokeWeight*size;
        double sx = x-sizedSroke;
        double sy = y-sizedSroke;
        double sw = w+sizedSroke*2;
        double sh = h+sizedSroke*2;
        
        if(stroking)
        {
            g2d.setColor(stroke);
            g2d.fillRect((int)sx, (int)sy, (int)sw, (int)sh);
        }
        if(filling)
        {
            g2d.setColor(fill);
            g2d.fillRect((int)x, (int)y, (int)w, (int)h);
        }
    }

    public void text(String text, double x, double y, int textSize)
    {
        x = x*size;
        y = y*size;
        System.out.println(x);
        g2d.setColor(fill);
        g2d.drawString(text, (int)x, (int)y);
    }
    
    public void setSize(double size)
    {
        this.size = size;
    }
}
