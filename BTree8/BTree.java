import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidParameterException;

public class BTree{
	
	private int deg, seqLength;
	BTreeNode root;
	Cache cache;
	File BtreeFile;
	RandomAccessFile treeRAF;
	
	public BTree(int degree, int seqLength, String gbk, Cache cache) throws IOException{
		this.deg = degree;
		this.seqLength = seqLength;
		this.cache = cache;
		File metadata = new File(gbk + ".btree.metadata." + seqLength + "." + degree);
		
		treeRAF = new RandomAccessFile(metadata, "rw");
		treeRAF.writeInt(degree); //write tree degree to metadata file
		treeRAF.writeInt(seqLength); //write sequence length to metadata file
		treeRAF.close();

		root = new BTreeNode(degree,0);
		BtreeFile = new File(gbk + ".btree.data." + seqLength + "." + degree);			
		diskWrite(root);
	}
	
	public BTree(File BtreeFile, File metadata, Cache cache) throws IOException {	
		treeRAF = new RandomAccessFile(metadata, "r");
		this.deg = treeRAF.readInt(); //read in degree in terms of t
		this.seqLength = treeRAF.readInt(); //sequence length (k) 
		treeRAF.close();
		
		this.BtreeFile = BtreeFile;
		treeRAF = new RandomAccessFile(BtreeFile, "r");
		root = readCache(0);		
		treeRAF.close();
		
		this.cache = cache;
	}

	//insert key
	public void insert(long key)  {
		//check if it is a duplicate
		BTreeNode duplicate = search(root, key);
		if(duplicate != null) {
			for(int i=0; i<duplicate.keys.length; i++) {
				if(duplicate.keys[i].key == key) {
					duplicate.keys[i].frequency++;
					nodeWrite(duplicate);
					return;
				}
			}
		}
		
		BTreeNode r = this.root;
		if(r.numKeys == 2*deg-1) {
			BTreeNode newNode = new BTreeNode(deg, getFileLength());	
			diskWrite(newNode);	
			this.root.nodePosition = getFileLength();
			diskWrite(root);
			this.root = newNode;
			newNode.numKeys = 0;
			newNode.children[0] = r.nodePosition;
			newNode.nodePosition = 0;
			newNode.isLeaf = false;
			splitChild(newNode,0,r);
			insertNonFull(newNode,key);
		}else {
			insertNonFull(root,key);
		}
	}
	
	//split the child node
	public void splitChild(BTreeNode parent, int i, BTreeNode child) {
		//parent is the parent to child
		//child is the node being split 
		//newChild is the new node which ~half of y's keys/children will go to
		BTreeNode newChild = new BTreeNode(deg, getFileLength());
		newChild.isLeaf = child.isLeaf;
		newChild.numKeys = deg-1;
		diskWrite(newChild);
		
		for(int j=0; j<deg-1; j++) {
			newChild.keys[j] = new TreeObject(child.keys[j+deg].key, child.keys[j+deg].frequency);
			child.keys[j+deg] = new TreeObject();
		}
		if(!child.isLeaf) {
			for(int j=0; j<deg; j++) {
				newChild.children[j] = child.children[j+deg];
				child.children[j+deg] = -1L;
			}
		}
		
		child.numKeys = deg-1;
		for(int j=parent.numKeys; j>i; j--) {
			parent.children[j+1] = parent.children[j];
			parent.children[j] = -1L;
		}
		parent.children[i+1] = newChild.nodePosition;
		for(int j=parent.numKeys-1; j>i-1; j--) {
			parent.keys[j+1] = new TreeObject(parent.keys[j].key, parent.keys[j].frequency);
		}
		parent.keys[i] = new TreeObject(child.keys[deg-1].key, child.keys[deg-1].frequency);
		child.keys[deg-1] = new TreeObject();
		parent.numKeys = parent.numKeys + 1;
		nodeWrite(newChild);		
		nodeWrite(child);		
		nodeWrite(parent);
	}
	
	public void insertNonFull(BTreeNode node, long key) {
		int i = node.numKeys - 1;
		if(node.isLeaf) {
			while( i >= 0 && key < node.keys[i].key ) {
				node.keys[i+1] = new TreeObject(node.keys[i].key, node.keys[i].frequency);
				i--;			
			}
			node.keys[i+1] = new TreeObject(key, 1);
			node.numKeys++;
			nodeWrite(node);	
		}else {
			while( i >= 0 && key < node.keys[i].key) {
				i--;
			}
			i++;
			BTreeNode c;
			if(node.children[i] != -1) {
				c = readCache(node.children[i]);
				if( c.numKeys == 2*deg-1 ) {
					splitChild(node, i, c);
					if( key > node.keys[i].key){
						i++;
					}
				}
				insertNonFull(readCache(node.children[i]), key);
			}			
		}
	}
	
	public BTreeNode search(BTreeNode root, long key) {
		int i = 0;
		BTreeNode retNode = null;
		while(i < root.numKeys && key > root.keys[i].key) {
			i++;
		}
		if(i < root.numKeys && key == root.keys[i].key) {
			return root;
		}
		if(root.isLeaf) {
			return null;
		}
		if(root.children[i]!=-1) {
			retNode = readCache(root.children[i]);
		}				
		return search(retNode,key);
	}
	
	public long sequenceToLong(String s) {
		if( s.length() > 31 ) throw new InvalidParameterException("stringToLong() sequence paramter is between 1-31");
		Long retVal = 0L;
		for( int i=0; i<s.length(); i++ ) {
			int cur = 0;
			switch( s.substring(i, i+1) ){
				case "A": cur = 0; break;
				case "C": cur = 1; break;
				case "T": cur = 3; break;
				case "G": cur = 2; break;
			};
			if(i==0) retVal += cur;
			else retVal += cur * (long)Math.pow(4,i);
		}
		return retVal;
	}
	
	public String longToDNAstring(long key, int subsequenceLength) {
		String retString = "";
		String s = "";
		for(int i=0; i < subsequenceLength; i++){
			String cur = "";
			switch((int)(key % 4)) {
				case 1: cur = "C"; break;
				case 2: cur = "G"; break;
				case 3: cur = "T"; break;
				case 0: cur = "A"; break;
			}
			retString += cur;
			key = key >> 2;
		}
		for(int i = retString.length() - 1; i >= 0 ;i--) {
			s = s + retString.charAt(i);
		}
		return s;
	}

	public void nodeWrite(BTreeNode node) {
		if (cache != null) {
			//add node to the cache, if the cache is full addObject will return the last element in the cache. 
			//When the last element is returned, write it to disk.
			BTreeNode checkNode = cache.addObject(node);
			if (checkNode != null) {
				diskWrite(checkNode);
			}
		}
		else {
			diskWrite(node);
		}
		
	}
	
	
	public void diskWrite(BTreeNode node) {
		try {
			treeRAF = new RandomAccessFile(BtreeFile, "rw");
			treeRAF.seek(node.nodePosition);
			for (int i = 0; i < node.keys.length; i++) {
				treeRAF.writeLong(node.keys[i].key);
				treeRAF.writeInt(node.keys[i].frequency);
			}
			for (int i = 0; i < node.children.length; i++) {
				treeRAF.writeLong(node.children[i]);
			}
			treeRAF.writeInt(node.numKeys);
			treeRAF.writeBoolean(node.isLeaf);
			treeRAF.writeLong(node.nodePosition);
			treeRAF.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BTreeNode readCache(long filePos) {		
		//search the cache for the node with the given filePos
		//if it is found in the cache, return it instead of reading from disk
		BTreeNode checkCache = null;
		if (cache != null) {
			checkCache = cache.getObject(filePos);
		}
		if (checkCache != null)
		{
			return checkCache;
		}
		
		BTreeNode node = new BTreeNode(deg,filePos);
		try {
			treeRAF = new RandomAccessFile(BtreeFile, "r");
			treeRAF.seek(filePos);
			for (int i = 0; i < node.keys.length; i++) {
				node.keys[i].key = treeRAF.readLong();
				node.keys[i].frequency = treeRAF.readInt();
			}
			for (int i = 0; i < node.children.length; i++) {
				node.children[i] = treeRAF.readLong();
			}
			node.numKeys = treeRAF.readInt();
			node.isLeaf = treeRAF.readBoolean();
			node.nodePosition = treeRAF.readLong();
			treeRAF.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return node;
	}
	
	public void writeCache() {
		for (int i = cache.cacheSize(); i > 0; i--) {
			diskWrite(cache.getLast());
		}
	}
	
	private long getFileLength() {
		long fileLength = -1L;
		try {
			treeRAF = new RandomAccessFile(BtreeFile, "r");
			fileLength = treeRAF.length();
			treeRAF.close();
		} catch (IOException e) {
			System.out.println("Error accessing file");
			e.printStackTrace();
		}	
		return fileLength;
	}
	
	public void printToFile(BTreeNode root_node, boolean debug) {	
		//in-order traversal of nodes
		//print the keys of the node for each step
		int i;
		for(i=0; i < 2*deg-1; i++) {
			if (!root_node.isLeaf) {
				if(root_node.children[i] != -1L) {
					BTreeNode n = readCache(root_node.children[i]);
					printToFile(n, debug);
				}
			}
			TreeObject cur = root_node.keys[i];
			if(cur.key != -1) {
				System.out.print(cur.key + " ");
				System.out.print(longToDNAstring(cur.key, seqLength).toLowerCase() + " ");
				System.out.print(cur.frequency + " ");
				System.out.println();
			}
		}
		
		if (!root_node.isLeaf) {
			if(root_node.children[i] != -1L) {
				BTreeNode n = readCache(root_node.children[i]);
				printToFile(n, debug);
			}
		}		
	}
}