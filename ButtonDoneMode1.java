import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ButtonDoneMode1 extends Button3State
{
	public ButtonDoneMode1(
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
		super(in,img1,img2,img3,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height,goToPage);
	}
	public void action()
	{
		if (in.storer().good())
		{
			in.setPageNumber(goToPage);
			in.storer().restore();
		}
		else
		{
			in.mjc().showDialog("You could color this graph with "+in.storer().getNumber()+" colors");
		}
	}
}