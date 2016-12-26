import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button3State extends Button1State implements Button
{
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img3;
	
	public Button3State(
		Input in,
		BufferedImage img1,
		BufferedImage img2,
		BufferedImage img3,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height,
		int goToPage)
	{
		super(in,null,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height,goToPage);
		setImg(img1);
		this.img1=img1;
		this.img2=img2;
		this.img3=img3;
		state=State.idle;
	}
	public boolean run()
	{
		if (state==State.idle)
		{
			if (in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight())
			{
				if (in.mouseLeft())
				{
					state=State.clicked;
					setImg(img3);
					return true;
				}
				else
				{
					state=State.highlighted;
					setImg(img2);
					return true;
				}
			}
		}
		else if (state==State.highlighted)
		{
			if (!(in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight()))
			{
				state=State.idle;
				setImg(img1);
				return true;
			}
			else if(in.mouseLeft())
			{
				state=State.clicked;
				setImg(img3);
				return true;
			}
		}
		else if(state==State.clicked)
		{
			if (!(in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight()))
			{
				state=State.idle;
				setImg(img1);
				return true;
			}
			else if (!in.mouseLeft())
			{
				action();
				state=State.idle;
				setImg(img1);
				return true;
			}
			//System.out.println("hi");
		}
		return false;
	}
}