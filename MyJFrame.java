import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class MyJFrame extends JFrame
{
	private final boolean DEBUG=true;
	MyJComponent mjc;
	private int width;
	private int height;
	private int dw;
	private int dh;
	public MyJFrame() throws IOException
	{
		//Constracter
		width=1600;
		height=1200;
		pack();
		dw=getWidth()-getContentPane().getWidth();
		dh=getHeight()-getContentPane().getHeight();
		setSize(width+dw,height+dh);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImages(fileLoader.Icon());
		setLayout(null);
		mjc=new MyJComponent(this,width,height);
		class MyComponentAdapter extends ComponentAdapter
		{
			public void componentResized(ComponentEvent e)
			{
				mjc.setBounds(0,0,(int)getWidth()-dw,(int)getHeight()-dh);
			}
        }
        addComponentListener(new MyComponentAdapter());
		add(mjc,BorderLayout.CENTER);
		setVisible(true);
	}
}
