package application;

public class GamesFactory {

	public Game getGame(String gameType) {
			
		if(gameType == null)
			return null;
		
		if(gameType.equalsIgnoreCase("SNAKE"))		
			return new Snake();
		
			
			
			
			
		return null;	
			
	}
}
