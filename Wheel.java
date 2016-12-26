public class Wheel implements SetXY
{
  //Parameters declaration
  private adjGraph graph;
  private int centerVertex;
  private boolean DEBUG = true;

  public Wheel(adjGraph graph, int centerVertex) //position is 0,1,2,etc...
  {
    int[][] vertex = new int[graph.length.length-1][];
    int[] length = new int[graph.length.length-1];
    int pointer =0;
    for(int i=0; i<graph.length.length;i++)
    {
      int[] vertices = new int[vertex.length];
      if(i != centerVertex && graph.vertex[i] !=null)
       {
         int pointer2 =0;
         for (int j=0;j<graph.vertex[i].length;j++)
         {
           if(graph.vertex[i][j]!=centerVertex)
           {
             vertices[pointer2] = graph.vertex[i][j];
             length[pointer] = graph.length[i];
             pointer2++;
            }

         }
         vertex[i] = vertices;
         //if(pointer<length.length)
         pointer++;
       }
    }


    this.graph = new adjGraph(vertex,length);
    this.centerVertex = centerVertex;
    System.out.println("The adjGraph vertex is: " );
    /*for (int i=0;i<length.length;i++)
    {
      for (int j=0;j<vertex.length;j++)
      {
        System.out.print(vertex[i][j] +" ");
      }
      System.out.println();
    }*/
  }

  public  int[][] setXY(int xStart, int yStart, int width, int height)
  {
    //check
    /*for (int i = 0;i<graph.length.length;i++)
    {
      for (int j=0;j<graph.vertex.length;j++)
      {
          System.out.print( graph.vertex[i][j]+ " ");
      }
      System.out.println();
    }*/
    //check
    int LENGTH =0;

    //variable declaration
    int[][] vertexXY = new int[graph.length.length][];
    int radius;
    //Set the radius of the circle
    if( width> height) radius = height /2;
    else radius = width/2;

    int xCenter = xStart + width/2;
    int yCenter = yStart + height/2;
    int counter = 0;

    for(int i=0;i<graph.length.length;i++)
    {
      if(graph.vertex[i] != null) LENGTH++;
    }
    //Give each vertex an x and y and the center vertex
    for(int j =0; j<vertexXY.length;j++)
    {
      int[] xy = new int[2];

      if(graph.vertex[j]==null)
      {
        vertexXY[j] =null;
      }
      else
      {
        if(j == centerVertex)
        {

          xy[0] = xCenter;
          xy[1] = yCenter;
        }
        else
        {
          xy[0] = xCenter + (int)(radius*Math.cos(Math.PI*2*(counter*1.0/LENGTH)+3*Math.PI/2));
          xy[1] = yCenter + (int)(radius*Math.sin(Math.PI*2*(counter*1.0/LENGTH)+3*Math.PI/2));
        }

        vertexXY[j] = xy;
        counter++;
      }

    }
    for(int j=0;j<vertexXY.length;j++)
    {
      System.out.println("The coordinates for the vertex " + j + " is: [" + vertexXY[j][0] + "," + vertexXY[j][1] + "]");
    }
    return vertexXY;
  }

  public int[] vertexOrderingStart()
  {
    int[] orderedVertex=new int[graph.length.length];
    int length=vertexOrdering(orderedVertex,0,-1,0,0,4);
    int[] result = new int[length];
    for(int i=0;i<length;i++)
    {
      result[i]=orderedVertex[i];
    }
    return result;
  }
  public int vertexOrdering(int[] orderedVertex, int index ,int previous,int length,int start,int centerVertex)
  {

    if (!(index==start && previous!=-1))
      {
      orderedVertex[length]=index;
      length++;
      if(graph.vertex[index][0]!=previous)
      {
        return vertexOrdering(orderedVertex,graph.vertex[index][0],index,length,start,centerVertex);
      }
      else
      {
        return vertexOrdering(orderedVertex,graph.vertex[index][1],index,length,start,centerVertex);
      }
      }
      else
      {
        return length;
      }
    }
}
