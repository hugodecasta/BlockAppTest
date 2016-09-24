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
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author i3mainz
 */
public class AndroidFrame extends JFrame
{
    Drawable game;
    public AndroidFrame(Drawable game)
    {
        super();
        this.game = game;
        this.setTitle("Android frame 0.1");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500,500));
        this.add(new Panel());
        this.pack();
        this.setVisible(true);
    }
    class Panel extends JPanel
    {
        Image androidImage;
        public Panel()
        {
            //androidImage = new BufferedImage(WIDTH, WIDTH, WIDTH)
        }
        @Override
        public void paint(Graphics g)
        {
            super.paint(g);
            this.setBackground(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(androidImage, 0,0,50,50,this);
            game.draw(30, 30, 300, 400, g2);
        }
    }
}
