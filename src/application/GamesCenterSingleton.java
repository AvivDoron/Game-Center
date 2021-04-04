package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class GamesCenterSingleton extends AbstractColntroller{
	public static GamesCenterSingleton GamesCenter = null;
	private	GamesFactory gamesFactory = null;

	
	
    @FXML
    private Button snakeButton;


    @FXML
    private BorderPane pane;
    
    
    
	private GamesCenterSingleton() {
		gamesFactory = new GamesFactory();
	}
	
	
	
	
	  public static GamesCenterSingleton getInstance() { 
		  if(GamesCenter == null)
			  GamesCenter = new GamesCenterSingleton();
		  return GamesCenter;
	  
	  }
	 

    @FXML
    void snakeButtonClicked(ActionEvent event) {
    	currentGame = gamesFactory.getGame("SNAKE");
    	currentGame.startNewGame();
    }

    
    
    
	@Override
	public Stage generateUserInterface() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GamesCenter.fxml"));			
			loader.setController(this);
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("GameCenter"); 
			stage.setScene(scene);		 
			return stage;
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
