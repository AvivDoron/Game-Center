package application;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.Hashtable;

public class Snake extends BoardGame{
	private PointInBoard headOfSnake;
	private PointInBoard currentApple;
	private Queue<PointInBoard> snakeQueue;
	private Hashtable<KeyCode, SnakeDirection> directions;
	private SnakeDirection currentDirection;
	private Score currentScore;
	private GameLevel gameSpeed;
	private boolean isGameOver;
	private boolean isAppleExist;
	
	
	public Snake() {
		super(20);
		initializeHashDirections();
		this.headOfSnake = new PointInBoard((boardSize/2-1), (boardSize/2+1));
		this.snakeQueue = new LinkedList<PointInBoard>();
		this.isGameOver = false;
		this.isAppleExist = false;
		this.currentDirection = new SnakeDirection();
		this.currentScore = new Score();
		this.gameSpeed = new GameLevel();
		
	}

	private enum EGameUtils{
		APPLES_IN_LEVEL(10),
		MAX_LEVEL(5),
		INITIAL_STEP_FREQUENCY(5),
		DIRECTIONS_NUMBER(4);
		
		public final int num;
		
		private EGameUtils(int num) {
			this.num = num;
		}
		
		private enum ESnakeColors{
			SNAKE_BODY_COLOR(EStyleColors.GREEN),
			APPLE_COLOR(EStyleColors.RED);	
			
			public final EStyleColors color;	
			
			private ESnakeColors(EStyleColors color) {
				this.color = color;
			}
			
			public String getValue() {
				return color.getColorStyle();
			}
			
			public EStyleColors toStyleColor() {
				return color;
			}
		
		}
				
			public int getValue() {
				return num;
			}		
				
	}

	
	private class GameLevel{
		private int currentStepFrquency;
		private int milisecsBetweenSteps;
		private int nextLevelScore;
		private int level;
		
		public GameLevel() {
			this.currentStepFrquency = EGameUtils.INITIAL_STEP_FREQUENCY.getValue();
			this.milisecsBetweenSteps = 300;
			this.nextLevelScore = EGameUtils.APPLES_IN_LEVEL.getValue();
			this.level = 1;
		}
		
		public int getcurrentStepFrquency() {
			return currentStepFrquency;
		}
		
		public int getMilisecsBetweenSteps() {
			return milisecsBetweenSteps;
		}
		
		public boolean isSpeedupAllowed() {
			return currentScore.get() == nextLevelScore;
		}
		
		public void nextLevel() {
			if(level < EGameUtils.MAX_LEVEL.getValue()) {
				level++;
				nextLevelScore *= level;
				milisecsBetweenSteps -= 100;
			}
		}
	}
	
	
	private class SnakeDirection{
		private int directionX, directionY;

		public SnakeDirection(int horizontal, int vertical) {
			this.directionX = horizontal;
			this.directionY = vertical;
		}
		
		public SnakeDirection() {
			this.directionX = 0;
			this.directionY = 0;
		}
		
		public int getDirectionX() {
			return directionX;
		}

		public void setDirectionX(int directionX) {
			this.directionX = directionX;
		}

		public int getDirectionY() {
			return directionY;
		}

		public void setDirectionY(int directionY) {
			this.directionY = directionY;
		}
		
		public boolean isOpositeTo(SnakeDirection direction) {
			return (this.directionX == -direction.getDirectionX() || this.directionY == -direction.getDirectionY());
		}
	}
	
	
	private void initializeHashDirections() {
		directions = new Hashtable<>(EGameUtils.DIRECTIONS_NUMBER.getValue());
		directions.put(KeyCode.UP, new SnakeDirection(-1,0));
		directions.put(KeyCode.DOWN, new SnakeDirection(1,0));
		directions.put(KeyCode.LEFT, new SnakeDirection(0,-1));
		directions.put(KeyCode.RIGHT, new SnakeDirection(0,1));
	}
	
	

	@Override
	protected void play() {
		gamePlay = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!isGameOver) {
					try {
						Thread.sleep(gameSpeed.getMilisecsBetweenSteps());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!isAppleExist) {
						generateApple();
						if(gameSpeed.isSpeedupAllowed())
							gameSpeed.nextLevel();
					}
					makeSnakeStep();					
				}
				Platform.runLater(()->{
					Label label = getPopupLabel();
					label.setText(String.format("Game Over\nYour score: %d",currentScore.get()));
					popup.show(stage);	
				});
				
			}	
		});
			
		setPointColor(headOfSnake.getX(), headOfSnake.getY(),EGameUtils.ESnakeColors.SNAKE_BODY_COLOR.toStyleColor());
		snakeQueue.add(new PointInBoard(headOfSnake.getX(), headOfSnake.getY()));

		gamePlay.start();
	}

	
	private void makeSnakeStep() {

		if(!isPointWithApple(headOfSnake.getX(), headOfSnake.getY())) {
			PointInBoard snakeTail = snakeQueue.poll();
			setPointColor(snakeTail.getX(),snakeTail.getY(),EStyleColors.BLACK);
		}
		else {
			eatApple();

			
		}
		
		updateHeadOfSnake();
		if(!isValidPoint(headOfSnake.getX(), headOfSnake.getY()) || isPointInSnakeBody(headOfSnake.getX(), headOfSnake.getY())) {
			isGameOver = true;
			return;
		}	

		setPointColor(headOfSnake.getX(),headOfSnake.getY(),EGameUtils.ESnakeColors.SNAKE_BODY_COLOR.toStyleColor());		
		snakeQueue.add(headOfSnake.makeCopy());
	}

	
	private void updateHeadOfSnake() {
		headOfSnake.setX(headOfSnake.getX() + currentDirection.getDirectionX());
		headOfSnake.setY(headOfSnake.getY() + currentDirection.getDirectionY());		
	}
	
	
	
	
	
//	private void updateHeadOfSnake() {
//		PointInBoard nextHead = headOfSnake.makeCopy();
//		nextHead.setX(headOfSnake.getX() + currentDirection.getDirectionX());
//		nextHead.setY(headOfSnake.getY() + currentDirection.getDirectionY());
//		headOfSnake = nextHead;
//		setPointColor(headOfSnake.getX(),headOfSnake.getY(),EStyleColors.RED);		
//		snakeQueue.add(nextHead);
//		
//	}
	
	
	private void eatApple() {
		isAppleExist = false;
		currentScore.raise();
		Platform.runLater(()->{
			scoreProperty.set(currentScore.get().toString());			
		});

	}
	
	
	
	private boolean isPointWithApple(int x, int y) {
		return (x == currentApple.getX() && y == currentApple.getY());
	}
	
	private void generateApple() {
		int x, y;
		do {
		x = random.nextInt(boardSize);
		y =  random.nextInt(boardSize) + 1;
		}
		while(!(getPointColor(x,y).equals(EStyleColors.BLACK.getColorStyle())));
		
		if(currentApple == null)
			currentApple = new PointInBoard(x,y);
		else {
			currentApple.setX(x);
			currentApple.setY(y);

		}
		setPointColor(x,y,EGameUtils.ESnakeColors.APPLE_COLOR.toStyleColor());
		isAppleExist = true;
	}

	
	private boolean isGameActive() {
		return (currentDirection.getDirectionX() != 0 || currentDirection.getDirectionY() != 0);
	}
	
	
	@Override
	protected void arrowKeyClicked(KeyCode key) {
		if(!directions.get(key).isOpositeTo(currentDirection) || !(isGameActive())) {
			currentDirection.setDirectionX(directions.get(key).getDirectionX());
			currentDirection.setDirectionY(directions.get(key).getDirectionY());
		}		
	}
	
//	
//	@Override
//	public void upKeyClicked(KeyCode key) {
//		if(!directions.get("UP").isOpositeTo(currentDirection)) {
//			currentDirection.setDirectionX(directions.get(key).getDirectionX());
//			currentDirection.setDirectionY(directions.get(key).getDirectionY());
//		}
//	}
//
//
//	@Override
//	public void downKeyClicked() {
//		if(!directions.get("DOWN").isOpositeTo(currentDirection)) {
//			currentDirection.setDirectionX(directions.get("DOWN").getDirectionX());
//			currentDirection.setDirectionY(directions.get("DOWN").getDirectionY());
//		}
//	}
//
//
//	@Override
//	public void leftKeyClicked() {
//		if(!directions.get("LEFT").isOpositeTo(currentDirection)) {
//			currentDirection.setDirectionX(directions.get("LEFT").getDirectionX());
//			currentDirection.setDirectionY(directions.get("LEFT").getDirectionY());
//		}
//	}
//
//
//	@Override
//	public void rightKeyClicked() {
//		if(!directions.get("RIGHT").isOpositeTo(currentDirection)) {
//			currentDirection.setDirectionX(directions.get("RIGHT").getDirectionX());
//			currentDirection.setDirectionY(directions.get("RIGHT").getDirectionY());
//		}
//	}


	@Override
	protected boolean isValidPoint(int x, int y) {
		return ((x > 0) && (x < boardSize - 1) && (y > 0) && (y < boardSize - 1));
	}


	@Override
	protected boolean gameIsEnded() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isPointInSnakeBody(int x, int y) {
		return getPointColor(x, y).equals(EGameUtils.ESnakeColors.SNAKE_BODY_COLOR.getValue());	
	}













	
	
	
	
	

	
}
