package grafika3;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class ImagePanel extends JPanel 
{
	static Graphics2D g2d;
    private BufferedImage image;
    
    VectorImage vImg;
    
    public ImagePanel()
    {
    	vImg = new VectorImage();
    }
    
    public ImagePanel(BufferedImage image) 
    {
        this.image = image;
    }
    
    public void setImage(BufferedImage newImg)
    {
    	image = newImg;
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	/*int height = getHeight() > getWidth() ? (int)((image.getHeight(null)/image.getWidth(null))*getWidth())
    			: getHeight();
    	int width = getHeight() > getWidth() ? getWidth()
    			: (int)((image.getWidth(null)/image.getHeight(null))*getHeight());*/
    	super.paintComponent(g);
		g2d = (Graphics2D) g;
		vImg.draw();
    }
}