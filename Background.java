import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Background implements Drawable
{
	private Input in;
	private BufferedImage img;
	public Background(
		Input in,
		BufferedImage img)
	{
		this.in=in;
		this.img=img;
	}
	public void draw(
		Graphics g)
		{
			int imageWidth;
			int imageHeight;
			int x;
			int y;
			if (in.width()*in.defaultHeight()>in.height()*in.defaultWidth())
			{
				imageWidth=in.width();
				x=0;
				imageHeight=(in.defaultHeight()*in.width())/in.defaultWidth();
				y=(in.height()-imageHeight)/2;
			}
			else
			{
				imageWidth=(in.defaultWidth()*in.height())/in.defaultHeight();
				x=(in.width()-imageWidth)/2;
				imageHeight=in.height();
				y=0;
			}
			g.drawImage(img,x,y,imageWidth,imageHeight,null);
		}
}