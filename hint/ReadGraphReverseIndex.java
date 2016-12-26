import java.util.*;
import java.io.*;
public class ReadGraphReverseIndex
		{

		public final static boolean DEBUG =false;

		public final static String COMMENT = "//";

		public static adjGraph main( String args[] )
			{
			if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}


			String inputfile = args[0];

			boolean seen[] = null;

			//! n is the number of vertices in the graph
			int n = -1;

			//! m is the number of edges in the graph
			int m = -1;

			//! e will contain the edges of the graph
			adjGraph v=null; //new adjGraph();
			try 	
			{
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();

					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.

					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}

					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];

					record = br.readLine();

					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					int A,B;
					int vertex[][]=new int[n][n];
					int length [] = new int [n];
					for( int d=0; d<m; d++)
					{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
						{
							System.out.println("Error! Malformed edge line: "+record);
							System.exit(0);
						}


					A= Integer.parseInt(data[0]);
					B = Integer.parseInt(data[1]);

					vertex[A-1][length[A-1]]=(B-1);
					length[A-1]++;
					vertex[B-1][length[B-1]]=(A-1);
					length[B-1]++;
					seen[A] = true;
					seen[B] = true;

					if(DEBUG) System.out.println(COMMENT + " Edge: "+ A +" "+B);

					}
					v=new adjGraph(vertex,length);
						if (DEBUG)
						{
							System.out.println("matrix of vertex[][]");
							for(int y=0;y<n;y++)
							{
								for(int x=0;x<n;x++)
								{
									System.out.print(vertex[x][y]+" ");
								}
								System.out.println();
							}
							System.out.println("vector of length[]");
							for(int x=0;x<n;x++)
							{
								System.out.print(length[x]+" ");
							}
							System.out.println();
						}
						String surplus = br.readLine();
						if( surplus != null )
						{
							if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
						}

				}
			catch (IOException ex)
				{
					// catch possible io errors from readLine()
					System.out.println("Error! Problem reading file "+inputfile);
					System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
					if( seen[x] == false )
					{
						if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//!
			//! there will be n vertices in the graph, numbered 1 to n
//new backTrack(v);
return v;
			//! INSERT YOUR CODE HERE!






		}

}
