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

	public abstract Move<Hop> findMove(MoveDirection moveDirection, Tile[][] board);

	public Move<Hop> findMoveInDirection(MoveDirection moveDirection, Tile[][] board) {
		Tile target = findTarget(moveDirection, board, 1);
		if (target.getState() == Tile.State.EMPTY) {
			return new Move<>(this, new Hop(position, target));
		} else {
			return null;
		}
	}

	public Capture findCapture(MoveDirection direction, Tile[][] board, ArrayList<Piece> oppositePieces) {
		if (isCapturePossible(direction)) {
			Tile target = findTarget(direction, board, 2);
			Tile possibleTake = findTarget(direction, board, 1);

			if (isTakePossible(target, possibleTake)) {
				try {
					Piece pieceToTake = findPieceBeingTaken(possibleTake, oppositePieces);
					return new Capture(position, target, pieceToTake);
				} catch (NoPieceFoundInRequestedTileException ex) {
					ex.printStackTrace();
					return null;
				}
			}
			else return null;
		}
		else return null;
	}

	public ArrayList<Capture> findAllCaptures(Tile[][] board, ArrayList<Piece> oppositePieces) {
		ArrayList<Capture> captures = new ArrayList<>();

		addCaptureIfNotNull(captures, findCapture(MoveDirection.UP_LEFT, board, oppositePieces));
		addCaptureIfNotNull(captures, findCapture(MoveDirection.UP_RIGHT, board, oppositePieces));
		addCaptureIfNotNull(captures, findCapture(MoveDirection.DOWN_LEFT, board, oppositePieces));
		addCaptureIfNotNull(captures, findCapture(MoveDirection.DOWN_RIGHT, board, oppositePieces));

		return captures;
	}
	
	public boolean isTakePossible(Tile target, Tile possibleTake) {
		return isTileOccupiedByOppositeColor(possibleTake) && 
				   target.getState() == Tile.State.EMPTY;
	}

}
