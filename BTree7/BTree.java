import java.util.Vector;

public class BTree {
	int t;
	
	Vector<TreeObject> obj = new Vector<TreeObject>();
	
    Node root;

    class Node
    {
    	
    	
        public Node(Node parent)
        {	
            key = new Vector<String>();  
            kids = new Vector<Node>();
            this.parent=parent;
        }

        boolean isFull()
        {
            return key.size()==2*t-1;
        }

        boolean isLeaf()
        {
            return kids.size()==0;
        }

        boolean isduplicate(String key) {
        	int d;
        	for(d = 0; d < this.key.size();d++) {
        		if(key.compareTo(this.key.get(d)) == 0 ) return true;
        	}
			return false;
        }
        int findChildIndex(String key)
        {
            int x;
            for(x=0;x<this.key.size();x++)
            {
                if(key.compareTo(this.key.get(x)) <= 0 ) return x; // Walk through the list of keys until you find one bigger than yourself
            }
            return x;
        }
        Node getChild(int index)
        {
            return kids.get(index);
        }


        Vector<String> key;
        Vector<Node> kids;
        Node parent;
    }
    
    public BTree()
    {
        root = new Node(null);
    }

    public BTree(int t)
    {
        root = new Node(null);
        this.t=t;
    }
    
    private void insert(String key, Node curr) {
    	
    	if(curr.isFull()) {			//Split the node
    		
    		Node newNode = new Node(curr.parent);
    		
    		while(curr.key.size() > t)
            {
                if(!curr.isLeaf()) 
                {
                    Node child; // we need to switch newNode's kids.parent link to point back to newNode
                    newNode.kids.add(0,child = curr.kids.remove(curr.kids.size()-1));
                    child.parent = newNode; // so we do that here
                }
                newNode.key.add(0,curr.key.remove(curr.key.size()-1));
            }
    		
    		if(!curr.isLeaf()) 
            {
                Node child; // Same thing, gotta switch newNodes children's parent link to newNode
                newNode.kids.add(0,child = curr.kids.remove(curr.kids.size()-1));
                child.parent = newNode;
            }
    		
    		int i;
    		String medKey = curr.key.remove(curr.key.size()-1);
    		
    		if(curr!=root)
            {
                curr.parent.key.add(i=curr.parent.findChildIndex(medKey),medKey);
                curr.parent.kids.add(i+1,newNode);
                curr=curr.parent;
            }
            else // we forgot to take care of the case where the tree gets a new root.  Doing that here.
            {
                root = new Node(null);
                root.kids.add(curr);
                root.kids.add(newNode);
                root.key.add(medKey);
                curr.parent=root;
                newNode.parent=root;
                curr=root;
            }
    	}
    }
    
    public void insert(String key) {
    	if (isDuplicate(key, root)) {
    		checkFrequency(new TreeObject(key));
    	} else if(isDuplicate(key, root)==false){
    		
		insert(key, root);
		addTreeObject(new TreeObject(key));
    	}
    }

	private boolean isDuplicate(String key, Node root2) {
		if(root2.isduplicate(key) == true) {
			return true;
		}  else {
		
		return false;
		}
	}

	private void addTreeObject(TreeObject treeObject) {
		obj.add(treeObject);
	}

	private void checkFrequency(TreeObject key) {
		for(int i = 0; i < obj.size(); i++) {
			if(key.equals(obj.get(i))) {
				obj.get(i).increaseFrequency();
			}
		}
	}
	
	public String debugFile() {
		StringBuilder s = new StringBuilder();
		
		for(int j = 0; j < obj.size(); j++) {
			s.append(obj.get(j).getTreeObject() + " Frequency " + obj.get(j).getFrequency() + '\n');
		}
		return s.toString();
	}
}