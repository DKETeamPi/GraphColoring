import java.util.*;
import java.awt.Color.*;
import java.awt.*;

public class HintCalculator implements Runnable
{
	private static boolean DEBUG = true;
	private int[] coloring;//HC coloring
	private int[] backTrackColoring;
	private int[] playerColoring;//player Coloring
	private int rightNumberOfColors;//right number of colors
	private int[] tempPlayerColoring;
	private int[] tempBackTrackColoring;
	private int tempRightNumberOfColors;
	private int [] result;
	private boolean newPlayerColoring;
	private boolean newBackTrackColoring;
	private int[] colorToUse;
	private adjGraph graph;
	final private Object LOCK=new Object();
	final private Object PC_LOCK=new Object();
	final private Object BT_LOCK=new Object();
	public boolean stop =false;
	//final private Object RESULT_LOCK=new Object();
	HintCalculator(adjGraph graph)
	{
		this.graph=graph;
		coloring = new int[graph.length.length];
		backTrackColoring = null;
		playerColoring = new int[graph.length.length];
		rightNumberOfColors=graph.length.length;
		tempPlayerColoring =new int[graph.length.length];
		tempBackTrackColoring =new int[graph.length.length];
		tempRightNumberOfColors=graph.length.length;
		result=new int[graph.length.length];
		newPlayerColoring=false;
		newBackTrackColoring=false;
		colorToUse=new int[0];
		//LOCK=new Object();
	}
	public void run()
	{
		while(!stop)
		{
			boolean sort=(getPlayerColoring()||getBackTrackColoring());
			if (sort && backTrackColoring!=null)
			{System.out.println("hi");
				sortArray();
			}
			boolean changed = false;
			for(int i=0;i<coloring.length;i++)
			{
				if (kempeChainStart(i))
				{
					changed=true;
				}
				//System.out.println("     "+(i+1)+": "+coloring[i]);
			}
			if(DEBUG)
			{
				System.out.println("one iteration");
				for(int i=0;i<coloring.length;i++)
				{
					System.out.println("iteration "+(i+1)+": "+coloring[i]);
				}
			}
			if(!changed)
			{
				boolean giveResult=false;
				synchronized(PC_LOCK)
				{
					synchronized(BT_LOCK)
					{
						if(!newPlayerColoring && !newBackTrackColoring)
						{
							giveResult=true;
						}
					}
					if (giveResult)
					{
						for(int i=0;i<coloring.length;i++)
						{
							if (DEBUG)
							{
							System.out.println(coloring[i]);
							}
							result[i]=coloring[i];
						}
						System.out.println("HintCalculator going to sleep");
							
					}
					else
					{
						System.out.println("there is a new graph");
					}
				}
				if(giveResult)
				{
					synchronized(LOCK)
					{
						for(int i=0;i<coloring.length;i++)
						{
							result[i]=coloring[i];
							System.out.println(result[i]);
						}
						try
						{
							LOCK.wait();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
						System.out.println("woked up");
					}
				}
			}
		}
	}
	
	public boolean kempeChainStart(int index)
	{
		int j=0;
		boolean found=false;
		while(!found && j<colorToUse.length)
		{
			if(playerColoring[index]==colorToUse[j])
			{
				found=true;
			}
			j++;
		}
		if (j<rightNumberOfColors && found && playerColoring[index]!=Color.WHITE.getRGB() && coloring[index]!=playerColoring[index])
		{
			FastArray indexChecked = new FastArray(graph.length.length);
			int cItIs=coloring[index];
			int cShouldBe=playerColoring[index];
			if (kempeChain(indexChecked,index,cShouldBe,cItIs))
			{
				for(int i=0;i<indexChecked.length();i++)
				{
					if(coloring[indexChecked.get(i)]==cItIs)
					{
						coloring[indexChecked.get(i)]=cShouldBe;
					}
					else
					{
						coloring[indexChecked.get(i)]=cItIs;
					}
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	public boolean kempeChain(FastArray indexChecked,int index,int cShouldBe,int cItIs)
	{
		indexChecked.add(index);
		boolean doAble=true;
		int i=0;
		while(i<graph.length[index] && doAble)
		{
			//System.out.println(i+" "+graph.length[index]);
			if (!indexChecked.contains(graph.vertex[index][i]))
			{
				if (coloring[graph.vertex[index][i]]==cShouldBe)
				{
					if(coloring[graph.vertex[index][i]]==playerColoring[graph.vertex[index][i]]
						|| !kempeChain(indexChecked,graph.vertex[index][i],cItIs,cShouldBe))
					{
						doAble=false;
					}
				}
			}
			i++;
		}
		return doAble;
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
		synchronized(PC_LOCK)//to check
		{
			if(newPlayerColoring)
			{	
				result=true;
				for(int i=0;i<playerColoring.length;i++)
				{
					playerColoring[i]=tempPlayerColoring[i];
					if(DEBUG){System.out.println("HC parsing player coloring: "+playerColoring[i]);}
				}
				newPlayerColoring=false;
			}
		}
		return result;
	}
	private boolean getBackTrackColoring()
	{
		boolean result=false;
		synchronized(BT_LOCK)//to check
		{
			if(newBackTrackColoring)
			{
				result=true;
				backTrackColoring=new int[graph.length.length];
				for(int i=0;i<backTrackColoring.length;i++)
				{
					backTrackColoring[i]=tempBackTrackColoring[i];
					if(DEBUG){System.out.println("HC parsing backtrack coloring: "+backTrackColoring[i]);};
				}
				rightNumberOfColors=tempRightNumberOfColors;
				newBackTrackColoring=false;
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
			synchronized(LOCK)
			{
				LOCK.notify();
			}
		}
	}
	public void setBackTrackColoring(int[] a,int nColor)
	{
		synchronized(BT_LOCK)
		{
			for(int i=0;i<tempBackTrackColoring.length;i++)
			{
				if (DEBUG){System.out.println("HC got backTrack coloring "+(i+1)+": "+a[i]);}
				tempBackTrackColoring[i]=a[i];
			}
			if (DEBUG){System.out.println("HC got backTrack number of colors="+nColor);}
			tempRightNumberOfColors=nColor;
			newBackTrackColoring=true;
			synchronized(LOCK)
			{
				LOCK.notify();
			}
		}
	}
	public int getNumber()
	{
		int result;
		synchronized(BT_LOCK)
		{
			result=rightNumberOfColors;
		}
		return result;
	}
	public void sortArray()
	{
		System.out.println("hi");
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
		for(int i=0;i<playerColoring.length;i++)
		{
			if(playerColoring[i]!=Color.WHITE.getRGB())
			{
				boolean found=false;
				int j=0;
				while(!found && j<length)
				{
					if(toSort[j].equal(playerColoring[i]))
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
					toSort[length]=new numberOfColor(playerColoring[i]);
					length++;
				}
			}
		}
		for(int i=length;i<toSort.length;i++)
		{
			toSort[i]=new numberOfColor(Color.WHITE.getRGB());
		}
		Arrays.sort(toSort);
		if(DEBUG)
		{
			for(int i=0;i<toSort.length;i++)
			{
				System.out.println("toSort "+toSort[i].quantity+" "+toSort[i].getRGB());
			}
		}
		colorToUse=new int[graph.length.length];
		for(int i=0;i<colorToUse.length;i++)
		{
			colorToUse[i]=toSort[toSort.length-i-1].getRGB();
			if(colorToUse[i]==Color.WHITE.getRGB())
			{
				colorToUse[i]=(int)(Math.random()*(Integer.MIN_VALUE));
			}
		}
		int[] tbc=new int[coloring.length];
		for(int i=0;i<tbc.length;i++)
		{
			tbc[i]=colorToUse[backTrackColoring[i]-1];
		}
		coloring=tbc;
		if(true)
		{
			for(int i=0;i<coloring.length;i++)
			{
				System.out.println("coloring :"+coloring[i]);
			}
			for(int i=0;i<colorToUse.length;i++)
			{
				System.out.println("colorToUse :"+colorToUse[i]);
			}
		}
	}
}