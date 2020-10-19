import java.util.*;

public class Cache_Level<T>{
	private int cacheSize;
	private int cacheReference;
	private	int cacheHit;
	private LinkedList<T> List;
	
	public Cache_Level(int size)
	{
		cacheSize = size;
		cacheReference = 0;
		cacheHit = 0;
		List = new LinkedList<T>();
	}
	
	public int getSize() //get the size of list
	{
		return cacheSize;
	}

	public int getReference() //how many traversals
	{
		return cacheReference;
	}
	
	public int getHit()// how many times you found the object
	{
		return cacheHit;
	}

	public boolean getObject(T O)
	{
		boolean c = List.contains(O);
		cacheReference++;
		return c;
	}
	
	public boolean addObject(T O)
	{
		if(O != null)	
		{		
			boolean x = getObject(O);
			if(x == true && List.size() == cacheSize) //if object is found and size of list is full
			{
				List.remove(O);
				List.addFirst(O);
				cacheHit++; //increment to show we found the object
			}
			else if(x == true && List.size() != cacheSize)//found but not full
			{
				List.remove(O);
				List.addFirst(O);
				cacheHit++;
			}	
			else if(x != true && List.size() == cacheSize) //item not found but list full
			{
				List.removeLast();
				List.addFirst(O);
			}
			else  //item not found and list is not full
			{
				List.addFirst(O);
			}	
		
			return x;
		}
		else 
		{
			System.out.println("NULL OBJECT WAS FOUND");
			return false;
		}
	}	
}

