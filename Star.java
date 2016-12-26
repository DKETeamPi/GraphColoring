public class Star implements SetXY
{
  //Parameters declaration
private adjGraph graph;
private int centerVertex;

public  Star(adjGraph graph, int centerVertex) //position is 0,1,2,etc...
{
this.graph = graph;
this.centerVertex = centerVertex;
}

public  int[][] setXY(int xStart, int yStart, int width, int height)
{
  int LENGTH = graph.length.length;

  //variable declaration
  int[][] vertexXY = new int[LENGTH][];
  int radius;
  //Set the radius of the circle
  if( width> height) radius = height /2;
  else radius = width/2;

  int xCenter = xStart + width/2;
  int yCenter = yStart + height/2;
  int counter =0;
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
        xy[0] = xCenter + (int)(radius*Math.cos(Math.PI*2*(counter*1.0/(LENGTH-1))+3*Math.PI/2));
        xy[1] = yCenter + (int)(radius*Math.sin(Math.PI*2*(counter*1.0/(LENGTH-1))+3*Math.PI/2));
        counter++;
      }

      vertexXY[j] = xy;
    }

  }

  for(int j=0;j<vertexXY.length;j++)
  {
    System.out.println("xy: "+vertexXY[j]);
  }
          return vertexXY;
  }


}
