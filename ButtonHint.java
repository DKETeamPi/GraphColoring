import java.awt.image.BufferedImage;

public class ButtonHint extends Button3State
{
	public ButtonHint(
		Input in,
		BufferedImage img1,
		BufferedImage img2,
		BufferedImage img3,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height)
	{
		super(in,img1,img2,img3,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height,-1);
	}
	public void action()
	{
		in.storer().showHintColoring();
	}
}