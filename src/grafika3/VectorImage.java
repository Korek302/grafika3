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
	ArrayList<Polygon> polyListTrans;
	
	public VectorImage()
	{
		polyList = new ArrayList<Polygon>();
		polyListTrans = new ArrayList<Polygon>();
		
		String formFile = null;
		try 
		{
			formFile = readFile("vectorImage.txt");
		} 
		catch (IOException e) 
		{
			System.out.println("Error while loading file (vectorImage.txt)");
		}
		char[] charArr = formFile.toCharArray();
		for(int i = 0; i < formFile.length(); i++)
		{
			if(charArr[i] == 'f')
			{
				StringBuilder sb = new StringBuilder();
				char next = ' ';
				int j = 1;
				while(next != 'f' && next != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					if(i+j+1 < formFile.length() - 1)
					{
						next = charArr[i+j+1];
					}
					j++;
				}
				addPolygon(sb.toString());
				i = i + j - 1;
			}
			if(charArr[i] == 'p')
			{
				StringBuilder sb = new StringBuilder();
				char next = ' ';
				int j = 1;
				while(next != 'f' && next != 'p' && i+j < formFile.length())
				{
					sb.append(charArr[i+j]);
					if(i+j+1 < formFile.length() - 1)
					{
						next = charArr[i+j+1];
					}
					j++;
				}
				addPolynomial(sb.toString());
				i = i + j - 1;
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
		
		char[] charArr = formFile.toCharArray();
		
		for(int i = 0; i < formFile.length(); i++)
		{
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
				addPolygon(sb.toString());
				i = i + j - 1;
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
				addPolynomial(sb.toString());
				i = i + j - 1;
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
	
	public void drawTrans()
	{
		transform();
		
		/*for(Polygon p : polyListTrans)
		{
			System.out.println("///////");
			for(int i = 0; i<p.xpoints.length; i++)
			{
				System.out.println(p.xpoints[i] + ", " + p.ypoints[i]);
			}
		}*/
		
		if(!polyListTrans.isEmpty())
		{
			for(Polygon p : polyListTrans)
			{
				ImagePanel.g2d.drawPolygon(p);
			}
		}
		else
		{
			System.out.println("Error: polyListTrans empty");
		}
	}
	
	public void transform()
	{
		double[][] transMatrix = loadMatrix();/*new double[3][3];
		for(int i = 0; i < transMatrix[0].length; i++)
			for(int f = 0; f < transMatrix.length; f++)
				transMatrix[i][f] = (double) 0;
		
		transMatrix[0][0] = (double) 1;
		transMatrix[1][1] = (double) 1;
		transMatrix[2][2] = (double) 1;
		
		transMatrix[2][0] = (double) 100;
		transMatrix[2][1] = (double) 100;
		
		transMatrix[0][0] = (double) 0.707;
		transMatrix[0][1] = (double) 0.707;
		transMatrix[1][0] = (double) -0.707;
		transMatrix[1][1] = (double) 0.707;*/
		
		ArrayList<int[]> currPoints;
		ArrayList<int[]> newPoints;
		
		for(Polygon p : polyList)
		{
			currPoints = new ArrayList<int[]>();
			for(int i = 0; i < p.xpoints.length; i++)
			{
				int[] tempTab = new int[3];
				tempTab[0] = p.xpoints[i];
				tempTab[1] = p.ypoints[i];
				tempTab[2] = 1;
				currPoints.add(tempTab);
			}
			
			newPoints = new ArrayList<int[]>();
			for(int[] currPoint : currPoints)
			{
				int[] newPoint = new int[3];
				for(int i = 0; i < transMatrix[0].length; i++)
				{
					int val = 0;
					for(int j = 0; j < transMatrix.length; j++)
					{
						val += currPoint[j] * transMatrix[j][i];
					}
					newPoint[i] = val;
				}
				newPoints.add(newPoint);
			}
			int[] xVals = new int[p.npoints];
			int[] yVals = new int[p.npoints];
			
			for(int i = 0; i < xVals.length; i++)
			{
				xVals[i] = newPoints.get(i)[0];
				yVals[i] = newPoints.get(i)[1];
			}
			
			polyListTrans.add(new Polygon(xVals, yVals, xVals.length));
		}
	}
	
	private double[][] loadMatrix()
	{
		double[][] out = new double[3][3];
		
		String formFile = null;
		try 
		{
			formFile = readFile("transMatrix.txt");
		} 
		catch (IOException e) 
		{
			System.out.println("Error while loading file (vectorImage.txt)");
		}
		
		String[] values = formFile.split(",");
		if(values.length % 5 != 0)
		{
			System.out.println("Error - txt format");
		}
		else
		{
			if(values.length > 5)
			{
				int numOfM = values.length/5;
				
				for(int i = 0; i < numOfM; i++)
				{
					double[][] temp = new double[3][3];
					
					temp[0][0] = Double.parseDouble(values[5*i + 1]) * Math.cos(Math.toRadians(Double.parseDouble(values[5*i + 0])));
					temp[0][1] = Math.sin(Math.toRadians(Double.parseDouble(values[5*i + 0])));
					temp[0][2] = 0;
					temp[1][0] = (-Math.sin(Math.toRadians(Double.parseDouble(values[5*i + 0]))));
					temp[1][1] = Double.parseDouble(values[5*i + 2]) * Math.cos(Math.toRadians(Double.parseDouble(values[5*i + 0])));
					temp[1][2] = 0;
					temp[2][0] = Double.parseDouble(values[5*i + 3]);
					temp[2][1] = Double.parseDouble(values[5*i + 4]);
					temp[2][2] = 1;
					
					if(i == 0)
					{
						out = temp;
					}
					else
					{
						out = matrixMul(out, temp);
					}
				}
				
			}
			else
			{
				out[0][0] = Double.parseDouble(values[1]) * Math.cos(Math.toRadians(Double.parseDouble(values[0])));
				out[0][1] = Math.sin(Math.toRadians(Double.parseDouble(values[0])));
				out[0][2] = 0;
				out[1][0] = (-Math.sin(Math.toRadians(Double.parseDouble(values[0]))));
				out[1][1] = Double.parseDouble(values[2]) * Math.cos(Math.toRadians(Double.parseDouble(values[0])));
				out[1][2] = 0;
				out[2][0] = Double.parseDouble(values[3]);
				out[2][1] = Double.parseDouble(values[4]);
				out[2][2] = 1;
			}
			
			if(out[0][0] == 0 || out[1][1] == 0)
			{
				out[0][0] = 1;
				out[1][1] = 1;
			}
		}
		
		return out;
	}
	
	private double[][] matrixMul(double[][] A, double[][] B) 
	{

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) 
        {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) 
        {
            for (int j = 0; j < bColumns; j++) 
            {
                C[i][j] = (double)0;
            }
        }

        for (int i = 0; i < aRows; i++) 
        {
            for (int j = 0; j < bColumns; j++) 
            {
                for (int k = 0; k < aColumns; k++) 
                {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
	
	private void addPolygon(String str)
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
			
			polyList.add(new Polygon(xValues, yValues, xValues.length));
		}
	}
	
	private void addPolynomial(String str)
	{
		String[] values = str.split("-");
		int[] xValues = new int[values.length - 1];
		int[] yValues = new int[values.length - 1];
		if(values.length % 2 != 0)
		{
			System.out.println("Error - txt format");
		}
		else
		{
			for(int i = 0; i < values.length; i+=2)
			{
				xValues[i/2] = Integer.parseInt(values[i]);
				xValues[xValues.length - 1 - i/2] = Integer.parseInt(values[i]);
				
				yValues[i/2] = Integer.parseInt(values[i+1]);
				yValues[yValues.length - 1 - i/2] = Integer.parseInt(values[i+1]);
			}
			xValues[xValues.length - 1] = Integer.parseInt(values[0]);
			yValues[yValues.length - 1] = Integer.parseInt(values[1]);
			polyList.add(new Polygon(xValues, yValues, xValues.length));
		}
	}
	
	static String readFile(String path) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}
	
}