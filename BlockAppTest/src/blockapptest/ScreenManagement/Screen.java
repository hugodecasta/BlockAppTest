/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.ScreenManagement;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
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
    static TreeMap<String,ScreenLayer>layers,layersToAdd;
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
        if(layers==null)
            layers = new TreeMap<>();
        if(layersToAdd==null)
            layersToAdd = new TreeMap<>();
    }
    public static void addLayer(String name)
    {
        initComponent();
        layers.put(name, new ScreenLayer(name));
    }
    public static void addComponent(ScreenComponent c,String layer)
    {
        initComponent();
        if(layers.containsKey(layer))
            layers.get(layer).addComponent(c);
        else
            System.err.println("No layer called '"+layer+"'");
    }
    public static void removeComponent(ScreenComponent c,String layer)
    {
        initComponent();
        if(layers.containsKey(layer))
        {
            layers.get(layer).removeComponent(c);
        }
        else
            System.err.println("No layer called '"+layer+"'");
    }
    public static void setMouseState(boolean clicked)
    {
        mouseState = clicked;
    }
    //-------------------------------------------
    static class Frame extends JFrame implements MouseListener,KeyListener
    {
        public Frame()
        {
            this.addMouseListener(this);
            this.addKeyListener(this);
            this.setPreferredSize(new Dimension((int)(width*size)+6,(int)(height*size)+29));
            this.setResizable(false);
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

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            ArrayList<ScreenLayer>temp_layers = new ArrayList<>(layers.values());
            Collections.reverse(temp_layers);
            for(ScreenLayer l : temp_layers)
            {
                ArrayList<ScreenComponent>temp_comps = l.getComponents();
                for(ScreenComponent c : temp_comps)
                {
                    c.keyPressed(e);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
        
        class Panel extends JPanel
        {
            public Panel()
            {
                super();
            }
            private int screenTouch(ScreenComponent c,boolean act)
            {
                boolean mousePressed = mouseState && !pmouseState;
                boolean mouseReleased = !mouseState && pmouseState;
                boolean mouseHold = mouseState && pmouseState;
                boolean noHold = !mouseState && !pmouseState;
                
                boolean cTouched = c.touched(mouseX,mouseY);
                int mouseCatched = 0; //0=no; 1=touched but follow;2 =touched end;
                
                if(mousePressed)
                {
                    if(cTouched)
                    {
                        mouseCatched = 2;
                        pressed = c;
                        if(act)c.mousePressed();
                    }
                }
                else if(mouseReleased)
                {
                    if(c==pressed)
                    {
                        mouseCatched = 1;
                        if(act)c.mouseReleased();
                        if(act)c.mouseClicked();
                        if(lastDoubleClickCount<10)
                            if(act)c.mouseDoubleClicked();
                    }
                    if(c==grabbed)
                    {
                        if(act)c.mouseDrop();
                    }
                    else if(cTouched)
                    {
                        mouseCatched = 2;
                        if(grabbed!=null)
                        {
                            if(act)c.mouseDropObject(grabbed);
                        }
                        if(act)c.mouseReleased();
                    }
                }
                else if(mouseHold)
                {
                    if(c==pressed)
                    {
                        mouseCatched = 1;
                        if(Math.abs(mouseX-pmouseX)>5 || Math.abs(mouseY-pmouseY)>5 && grabbed==null)
                            grabbed = c;
                    }
                    if(c==grabbed)
                    {
                        mouseCatched = 1;
                        if(act)c.mouseGrab();
                    }
                    else if(grabbed!=null)
                    {
                        if(cTouched)
                        {
                            mouseCatched = 2;
                            if(act)c.mouseMoveOverWidth(grabbed);
                            if(act)grabbed.mouseMoveOn(c);
                        }
                    }
                    else if(cTouched)
                    {
                        mouseCatched = 2;
                        if(act)c.mouseHover();
                    }
                }
                else if(noHold)
                {
                    if(cTouched)
                    {
                        mouseCatched = 2;
                        if(act)c.mouseHover();
                    }
                }
                
                return c.mouseCatcher?mouseCatched:0;
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
                
                ArrayList<ScreenLayer>temp_layers = new ArrayList<>(layers.values());
                Collections.reverse(temp_layers);
                
                for(ScreenLayer l : temp_layers)
                {
                    l.update();
                }
                
                background(0);
                
                boolean mouseFunc = false;
                ArrayList<ScreenComponent>touched = new ArrayList<>();
                for(int l=temp_layers.size()-1;l>=0;--l)
                {
                    ScreenLayer layer = temp_layers.get(l);
                    ArrayList<ScreenComponent>temp_comps = layer.getComponents();
                    for(int i=temp_comps.size()-1;i>=0 && !mouseFunc;--i)
                    {
                        ScreenComponent c = temp_comps.get(i);
                        if(c.touchable)
                        {
                            int touchAppend = screenTouch(c,false);
                            mouseFunc = touchAppend==2;
                            if(touchAppend>0)
                                touched.add(c);
                        }
                    }
                }
                
                for(ScreenLayer l : temp_layers)
                {
                    ArrayList<ScreenComponent>temp_comps = l.getComponents();
                    for(ScreenComponent c : temp_comps)
                    {
                        c.updateDrivers();
                        boolean grabbedOverlay = c==grabbed && grabbed.overlayOnGrab;
                        if(!grabbedOverlay)
                            c.initDraw();
                        if(touched.contains(c) && !grabbedOverlay)
                        {
                            screenTouch(c,true);
                        }
                        else if(!grabbedOverlay && c.recieveGlobalPressed && (mouseState && !pmouseState))
                            c.mouseGlobalPressed();
                        if(!grabbedOverlay)
                            c.draw();
                    }
                }
                
                if(grabbed!=null && grabbed.overlayOnGrab)
                {
                    grabbed.initDrawOverlay();
                    if(mouseState && !pmouseState && grabbed.recieveGlobalPressed)
                        grabbed.mouseGlobalPressed();
                    screenTouch(grabbed,true);
                    grabbed.drawOverlay();
                }
                
                if(pmouseState && !mouseState)
                {
                    grabbed = null;
                    pressed = null;
                    recordClickLess = 0;
                }
                if(!mouseState && !pmouseState)
                {
                    recordClickLess++;
                }
                
                if(mouseState)
                {
                    fill(0,0,0,100);
                    ellipse(mouseX-20,mouseY-20,40,40);
                    lastDoubleClickCount = recordClickLess;
                }
                
                pmouseState = mouseState;
            }
        }
    }
    //-------------------------------------------
    static ScreenComponent pressed,grabbed;
    public static double mouseX, mouseY, pmouseX, pmouseY;
    static boolean mouseState, pmouseState;
    static int recordClickLess,lastDoubleClickCount;
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
    public static void stroke(Color c)
    {
        stroke(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
    }
    public static void stroke(double rgb)
    {
        stroke(rgb,rgb,rgb,255);
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
    public static void ellipse(double x,double y,double w,double h)
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
            g.fillOval((int)sx, (int)sy, (int)sw, (int)sh);
        }
        if(filling)
        {
            g.setColor(fill);
            g.fillOval((int)x, (int)y, (int)w, (int)h);
        }
    }
    public static void image(Image image,double x,double y,double w,double h)
    {
        image(image,x,y,w,h,255);
    }
    public static void image(Image image,double x,double y,double w,double h,double alpha)
    {
        x = x*size;
        y = y*size;
        w = w*size;
        h = h*size;
        alpha = alpha/255;
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)alpha));
        
        g.drawImage(image,(int)x,(int)y,(int)w,(int)h,null);
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
    
    public static void showKeyboard()
    {
        
    }
    public static void hideKeyboard()
    {
        
    }
    static HashMap<String,Image>imageCache;
    static double tx,ty;
    static double tangle;
}
