import java.awt.*;

public class ButtonColorStorer extends Button1State
{
	private int color = Color.WHITE.getRGB();
	private boolean oldMiddle=false;
	private boolean oldRight=false;
	ButtonColorStorer(
		Input in,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height)
	{
		super(in,null,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height,0);
	}
	public void draw(Graphics g)
	{
		g.setColor(new Color(color));
		g.fillRect(getX1(),getY1(),(int)(width*in.getScale()),(int)(height*in.getScale()));
		g.setColor(Color.BLACK);
		g.drawRect(getX1(),getY1(),(int)(width*in.getScale()),(int)(height*in.getScale()));
	}
	public boolean run()
	{
		boolean result=false;
		if (state==State.idle)
		{
			if (in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight())
			{
				state=State.highlighted;
			}
		}
		
		if (state==State.highlighted)
		{
			if (!(in.mouseX()>getX1() && in.mouseX()<getX1()+getWidth() && in.mouseY()>getY1() && in.mouseY()<getY1()+getHeight()))
			{
				state=State.idle;
			}
			if(!in.mouseRight() && oldRight)
			{
				color=in.storer().getUsingColor();
				result=true;
			}
			if(!in.mouseMiddle() && oldMiddle)
			{
				in.storer().setUsingColor(color);
				result=true;
			}
		}
		oldMiddle=in.mouseMiddle();
		oldRight=in.mouseRight();
		return result;
	}
}