package draughts.library.boardmodel;

import java.util.Objects;

public class Tile {
	
	private int index;
	private State state;
	
	
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

	private int calculateIndex(int row, int column) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Tile tile = (Tile) o;
		return index == tile.index &&
				state == tile.state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(index, state);
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
