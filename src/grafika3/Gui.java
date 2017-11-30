package grafika3;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Gui
{
	JFrame frame;
	BufferedImage currImage;
	
	public static void main(String[] args)
	{
		Gui gui = new Gui();
		gui.GUI();
	}
	
	private void GUI()
	{
		frame = new JFrame();
		ImagePanel panel = new ImagePanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Grafika3");
		frame.setSize(700, 700);
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}

