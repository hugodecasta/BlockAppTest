/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

import java.util.ArrayList;

/**
 *
 * @author i3mainz
 */
public abstract class ScreenComponent
{
    public double x,y,width,height;
    public int R,G,B,A;
    public boolean overlayOnGrab = false, mouseCatcher = true, touchable = true;
    public ArrayList<VDriver>drivers;
    
    public ScreenComponent()
    {
        drivers = new ArrayList<>();
    }
    
    public void updateDrivers()
    {
        for(VDriver d : drivers)
            d.update();
    }
    
    public VDriver addDriver()
    {
        VDriver d = new VDriver("");
        drivers.add(d);
        return d;
    }
    
    public void setBounds(double x,double y,double width,double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean touched(double mx, double my)
    {
        return mx>x && mx<x+width && my>y && my<y+height;
    }
    
    public void setColor(int R,int G,int B,int A)
    {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = A;
    }
    
    public abstract void initDraw();
    public abstract void draw();
    
    public void initDrawOverlay()
    {
        
    }
    public void drawOverlay()
    {
        
    }
    
    public void mousePressed()
    {
        
    }
    public void mouseReleased()
    {
        
    }
    public void mouseClicked()
    {
        
    }
    public void mouseHover()
    {
        
    }
    public void mouseGrab()
    {
        
    }
    public void mouseDrop()
    {
        
    }
    public void mouseDropObject(ScreenComponent c)
    {
        
    }
    public void mouseMoveOverWidth(ScreenComponent c)
    {
        
    }
    public void mouseMoveOn(ScreenComponent c)
    {
        
    }
}
