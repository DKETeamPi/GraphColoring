import java.util.*;
import java.awt.Color.*;
import java.awt.*;

public class HintCalculatorMode3 implements Runnable
{
	private static boolean DEBUG = true;
	private adjGraph graph;
	private int[] coloring;//HC coloring
	private int[] colorToUse;
	private int[] tempPlayerColoring;
	private int [] result;
	private boolean newPlayerColoring;
	final private Object PC_LOCK=new Object();
	final private Object LOCK=new Object();
	public boolean stop=false;
	
	public int minColour;
	HintCalculatorMode3(adjGraph graph)
	{
		this.graph=graph;
		coloring = new int[graph.length.length];
		colorToUse=new int[graph.length.length];
		tempPlayerColoring =new int[graph.length.length];
		result=new int[graph.length.length];
		newPlayerColoring=false;
		
		minColour=graph.length.length;
	}
	public void run()
	{
		while(!stop)
		{
			if(DEBUG){System.out.println("newPlayerColoring "+newPlayerColoring);}
			getPlayerColoring();
			sortArray();
			if(DEBUG){System.out.println("newPlayerColoring "+newPlayerColoring);}
			int i=0;
			while(i<coloring.length && coloring[i]!=Color.WHITE.getRGB())
			{
				i++;
			}
			if(i<graph.length.length)
			{
				Calculate(i,0);
			}
			synchronized(PC_LOCK)
			{
				if(!newPlayerColoring)
				{
					System.out.println("sleeping");
					try
					{
						PC_LOCK.wait();
					}
					catch(InterruptedException e)
					{
						
					}
					System.out.println("wakeing up");
				}
			}
		}
	}
	public void Calculate(int index,int colourUsed)
	{
		colourUsed=Math.max(ColouringVertex(index),colourUsed);
		int i=0;
		if (!newPlayerColoring && colourUsed<minColour)
		{
			boolean a=true;
			while(!newPlayerColoring && colourUsed<minColour && i<graph.length.length)
			{
				if (coloring[i]==Color.WHITE.getRGB())
				{
					Calculate(i,colourUsed);
					a=false;
				}
				i++;
			}
			if (a && !newPlayerColoring)
			{
				minColour=colourUsed;
				ShowColoring();
				minColour=colourUsed;
			}
		}
		ColouringVertexEnd(index);
		//System.out.print("ciao");
	}
	public int ColouringVertex(int index)
	{
		boolean RightColour=false;
		int colour=-1;
		int i;
		while(!RightColour)
		{
			colour++;
			RightColour=true;
			i=0;
			while (RightColour && i<graph.length[index])
			{
				//System.out.println(ColourVertex.length+" "+v.vertex[index][i]+" hi");
				if (coloring[graph.vertex[index][i]]==colorToUse[colour])
				{
					RightColour=false;
				}
				i++;
			}
		}
		coloring[index]=colorToUse[colour];
		return colour;
	}
	public void ColouringVertexEnd(int index)
	{
		coloring[index]=Color.WHITE.getRGB();
	}
	private void ShowColoring()
	{
		System.out.println("ShowColoring");
		for(int i=0;i<graph.length.length;i++)
		{
			result[i]=coloring[i];
			System.out.println(coloring[i]);
			
		}
	}
	public int[] getResult()
	{
		int[] toGive = new int[result.length];
		synchronized(LOCK)
		{
			for(int i=0;i<result.length;i++)
			{
				toGive[i]=result[i];
			}
		}
		return toGive;
	}
	private boolean getPlayerColoring()
	{
		boolean result=false;
		minColour=graph.length.length;
		synchronized(PC_LOCK)//to check
		{
			if(newPlayerColoring)
			{	
				result=true;
				for(int i=0;i<coloring.length;i++)
				{
					coloring[i]=tempPlayerColoring[i];
					if(DEBUG){System.out.println("HC parsing player coloring: "+coloring[i]);}
				}
				newPlayerColoring=false;
			}
		}
		return result;
	}
	public void setPlayerColoring(int[] a)
	{
		synchronized(PC_LOCK)
		{
			tempPlayerColoring=a;
			newPlayerColoring=true;
			PC_LOCK.notify();
		}
	}
	public void sortArray()
	{
		class numberOfColor implements Comparable<numberOfColor>
		{
			int rgb;
			public int quantity;
			public numberOfColor(int rgb)
			{
				this.rgb=rgb;
				quantity=1;
			}
			public int compareTo(numberOfColor a)
			{
				if (this.equal(Color.WHITE.getRGB()))
				{
					return -1;
				}
				else if(a.equal(Color.WHITE.getRGB()))
				{
					return 1;
				}
				else
				{
					return((new Integer(quantity)).compareTo(new Integer(a.quantity)));
				}
			}
			public boolean equal(int a)
			{
				return (rgb==a);
			}
			public void add()
			{
				quantity++;
			}
			public int getRGB()
			{
				return rgb;
			}
		}
		numberOfColor[] toSort=new numberOfColor[graph.length.length];
		int length=0;
		for(int i=0;i<coloring.length;i++)
		{
			if(coloring[i]!=Color.WHITE.getRGB())
			{
				boolean found=false;
				int j=0;
				while(!found && j<length)
				{
					if(toSort[j].equal(coloring[i]))
					{
						found=true;
					}
					j++;
				}
				j--;
				if(found)
				{
					toSort[j].add();
				}
				else
				{
					toSort[length]=new numberOfColor(coloring[i]);
					length++;
				}
			}
		}
		for(int i=length;i<toSort.length;i++)
		{
			toSort[i]=new numberOfColor(Color.WHITE.getRGB());
		}
		Arrays.sort(toSort);
		for(int i=0;i<colorToUse.length;i++)
		{
			if(toSort[toSort.length-i-1].getRGB()!=Color.WHITE.getRGB())
			{
				colorToUse[i]=toSort[toSort.length-i-1].getRGB();
			}
			else
			{
				colorToUse[i]=new java.util.Random().nextInt();
			}
		}
		if(DEBUG)
		{
			for(int i=0;i<colorToUse.length;i++)
			{
			System.out.println("colorToUse "+colorToUse[i]);
			}
		}
	}
}