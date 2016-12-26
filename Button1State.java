import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button1State extends Graphic implements Button
{
	protected int goToPage;
	
	protected State state;
	
	public Button1State(
		Input in,
		BufferedImage img,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height,
		int goToPage)
	{
		super(in,img,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height);
		this.goToPage=goToPage;
		
		state=State.idle;
	}
	public boolean run()
	{
		boolean result=false;
		if (state==State.idle)
		{
			if (in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight())
			{
				if (in.mouseLeft())
				{
					state=State.clicked;
				}
				else
				{
					state=State.highlighted;
				}
			}
		}
		else if (state==State.highlighted)
		{
			if (!(in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight()))
			{
				state=State.idle;
			}
			else if(in.mouseLeft())
			{
				state=State.clicked;
			}
		}
		else if(state==State.clicked)
		{
			if (!(in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight()))
			{
				state=State.idle;
			}
			else if (!in.mouseLeft())
			{
				result=true;
				action();
				state=State.highlighted;
			}
			//System.out.println("hi");
		}
		return result;
	}
	public void action()
	{
		PlaySound();
		System.out.println("Entred page");
		in.setPageNumber(goToPage);
	}
	public void PlaySound()
	{
		(new	Sound()).start();
	}
}