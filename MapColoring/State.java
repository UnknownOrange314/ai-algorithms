import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.*;
public class State {
	int[] myXPoints;
	int[] myYPoints;
	String myName=null;
	ArrayList<State> myBorders=null;

	
	ArrayList<int[]> xIslands=null;
	ArrayList<int[]> yIslands=null;
	ArrayList<Color> legalColors;
	
	public State(String name)
	{
		xIslands=new ArrayList<int[]>();
		yIslands=new ArrayList<int[]>();
		myName=name;
		myBorders=new ArrayList<State>();
		legalColors=new ArrayList<Color>();
	}
	public void addIsland(ArrayList<Integer> x,ArrayList<Integer> y)
	{
		int[] xP=new int[x.size()];
		int[] yP=new int[x.size()];
		for(int i=0;i<x.size();i++)
		{
			xP[i]=x.get(i);
			yP[i]=y.get(i);
		}
		xIslands.add(xP);
		yIslands.add(yP);
	}
	public void addBorder(State s)
	{
		myBorders.add(s);
		
	}
	public ArrayList<State> getBorders()
	{
		return myBorders;
	}
	public int[] getXPoints()
	{
		return myXPoints;
	}
	public int[] getYPoints()
	{
		return myYPoints;
	}
	public void draw(Graphics g)
	{
		g.setColor(getLegalColor());
		for(int i=0;i<xIslands.size();i++)
		{
			int[] xPoints=xIslands.get(i);
			int[] yPoints=yIslands.get(i);
			System.out.println(xPoints);
			System.out.println(yPoints);
			System.out.println(g);
			g.fillPolygon(xPoints,yPoints,xIslands.get(i).length);
		}
	}
	public String getName()
	{
		return myName;
	}
	public void setLegalColors(ArrayList<Color> c)
	{
		legalColors=(ArrayList<Color>)(c.clone());
	}
	public void setLegalColor(Color c)
	{
		legalColors.clear();
		legalColors.add(c);
	}
	public Color pickLegalColor()
	{
		return legalColors.get((int)(Math.random()*legalColors.size()));
	}
	public void removeLegalColor(Color c)
	{
		legalColors.remove(c);
	}
	public Color getLegalColor()//should only be called if there is only one legal color
	{
		return legalColors.get(0);
	}
	public ArrayList<Color> getLegalColors()
	{
		return legalColors;
	}

	

}