import java.util.*;

public class BTree
{
    int t;

    Node root;
    

    class Node
    {
        public Node(Node parent)
        {
            key = new Vector<String>(); 
            kids = new Vector<Node>();;
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

        int findChildIndex(String key)
        {
            int x;
            for(x=0;x<this.key.size();x++)
            {
                if(key.compareTo(this.key.get(x)) <= 0) return x; // Walk through the list of keys until you find one bigger than yourself
            }
            return x;
        }
        int frequency(String key) {
        	int c, f;
        	f = 0;
        	for(c = 0; c < this.key.size(); c++) {
        		if(key.compareTo(this.key.get(c)) == 0) {
        			f++;
        			return f;
        		}
        	} return f;
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

    private void insert(String key, Node curr)
    {	
        if(curr.isFull()) // need to split
        {
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
           String medKey= curr.key.remove(curr.key.size()-1);
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
        if(curr.isLeaf()) // add key to node
        {
           curr.key.add(curr.findChildIndex(key),key);
           // need to write some code here
        }
        else insert(key,curr.getChild(curr.findChildIndex(key))); 

    }

    private String toString(Node curr)
    {
        if(curr==null) return "";
        String rval="";
        int x;
        for(x=0;x<curr.key.size();x++)
        {
            if(!curr.isLeaf()) rval+= toString(curr.kids.get(x)) + curr.key.get(x) + ", ";
            else rval+= curr.key.get(x) + ", ";
        }
        if(!curr.isLeaf()) rval+= toString(curr.kids.get(x)) + ",";

        return rval;
    }

    public String toString()
    {
        return toString(root);
    }

    public void insert(String key)
    {   
        insert(key,root);
    }

    private void preOrder(Node curr)
    {
        int x;
        for(x=0;x<curr.key.size();x++) System.out.print(curr.key.get(x)+ ", ");
        System.out.println();
        for(x=0;x<curr.kids.size();x++) preOrder(curr.kids.get(x));
    }
}





