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
	
	public ArrayList<Move<Hop>> findMoves(Tile[][] board) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();
				
		if(getPosition().getRow()>1 && getPosition().getColumn()>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_LEFT, board));
		if(getPosition().getRow()>1 && getPosition().getColumn()<10) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.UP_RIGHT, board));
		if(getPosition().getRow()<10 && getPosition().getColumn()>1) 
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_LEFT, board));
		if(getPosition().getRow()<10 && getPosition().getColumn()<10)
			addMovesIfAny(moves, findMovesInDirection(MoveDirection.DOWN_RIGHT, board));

		return moves;
	}
	
	public ArrayList<Move<Hop>> findMovesInDirection(MoveDirection moveDirection, Tile[][] board) {
		ArrayList<Move<Hop>> moves = new ArrayList<>();
		int hopLength = 1;
		
		while(isMovePossible(moveDirection, hopLength)) {
			Tile target = findTarget(moveDirection, board, hopLength);

			if(target.getState() == Tile.State.EMPTY) {
				moves.add(new Move<Hop>(this, new Hop(getPosition(), target)));
				hopLength++;
			} else break;
			
		}
		
		return moves;
	}
	
	public boolean isMovePossible(MoveDirection moveDirection, int hopLength) {
		switch(moveDirection) {
		case UP_LEFT:
			return (getPosition().getRow()-hopLength > 0 && getPosition().getColumn()-hopLength > 0);
		case UP_RIGHT:
			return (getPosition().getRow()-hopLength > 0 && getPosition().getColumn()+hopLength < 11);
		case DOWN_LEFT:
			return (getPosition().getRow()+hopLength < 11 && getPosition().getColumn()-hopLength > 0);
		case DOWN_RIGHT:
			return (getPosition().getRow()+hopLength < 11 && getPosition().getColumn()+hopLength < 11);
		default:
			break;
		}
		return false;
	}
	
	public ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, Tile[][] board) {
		ArrayList<Capture> moves = new ArrayList<>();
		int hopLength = 1;
		Tile foundPawnToTake = null;
		
		while(isMovePossible(moveDirection, hopLength)) {
			Tile target = findTarget(moveDirection, board, hopLength);
			
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
