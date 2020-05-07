package draughts.library.boardmodel;

public class Tile {
	
	private int index;
	
	public Tile(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getRow() {
		return (index-1)/(Board.TILES_IN_ROW/2) + 1;
	}
	
	public boolean isRowEven() {
		return getRow()%2 == 0 ? true : false;
	}
	
	public int getColumn() {
		int column = (index*2-1)%Board.TILES_IN_ROW;
		if(isRowEven()) return column;
		else return column+1;
	}
	
	public boolean isTileMostLeftInRow() {
		return index % (Board.TILES_IN_ROW/2) == 1 ? true : false;
	}
	
	public boolean isTileMostRightInRow() {
		return index % (Board.TILES_IN_ROW/2) == 0 ? true : false;
	}

}
