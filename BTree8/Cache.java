import java.util.LinkedList;
public class Cache { 
	//cache
	private LinkedList<BTreeNode> cache;
	//cache size
	private final int CACHE_MAX_SIZE; 
	
	public Cache(int cacheSize) {
		//initialize class variables
		cache = new LinkedList<BTreeNode>();
		this.CACHE_MAX_SIZE = cacheSize;

	}
	
	public BTreeNode getObject(BTreeNode toGet) {
		//look for object in cache and return if found
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).equals(toGet)) {

				return cache.remove(i);
			}
		}
		//not found
		return null;
	}

	public BTreeNode addObject(BTreeNode toAdd) {
		BTreeNode returnNode = null;
		if (isFull()) {
			 returnNode = cache.removeLast();
		}
		BTreeNode moveToFront = getObject(toAdd);
		if (moveToFront == null){
			cache.addFirst(toAdd);
		}
		else { //already in cache move to front
			cache.addFirst(moveToFront);
		}
		return returnNode;
	}
	
	public BTreeNode getObject(long fileOffset) {
		//look for object in cache and return if found
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).nodePosition == fileOffset) {
				BTreeNode toReturn = cache.remove(i);
				cache.addFirst(toReturn);
				return toReturn;
			}
		}
		//not found
		return null;
	}
	
	public BTreeNode getLast () {
		return cache.removeLast();
	}

	public boolean isFull() {
		return cache.size() == CACHE_MAX_SIZE;
	}
	
	public int cacheSize() {
		return cache.size();
	}
}