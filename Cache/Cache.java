import java.util.LinkedList;
/*
 * Class cache that has a constructor and methods to be used.
 * Author: Randy Camacho
 * 
 */

public class Cache {
	private int size;
	private int limit;
	private int index;
	
	LinkedList<String> list = new LinkedList<String>();
	
	public Cache(int sizeInput) {
		limit = sizeInput;
	}
	
	public int getObject(String data) {
		index = list.indexOf(data);
		return index;
	}
	
	public void addObject(String data) {
		list.addFirst(data);
		size += 1;
		if (size > limit) {
			list.removeLast();
			size -= 1;
		}
	}
	
	public void removeObject(int data) {
		list.remove(data);
		size -= 1;
	}
	
	public void clearCache() {
		list.clear();
		size = 0;
	}
}
