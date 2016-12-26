import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.event.*;

public class ButtonDoneMode2 extends Button3State
{
	boolean sleeping=true;
	int timer=0;
	Timer timerEvent;
	BufferedImage[][] numbers;
	public ButtonDoneMode2(
		Input in,
		BufferedImage img1,
		BufferedImage img2,
		BufferedImage[][] numbers,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height,
		int goToPage)
	{
		super(in,img1,img2,img2,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height,goToPage);
		this.numbers=numbers;
	}
	public boolean run()
	{
		boolean result=false;
		if(sleeping)
		{
			result=true;
			sleeping=false;
			timer=in.storer().v.length*10;
			timerEvent=null;
			class timeDown implements ActionListener
			{
				public void actionPerformed(ActionEvent e) 
				{
					System.out.println(timer);
					timer-=1;
					in.frame().repaint();
					if (timer==0)
					{
						in.setPageNumber(4);
						timerEvent.stop();
					}
					
				}
			}
			timerEvent = new Timer(1000,new timeDown());
			timerEvent.setRepeats(true);
			timerEvent.start();
		}
		return (result||super.run());
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
	public void draw(Graphics g)
	{
		super.draw(g);
		int numberHeight=50;
		int numberWidth=48;
		String str=""+timer;
		char[] cha=str.toCharArray();
		int[] timeToDraw=new int[cha.length];
		for(int i=0;i<cha.length;i++)
		{
			timeToDraw[i]=Character.getNumericValue(cha[i]);
		}
		System.out.println(timeToDraw.length+" ciaoo");
		int initialX1=(int)(getX1()+(getWidth()-timeToDraw.length*numberWidth*getScale())/2);
		for(int i=0;i<timeToDraw.length;i++)
		{
			g.drawImage(numbers[0][timeToDraw[i]],initialX1,getY1(),(int)(getScale()*numberWidth),(int)(getScale()*numberHeight),null);
			initialX1+=numberWidth*getScale();
		}
	}
}