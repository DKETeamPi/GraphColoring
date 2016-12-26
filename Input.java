import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Input
{
  	private MyJFrame frame;
  	private JFileChooser fileChooser;
  	private JColorChooser colorChooser;
  	private JDialog colorChooserDialog;
  	private MyJComponent mjc;
  	private GraphStorer storer;
  	private int pageNumber;
    private int defaultWidth;
 	private int defaultHeight;
	private int width;
	private int height;
	private int mouseX;
	private int mouseY;
	private boolean mouseLeft;
	private boolean mouseMiddle;
	private boolean mouseRight;
	private boolean[] keyboard;
	public Input(MyJFrame frame,MyJComponent mjc,int defaultWidth,int defaultHeight,int width,int height)
	{
      	this.frame=frame;
      	
      	fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".TXT","txt");
		fileChooser.setFileFilter(filter);
		frame.add(fileChooser);
		
		JColorChooser colorChooser = new JColorChooser();
		colorChooser.setPreviewPanel(new JPanel());
		
		class cancelListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				colorChooserDialog.setModal(false);
				colorChooserDialog.setVisible(false);
			}
		}
		class okListener extends cancelListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				super.actionPerformed(e);
				storer.setUsingColor(colorChooser.getColor().getRGB());
			}
		}
		colorChooserDialog=JColorChooser.createDialog(frame,"chose color",false,colorChooser,new okListener(),new cancelListener());
		
      	this.mjc=mjc;
      	this.storer=null;
      	pageNumber=0;
      	this.defaultWidth=defaultWidth;
      	this.defaultHeight=defaultHeight;
		this.width=width;
		this.height=height;
		mouseX=0;
		mouseY=0;
		mouseLeft=false;
		mouseMiddle=false;
		mouseRight=false;
		keyboard=new boolean[65536];
	}
  	public MyJFrame frame()
    {
      return frame;
    }
    public JFileChooser fileChooser()
    {
    	return fileChooser;
    }
    public JColorChooser colorChooser()
    {
    	return colorChooser;
    }
    public JDialog colorChooserDialog()
    {
    	return colorChooserDialog;
    }
    public MyJComponent mjc()
    {
    	return mjc;
    }
  	public GraphStorer storer()
    {
      return storer;
    }
    public int pageNumber()
    {
    	return pageNumber;
    }
  	public int defaultWidth()
    {
      return defaultWidth;
    }
  	public int defaultHeight()
    {
      return defaultHeight;
    }
	public int width()
	{
		return width;
	}
	public int height()
	{
		return height;
	}
	public int mouseX()
	{
		return mouseX;
	}
	public int mouseY()
	{
		return mouseY;
	}
	public boolean mouseLeft()
	{
		return mouseLeft;
	}
	public boolean mouseMiddle()
	{
		return mouseMiddle;
	}
	public boolean mouseRight()
	{
		return mouseRight;
	}
	public boolean keyboard(int a)
	{
		return keyboard[a];
	}
	public double getScale()
	{
		if (width()*defaultHeight()>height()*defaultWidth())//if window wide
		{
			return height()*1.0/defaultHeight();
		}
		else
		{
			return width()*1.0/defaultWidth();
		}
	}
	public void setStorer(GraphStorer storer)
    {
      this.storer=storer;
    }
	public void setPageNumber(int a)
	{
		pageNumber=a;
		mjc.run();
		frame.repaint();
	}
	public void setWidth(int a)
	{
		width=a;
	}
	public void setHeight(int a)
	{
		height=a;
	}
	public void setMouseX(int a)
	{
		mouseX=a;
	}
	public void setMouseY(int a)
	{
		mouseY=a;
	}
	public void setMouseLeft(boolean a)
	{
		mouseLeft=a;
	}
	public void setMouseMiddle(boolean a)
	{
		mouseMiddle=a;
	}
	public void setMouseRight(boolean a)
	{
		mouseRight=a;
	}
	public void setKeyboard(int a,boolean b)
	{
		keyboard[a]=b;
	}
	
}