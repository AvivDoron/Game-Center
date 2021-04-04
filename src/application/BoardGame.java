package application;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.LabelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;


public abstract class BoardGame extends Game{
	protected Node[] boardViewArr;
	protected int boardSize;
	protected Integer score;
	protected SimpleStringProperty scoreProperty;
	
//    @FXML
    protected GridPane board;

	
	public BoardGame(int size) {
		this.boardSize = size;
		boardViewArr = new Node[boardSize * boardSize];
		score = new Integer(0);
		scoreProperty = new SimpleStringProperty(score.toString());
	}
	
	

	protected class PointInBoard{
		private int x,y;
		
		protected PointInBoard(int x, int y) {
			this.x = x;
			this.y = y;			
		}
				
		public int getX() {
			return x;
		}
		
	
		public int getY() {
			return y;
		}
		
		public int setX(int x) {
			return this.x = x;
		}	
		
		public int setY(int y) {
			return this.y = y;
		}	
		
		public PointInBoard makeCopy() {
			return new PointInBoard(this.getX(), this.getY());
		}
		
	}
	
	
	
	public String getPointColor (int x, int y) {
//		if(x < 1 || x > boardSize || y < 1 || y > boardSize)
//			throw new IndexOutOfBoundsException();
		return board.getChildren().get(boardSize * x + y).getStyle();
	}
	
	public void setPointColor(int x, int y, EStyleColors color) {
		Pane temp = (Pane)boardViewArr[boardSize* x+y];
		temp.setStyle(color.getColorStyle());

	}
	
	
	
	
	

	protected void initializeGameOnBorad() {
		board = new GridPane();
		Scene scene = stage.getScene();
		BorderPane borderPane = (BorderPane) scene.getRoot();
		borderPane.setCenter(board);
		Label label = new Label(String.format("\tscore: "));
		label.setFont(Font.font(24));
		label.setVisible(true);
		

		
		Label scoreLabel = new Label();		
//		SimpleStringProperty scoreStringProperty = new SimpleStringProperty(score.toString());	
		scoreLabel.textProperty().bind(scoreProperty);
		

		
		scoreLabel.setFont(Font.font(24));
		HBox scoreArea = new HBox();
		scoreArea.getChildren().add(label);
		scoreArea.getChildren().add(scoreLabel);

		borderPane.setTop(scoreArea);

//		Scene scene = new Scene(board, 500, 500);
//		stage.setScene(scene);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		for(int i = 0 ; i < boardSize ; i++)
			for(int j = 0 ; j < boardSize ; j++) {				
				board.getColumnConstraints().add(new ColumnConstraints(25));
				board.getRowConstraints().add(new RowConstraints(25));
				Pane pane = new Pane();
				if(!isValidPoint(i, j))
					pane.setStyle(EStyleColors.GREY.getColorStyle());
				else
					pane.setStyle(EStyleColors.BLACK.getColorStyle());
				board.add(pane, j, i);
			}

		boardViewArr = board.getChildren().toArray(boardViewArr);
		
	}

	
	
	
	@Override
	public void startNewGame() {
		initializeGameOnBorad();
		play();
	}


	
	@Override	
	public void resumeGame() {
		
		
	}
	
	protected abstract void play();
	
	protected abstract boolean isValidPoint(int x, int y);
	
	
	/*
	 * @FXML void keyClicked(KeyEvent event) { System.out.println(event.toString());
	 * if(event.getCharacter().equalsIgnoreCase("p")) System.out.println("pause"); }
	 */
	
	
	
	
	
	
	
	
}
