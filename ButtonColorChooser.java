import javax.swing.JColorChooser;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.io.File;

public class ButtonColorChooser extends Button3State
{
	boolean DEBUG=true;
	
	public ButtonColorChooser(Input in,BufferedImage img1,BufferedImage img2,BufferedImage img3,double a,double b,int c,int d,int e,int f)
	{
		super(in,img1,img2,img3,a,b,c,d,e,f,0);
	}
	public void draw(Graphics g)
	{
		g.setColor(new Color(in.storer().getUsingColor()));
		g.fillRect(getX1(),getY1(),getWidth(),getHeight());
		g.drawImage(img,getX1(),getY1(),getWidth(),getHeight(),null);
		g.setColor(Color.GRAY);
		g.drawRect(getX1(),getY1(),getWidth(),getHeight());
	}	
	public void action()
	{
		in.colorChooserDialog().setModal(true);
		in.colorChooserDialog().setVisible(true);
		//in.frame().repaint();
	}
}