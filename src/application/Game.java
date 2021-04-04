package application;



import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.util.Random;


import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public abstract class Game extends AbstractColntroller{
	protected String gameName;  
	protected Random random;
	private boolean isGamePaused;
	protected Thread gamePlay;
	
	
	public Game() {
		super();
		this.random = new Random();
	}
	
	
	protected class KeyboardEventHandler implements EventHandler<KeyEvent>{

		@Override
		public void handle(KeyEvent event) {
			if(event == null)
				return;
			
			if(event.getCode().isArrowKey()) { 
				arrowKeyClicked(event.getCode());
			}
			
		
			else {
				String clickedKeyName = event.getText();				
				if(clickedKeyName == null || clickedKeyName.isEmpty()) {
					event.consume();
					return;
				}
				if(clickedKeyName.equalsIgnoreCase("P")) {
					pauseGame();		
					
				}
			}
			event.consume();

		}	
			
			
			
//				switch(event.getCode()) {
//				
//				case UP:
//					upKeyClicked(KeyCode.UP);
//				break;
//				
//				case DOWN:
//					downKeyClicked();
//				break;					
//				
//				case LEFT:
//					leftKeyClicked();
//				break;					
//				
//				case RIGHT:
//					rightKeyClicked();
//				break;
//				
//				default:						
//					String clickedKeyName = event.getText();		
//					if(clickedKeyName == null || clickedKeyName.isEmpty()) {
//						System.out.println("empty");
//						return;
//					}
//					if(clickedKeyName.equalsIgnoreCase("P"))
//						pauseGame();		
//				}
//			
//		}

	}
	
	
	protected abstract void startNewGame();	
	protected abstract void arrowKeyClicked(KeyCode key);
	
	
	
	public String getGameName() { 
		String temp = getClass().getName();
		temp = temp.substring((temp.lastIndexOf(Character.valueOf('.')))+1);
		return temp;
	  }
	 

	protected synchronized void makeGameStep() {
		
	}
	
	

		
	protected void pauseGame() {
		
		isGamePaused = true;
		Label label = getPopupLabel();
		label.setText("Pause");
		popup.show(stage);
		
	}
	
	protected abstract void resumeGame();
		
//	protected abstract void upKeyClicked();
	
//	protected abstract void downKeyClicked();	
	
//	protected abstract void leftKeyClicked();
	
//	protected abstract void rightKeyClicked();
	
	protected abstract boolean gameIsEnded();

	
	@Override
	public Stage generateUserInterface() {
		gameName = getGameName();
		
		try {
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardGame.fxml"));			
			//loader.setController(this);
			//Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			BorderPane borderPane = new BorderPane();
			Scene scene = new Scene(borderPane, 500, 535);
			stage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
		//	Scene scene = new Scene(null,500,500);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.addEventHandler(KeyEvent.KEY_PRESSED, new KeyboardEventHandler());
			
//			stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//
//				@Override
//				public void handle(KeyEvent event) {
//
//					switch(event.getCode()) {
//					
//					case UP:
//						 System.out.println("UP");//toggle up key clicked method
//					break;
//					
//					case DOWN:
//						 System.out.println("DOWN");//toggle up key clicked method
//					break;					
//					
//					case LEFT:
//						 System.out.println("LEFT");//toggle up key clicked method
//					break;					
//					
//					case RIGHT:
//						 System.out.println("RIGHT");//toggle up key clicked method
//					break;
//					
//					default:						
//						String clickedKeyName = event.getText();		
//						if(clickedKeyName == null || clickedKeyName.isEmpty()) {
//							System.out.println("empty");
//							return;
//						}
//						if(clickedKeyName.equalsIgnoreCase("P"))
//							pauseGame();		
//					}											
//					event.consume();	
//				}			
//			});
//			
			
			
			
			stage.setTitle(gameName); 
			//stage.setScene(scene);		 
			return stage;
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
}
