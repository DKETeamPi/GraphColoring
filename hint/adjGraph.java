public class adjGraph
{
	public int[][] vertex;
	public int[] length;
	public adjGraph()
	{
		this.vertex=new int[0][0];
		this.length=new int[0];
	}
	public adjGraph(int[][] vertex,int[] length)
	{
		this.vertex=vertex;
		this.length=length;
	}
	public adjGraph clone()
	{
		int[][] vertex2=new int[vertex.length][];
		int[] length2=new int[length.length];
		for(int i=0;i<length.length;i++)
		{
			vertex2[i]=new int[vertex[i].length];
			for(int j=0;j<vertex[i].length;j++)
			{
				vertex2[i][j]=vertex[i][j];
			}
			length2[i]=length[i];
		}
		return new adjGraph(vertex2,length2);
	}
}