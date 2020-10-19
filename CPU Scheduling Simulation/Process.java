/**
 * 
 * Process class that defines a process
 * @author randycamacho
 *
 */
public class Process {
	private int priorityLevel;
	private int arrivalTime;
	private int timeNotProcessed;
	private int timeRemaining;
	
	public Process(int priorityLevelInput, int timeRemainingInput, int arrivalTimeInput) {
		priorityLevel = priorityLevelInput;
		timeRemaining = timeRemainingInput;
		arrivalTime = arrivalTimeInput;
		timeNotProcessed = 0;
	}
	
	public int getTimeRemaining() {
		return timeRemaining;
	}
	
	public int getTimeNotProcessed() {
		return timeNotProcessed;
	}
	
	public int getPriority() {
		return priorityLevel;
	}
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	public void reduceTimeRemaining() {
		timeRemaining -= 1;
	}
	
	public void  resetTimeRemaining() {
		timeNotProcessed = 0;
	}
	
	public void increaseTimeNotProcessed() {
		timeNotProcessed += 1;
	}
	
	public void resetTimeNotProcessed() {
		timeNotProcessed = 0;
	}
	
	public void increasePriority(int maxLevel) {
		if((priorityLevel + 1) <= maxLevel){
			priorityLevel += 1;
		}
	}
	
	public int compareTo(Process parent) {
		if(this.priorityLevel > parent.getPriority()) {
			return 1;
		} else if((this.priorityLevel == parent.getPriority()) && (this.arrivalTime < parent.getArrivalTime())) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public boolean finish() {
		if(timeRemaining == 0) {
			return true;
		} else {
			return false;
		}
	}
}
