package de.harz.bildbearbeitung;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class ImageComponent extends JPanel
{
    private Image image;
//    public ImageComponent(Image image)
//    {
//        this.image = image;
//    }
    
    public ImageComponent() {
    	
    }
    
    public void setImage(Image image) {
    	this.image = image;
    }
 
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Male das Bild so groﬂ, wie diese Component gerade ist:
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
 
    }
}