public class LinkedListInt
{
	class Link
	{
		private Link headLink;
		private Link backLink;
		private int element;
		public Link(Link backLink,int element)
		{
			this.element=element;
			backLink.headLink=this;
			this.backLink=backLink;
			headLink=null;
		}public Link(int element)
		{
			this.element=element;
			backLink=null;
			headLink=null;
		}
		public int get()
		{
			return element;
		}
		public Link next()
		{
			return headLink;
		}
		public Link previous()
		{
			return backLink;
		}
	}
	private Link startLink;
	private Link endLink;
	private Link middleLink;
	public LinkedListInt(int element)
	{
		Link first=new Link(element);
		startLink=first;
		endLink=first;
		middleLink=first;
	}
	public void add(int element)
	{
		endLink=new Link(endLink,element);
	}
	public void restart()
	{
		middleLink=startLink;
	}
	public boolean next()
	{
		if(middleLink==endLink)
		{
			return false;
		}
		else
		{
			middleLink=middleLink.next();
			return true;
		}
	}
	public boolean previous()
	{
		if(middleLink==startLink)
		{
			return false;
		}
		else
		{
			middleLink=middleLink.previous();
			return true;
		}
	}
	public int get()
	{
		return middleLink.get();
	}
	public void removeLink()
	{
		
	}
}