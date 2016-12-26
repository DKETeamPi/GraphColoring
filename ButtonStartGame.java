import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ButtonStartGame extends Button3State
{
	boolean drawDialog;
	public ButtonStartGame(
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
	public boolean run()
	{
		return (drawDialog||super.run());
	}
	public void action()
	{
		if(!in.storer().getIsEmpty())
		{
			in.setPageNumber(goToPage);
		}
		else
		{
			in.mjc().showDialog("Chose a Graph");
		}
	}
}