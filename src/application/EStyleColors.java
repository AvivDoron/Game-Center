package application;

public enum EStyleColors {
	BLACK("-fx-background-color:  black"),
	GREEN("-fx-background-color:  green"),
	ORANGE("-fx-background-color:  orange"),
	YELLOW("-fx-background-color:  yellow"),
	GREY("-fx-background-color:  grey"),
	RED("-fx-background-color:  red");
	
	public final String color;
	
	private EStyleColors(String color) {
		this.color = color;
	}
	
	public String getColorStyle() {
		return color;
	}
	
}
