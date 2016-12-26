import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Graphic implements Drawable
{
	public Input in;
	protected BufferedImage img;
	private	double xAncorScale;
	private double yAncorScale;
	private int xOffSet;
	private int yOffSet;
	protected int width;
	protected int height;
	
	public Graphic
	(
		Input in,
		BufferedImage img,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height)
	{
		this.in=in;
		this.img=img;
		this.xAncorScale=xAncorScale;
		this.yAncorScale=yAncorScale;
		this.xOffSet=xOffSet;
		this.yOffSet=yOffSet;
		this.width=width;                                                   
		this.height=height;
	}
	
	public void draw(
		Graphics g)
	{
		g.drawImage(
			img,
			getX1(),
			getY1(),
			(int)(width*getScale()),
			(int)(height*getScale()),
			null);                             
	}
	public void setImg(BufferedImage img)
	{
		this.img=img;
	}
	protected double getScale()
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
	public int getX1()
	{
		return (int)(in.width()*xAncorScale+(xOffSet-width/2)*getScale());
	}
	public int getY1()
	{
		return (int)(in.height()*yAncorScale+(yOffSet-height/2)*getScale());
	}
	public int getHeight()
	{
		return (int)(height*getScale());
	}
	public int getWidth()
	{
		return (int)(width*getScale());
	}
}