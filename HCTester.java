import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.*;

public class HCTester
{
	//private AtomicBoolean a=new AtomicBoolean(false);
	//private int[] ciao=new int[0];
	public static void main(String[] args)
	{
		new HCTester(args);
	}
	public HCTester(String[] args)
	{
		int[][] vector={{1,2,4},{0,2},{0,1,4,3},{2,4},{0,2,3}};
		int[] length={3,2,4,2,3};
		adjGraph graph=new adjGraph(vector,length);//ReadGraphReverseIndex.main(args);
		HintCalculator hc = new HintCalculator(null,graph.clone());
		backTrack bt = new backTrack(hc,graph.clone());
		int[] playerColoring={1,1,5,3,3};//{2,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		(new Thread(bt)).start();
		(new Thread(hc)).start();
		hc.setPlayerColoring(playerColoring);
	}
}