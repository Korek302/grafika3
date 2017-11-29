package grafika3;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class ImagePanel extends JPanel 
{
	static Graphics2D g2d;
    VectorImage vImg;
    
    public ImagePanel()
    {
    	vImg = new VectorImage();
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
		g2d = (Graphics2D) g;
		vImg.drawTrans();
    }
}