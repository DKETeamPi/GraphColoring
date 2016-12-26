import java.util.concurrent.atomic.AtomicBoolean;

public class tester
{
	//private AtomicBoolean a=new AtomicBoolean(false);
	//private int[] ciao=new int[0];
	public static void main(String[] args)
	{
		new tester(args);
	}
	public tester(String[] args)
	{
		int[][] vector={{1,2},{0,3,2},{0,1,3},{1,2}};
		int[] length={2,3,3,2};
		adjGraph graph=new adjGraph(vector,length);//ReadGraphReverseIndex.main(args);
		HintCalculator hc = new HintCalculator(this,graph.clone());
		backTrack bt = new backTrack(hc,graph.clone());
		int[] playerColoring={2,3,0,0};//{2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		(new Thread(bt)).start();
		(new Thread(hc)).start();
		hc.setPlayerColoring(playerColoring);
	}
}