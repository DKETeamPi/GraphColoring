public class tester
{
	public static void main(String[] args)
	{
		performanceMeasurer p1=new performanceMeasurer();
		performanceMeasurer p2=new performanceMeasurer();
		performanceMeasurer p3=new performanceMeasurer();
		int[] vector=new int[100];
		for(int i=0;i<vector.length;i++)
		{
			vector[i]=i;
		}
		LinkedList<Integer> ll=new LinkedList(vector[0]);
		LinkedListInt lli=new LinkedListInt(vector[0]);
		for(int i=1;i<vector.length;i++)
		{
			ll.add(vector[i]);
			lli.add(vector[i]);
		}
		System.out.println("done");
		p1.start();
		do
		{
			System.out.println(ll.get());
		}while(ll.next());
		p1.stop();
		p2.start();
		do
		{
			System.out.println(lli.get());
		}while(lli.next());
		p2.stop();
		p3.start();
		for(int i=0;i<vector.length;i++)
		{
			System.out.println(vector[i]);
		}
		p3.stop();
		System.out.println("Performance linked list: "+p1.getTime()+"  Performance linked list int: "+p2.getTime()+"  Performance array: "+p3.getTime());
	}
}