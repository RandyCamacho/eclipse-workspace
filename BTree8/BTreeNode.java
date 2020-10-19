

public class BTreeNode {
	
	public TreeObject[] keys;
	public long[] children;
	public boolean isLeaf;
	public int numKeys; //current number of keys
	public long nodePosition; //position of node in file
	
	public BTreeNode(int degree, long filePos) {
		this.keys = new TreeObject[(2*degree-1)];		
		for(int i=0; i<keys.length; i++) {
			keys[i] = new TreeObject(-1L, 0);
		}	
		
		this.children = new long[(2*degree)];
		for(int i=0; i<children.length; i++) {
			children[i] = -1L;
		}
		
		this.isLeaf = true;
		numKeys = 0;
		this.nodePosition = filePos;
	}
	
	public boolean equals(BTreeNode t) {
		return this.nodePosition == t.nodePosition;
	}
	


}
