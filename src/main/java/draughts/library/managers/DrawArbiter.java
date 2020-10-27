package draughts.library.managers;

public class DrawArbiter {
	
	private int drawCounter;
	private DrawConditions drawConditions;
	
	public DrawArbiter() {
		this.drawCounter = 50; //25 moves for each player
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
			if(!bothColorsHaveQueen) break;
			else {
				if(numberOfWhitePieces > 1 && numberOfBlackPieces > 1) {
					drawConditions = DrawConditions.NORMAL;
					drawCounter = 50;
					break;
				}
				else {
					if (numberOfWhitePieces > 3 || numberOfBlackPieces > 3) {
						drawConditions = DrawConditions.NORMAL;
						drawCounter = 50;
					}
					else if(numberOfWhitePieces == 3 || numberOfBlackPieces == 3) {
						drawConditions = DrawConditions.THREE_VS_ONE;
						drawCounter = 32;
						break;
					}
					else {
						drawConditions = DrawConditions.TWO_VS_ONE; // 1 queen vs 1 queen is also 2_VS_1 conditions
						drawCounter = 10;
						break;
					}
				}
			}
		case NORMAL:
			if(!bothColorsHaveQueen) {
				drawConditions = DrawConditions.NONE;
				drawCounter = 50;
				break;
			}
			else {
				if(numberOfWhitePieces > 1 && numberOfBlackPieces > 1) break;
				else {
					if(numberOfWhitePieces == 3 || numberOfBlackPieces == 3) {
						drawConditions = DrawConditions.THREE_VS_ONE;
						drawCounter = 32;
						break;
					}
					else {
						drawConditions = DrawConditions.TWO_VS_ONE; // 1 queen vs 1 queen is also 2_VS_1 conditions
						drawCounter = 10;
					}
				}
			}		
		case THREE_VS_ONE:
			if(numberOfWhitePieces <= 2 && numberOfBlackPieces <= 2) {
				drawConditions = DrawConditions.TWO_VS_ONE;
				drawCounter = 10;
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
