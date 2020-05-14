package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Queen extends Piece {

	public Queen(int position) {
		super(position);
	}
	
	public ArrayList<Move> findMoves(Tile[][] board, int currentRow, int currentColumn) {
		
		ArrayList<Move> moves = new ArrayList<>();
				
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
	
	public ArrayList<Move> findMovesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column) {
		ArrayList<Move> moves = new ArrayList<>();
		int distanceInTiles = 1;
		
		while(isMovePossible(moveDirection, row, column, distanceInTiles)) {
			Tile target = findTarget(moveDirection, board, row, column, distanceInTiles);

			if(target.getState() == Tile.State.EMPTY) {
				moves.add(new Move(getPosition(), target.getIndex()));
				distanceInTiles++;
			} else break;
			
		}
		
		return moves;
	}
	
	public boolean isMovePossible(MoveDirection moveDirection, int row, int column, int distanceInTiles) {
		switch(moveDirection) {
		case UP_LEFT:
			return (row-distanceInTiles > 0 && column-distanceInTiles > 0);
		case UP_RIGHT:
			return (row-distanceInTiles > 0 && column+distanceInTiles < 11);
		case DOWN_LEFT:
			return (row+distanceInTiles < 11 && column-distanceInTiles > 0);
		case DOWN_RIGHT:
			return (row+distanceInTiles < 11 && column+distanceInTiles < 11);
		default:
			break;
		}
		return false;
	}
	
	public ArrayList<Move> findTakesInDirection(MoveDirection moveDirection, Tile[][] board, int row, int column) {
		ArrayList<Move> moves = new ArrayList<>();
		int distanceInTiles = 1;
		int foundPawnToTake = 0;
		
		while(isMovePossible(moveDirection, row, column, distanceInTiles)) {
			Tile target = findTarget(moveDirection, board, row, column, distanceInTiles);
			
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == 0) 
					distanceInTiles++; 
				else {
					moves.add(new Move(getPosition(), target.getIndex(), foundPawnToTake));
					distanceInTiles++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target)) {
				break;
			}
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == 0) {
					foundPawnToTake = target.getIndex();
					distanceInTiles++;
				}
				else break;
			}
		}
		
		return moves;
	}
	
}
