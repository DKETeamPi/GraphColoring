public class HCTester
{
	public static void main(String[] args)
	{
		int[][] vertex ={{1,3},{0,3,2},{1,3},{0,1,2}};
		int[] length={2,3,2,3};
		adjGraph graph =new adjGraph(vertex,length);
		int[] coloring ={1,2,1,3};
		int[] PlayerColoring ={1,2,3,1};
		HintCalculator hc=new HintCalculator(null,graph);
		hc.setPlayerColoring(PlayerColoring);
		hc.setBackTrackColoring(coloring,3);
		hc.run();
	}
}