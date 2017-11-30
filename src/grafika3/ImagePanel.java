package grafika3;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class ImagePanel extends JPanel implements ActionListener
{
	static Graphics2D g2d;
    VectorImage vImg;
    
    private JButton normal;
    private JButton trans;
    
    int flag;
    
    public ImagePanel()
    {
    	vImg = new VectorImage();
    	
    	flag = 0;
    	
    	normal = new JButton("Normal");
    	trans = new JButton("Transformation");
    	
    	setLayout(null);
    	normal.setBounds(490, 620, 90, 30);
    	trans.setBounds(590, 620, 90, 30);
    	
    	add(normal);
    	add(trans);
    	
    	normal.addActionListener(this);
    	trans.addActionListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
		g2d = (Graphics2D) g;
		if(flag == 0)
		{
			vImg.draw();
		}
		else if(flag == 1)
		{
			vImg.drawTrans();
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source == normal)
		{
			flag = 0;
		}
		else if(source == trans)
		{
			flag = 1;
		}
		repaint();
	}
}