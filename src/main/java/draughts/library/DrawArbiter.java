package draughts.library;

public class DrawArbiter {
	
	private int drawCounter;
	private DrawConditions drawConditions;
	
	public DrawArbiter() {
		this.drawCounter = 50; //25 moves for each player
		this.drawConditions = DrawConditions.NORMAL;
	}
	
	public int getDrawCounter() {
		return drawCounter;
	}
	
	public DrawConditions getDrawConditions() {
		return drawConditions;
	}
	
	public boolean isGameDrawn() {
		return (drawCounter == 0) ? true : false;
	}
	
	public void resetDrawCounter() {
		switch(drawConditions) {
			case NORMAL:
				drawCounter = 25*2;
				break;
			case THREE_VS_ONE:
				drawCounter = 16*2;
				break;
			case TWO_VS_ONE:
				drawCounter = 5*2;
				break;
			default:
			break;
		}
	}
	
	public void setDrawConditions(DrawConditions conditions) {
		switch(conditions) {
			case NORMAL:
				this.drawConditions = conditions;
				drawCounter = 25*2;
				break;
			case THREE_VS_ONE:
				this.drawConditions = conditions;
				drawCounter = 16*2;
				break;
			
			case TWO_VS_ONE:
				this.drawConditions = conditions;
				drawCounter = 5*2;
				break;
			default:break;
		}
	}
	
	public enum DrawConditions {
		NORMAL,
		THREE_VS_ONE,
		TWO_VS_ONE,
		POSITION_REPEATED_THRICE
	}

}
