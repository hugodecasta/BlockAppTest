/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author i3mainz
 */
public class Screen
{
    static ArrayList<ScreenComponent>components;
    static int width, height;
    static double size;
    static Graphics2D g;
    static Frame f;
    
    public Screen()
    {
        initComponent();
    }
    //-------------------------------------------
    public static void launch()
    {
        initComponent();
        f = new Frame();
    }
    public static void draw()
    {
        f.repaint();
    }
    public static void initScreen(int width,int height,double size)
    {
        initComponent();
        Screen.width = width;
        Screen.height = height;
        Screen.size = size;
    }
    private static void initComponent()
    {
        if(components==null)
            components = new ArrayList<>();
    }
    public static void addComponent(ScreenComponent c)
    {
        initComponent();
        components.add(c);
    }
    
    public static void removeComponent()
    {
        initComponent();
    }
    public static void setMouseState(boolean clicked)
    {
        pmouseState = mouseState;
        mouseState = clicked;
    }
    //-------------------------------------------
    static class Frame extends JFrame implements MouseListener
    {
        public Frame()
        {
            this.addMouseListener(this);
            this.setPreferredSize(new Dimension((int)(width*size)+17,(int)(height*size)+40));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.add(new Panel());
            this.pack();
            this.setVisible(true);
        }

        @Override
        public void mouseClicked(MouseEvent e){}
        @Override
        public void mousePressed(MouseEvent e) {
            mouseButton = e.getButton();
            setMouseState(true);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            mouseButton = e.getButton();
            setMouseState(false);
        }
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        
        class Panel extends JPanel
        {
            public Panel()
            {
                super();
            }
            private boolean touch(double mx,double my,ScreenComponent c)
            {
                return mx>c.x && mx<c.x+c.width && my>c.y && my<c.y+c.height;
            }
            private boolean screenTouch(ScreenComponent c)
            {
                boolean mousePressed = mouseState && !pmouseState;
                boolean mouseReleased = !mouseState && pmouseState;
                boolean mouseHold = mouseState && pmouseState;
                boolean noHold = !mouseState && !pmouseState;
                
                
                
                if(mousePressed)
                {
                    if(touch(mouseX,mouseY,c))
                    {
                        c.mousePressed();
                        grabbed = c;
                        return true;
                    }
                }
                else if(mouseReleased)
                {
                    if(grabbed!=null && grabbed==c)
                    {
                        grabbed.mouseDrop();
                    }
                    if(touch(mouseX,mouseY,c))
                    {
                        c.mouseReleased();
                        if(grabbed!=null)
                        {
                            c.mouseDropObject(grabbed);
                        }
                        return true;
                    }
                }
                else if(mouseHold)
                {
                    if(touch(mouseX,mouseY,c) && c!=grabbed && grabbed!=null)
                    {
                        c.mouseMoveOverWidth(grabbed);
                        return true;
                    }
                }
                else if(noHold)
                {
                    if(touch(mouseX,mouseY,c))
                    {
                        c.mouseHover();
                        return true;
                    }
                }
                return false;
            }
            @Override
            public void paint(Graphics g)
            {
                super.paint(g);
                pmouseX = mouseX;
                pmouseY = mouseY;
                mouseX = MouseInfo.getPointerInfo().getLocation().x - f.getLocation().x - 8;
                mouseY = MouseInfo.getPointerInfo().getLocation().y - f.getLocation().y - 30;
                
                Screen.g = (Graphics2D)g;
                Screen.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                Screen.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                background(0);
                /*fill(255);
                if(mouseState)
                    if(mouseButton==1)
                        fill(255,0,0);
                    else if(mouseButton==2)
                        fill(0,255,0);
                    else if(mouseButton==3)
                        fill(0,0,255);
                rect(mouseX-10,mouseY-10,20,20);*/
                
                boolean mouseFunc = false;
                ScreenComponent t = null;
                for(int i=components.size()-1;i>=0;--i)
                {
                    if(!mouseFunc)
                    {
                        mouseFunc = screenTouch(components.get(i));
                        if(mouseFunc)
                            t = components.get(i);
                    }
                }
                for(ScreenComponent c : components)
                {
                    c.initDraw();
                    if(t==c)
                    {
                        mouseFunc = screenTouch(c);
                    }
                    c.draw();
                }
                if(grabbed!=null && grabbed.overlay)
                {
                    grabbed.initDraw();
                    screenTouch(grabbed);
                    if(mouseState && pmouseState)
                        grabbed.mouseGrab();
                    grabbed.draw();
                }
                if(pmouseState = !mouseState)
                {
                    grabbed = null;
                }
                
                pmouseState = mouseState;
            }
        }
    }
    //-------------------------------------------
    static ScreenComponent grabbed;
    public static double mouseX, mouseY, pmouseX, pmouseY;
    static boolean mouseState, pmouseState;
    static int mouseButton;
    static Color fill;
    static Color stroke;
    static boolean filling,stroking;
    static double strokeWeight;
    static Font font;
    
    public static void strokeWeight(double weight)
    {
        strokeWeight = weight;
    }
    public static void background(double R,double G, double B)
    {
        fill(R,G,B,255);
        rect(0,0,width,height);
    }
    public static void background(double R)
    {
        fill(R,R,R,255);
        rect(0,0,width,height);
    }
    public static void fill(Color c)
    {
        fill(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
    }
    public static void fill(double rgb,double alpha)
    {
        fill(rgb,rgb,rgb,alpha);
    }
    public static void fill(double rgb)
    {
        fill(rgb,rgb,rgb,255);
    }
    public static void fill(double R,double G,double B)
    {
        fill(R,G,B,255);
    }
    public static void fill(double R,double G, double B, double A)
    {
        A = A/255;
        R = R/255;
        G = G/255;
        B = B/255;
        filling = true;
        fill = new Color((float)R, (float)G, (float)B, (float)A);
    }
    public static void stroke(double R,double G, double B, double A)
    {
        A = A/255;
        R = R/255;
        G = G/255;
        B = B/255;
        stroking = true;
        stroke = new Color((float)R, (float)G, (float)B, (float)A);
    }
    public static void noFill()
    {
        filling = false;
    }
    public static void noStroke()
    {
        stroking = false;
    }
    //-----------------------------------------------------------------
    public static void rect(double x,double y,double w,double h)
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
            g.setColor(stroke);
            g.fillRect((int)sx, (int)sy, (int)sw, (int)sh);
        }
        if(filling)
        {
            g.setColor(fill);
            g.fillRect((int)x, (int)y, (int)w, (int)h);
        }
    }
    public static void roundRect(double x,double y,double w,double h,double round)
    {
        x = x*size;
        y = y*size;
        w = w*size;
        h = h*size;
        round = round*size;
        double sizedSroke = strokeWeight*size;
        double sx = x-sizedSroke;
        double sy = y-sizedSroke;
        double sw = w+sizedSroke*2;
        double sh = h+sizedSroke*2;
        double sround = round;
        
        if(stroking)
        {
            g.setColor(stroke);
            g.fillRoundRect((int)sx, (int)sy, (int)sw, (int)sh,(int)sround,(int)sround);
        }
        if(filling)
        {
            g.setColor(fill);
            g.fillRoundRect((int)x, (int)y, (int)w, (int)h,(int)round,(int)round);
        }
    }
    public static void image(Image image,double x,double y,double w,double h)
    {
        x = x*size;
        y = y*size;
        w = w*size;
        h = h*size;
        
        g.drawImage(image,(int)x,(int)y,(int)w,(int)h,null);
    }

    public static void text(String text, double x, double y)
    {
        x = x*size;
        y = y*size;
        g.setColor(fill);
        g.setFont(font);
        g.drawString(text, (int)x, (int)y);
    }
    
    public static void textSize(double textSize)
    {
        Screen.font = new Font("Arial",Font.PLAIN, (int)(textSize*size));
    }
    
    public static void rotate(double deg)
    {
        tangle += deg;
        g.rotate(Math.toRadians(deg));
    }
    
    public static void translate(double x,double y)
    {
        tx += x;
        ty += y;
        g.translate(x, y);
    }
    
    public static void resetTransform()
    {
        rotate(-tangle);
        translate(-tx,-ty);
        tangle = 0;
        tx = 0;
        ty = 0;
    }
    
    public static Image loadImage(String src)
    {
        if(imageCache == null)
            imageCache = new HashMap<>();
        if(imageCache.containsKey(src))
            return imageCache.get(src);
        else
        {
            File f = new File(src);
            if(f.exists() && !f.isDirectory())
            {
                try {
                    Image i;
                    i = ImageIO.read(new File(src));
                    imageCache.put(src, i);
                    return i;
                }
                catch (IOException ex) {
                    Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    static HashMap<String,Image>imageCache;
    static double tx,ty;
    static double tangle;
}