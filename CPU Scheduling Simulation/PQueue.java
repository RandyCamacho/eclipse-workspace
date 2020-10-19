/**
 * 
 * PQueue class that defines a priority queue.
 * @author randycamacho
 *
 */
public class PQueue {
	private MaxHeap heap = new MaxHeap();
	
	public void enPQueue(Process p) {
		if(p.getTimeRemaining() >= 1) {
			heap.add(p);
			heap.maxHeapify();
		}
	}
	
	public Process dePQueue() {
		heap.maxHeapify();
		Process rmFromQueue = heap.getFirst();
		heap.rmFirst();
		return rmFromQueue;
	}
	
	public boolean isEmpty() {
		if((heap.getSize()) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void update(int timeToIncrementPriority, int maxLevel) {
		for(int i =0; i < heap.getSize(); i++) {
			heap.update(i, timeToIncrementPriority, maxLevel);
		}
		heap.maxHeapify();
	}
}
