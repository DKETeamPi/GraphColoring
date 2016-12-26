import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.io.File;

public class ButtonFileChooser extends Button3State
{
	boolean DEBUG=true;
	
	public ButtonFileChooser(Input in,BufferedImage img1,BufferedImage img2,BufferedImage img3,double a,double b,int c,int d,int e,int f,int g)
	{
		super(in,img1,img2,img3,a,b,c,d,e,f,g);
	}
	public void action()
	{
		/*JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".TXT","txt");
		chooser.setFileFilter(filter);
		in.frame().add(chooser);*/
		int returnVal = in.fileChooser().showOpenDialog(in.frame());
		if(returnVal == JFileChooser.APPROVE_OPTION) 
		{
			if(DEBUG){System.out.println("You chose to open this file: " +
            in.fileChooser().getSelectedFile().toString());}
            ReadGraphReverseIndex rgri=new ReadGraphReverseIndex(in.fileChooser().getSelectedFile().toString());
            if(in.storer()!=null)
					{
						in.storer().restore();
					}
            in.storer().setGraph(in,rgri.graph(),rgri.edge());
        }
	}
}