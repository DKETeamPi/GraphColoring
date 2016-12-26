public class Random implements SetXY
{
	adjGraph graph;
	public Random(adjGraph graph)
	{
		this.graph=graph;
	}
	public int[][] setXY(int xStart, int yStart ,int width, int height)
	{
		int[][] result=new int[graph.length.length][];
		for(int i=0;i<graph.length.length;i++)
		{
			if(graph.vertex[i]==null)
			{
				result[i]=null;
			}
			else
			{
				result[i]=new int [2];
				result[i][0]=(int)(Math.random()*width+xStart);
				result[i][1]=(int)(Math.random()*height+yStart);
			}
		}
		return result;
	}
}