import java.util.Random;
/**
 * 
 * Class that randomly generates processes with a given probability. 
 * @author randycamacho
 *
 */

public class ProcessGenerator {
	private Random rand = new Random();
	private double probability;
	
	public ProcessGenerator(double probabilityInput) {
		probability = probabilityInput;
	}
	
	public Process getNewProcess(int currentTime, int maxProcessTime, int maxLevel) {
		return new Process((rand.nextInt(maxLevel)+1), (rand.nextInt(maxProcessTime)+1), currentTime);
	}
	
	public boolean query() {
		if(Math.random() <= probability) {
			return true;
		} else {
			return false;
		}
	}
}
