package grafika3;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class VectorImage 
{
	ArrayList<Polygon> polyList;
	
	public VectorImage()
	{
		polyList = new ArrayList<Polygon>();
		
		String formFile = null;
		try 
		{
			formFile = readFile("vectorImage.txt");
		} 
		catch (IOException e) 
		{
			System.out.println("Error while loading file (vectorImage.txt)");
		}
		
		for(int i = 0; i < formFile.length(); i++)
		{
			char[] charArr = formFile.toCharArray();
			if(charArr[i] == 'f')
			{
				StringBuilder sb = new StringBuilder();
				char next = ' ';
				int j = 1;
				while(next != 'f' && next != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					next = charArr[i+j+1];
					j++;
				}
				addFigure(sb.toString());
				i = i + j;
			}
			if(charArr[i] == 'p')
			{
				StringBuilder sb = new StringBuilder();
				char next = ' ';
				int j = 1;
				while(next != 'f' && next != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					next = charArr[i+j+1];
					j++;
				}
				addPolygon(sb.toString());
				i = i + j;
			}
		}
	}
	
	public VectorImage(String location)
	{
		String formFile = null;
		try 
		{
			formFile = readFile(location);
		} 
		catch (IOException e) 
		{
			System.out.println("Error while loading file (vectorImage.txt)");
		}
		
		for(int i = 0; i < formFile.length(); i++)
		{
			char[] charArr = formFile.toCharArray();
			if(charArr[i] == 'f')
			{
				StringBuilder sb = new StringBuilder();
				char curr = ' ';
				int j = 1;
				while(curr != 'f' && curr != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					curr = charArr[i+j];
					j++;
				}
				addFigure(sb.toString());
				i = i + j;
			}
			if(charArr[i] == 'p')
			{
				StringBuilder sb = new StringBuilder();
				char curr = ' ';
				int j = 1;
				while(curr != 'f' && curr != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					curr = charArr[i+j];
					j++;
				}
				addPolygon(sb.toString());
				i = i + j;
			}
		}
	}
	
	public void draw()
	{
		if(!polyList.isEmpty())
		{
			for(Polygon p : polyList)
			{
				ImagePanel.g2d.drawPolygon(p);
			}
		}
		else
		{
			System.out.println("Error: polyList empty");
		}
	}
	
	private void addFigure(String str)
	{
		String[] values = str.split("-");
		int[] xValues = new int[values.length/2 + 1];
		int[] yValues = new int[values.length/2 + 1];
		if(values.length % 2 != 0)
		{
			System.out.println("Error - txt format");
		}
		else
		{
			for(int i = 0; i < values.length; i=i+2)
			{
				xValues[i/2] = Integer.parseInt(values[i]);
				yValues[i/2] = Integer.parseInt(values[i+1]);
			}
			xValues[xValues.length - 1] = Integer.parseInt(values[0]);
			yValues[yValues.length - 1] = Integer.parseInt(values[1]);
			
			polyList.add(new Polygon(xValues, yValues, xValues.length - 1));
		}
	}
	
	private void addPolygon(String str)
	{
		String[] values = str.split("-");
		int[] xValues = new int[values.length/2];
		int[] yValues = new int[values.length/2];
		if(values.length % 2 != 0)
		{
			System.out.println("Error - txt format");
		}
		else
		{
			for(int i = 0; i < values.length; i=i+2)
			{
				xValues[i/2] = Integer.parseInt(values[i]);
				yValues[i/2] = Integer.parseInt(values[i+1]);
			}
			
			polyList.add(new Polygon(xValues, yValues, xValues.length - 1));
		}
	}
	
	static String readFile(String path) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}
}