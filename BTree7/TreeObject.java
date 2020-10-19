public class TreeObject
{
	public String sequence = "";
	public int frequency;
	
	public TreeObject (String key){
		
		this.sequence = key;
		this.frequency = 1;
	}
	
	public String getTreeObject(){
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

