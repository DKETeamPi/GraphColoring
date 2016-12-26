import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;


public class MyJComponent extends JPanel
{
	private final boolean DEBUG=false;
	private MyJFrame frame;
	private Input in;
	private Page[] pages;
	private String dialog=null;
	private Timer timer;
	
	public MyJComponent(MyJFrame frame,int width,int height) throws IOException
	{
		this.frame=frame;
		in = new Input(frame,this,width,height,(int)getWidth(),(int)getHeight());
		pages=fileLoader.load(in);
		MyMouseAdapter mma=new MyMouseAdapter();
		addMouseListener(mma);
		addMouseMotionListener(mma);
		addMouseWheelListener(mma);
		addComponentListener(new MyComponentAdapter());
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		pages[in.pageNumber()].draw(g);
		if (dialog!=null)
		{
			Font font=new Font("TimesRoman",Font.PLAIN, (int)(30*in.getScale()));
			g.setFont(font);
			FontMetrics metrics = g.getFontMetrics(font);
			int width=metrics.stringWidth(dialog);//(int)(400*in.getScale());
			int height=metrics.getHeight();//(int)(40*in.getScale());
			g.setColor(new Color(-16737946));
			g.fillRect(in.mouseX()-width,in.mouseY()-height,width,height);
			g.setColor(Color.BLACK);
			g.drawString(dialog,in.mouseX()-width,in.mouseY()-metrics.getDescent());
			g.drawRect(in.mouseX()-width,in.mouseY()-height,width,height);
		}
		
		if(DEBUG){System.out.println("redraw");}
	}
	public void run()
	{
		if (pages[in.pageNumber()].run() || dialog!=null)
		{
			frame.repaint();
		}
	}
	public void showDialog(String a)
	{
		dialog=a;
		class DialogDeleter implements ActionListener
		{
			public void actionPerformed(ActionEvent e) 
			{
				dialog=null;
				in.frame().repaint();
			}
		}
			if (timer!=null)
			{
				timer.stop();
			}
			timer = new Timer(2000,new DialogDeleter());
			timer.setRepeats(false);
			timer.start();
	}
	class MyMouseAdapter extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if (DEBUG){System.out.println("Mouse pressed");}
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				in.setMouseLeft(true);
			}
			if (e.getButton() == MouseEvent.BUTTON2)
			{
				in.setMouseMiddle(true);
			}
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				in.setMouseRight(true);
			}
			run();
		}
		public void mouseReleased(MouseEvent e)
		{
			if (DEBUG){System.out.println("Mouse released");}
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				in.setMouseLeft(false);
			}
			if (e.getButton() == MouseEvent.BUTTON2)
			{
				in.setMouseMiddle(false);
			}
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				in.setMouseRight(false);
			}
			run();
		}
		public void mouseMoved(MouseEvent e)
		{
			if (DEBUG){System.out.println("Mouse moving");}
			in.setMouseX((int)e.getX());
			in.setMouseY((int)e.getY());
			run();
		}
		public void mouseDragged(MouseEvent e)
		{
			if (DEBUG){System.out.println("Mouse moving");}
			in.setMouseX((int)e.getX());
			in.setMouseY((int)e.getY());
			run();
		}
		 public void mouseWheelMoved(MouseWheelEvent e) 
		 {
		 	 if(!in.storer().getIsEmpty())
		 	 {
				 in.storer().changeR(e.getWheelRotation());
				 in.frame().repaint();
		 	 }
		 }
	}
	class MyComponentAdapter extends ComponentAdapter
	{
		public void componentResized(ComponentEvent e)
		{
			in.setWidth((int)getWidth());
			in.setHeight((int)getHeight());
			run();
			frame.repaint();
        }
	}
	class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			in.setKeyboard(e.getKeyCode(),true);
			//pages[pageNumber].run();
		}
		public void keyReleased(KeyEvent e)
		{
			in.setKeyboard(e.getKeyCode(),false);
			//pages[pageNumber].run();
		}
	}
}