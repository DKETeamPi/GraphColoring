import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ButtonGraphShower extends Graphic implements Button
{
	public ButtonGraphShower
	(
		Input in,
		double xAncorScale,
		double yAncorScale,
		int xOffSet,
		int yOffSet,
		int width,
		int height)
	{
		super(in,null,xAncorScale,yAncorScale,xOffSet,yOffSet,width,height);
	}
	public void draw(Graphics g)
	{
		BufferedImage imageToDraw=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics gtd=imageToDraw.getGraphics();
		gtd.setColor(Color.WHITE);
		gtd.fillRect(0,0,getWidth(),getHeight());
		if(!in.storer().getIsEmpty())
		{
			in.storer().draw(gtd,getWidth(),getHeight());
		}
		g.drawImage(imageToDraw,getX1(),getY1(),null);
		g.drawRect(getX1(),getY1(),getWidth(),getHeight());
	}
	public boolean run()
	{
		double widthScale=(in.defaultWidth()*1.0)/getWidth();
		double heightScale=(in.defaultHeight()*1.0)/getHeight();
		return in.storer().run((int)((in.mouseX()-getX1())*(widthScale)),(int)((in.mouseY()-getY1())*(heightScale)));
	}
}