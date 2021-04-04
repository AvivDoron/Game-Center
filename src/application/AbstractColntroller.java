package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

public abstract class AbstractColntroller {
	// private GamesCenterSingleton gameCenterSingleton;
	protected Stage stage;
	protected Game currentGame = null;
	protected Popup popup;

	
	
	public AbstractColntroller() {
		intializePopup();
		stage = generateUserInterface();
		stage.show();
	}
	
	
	
	protected void intializePopup() {
		this.popup = new Popup();
		Label label = new Label();
		label.setFont(Font.font(50));
		label.setStyle(" -fx-text-fill: white;");
		label.setTranslateY(120);
		popup.getContent().add(label);

	}
	
	protected Label getPopupLabel() {
		return (Label)popup.getContent().get(0);
	}

	protected abstract Stage generateUserInterface();

	
	
	public Stage getStage() {
		return stage;
	}

	
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}



}
