package draughts.library.boardmodel;

import java.util.ArrayList;
import java.util.Arrays;

import draughts.library.Move;

public abstract class Pawn extends Piece {

	public Pawn(int position) {
		super(position);
	}
	
	public abstract Move findLeftMove(Tile[][] board, int row, int column);
	
	public abstract Move findRightMove(Tile[][] board, int row, int column);
	
	public ArrayList<Move> findMoves(Tile[][] board, int row, int column) {
	
		ArrayList<Move> moves = new ArrayList<Move>();
	
		if(column > 1)
			addMoveIfNotNull(moves, findLeftMove(board, row, column));
		if(column < 10) 
			addMoveIfNotNull(moves, findRightMove(board, row, column));
		return moves;
	}
	
	public ArrayList<Move> findUpLeftTakes(Tile[][] board, int row, int column) {
		Tile target = board[row-1-2][column-1-2];
		Tile possibleTake = board[row-1-1][column-1-1];
		
		return checkForTake(target, possibleTake, row, column);
			
	}
	
	public ArrayList<Move> findUpRightTakes(Tile board[][], int row, int column) {
		Tile target = board[row-1-2][column-1+2];
		Tile possibleTake = board[row-1-1][column-1+1];
		
		return checkForTake(target, possibleTake, row, column);
	}
	
	public ArrayList<Move> findDownLeftTakes(Tile board[][], int row, int column) {
		Tile target = board[row-1+2][column-1-2];
		Tile possibleTake = board[row-1+1][column-1-1];
		
		return checkForTake(target, possibleTake, row, column);
	}
	
	public ArrayList<Move> findDownRightTakes(Tile board[][], int row, int column) {
		Tile target = board[row-1+2][column-1+2];
		Tile possibleTake = board[row-1+1][column-1+1];
		
		return checkForTake(target, possibleTake, row, column);
	}	
	
	public boolean isTakePossible(Tile target, Tile possibleTake, int row, int column) {
		return isTileOccupiedByOppositeColor(possibleTake) && 
				   target.getState() == Tile.State.EMPTY ? true : false;
	}
	
	public ArrayList<Move> checkForTake(Tile target, Tile possibleTake, int row, int column) {
		if(isTakePossible(target, possibleTake, row, column)) {
			Move move = new Move(Tile.calculateIndex(row, column), 
							    target.getIndex(), 
							    possibleTake.getIndex());
			return new ArrayList<Move>(Arrays.asList(move));
		}
		else return null;
	}

}
