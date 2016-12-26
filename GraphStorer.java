import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Hashtable;
public class GraphStorer
{
	private boolean DEBUG=true;
	
	protected Input in;
	protected adjGraph graph;
	public Vertex[] v;
	protected boolean isEmpty;
	protected boolean oldMouseMiddle=false;
	protected boolean oldMouseRight=false;
	protected int click;
	protected int usingColor;
	protected int OldMouseX;
	protected int OldMouseY;
	protected int [][] edge;
	protected HintCalculator hc;
	protected backTrack bt;
	protected int[] hintColoring=null;
	protected class Vertex
	{
		public int color;
		int[] position;
		public State state;
		public int r;
		public Vertex(int[] position)
		{
			this.position=position;
			color=Color.WHITE.getRGB();
			state=State.idle;
			int r=10;
		}
		public int getX(int width)
		{
			return (int)(position[0]*(width*1.0/in.defaultWidth()));
		}
		public int getY(int height)
		{
			return (int)(position[1]*(height*1.0/in.defaultHeight()));
		}
		public int getX()
		{
			return position[0];
		}
		public int getY()
		{
			return position[1];
		}
		public void addX(int a)
		{
			position[0]+=a;
		}
		public void addY(int a)
		{
			position[1]+=a;
		}
		public void setX(int a)
		{
			position[0]=a;
		}
		public void setY(int a)
		{
			position[1]=a;
		}
	}
	public GraphStorer()
	{
		restore();
	}
	public void restore()
	{
		in=null;
		graph=null;
		v=new Vertex[0];
		isEmpty=true;
		click=-1;
		usingColor=Color.WHITE.getRGB();
		OldMouseX=0;
		OldMouseY=0;
		edge=new int[0][0];
		if(hc != null)
		{
			hc.stop=true;
		}
		if(bt != null)
		{
			bt.stop=true;
		}
	}
	public void setHintCalculator()
	{
		hc=new HintCalculator(graph);
		bt=new backTrack(hc,graph);
		new Thread(bt).start();
		new Thread(hc).start();
	}
	public void setGraph(Input in,adjGraph graph,int[][] edge)
	{
		this.in=in;
		this.graph=graph;
		v=new Vertex[graph.length.length];
		isEmpty=false;
		click=-1;
		this.edge=edge;
		
		setHintCalculator();
		
		v=new Vertex[graph.length.length];
		adjGraph[] multipleGraph=divideGraph(graph);
		int [][][] tempVertexPosition=new int[multipleGraph.length][][];
		for(int i=0;i<multipleGraph.length;i++)
		{
			tempVertexPosition[i]=reconizeGraph(multipleGraph[i],i,(int)(in.defaultWidth()/multipleGraph.length));
		}
		for(int i=0;i<graph.length.length;i++)
		{
			int j=0;
			while(j<tempVertexPosition.length && tempVertexPosition[j][i]==null)
			{
				j++;
			}
			v[i]=new Vertex(tempVertexPosition[j][i]);
			v[i].r=10;
		}
	}
	public boolean run(int mouseX,int mouseY)
	{
		boolean result=false;
		boolean sendHcColoring=false;
		for(int i=graph.length.length-1;i>=0;i--)
		{
			if(!oldMouseMiddle && in.mouseMiddle() && Math.pow(v[i].getX()-mouseX,2)+Math.pow(v[i].getY()-mouseY,2)<v[i].r*v[i].r)
			{
				usingColor=v[i].color;
				oldMouseMiddle=true;
				result=true;
			}
			if(!oldMouseRight && in.mouseRight() && Math.pow(v[i].getX()-mouseX,2)+Math.pow(v[i].getY()-mouseY,2)<v[i].r*v[i].r)
			{
				v[i].color=usingColor;
				sendHcColoring=true;
				oldMouseRight=true;
				result=true;
			}
			if (v[i].state==State.idle)
			{
				if(Math.pow(v[i].getX()-mouseX,2)+Math.pow(v[i].getY()-mouseY,2)<v[i].r*v[i].r && !in.mouseLeft())
				{
					v[i].state=State.highlighted;
				}
			}
			else if (v[i].state==State.highlighted)
			{                                                                                                                      
				if(click==-1 && in.mouseLeft())
				{
					v[i].state=State.clicked;
					click=i;
					result=true;
				}
				else if(Math.pow(v[i].getX()-mouseX,2)+Math.pow(v[i].getY()-mouseY,2)>=v[i].r*v[i].r)
				{
					v[i].state=State.idle;
				}
			}
			else if (v[i].state==State.clicked)
			{
				v[i].addX(mouseX-OldMouseX);
				v[i].addY(mouseY-OldMouseY);
				if (!in.mouseLeft())
				{
					v[i].state=State.highlighted;
					click=-1;
				}
				result=true;
			}
			if(v[i].getX()>in.defaultWidth())
			{
				v[i].setX(in.defaultWidth());
			}
			else if(v[i].getX()<0)
			{
				v[i].setX(0);
			}	
			if (v[i].getY()>in.defaultHeight())
			{
				v[i].setY(in.defaultHeight());
			}
			else if(v[i].getY()<0)
			{
				v[i].setY(0);
			}
		}
		if(!oldMouseMiddle && in.mouseMiddle() && mouseX>=0 && mouseX<=in.defaultWidth() && mouseY>=0 && mouseY<=in.defaultHeight())
			{
				usingColor=Color.WHITE.getRGB();
				oldMouseMiddle=true;
				result=true;
			}
		oldMouseMiddle=in.mouseMiddle();
		oldMouseRight=in.mouseRight();
		OldMouseX=mouseX;
		OldMouseY=mouseY;
		if(sendHcColoring)
		{
			int[] toSend =new int[graph.length.length];
			for(int i=0;i<graph.length.length;i++)
			{
				toSend[i]=v[i].color;
			}
			hc.setPlayerColoring(toSend);
		}
		return result;
	}
	public void draw(Graphics g,int width,int height)
	{
		class e
		{
			public int[] edge;
			public e(int[] edge)
			{
				this.edge=edge;
			}
		}
		ArrayList<e> edgeToDrawAfter=new ArrayList();
		boolean[] adc=new boolean[graph.length.length];
		//idle
		for(int i=0;i<edge.length;i++)
		{
			if(v[edge[i][0]].state!=State.clicked && v[edge[i][1]].state!=State.clicked &&(v[edge[i][0]].color==Color.WHITE.getRGB() || v[edge[i][0]].color!=v[edge[i][1]].color))
			{
				g.setColor(Color.GRAY);
				drawEdge(edge[i],g,width,height,1);
				adc[edge[i][0]]=true;
				adc[edge[i][1]]=true;
			}
		}
		for(int i=0;i<v.length;i++)
		{
			if (adc[i])
			{
				g.setColor(Color.GRAY);
				drawVertex(v[i],i,g,width,height,0.1);
			}
		}
		//error
		adc=new boolean[graph.length.length];
		for(int i=0;i<edge.length;i++)
		{
			if(v[edge[i][0]].color!=Color.WHITE.getRGB() && v[edge[i][0]].color==v[edge[i][1]].color)
			{
				g.setColor(Color.RED);
				drawEdge(edge[i],g,width,height,2);
				adc[edge[i][0]]=true;
				adc[edge[i][1]]=true;
			}
		}
		for(int i=0;i<v.length;i++)
		{
			if (adc[i])
			{
				g.setColor(Color.RED);
				drawVertex(v[i],i,g,width,height,0.2);
			}
		}
		//highlited
		adc=new boolean[graph.length.length];
		for(int i=0;i<edge.length;i++)
		{
			if(v[edge[i][0]].state==State.clicked || v[edge[i][1]].state==State.clicked)
			{
				g.setColor(Color.BLACK);
				drawEdge(edge[i],g,width,height,3);
				adc[edge[i][0]]=true;
				adc[edge[i][1]]=true;
			}
		}
		for(int i=0;i<v.length;i++)
		{
			if (adc[i])
			{
				g.setColor(Color.BLACK);
				drawVertex(v[i],i,g,width,height,0.3);
			}
		}
	}
	public void drawEdge(int[] edge,Graphics g,int width,int height,int thickness)
	{
		Graphics2D g2 =(Graphics2D) g;
		g2.setStroke(new BasicStroke(thickness));
		g2.draw(new Line2D.Float(v[edge[0]].getX(width),v[edge[0]].getY(height),v[edge[1]].getX(width),v[edge[1]].getY(height)));
	}
	public void drawVertex(Vertex v,int i,Graphics g,int width,int height,double rScale)
	{
		int r=(int)(v.r*1.0*width/in.defaultWidth());
		int dr=(int)(r*rScale);
		if (dr<2)
		{
			dr=2;
		}
		
		g.fillOval(v.getX(width)-(r+dr),v.getY(height)-(r+dr),2*(r+dr),2*(r+dr));
		g.setColor(new Color(v.color));
		g.fillOval(v.getX(width)-r,v.getY(height)-r,2*r,2*r);
		if(hintColoring!=null)
		{
			dr=(int)(r*0.5);
			g.setColor(new Color(hintColoring[i]));
			g.fillOval(v.getX(width)-(r-dr),v.getY(height)-(r-dr),2*(r-dr),2*(r-dr));
		}
	}
	public void changeR(int dr)
	{
		if(click==-1)
		{
			for(int i=0;i<v.length;i++)
			{
				v[i].r+=dr;
				if (v[i].r<0)
				{
					v[i].r=0;
				}
			}
		}
		else
		{
			v[click].r+=dr;
		}
	}
	public boolean getIsEmpty()
	{
		return isEmpty;
	}
	public int getUsingColor()
	{
		return usingColor;
	}
	public void setUsingColor(int i)
	{
		usingColor=i;
	}
	public int[][] reconizeGraph(adjGraph graph,int j,int width)
	{
		boolean isCircle=true;
		boolean isWheel=true;
		boolean isStar=true;
		boolean isComplete=true;
		boolean hasACenter=false;
		int numberVertices =0;
		for(int i=0;i<graph.length.length;i++)
		{
			if (graph.vertex[i]!=null)
			{
				numberVertices+=1;
			}
		}
		int centerIndex=0;
		for(int i=0;i<graph.length.length;i++)
		{
			if(graph.length[i]!=3 && graph.length[i]!=numberVertices-1 )
				{
					isWheel=false;
				}
				if(graph.length[i]!=1 && graph.length[i]!=numberVertices-1)
				{
					isStar=false;
				}
			if(graph.vertex[i]!=null)
			{
				if(graph.length[i]!=2)
				{
					isCircle=false;
				}
				if(graph.length[i]!=numberVertices-1)
				{
					isComplete=false;
				}
				else if(!hasACenter)
				{
					hasACenter=true;
					centerIndex=i;
				}
				else
				{
					isStar=false;
					isWheel=false;
				}
			}
		}
		SetXY result;
		if(isCircle)
		{
			//is a colplete graph add to 
			System.out.println("it is a circle");
			result=(new Cycle(graph));
		}
		else if(isStar && hasACenter)
		{
			System.out.println("it is a star");
			// is a star
			result=new Star(graph,centerIndex);
		}
		else if(isComplete)
		{
			System.out.println("it is a complete");
			result=new Complete(graph);
		}
		else if(isWheel && hasACenter)
		{
			//is a wheel;
			System.out.println("it is a wheel");
			result=new Wheel(graph,centerIndex);
		}
		else
		{
			System.out.println("it is a random");
			//is an other graph
			result=new Random(graph);
		}
	return result.setXY(j*width,0,width,in.defaultHeight());
	}
	public static void paintVertices(adjGraph graph,int[] colors,int start, int color)
    {
        colors[start] = color;
        for (int i = 0; i<graph.length[start]; i++)
        {
            if (colors[graph.vertex[start][i]] == 0)
            {
                paintVertices(graph,colors,graph.vertex[start][i], color);
            }
        }
    }
    public static adjGraph[] divideGraph(adjGraph graph)
    {
        int[] colors = new int[graph.length.length];
        int color = 1;
        for (int i = 0; i < graph.length.length; i++)
        {
            if (colors[i] == 0)
            {
                paintVertices(graph,colors,i, color);
                color++;
            }
        }

        adjGraph[] newGraph = new adjGraph[color-1];
        for ( int i = 1; i<color; i++)
        {
            int[][] newVertices = new int[graph.length.length][];
            int[] newLength = new int[graph.length.length];
            for (int j = 0; j < graph.length.length; j++)
            {
                if (colors[j] == i) 
                {
                    newVertices[j] = graph.vertex[j];
                    newLength[j] = graph.length[j];
                }
                else
                {
                    newVertices[j]=null;
                    newLength[j]=0;
                }
            }
            newGraph[i - 1] = new adjGraph(newVertices, newLength);
        }
        return newGraph;
    }
    public void showHintColoring()
    {
    	class hint implements ActionListener
    	{
    		public void actionPerformed(ActionEvent e) 
    			{
    				hintColoring=null;
    				in.frame().repaint();
    			}
    	}
    	hintColoring=hc.getResult();
    	Timer timer = new Timer(3000,new hint());
    	timer.setRepeats(false);
    	timer.start();
    }
    public boolean good()
    {
    	int counter=0;
    	int cromaticNumber=hc.getNumber();
    	Hashtable<Integer,Object> hashTable=new Hashtable();
    	int i=0;
    	System.out.println("testing");
    	while(i<v.length && counter<=cromaticNumber)
    	{
    		for(int j=0;j<graph.length[i];j++)
    		{
    		if(v[i].color==v[graph.vertex[i][j]].color)
    			{
    				return false;
    			}
    		}
    		if(v[i].color==Color.WHITE.getRGB())
    		{
    			return false;
    		}
    		if(hashTable.get(v[i].color)==null)
    		{
    			counter++;
    			hashTable.put(v[i].color,new Object());
    		}
    		i++;
    	}
    	return (counter<=cromaticNumber);
    }
    public boolean allColored()
    {
    	for(int i=0;i<v.length;i++)
    	{
    		if(v[i].color==Color.WHITE.getRGB())
    		{
    			return false;
    		}
    	}
    	return true;
    }
    public int getNumber()
    {
    	return hc.getNumber();
    }
}