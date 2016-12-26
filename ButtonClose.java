import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.awt.event.WindowEvent;

public class ButtonClose extends Button1State
{
	public ButtonClose(Input in,BufferedImage img,double a,double b,int c,int d,int e,int f,int g)
	{
		super(in,img,a,b,c,d,e,f,g);
	}
	public void action()
	{
		in.frame().dispatchEvent(new WindowEvent(in.frame(), WindowEvent.WINDOW_CLOSING));
	}

}