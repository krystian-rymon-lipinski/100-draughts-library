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
			addMovesIfAny(moves, findUpLeftMoves(board, currentRow, currentColumn));
		if(currentRow>1 && currentColumn<10) 
			addMovesIfAny(moves, findUpRightMoves(board, currentRow, currentColumn));
		if(currentRow<10 && currentColumn>1) 
			addMovesIfAny(moves, findDownLeftMoves(board, currentRow, currentColumn));
		if(currentRow<10 && currentColumn<10)
			addMovesIfAny(moves, findDownRightMoves(board, currentRow, currentColumn));
		
		return moves;
	}
	
	//////////////////////////////////////////////////////
	//Moves
	
	public ArrayList<Move> findUpLeftMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> upLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row-moveToCheck > 0 && column-moveToCheck > 0) {
			Tile target = board[row-1-moveToCheck][column-1-moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				upLeftMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++; 
			} else break;
		}
		
		return upLeftMoves;	
	}
	
	public ArrayList<Move> findUpRightMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> upRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row-moveToCheck > 0 && column+moveToCheck < 11) {
			Tile target = board[row-1-moveToCheck][column-1+moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				upRightMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
		}
		
		return upRightMoves;
	}
	
	public ArrayList<Move> findDownLeftMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> downLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row+moveToCheck < 11 && column-moveToCheck > 0) {
			Tile target = board[row-1+moveToCheck][column-1-moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				downLeftMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
		}
		
		return downLeftMoves;
	}
	
	public ArrayList<Move> findDownRightMoves(Tile[][] board, int row, int column) {
		ArrayList<Move> downRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		
		while(row+moveToCheck < 11 && column+moveToCheck < 11) {
			Tile target = board[row-1+moveToCheck][column-1+moveToCheck];
			if(target.getState() == Tile.State.EMPTY) {
				downRightMoves.add(new Move(getPosition(), target.getIndex()));
				moveToCheck++;
			} else break;
			
		}
		
		return downRightMoves;
	}
	
	//////////////////////////////////////////////////////////////
	//Takes
	
	
	public ArrayList<Move> findUpLeftTakes(Tile[][] board, int row, int column) {
		ArrayList<Move> upLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		int foundPawnToTake = 0;
				
		while(row-moveToCheck > 0 && column-moveToCheck > 0) {
			Tile target = board[row-1-moveToCheck][column-1-moveToCheck];
					
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == 0) 
					moveToCheck++; 
				else {
					upLeftMoves.add(new Move(getPosition(), target.getIndex(), foundPawnToTake));
					moveToCheck++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target)) {
				break;
			}
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == 0) {
					foundPawnToTake = target.getIndex();
					moveToCheck++;
				}
				else break;
			}
		}
		return upLeftMoves;	
	}
	
	public ArrayList<Move> findUpRightTakes(Tile[][] board, int row, int column) {
		ArrayList<Move> upRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		int foundPawnToTake = 0;
		
		while(row-moveToCheck > 0 && column+moveToCheck < 11) {
			Tile target = board[row-1-moveToCheck][column-1+moveToCheck];
			
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == 0) 
					moveToCheck++; 
				else {
					upRightMoves.add(new Move(getPosition(), target.getIndex(), foundPawnToTake));
					moveToCheck++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target))
				break;
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == 0) {
					foundPawnToTake = target.getIndex();
					moveToCheck++;
				}
				else break;
			}
		}
		
		return upRightMoves;
	}
	
	public ArrayList<Move> findDownLeftTakes(Tile[][] board, int row, int column) {
		ArrayList<Move> downLeftMoves = new ArrayList<>();
		int moveToCheck = 1;
		int foundPawnToTake = 0;
		
		while(row+moveToCheck < 11 && column-moveToCheck > 0) {
			Tile target = board[row-1+moveToCheck][column-1-moveToCheck];
			
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == 0) 
					moveToCheck++; 
				else {
					downLeftMoves.add(new Move(getPosition(), target.getIndex(), foundPawnToTake));
					moveToCheck++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target))
				break;
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == 0) {
					foundPawnToTake = target.getIndex();
					moveToCheck++;
				}
				else break;
			}
		}
		
		return downLeftMoves;
	}
	
	public ArrayList<Move> findDownRightTakes(Tile[][] board, int row, int column) {
		ArrayList<Move> downRightMoves = new ArrayList<>();
		int moveToCheck = 1;
		int foundPawnToTake = 0;
		
		while(row+moveToCheck < 11 && column+moveToCheck < 11) {
			Tile target = board[row-1+moveToCheck][column-1+moveToCheck];
			
			if(target.getState() == Tile.State.EMPTY) {
				if(foundPawnToTake == 0) 
					moveToCheck++; 
				else {
					downRightMoves.add(new Move(getPosition(), target.getIndex(), foundPawnToTake));
					moveToCheck++;
				}			
			} 
			else if(isTileOccupiedBySameColor(target))
				break;
			else if(isTileOccupiedByOppositeColor(target)){
				if(foundPawnToTake == 0) {
					foundPawnToTake = target.getIndex();
					moveToCheck++;
				}
				else break;
			}
			
		}
		
		return downRightMoves;
	}
	

}
