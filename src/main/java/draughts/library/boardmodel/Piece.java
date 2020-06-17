package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public abstract class Piece {
	
	protected Tile position;
	
	public Piece(Tile position) {
		this.position = position;
	}

	public abstract boolean isQueen();
	
	public abstract boolean isWhite();
	
	public abstract boolean isTileOccupiedBySameColor(Tile tile);
	
	public abstract boolean isTileOccupiedByOppositeColor(Tile tile);
	
	public abstract ArrayList<Move<Hop>> findMoves(Tile[][] board);
	
	public abstract ArrayList<Move<Hop>> findMovesInDirection(MoveDirection moveDirection, Tile[][] board);
	
	public abstract ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, Tile[][] board, ArrayList<ArrayList<Piece>> pieces);
	
	
	

	public Tile getPosition() {
		return position;
	}

	public void setPosition(Tile position) {
		this.position = position;
	}
	
	public void hop(Tile dst) {
		this.position = dst;
	}
	
	public void addMovesIfAny(ArrayList<Move<Hop>> mainList, ArrayList<Move<Hop>> candidateList) {
		if(candidateList != null && candidateList.size() > 0)
			mainList.addAll(candidateList);
	}
	
	public void addMoveIfNotNull(ArrayList<Move<Hop>> mainList, Move<Hop> candidateMove) {
		if(candidateMove != null)
			mainList.add(candidateMove);
	}
	
	public void addHopsIfAny(ArrayList<Capture> mainList, ArrayList<Capture> candidateList) {
		if(candidateList != null && candidateList.size() > 0)
			mainList.addAll(candidateList);
	}
			
	public ArrayList<Capture> findCaptures(Tile[][] board, ArrayList<ArrayList<Piece>> pieces) {
		
		ArrayList<Capture> moves = new ArrayList<>();
		
		if(position.getColumn() > 2 && position.getRow() > 2) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.UP_LEFT, board, pieces));
		if(position.getColumn() < 9 && position.getRow() > 2) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.UP_RIGHT, board, pieces));
		if(position.getColumn() > 2 && position.getRow() < 9) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.DOWN_LEFT, board, pieces));
		if(position.getColumn() < 9 && position.getRow() < 9) 
			addHopsIfAny(moves, findCapturesInDirection(MoveDirection.DOWN_RIGHT, board, pieces));		
		
		return moves;
	}
	
	public Tile findTarget(MoveDirection moveDirection, Tile[][] board, int hopLength) {
		
		switch(moveDirection) {
			case UP_LEFT:
				return board[position.getRow()-1-hopLength][position.getColumn()-1-hopLength];
			case UP_RIGHT:
				return board[position.getRow()-1-hopLength][position.getColumn()-1+hopLength];
			case DOWN_LEFT:
				return board[position.getRow()-1+hopLength][position.getColumn()-1-hopLength];
			case DOWN_RIGHT:
				return board[position.getRow()-1+hopLength][position.getColumn()-1+hopLength];
			default:
				break;
		}
		return null;
	}
	
	public Piece findPieceBeingTaken(int tileIndex, ArrayList<ArrayList<Piece>> allPieces) throws NoPieceFoundInRequestedTileException {
		ArrayList<Piece> piecesOfColor;
		if (this.isWhite()) piecesOfColor = allPieces.get(0);
		else piecesOfColor = allPieces.get(1);
		
		for(Piece piece : piecesOfColor) {
			if (piece.getPosition().getIndex() == tileIndex) return piece;
		}
		
		throw new NoPieceFoundInRequestedTileException("No piece on seemingly taken tile");
	}
	
	public enum MoveDirection {
		UP_LEFT,
		UP_RIGHT,
		DOWN_LEFT,
		DOWN_RIGHT;
	}	

}
