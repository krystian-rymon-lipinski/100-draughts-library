package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.Move;

public abstract class Queen extends Piece {

	public Queen(int position) {
		super(position);
	}
	
	public ArrayList<Move> findMoves(Tile[][] board, Tile currentPosition) {
		
		int row = currentPosition.getRow();
		int column = currentPosition.getColumn();
		ArrayList<Move> moves = new ArrayList<>();
		
		ArrayList<Move> upLeftMoves = new ArrayList<>();
		ArrayList<Move> upRightMoves = new ArrayList<>();
		ArrayList<Move> downLeftMoves = new ArrayList<>();
		ArrayList<Move> downRightMoves = new ArrayList<>();
		
		if(row>1 && column>1) upLeftMoves = findUpLeftMoves(board, row, column);
		if(row>1 && column<10) upRightMoves = findUpRightMoves(board, row, column);
		if(row<10 && column>1) downLeftMoves = findDownLeftMoves(board, row, column);
		if(row<10 && column<10) downRightMoves = findDownRightMoves(board, row, column);
		
		if(upLeftMoves.size() > 0) moves.addAll(upLeftMoves);
		if(upRightMoves.size() > 0) moves.addAll(upRightMoves);
		if(downLeftMoves.size() > 0) moves.addAll(downLeftMoves);
		if(downRightMoves.size() > 0) moves.addAll(downRightMoves);
		
		
		return moves;
	}
	
	public ArrayList<Move> findTakes(Tile[][] board, Tile currentPosition) {
		int row = currentPosition.getRow();
		int column = currentPosition.getColumn();
		ArrayList<Move> moves = new ArrayList<>();
		
		ArrayList<Move> upLeftTakes = new ArrayList<>();
		ArrayList<Move> upRightTakes = new ArrayList<>();
		ArrayList<Move> downLeftTakes = new ArrayList<>();
		ArrayList<Move> downRightTakes = new ArrayList<>();
		
		if(row>2 && column>2) upLeftTakes = findUpLeftTakes(board, row, column);
		if(row>2 && column<9) upRightTakes = findUpRightTakes(board, row, column);
		if(row<9 && column>2) downLeftTakes = findDownLeftTakes(board, row, column);
		if(row<9 && column<9) downRightTakes = findDownRightTakes(board, row, column);

		if(upLeftTakes.size() > 0) moves.addAll(upLeftTakes);
		if(upRightTakes.size() > 0) moves.addAll(upRightTakes);
		if(downLeftTakes.size() > 0) moves.addAll(downLeftTakes);
		if(downRightTakes.size() > 0) moves.addAll(downRightTakes);
		
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
