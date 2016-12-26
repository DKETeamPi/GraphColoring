public class TesterDepth
{
    public static void main(String[] args)
    {
    	int[][] vertex ={{1,2},{0,2},{1,0},{4,5},{3,5},{4,3}};
        int[] length = {2, 2, 2, 2, 2, 2};
        adjGraph[] graphList = GraphStorer.divideGraph(new adjGraph(vertex,length));
        for (int i = 0; i < graphList.length; i++)
        {
            for (int j = 0;j < graphList[i].length.length; j++)
            {
                for (int k = 0;k < graphList[i].length[j]; k++)
                {
                    System.out.print(graphList[i].vertex[j][k]+" ");
                }
                if (graphList[i].length[j]==0)
                {
                	System.out.print("null");
                }
                System.out.println();
            }
            System.out.println("new graph");
        }

    }
}