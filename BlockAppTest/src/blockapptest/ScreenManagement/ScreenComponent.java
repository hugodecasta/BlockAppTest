/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

/**
 *
 * @author i3mainz
 */
public abstract class ScreenComponent
{
    public double x,y,width,height;
    public int R,G,B,A;
    public boolean overlay;
    
    public ScreenComponent()
    {
        
    }
    
    public void setBounds(double x,double y,double width,double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void setColor(int R,int G,int B,int A)
    {
        this.R = R;
        this.G = G;
        this.B = B;
        this.A = A;
    }
    public void setOverlay(boolean overlay)
    {
        this.overlay = overlay;
    }
    
    public abstract void initDraw();
    public abstract void draw();
    
    public void mousePressed()
    {
        
    }
    public void mouseReleased()
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
}
