package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.managers.BoardManager;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class PieceTest {
	
	public BoardManager boardManager;
	
	public void setUp() {
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}
	
	public ArrayList<Move<Hop>> findMovesForPiece(int piecePosition) {
		Piece piece = null;
		try {
			piece = boardManager.findPieceByIndex(piecePosition);
			return piece.findMoves(boardManager.getBoard());
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<Capture> findTakesForPiece(int piecePosition) {
		Piece piece = null;
		try {
			piece = boardManager.findPieceByIndex(piecePosition);		
			return piece.findCaptures(boardManager.getBoard());
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		return null;
		
	}

}
