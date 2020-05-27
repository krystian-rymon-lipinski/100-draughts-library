package draughts.library.boardmodel;

public class Tile {
	
	private int index;
	private State state;
	
	public Tile(int index) {
		this.index = index;
	}
	
	public Tile(int row, int column) {
		this.index = calculateIndex(row, column);
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
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

	public static int calculateIndex(int row, int column) {
		if(row % 2 == 0) {
			if(column % 2 == 0)
				return 0; //white tile in even row
			else return (row-1)*5 + ((column+1)/2);
		} else {
			if(column % 2 == 0) //
				return (row-1)*5 + column / 2; //white tile in odd row
			else 
				return 0;	
		}
	}
	
	public enum State {
		EMPTY, //empty black tile
		WHITE_PAWN,
		BLACK_PAWN,
		WHITE_QUEEN,
		BLACK_QUEEN,
		WHITE_TILE;
	}
	
}
