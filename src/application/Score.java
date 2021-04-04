package application;

public class Score{
	private Integer amount;
	
	public Score() {
		this.amount = 0;
	}
	
	public Integer get() {
		return amount;
	}
	
	
	public void raise() {
		amount++;
	}
	
	public void reset() {
		amount = 0;
				
	}
	
	
}
