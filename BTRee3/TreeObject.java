public class TreeObject
{
	public Long sequence = null;
	public Integer frequency;
	
	public TreeObject (Long key){
		
		this.sequence = key;
		this.frequency = 1;
	}
	
	public Long getTreeObject(){
		return this.sequence;
	}
	
	public Integer getFrequency(){
		return frequency;
	}
	
	public void increaseFrequency(){
		//this.frequency = (this.frequency + 1);
		frequency++;
	}
}

