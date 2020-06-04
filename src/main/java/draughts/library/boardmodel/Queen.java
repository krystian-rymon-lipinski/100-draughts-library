package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Hop;
import draughts.library.Capture;
import draughts.library.Move;

public abstract class Queen extends Piece {

	public Queen(Tile position) {
		super(position);
	}
	
	public boolean isQueen() {
		return true;
	}
	
	public ArrayList<Move<Hop>> findMoves(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();
				
		if(currentRow>1 && currentColumn>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_LEFT, board, currentRow, currentColumn));
		if(currentRow>1 && currentColumn<10) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_RIGHT, board, currentRow, currentColumn));
		if(currentRow<10 && currentColumn>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_LEFT, board, currentRow, currentColumn));
		if(currentRow<10 && currentColumn<10)
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_RIGHT, board, currentRow, currentColumn));

		return moves;
	}
	
	public ArrayList<Move<Hop>> findMovesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column) {
		ArrayList<Move<Hop>> moves = new ArrayList<>();
		int distanceInTiles = 1;
		
		while(isMovePossible(moveDirection, row, column, distanceInTiles)) {
			Tile target = findTarget(moveDirection, board, row, column, distanceInTiles);

			if(target.getState() == Tile.State.EMPTY) {
				moves.add(new Move<Hop>(new Hop(getPosition(), target)));
				distanceInTiles++;
			} else break;
			
		}
		
		return moves;
	}
	
	public boolean isMovePossible(MoveDirection moveDirection, int row, int column, int hopLength) {
		switch(moveDirection) {
		case UP_LEFT:
			return (row-hopLength > 0 && column-hopLength > 0);
		case UP_RIGHT:
			return (row-hopLength > 0 && column+hopLength < 11);
		case DOWN_LEFT:
			return (row+hopLength < 11 && column-hopLength > 0);
		case DOWN_RIGHT:
			return (row+hopLength < 11 && column+hopLength < 11);
		default:
			break;
		}
		return false;
	}
	
	public ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column) {
		ArrayList<Capture> moves = new ArrayList<>();
		int hopLength = 1;
		Tile foundPawnToTake = null;
		
		while(isMovePossible(moveDirection, row, column, hopLength)) {
			Tile target = findTarget(moveDirection, board, row, column, hopLength);
			
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == null) 
					hopLength++; 
				else {
					moves.add(new Capture(getPosition(), target, foundPawnToTake));
					hopLength++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target)) {
				break;
			}
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == null) {
					foundPawnToTake = target;
					hopLength++;
				}
				else break;
			}
		}
		
		return moves;
	}
	
}
