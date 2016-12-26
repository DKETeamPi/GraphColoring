import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Hashtable;

public class GraphStorerMode3 extends GraphStorer
{
	private HintCalculatorMode3 hc;
	private int toColor;
	public GraphStorerMode3()
	{
		super();
		toColor=0;
	}
	public void setHintCalculator()
	{
		hc=new HintCalculatorMode3(graph);
		new Thread(hc).start();
	}
	public void setGraph(Input in,adjGraph graph,int[][] edge)
	{
		super.setGraph(in,graph,edge);
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
			if(toColor==i && !oldMouseRight && in.mouseRight() && Math.pow(v[i].getX()-mouseX,2)+Math.pow(v[i].getY()-mouseY,2)<v[i].r*v[i].r && acceptableColor(i))
			{
				v[i].color=usingColor;
				sendHcColoring=true;
				result=true;
				if(toColor!=graph.length.length)
				{
					toColor++;
				}
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
		if(toColor<v.length)
		{
			int r=(int)(v[toColor].r*1.5*width/in.defaultWidth());
			g.setColor(Color.PINK);
			g.fillOval(v[toColor].getX(width)-r,v[toColor].getY(height)-r,2*r,2*r);
		}
			super.draw(g,width,height);
	}
	private boolean acceptableColor(int index)
	{
		boolean result = true;
		int i=0;
		if(usingColor==Color.WHITE.getRGB())
		{
			result=false;
			in.mjc().showDialog("White is not a color!");
		}
		while(i<graph.length[index] && result)
		{
			if(v[graph.vertex[index][i]].color==usingColor)
			{
				result=false;
				in.mjc().showDialog("Use an other color!");
			}
			i++;
		}
		return result;
	}
	private boolean test()
	{
		System.out.println("hi");
		return true;
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
    	for(int i=0;i<graph.length.length;i++)
    	{
    		if(v[i].color==Color.WHITE.getRGB())
    		{
    			return false;
    		}
    	}
    	toColor=0;
    	return true;
    }
}