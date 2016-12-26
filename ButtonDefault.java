import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ButtonDefault implements Button
{
	private BufferedImage img;
	private BufferedImage img2;
	private	int x;
	private int y;
	private int width;
	private int height;
	private int goToPage;
	
	public ButtonDefault(
		BufferedImage img,
		BufferedImage img2,
		int x,
		int y,		
		int width,
		int height,
		int goToPage)
	{
		this.img=img;
		this.img2=img2;
		this.x=x;
		this.y=y;
		this.goToPage=goToPage;
		this.width=width;                                                   
		this.height=height;
	}
	
	public void draw(
		Graphics g,
		int defaultWidth,
		int defaultHeight,
		int actualWidth,
		int actualHeight,
		int mouseX,
		int mouseY,
		double scaleingFactor)
	{
		int x1=(x*actualWidth)/defaultWidth-(int)((width*scaleingFactor)/2);
		int y1=(y*actualHeight)/defaultHeight-(int)((height*scaleingFactor)/2);
		
		if (mouseX>(x1) && 
			mouseX<(x1+(int)(width*scaleingFactor)) &&
			mouseY>(y1) &&
			mouseY<(y1+(int)(height*scaleingFactor)))
		{
			g.drawImage(img2,
				x1,
				y1,
				(int)(width*scaleingFactor),
				(int)(height*scaleingFactor),
				null);   
		}
		else
		{
			g.drawImage(img,
				x1,
				y1,
				(int)(width*scaleingFactor),
				(int)(height*scaleingFactor),
				null);   
		}
	}
	public boolean hitbox(
		int defaultWidth,
		int defaultHeight,
		int actualWidth,
		int actualHeight,
		int mouseX,
		int mouseY,
		double scaleingFactor)
	{
		int x1=(x*actualWidth)/defaultWidth-(int)((width*scaleingFactor)/2);
		int y1=(y*actualHeight)/defaultHeight-(int)((height*scaleingFactor)/2);
		
		return (mouseX>(x1) && 
			mouseX<(x1+(int)(width*scaleingFactor)) &&
			mouseY>(y1) &&
			mouseY<(y1+(int)(height*scaleingFactor)));
	}
	public int getGoToPage()
	{
		return goToPage;
	}
	
}