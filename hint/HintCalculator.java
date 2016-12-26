import java.util.*;

public class HintCalculator implements Runnable
{
	private static boolean DEBUG = true;
	private int[] coloring;//HC coloring
	private int[] playerColoring;//player Coloring
	private int rightNumberOfColors;//right number of colors
	private int[] tempPlayerColoring;
	private int[] tempBackTrackColoring;
	private int tempRightNumberOfColors;
	private int [] result;
	private boolean newPlayerColoring;
	private boolean newBackTrackColoring;
	private adjGraph graph;
	final private Object LOCK=new Object();
	final private Object PC_LOCK=new Object();
	final private Object BT_LOCK=new Object();
	//final private Object RESULT_LOCK=new Object();
	HintCalculator(tester test,adjGraph graph)
	{
		this.graph=graph;
		coloring = new int[graph.length.length];
		playerColoring = new int[graph.length.length];
		rightNumberOfColors=graph.length.length;
		tempPlayerColoring =new int[graph.length.length];
		tempBackTrackColoring =new int[graph.length.length];
		tempRightNumberOfColors=graph.length.length;
		result=new int[graph.length.length];
		newPlayerColoring=false;
		newBackTrackColoring=false;
		//LOCK=new Object();
	}
	public void run()
	{
		while(true)
		{
			getPlayerColoring();
			getBackTrackColoring();
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
				int[] result = new int[coloring.length];
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
				synchronized(LOCK)
				{
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
	
	public boolean kempeChainStart(int index)
	{
		if (playerColoring[index]!=0 && coloring[index]!=playerColoring[index] && playerColoring[index]<=rightNumberOfColors)
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
	private int[] getResult()
	{
		return this.result;
	}
	private void setResult(int[] result)
	{
		this.result=result;
	}
	private void getPlayerColoring()
	{
		synchronized(PC_LOCK)//to check
		{
			if(newPlayerColoring)
			{	
				for(int i=0;i<playerColoring.length;i++)
				{
					playerColoring[i]=tempPlayerColoring[i];
				}
				newPlayerColoring=false;
			}
		}
	}
	private void getBackTrackColoring()
	{
		synchronized(BT_LOCK)//to check
		{
			if(newBackTrackColoring)
			{
				for(int i=0;i<coloring.length;i++)
				{
					coloring[i]=tempBackTrackColoring[i];
				}
				rightNumberOfColors=tempRightNumberOfColors;
				newBackTrackColoring=false;
			}
		}
	}
	public void setPlayerColoring(int[] a)
	{
		synchronized(PC_LOCK)
		{
			for(int i=0;i<tempPlayerColoring.length;i++)
			{
				if (DEBUG){System.out.println("HC got player coloring "+(i+1)+": "+a[i]);}
				tempPlayerColoring[i]=a[i];
			}
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
}