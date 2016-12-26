import java.awt.*;

public class Vertex implements Button
{
	private int x;
	private int y;
	private int r;
	private State state;
	private int OldMouseX=-1;
	private int OldMouseY=-1;
	private int index;
	private Input in;
	private GraphStorer storer;
	int defaultWidth;
	int defaultHeight;
	public Vertex(Input in,int x,int y,int index)
	{
		this.in=in;
		this.storer=storer;
		this.x=x;
		this.y=y;
		this.index=index;
		this.state=State.idle;
		this.defaultWidth=defaultWidth;
		this.defaultHeight=defaultHeight;
	}
	public boolean run()
	{
		int x=(int)(this.x*getScale());
		int y=(int)(this.y*getScale());
		if (state==State.idle)
		{
			if (x<0)
				{
					x=0;
				}
				if (y<0)
				{
					y=0;
				}
				if(x>in.width())
				{
					x=in.width();
				}
				if(y>in.height())
				{
					y=in.height();
				}
			if((in.mouseX()-x)*(in.mouseX()-x)+(in.mouseY()-y)*(in.mouseY()-y)<r*r && in.mouseLeft())
			{
				state=State.clicked;
				OldMouseX=in.mouseX();
				OldMouseY=in.mouseY();
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (state==State.clicked)
		{
			if(!in.mouseLeft())
			{
				state=State.idle;
				return true;
			}
			else
			{
				x+=in.mouseX()-OldMouseX;
				y+=in.mouseY()-OldMouseY;
				OldMouseX=in.mouseX();
				OldMouseY=in.mouseY();
				if (x<0)
				{
					x=0;
				}
				if (y<0)
				{
					y=0;
				}
				if(x>in.width())
				{
					x=in.width();
				}
				if(y>in.height())
				{
					y=in.height();
				}
				return true;
			}
		}
		return false;
	}
	public void draw(Graphics g)
	{
		int x=(int)(this.x*getScale());
		int y=(int)(this.y*getScale());
		int c=(int)(r*0.1);
		if(state==State.idle)
		{
			g.setColor(Color.BLACK);
		}
		else if(state==State.clicked)
		{
			g.setColor(Color.BLUE);
		}
		g.fillOval(x-r-c,y-r-c,2*(r+c),2*(r+c));
		g.setColor(storer.getColor(index));
		g.fillOval(x-r,y-r,2*r,2*r);
		
	}
	private double getScale()
	{
		if (in.width()*in.defaultHeight()>in.height()*in.defaultWidth())//if window wide
		{
			return in.height()*1.0/in.defaultHeight();
		}
		else
		{
			return in.width()*1.0/in.defaultWidth();
		}
	}
}