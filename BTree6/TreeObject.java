public class TreeObject
{
	public Long sequence = null;
	public Integer frequency;
	
	public TreeObject (String s){
		//this.sequence = new Long(s);
															//Prevents a Long Overflow by converting 
		this.sequence = this.sequence.parseLong(s, 2);		//the string to the decimal representation
															//of its binary value and then parsing to a long
		this.frequency = 1;
	}
	
	public Long getTreeObject(){
		return this.sequence;
	}
	
	public Integer getFrequency(){
		return this.frequency;
	}
	
	public void increaseFrequency(){
		this.frequency = (this.frequency + 1);
	}
}

