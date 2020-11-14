package draughts.library.managers;

public class DrawArbiter {

	private final static int DRAW_COUNTER_NORMAL_CONDITIONS = 50;
	private final static int DRAW_COUNTER_3V1_CONDITIONS = 32;
	private final static int DRAW_COUNTER_2V1_CONDITIONS = 10;

	private int drawCounter;
	private DrawConditions drawConditions;
	
	public DrawArbiter() {
		this.drawCounter = DRAW_COUNTER_NORMAL_CONDITIONS; //25 moves for each player
		this.drawConditions = DrawConditions.NONE;
	}
	
	public int getDrawCounter() {
		return drawCounter;
	}

	public void setDrawCounter(int drawCounter) {
		this.drawCounter = drawCounter;
	}

	public DrawConditions getDrawConditions() {
		return drawConditions;
	}

	public void setDrawConditions(DrawConditions drawConditions) {
		this.drawConditions = drawConditions;
	}
	
	public void updateConditions(boolean bothColorsHaveQueen, int numberOfWhitePieces, int numberOfBlackPieces) {
		switch(drawConditions) {
		case NONE: 
			if(bothColorsHaveQueen) {
				if(numberOfWhitePieces > 1 && numberOfBlackPieces > 1) {
					drawConditions = DrawConditions.NORMAL;
					drawCounter = DRAW_COUNTER_NORMAL_CONDITIONS;
				}
				else {
					if (numberOfWhitePieces > 3 || numberOfBlackPieces > 3) {
						drawConditions = DrawConditions.NORMAL;
						drawCounter = DRAW_COUNTER_NORMAL_CONDITIONS;
					}
					else if(numberOfWhitePieces == 3 || numberOfBlackPieces == 3) {
						drawConditions = DrawConditions.THREE_VS_ONE;
						drawCounter = DRAW_COUNTER_3V1_CONDITIONS;
					}
					else {
						drawConditions = DrawConditions.TWO_VS_ONE; // 1 queen vs 1 queen is also 2_VS_1 conditions
						drawCounter = DRAW_COUNTER_2V1_CONDITIONS;
					}
				}
			}
			break;
		case NORMAL:
			if(!bothColorsHaveQueen) {
				drawConditions = DrawConditions.NONE;
				drawCounter = DRAW_COUNTER_NORMAL_CONDITIONS;
			}
			else {
				if(numberOfWhitePieces == 1 || numberOfBlackPieces == 1) {
					if (numberOfWhitePieces > 3 || numberOfBlackPieces > 3) {
						break;
					}
					if(numberOfWhitePieces == 3 || numberOfBlackPieces == 3) {
						drawConditions = DrawConditions.THREE_VS_ONE;
						drawCounter = DRAW_COUNTER_3V1_CONDITIONS;
					}
					else {
						drawConditions = DrawConditions.TWO_VS_ONE; // 1 queen vs 1 queen is also 2_VS_1 conditions
						drawCounter = DRAW_COUNTER_2V1_CONDITIONS;
					}
				}
			}
			break;
		case THREE_VS_ONE:
			if(numberOfWhitePieces <= 2 && numberOfBlackPieces <= 2) {
				drawConditions = DrawConditions.TWO_VS_ONE;
				drawCounter = DRAW_COUNTER_2V1_CONDITIONS;
			}
			break;
		case TWO_VS_ONE:
		default:
			break;
		}		
	}
	
	public void updateCounter(boolean isMoveCapture, boolean isMoveMadeByQueen) {
		switch(drawConditions) {
			case THREE_VS_ONE:
			case TWO_VS_ONE:
				drawCounter--;
				break;
				
			case NORMAL:
				if(isMoveCapture || !isMoveMadeByQueen) drawCounter = 50;
				else drawCounter--;
				break;
				
			default: break;	
		}
	}
	
	
	public boolean isGameDrawn() {
		return drawCounter == 0;
	}
	
	public enum DrawConditions {
		NONE,
		NORMAL,
		THREE_VS_ONE,
		TWO_VS_ONE,
		POSITION_REPEATED_THRICE
	}

}
