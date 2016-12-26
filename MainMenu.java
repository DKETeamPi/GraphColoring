import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class MainMenu implements Page
{
	private Input in;
	private Drawable[] drawable;
	private Button[] button;
	
	public MainMenu(Input in,Drawable[] drawable,Button[] button) throws IOException
	{
		this.in=in;
		this.drawable=drawable;
		this.button=button;
	}
	public boolean run()
	{
		boolean result = false;
		for(int i=0;i<button.length;i++)
		{
			if(button[i].run())
			{
				result=true;
			}
		}
		return result;
	}
	public void draw(Graphics g)
	{
		for(int i=0;i<drawable.length;i++)
		{
			drawable[i].draw(g);
		}
		for(int i=0;i<button.length;i++)
		{
			button[i].draw(g);
		}
	}
	
}