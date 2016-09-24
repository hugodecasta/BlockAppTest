/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockapptest.GraphixManagement;

import blockapptest.GameManagement.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author i3mainz
 */
public class AndroidFrame extends JFrame
{
    Drawable game;
    double size;
    int width, height;
    public AndroidFrame(Drawable game,double size)
    {
        super();
        this.game = game;
        this.size = size;
        this.width = (int)(size*1400);
        this.height = (int)(size*2100);
        this.setTitle("Android frame 0.1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(width,height));
        this.add(new Panel());
        this.pack();
        this.setVisible(true);
    }
    class Panel extends JPanel
    {
        Image androidImage;
        MainDrawer drawer;
        public Panel()
        {
            try
            {
                BufferedImage image = ImageIO.read(new File("src\\blockapptest\\GraphixManagement\\android.png"));
                androidImage = image;
            }
            catch(IOException e)
            {
            };
        }
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);
            g.drawImage(androidImage, 0, 0,width,height, this);
            
            Graphics2D g2 = (Graphics2D) g;
            if(drawer == null)
                drawer = new MainDrawer(g2,size);
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
            
            game.draw(316, 410, 770, 1280, drawer);
        }
    }
}
