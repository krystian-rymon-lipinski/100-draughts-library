package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public abstract class Pawn extends Piece {

	public Pawn(Tile position) {
		super(position);
	}
	
	public boolean isQueen() {
		return false;
	}
	
	public ArrayList<Move<Hop>> findMovesInDirection(MoveDirection moveDirection, Tile[][] board) {
		
		ArrayList<Move<Hop>> moves = new ArrayList<>();
		Tile target = findTarget(moveDirection, board, 1);
		
		if(target.getState() == Tile.State.EMPTY)
			moves.add(new Move<Hop>(this, new Hop(position, target)));
		return moves;
	}
	
	public ArrayList<Capture> findCapturesInDirection(MoveDirection moveDirection, Tile[][] board, ArrayList<Piece> allPieces) {
		
		ArrayList<Capture> hops = new ArrayList<>();
		
		Tile target = findTarget(moveDirection, board, 2);
		Tile possibleTake = findTarget(moveDirection, board, 1);
		
		if(isTakePossible(target, possibleTake)) {
			try {
				Piece takenPiece = findPieceBeingTaken(possibleTake, allPieces);
				hops.add(new Capture(position, target, takenPiece));
			} catch(NoPieceFoundInRequestedTileException ex) {
				ex.printStackTrace();
			}

		}
		return hops;
	}
	
	public boolean isTakePossible(Tile target, Tile possibleTake) {
		return isTileOccupiedByOppositeColor(possibleTake) && 
				   target.getState() == Tile.State.EMPTY;
	}

}
