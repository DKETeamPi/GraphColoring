public class LinkedList<E>
{
	class Link implements SingleLink
	{
		private Link frontLink;
		private Link backLink;
		private E element;
		public Link(Link backLink,E element)
		{
			this.element=element;
			backLink.frontLink=this;
			this.backLink=backLink;
			frontLink=null;
		}
		public Link(E element)
		{
			this.element=element;
			backLink=null;
			frontLink=null;
		}
		public E get()
		{
			return element;
		}
		public Link next()
		{
			return frontLink;
		}
		public Link previous()
		{
			return backLink;
		}
		public void add()
		{
			backLink.frontLink=this;
			frontLink.backLink=this;
		}
		public void remove()
		{
			backLink.frontLink=frontLink;
			frontLink.backLink=backLink;
		}
	}
	private Link startLink;
	private Link endLink;
	private Link middleLink;
	public LinkedList(E element)
	{
		Link first=new Link(element);
		startLink=first;
		endLink=first;
		middleLink=first;
	}
	private LinkedList(Link startLink,Link endLink)
	{
		this.startLink=startLink;
		this.middleLink=startLink;
		this.endLink=endLink;
	}
	public void add(E element)
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
	public E get()
	{
		return middleLink.get();
	}
	public SingleLink takeLink()
	{
		SingleLink result=middleLink;
		removeLink();
		return result;
	}
	public void addLink(SingleLink singleLink)
	{
		Link link=(Link) singleLink;
		link.add();
	}
	public void removeLink()
	{
		middleLink.remove();
		middleLink=middleLink.previous();
	}
	public LinkedList clone()
	{
		return new LinkedList(startLink,endLink);
	}
}