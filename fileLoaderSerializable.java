import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.swing.*;


public class fileLoaderSerializable
{
	private static final boolean DEBUG=true; 
	private static String s=""+File.separatorChar;
	public static String path =(new File(fileLoader.class.getResource("fileLoader.class").getPath())).toString().replace("%20"," ").replace(s+"fileLoader.class","");//System.getProperty("user.dir");fileLoader.class.getProtectionDomain().getCodeSource().getLocation().getFile();	
	public static Page[] load(Input in) throws IOException
	{
		
		if (DEBUG){System.out.println(path+s+"p0"+s+"GameBackground.png");}
		
		Page[] pages=new Page[12];
		GraphStorer storer=new GraphStorer();
		try 
		{
			BufferedImage img1 = null;
			BufferedImage img2 = null;
			BufferedImage img3 = null;
			Drawable[] d=new Drawable[2];
			Button[] b=new Button[5];
			pages[0]=new MainMenu(in,d,b);
			img1 = ImageIO.read(new File(path+s+"p0"+s+"GameBackground.png"));
			d[0]=new Background(in,img1);
			img1 = ImageIO.read(new File(path+s+"p0"+s+"GraphGameWhite.png"));
			d[1]=new Graphic(in,img1,0.5,170.0/in.defaultHeight(),0,0,700,74);
			img1 = ImageIO.read(new File(path+s+"p0"+s+"b1.png"));
			img2 = ImageIO.read(new File(path+s+"p0"+s+"b1h.png"));
			img3 = ImageIO.read(new File(path+s+"p0"+s+"b1c.png"));
			b[0] = new Button3State(in,img1,img2,img3,0.5,420.0/in.defaultHeight(),0,0,410,84,1);//mode 1
			img1 = ImageIO.read(new File(path+s+"p0"+s+"b2.png"));
			img2 = ImageIO.read(new File(path+s+"p0"+s+"b2h.png"));
			img3 = ImageIO.read(new File(path+s+"p0"+s+"b2c.png"));
			b[1] = new Button3State(in,img1,img2,img3,0.5,610.0/in.defaultHeight(),0,0,410,84,0);//mode 2
			img1 = ImageIO.read(new File(path+s+"p0"+s+"b3.png"));
			img2 = ImageIO.read(new File(path+s+"p0"+s+"b3h.png"));
			img3 = ImageIO.read(new File(path+s+"p0"+s+"b3c.png"));
			b[2] = new Button3State(in,img1,img2,img3,0.5,800.0/in.defaultHeight(),0,0,410,84,0);//mode 3
			img1 = ImageIO.read(new File(path+s+"p0"+s+"Team.png"));
			b[3] = new Button1State(in,img1,165.0/in.defaultWidth(),1053.0/in.defaultHeight(),0,0,112,146,11);// team pi
			img1 = ImageIO.read(new File(path+s+"p0"+s+"exit.png"));
			b[4] = new ButtonClose(in,img1,(in.defaultWidth()-165.0)/in.defaultWidth(),1053.0/in.defaultHeight(),0,0,112,146,0);//exit
			//page 1
			b = new Button[8];
			Drawable[] d1 = {d[0],d[1],new GraphicsGraphShower(in,1100.0/in.defaultWidth(),640.0/in.defaultHeight(),0,0,760,680)};
			pages[1]=new GameMode1Options(in,d1,b);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"b1.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"b1h.png"));
			b[0]=new Button3State(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),0,-268,560,136,2);//chamge width and height
			img1 = ImageIO.read(new File(path+s+"p1"+s+"b2.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"b2h.png"));
			b[1]=new Button3State(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),0,-134,560,136,0);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"b3.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"b3h.png"));
			b[2]=new Button3State(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),0,0,560,136,0);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"b4.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"b4h.png"));
			b[3]=new Button3State(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),0,134,560,136,0);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"Random.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"Randomh.png"));
			b[4]=new ButtonColorChooser(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),-139,268,280,136);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"Upload.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"Uploadh.png"));
			b[5]=new ButtonFileChooser(in,img1,img2,img2,400.0/in.defaultWidth(),640.0/in.defaultHeight(),+140,268,280,136,0);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"Back.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"Backh.png"));
			b[6]=new Button3State(in,img1,img2,img2,120.0/in.defaultWidth(),1155.0/in.defaultHeight(),0,0,200,50,0);
			img1 = ImageIO.read(new File(path+s+"p1"+s+"Play.png"));
			img2 = ImageIO.read(new File(path+s+"p1"+s+"Playh.png"));
			b[7]=new ButtonStartGame(in,img1,img2,img2,(in.defaultWidth()-120.0)/in.defaultWidth(),1155.0/in.defaultHeight(),0,0,200,50,2);
			Drawable[] d2={d[0]};
			b=new Button[3];
			pages[2]=new GameMode1(in,d2,b);
			b[0]=new ButtonGraphShower(in,0.5,555.0/in.defaultHeight(),0,0,1520,1030);
			img1 = ImageIO.read(new File(path+s+"p2"+s+"Color.png"));
			img2 = ImageIO.read(new File(path+s+"p2"+s+"Colorh.png"));
			b[1]=new ButtonColorChooser(in,img1,img2,img2,140.0/in.defaultWidth(),1150.0/in.defaultHeight(),0,0,200,50);
			img1 = ImageIO.read(new File(path+s+"p2"+s+"Hint.png"));
			img2 = ImageIO.read(new File(path+s+"p2"+s+"Hinth.png"));
			b[2]=new ButtonHint(in,img1,img2,img2,1320.0/in.defaultWidth(),1150.0/in.defaultHeight(),0,0,200,50);
			
			if (DEBUG){System.out.println("Good");}
		} 
		catch (IOException e) 
		{
			System.out.println("NO IMAGE");
			return null;
		}
		// First background, then graphic, then button.
		/*Drawable[] a={new Background(in,mjc,img1,width,height), new Graphic(in,mjc,img7,width,height,0.5,170.0/height,0,0,700,74)};
		
		Button[] b=new Button[5];
		b[0]= new ButtonFixPoint(in,mjc,img2,img3,img4,width,height,0.5,420.0/height,0,0,410,84.0,0);//mode 1
		b[1]= new ButtonFixPoint(in,mjc,img3,img3,img3,width,height,0.5,610.0/height,0,0,410,84,0);//mode 2
		b[2]= new ButtonFixPoint(in,mjc,img4,img4,img4,width,height,0.5,800.0/height,0,0,410,84,0);//mode 3
		b[3]= new ButtonFixPoint(in,mjc,img5,img5,img5,width,height,165.0/width,1053.0/height,0,0,112,146,11);// team pi
		b[4]= new ButtonFixPoint(in,mjc,img6,img6,img6,width,height,(width-165.0)/width,1053.0/height,0,0,112,146,0);//exit*/
		
		
		return pages;
	}
	public static ArrayList<BufferedImage> Icon() throws IOException
	{
		ArrayList<BufferedImage> icon = new ArrayList<BufferedImage>();
		BufferedImage img1=null;
		try
		{
			img1 = ImageIO.read(new File(path+s+"icon"+s+"16.png"));
			icon.add(img1);
			img1 = ImageIO.read(new File(path+s+"icon"+s+"32.png"));
			icon.add(img1);
			img1 = ImageIO.read(new File(path+s+"icon"+s+"64.png"));
			icon.add(img1);
			img1 = ImageIO.read(new File(path+s+"icon"+s+"128.png"));
			icon.add(img1);
		}
		catch (IOException e)
		{
			System.out.println("NO ICON");
		}
		return icon;
	}
}