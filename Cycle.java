public class Cycle implements SetXY
{
	    //Parameters declaration
  private adjGraph graph;

  public  Cycle(adjGraph graph) //position is 0,1,2,etc...
  {
    this.graph = graph;
  }

  public  int[][] setXY(int xStart, int yStart, int width, int height)
  {
    //variable declaration
    int[] orderedVertex = vertexOrderingStart();
    int LENGTH = orderedVertex.length;
    int[][] vertexXY = new int[graph.length.length][];
    int radius;
    //Set the radius of the circle
    if( width> height) radius = height /2;
    else radius = width/2;

    //Set the x and y of the center
    int xCenter = xStart + width/2;
    int yCenter = yStart + height/2;
    //Give each vertex an x and y


    for(int j =0; j<LENGTH;j++)
    {
      int[] xy = new int[2];
      xy[0] = xCenter + (int)(radius*Math.cos(Math.PI*2*(j*1.0/LENGTH)+3*Math.PI/2));
      xy[1] = yCenter + (int)(radius*Math.sin(Math.PI*2*(j*1.0/LENGTH)+3*Math.PI/2));
      vertexXY[orderedVertex[j]] = xy;
    }
            return vertexXY;
    }

  public int[] vertexOrderingStart()
  {
  	int[] orderedVertex=new int[graph.length.length];
  	int start=0;
  	while(graph.vertex[start]==null)
  	{
  		start++;
  	}
  	int length=vertexOrdering(orderedVertex,start,-1,0,start);
  	int[] result = new int[length];
  	for(int i=0;i<length;i++)
  	{
  		result[i]=orderedVertex[i];
  	}
  	return result;
  }
  public int vertexOrdering(int[] orderedVertex, int index ,int previous,int length,int start)
  {
  	if (!(index==start && previous!=-1))
  	{
		orderedVertex[length]=index;
		length++;
		if(graph.vertex[index][0]!=previous)
		{
			return vertexOrdering(orderedVertex,graph.vertex[index][0],index,length,start);
		}
		else
		{
			return vertexOrdering(orderedVertex,graph.vertex[index][1],index,length,start);
		}
  	}
  	else
  	{
  		return length;
  	}
  }
}
