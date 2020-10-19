import java.util.ArrayList;
/**
 * MaxHeap class that defines a max-heap
 * @author randycamacho
 *
 */
public class MaxHeap {
	private ArrayList<Process> heapList = new ArrayList<Process>();
	private Process leftChild;
	private Process rightChild;
	private Process parent;
	private Process largest;
	private int largestIndex;
	private Process temp;
	
	public void add(Process processInput) {
		heapList.add(processInput);
		maxHeapify();
	}
	
	public Process getFirst() {
		return heapList.get(0);
	}
	
	public void rmFirst() {
		heapList.remove(0);
	}
	
	public int getSize() {
		return heapList.size();
	}
	
	public void update(int i, int timeToIncrementPriority, int maxLevel) {
		heapList.get(i).increaseTimeNotProcessed();
		if ( heapList.get(i).getTimeNotProcessed() >= timeToIncrementPriority) {
			heapList.get(i).increasePriority(maxLevel);
			heapList.get(i).resetTimeNotProcessed();
		}
		if (heapList.get(0).finish()) {
			heapList.remove(0);
		}
		
	}
	
	public void maxHeapify() {
		int index = ((heapList.size()-1)-1)/2;
		while ( index >= 0 ) {
			if (heapList.size() >= 2) {
				leftChild = heapList.get(2*index+1);
				parent = heapList.get(index);
				largest = parent;
				if (leftChild.compareTo(parent) == 1) {
					largest = leftChild;
					largestIndex = 2*index + 1;
				} else {
					largest = parent;
					largestIndex = index;
				}
			}
			try {
				rightChild = heapList.get(2*index+2);
				if (rightChild.compareTo(parent) == 1) {
					largest = rightChild;
					largestIndex = 2*index + 2;
				}
			} catch (IndexOutOfBoundsException e) {
				
			}
			
			if (largest != parent && heapList.size() >= 2) {
				temp = heapList.get(index);
				heapList.set(index, heapList.get(largestIndex));
				heapList.set(largestIndex, temp);
			}
			index--;
		}
		if (heapList.size() >=1) {
			if (heapList.get(0).finish()) {
				heapList.remove(0);
			}
		}	
	}
}
