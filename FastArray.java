public class FastArray
{
	int length;
	int[] content;
	public FastArray(int size)
	{
		length=0;
		content=new int[size];
	}
	public int length()
	{
		return length;
	}
	public int get(int index)
	{
		return content[index];
	}
	public void set(int index,int a)
	{
		content[index]=a;
		if (index>length){System.out.println("out of bounds!!");}
	}
	public void add(int a)
	{
		content[length]=a;
		length++;
	}
	public boolean contains(int n)
	{
		boolean result = false;
		int i=0;
		while(!result && i<length)
		{
			if(content[i]==n)
			{
				result=true;
			}
			i++;
		}
		return result;
	}
}