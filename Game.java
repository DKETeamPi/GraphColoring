import java.awt.*;

public class Game implements Page
{
	private static boolean DEBUG=true;
	private Input in;
	private GraphStorer storer;
	private Button[] buttons;
	private Drawable[] drawables;
	private Button doneButton;
	public Game(Input in,Drawable[] drawables,Button[] buttons)
	{
		this.in=in;
		this.storer=storer;
		this.drawables = drawables;
		this.buttons=buttons;
	}
	public boolean run()
	{
		boolean result=false;
		for(int i=0;i<buttons.length;i++)
		{
			if(buttons[i].run())
			{
				result=true;
			}
		}
		return (result||doneButton.run());//result;
	}
	public void draw(Graphics g)
	{
		for(int i=0;i<drawables.length;i++)
		{
			drawables[i].draw(g);
		}
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i].draw(g);
		}
		doneButton.draw(g);
	}
	public void setDoneButton(Button doneButton)
	{
		this.doneButton=doneButton;
	}
}
