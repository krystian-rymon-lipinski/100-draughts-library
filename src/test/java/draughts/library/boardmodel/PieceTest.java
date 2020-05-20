package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.BoardManager;
import draughts.library.Capture;
import draughts.library.Hop;
import draughts.library.Move;
import draughts.library.NoPieceFoundInRequestedTileException;

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
			Tile currentTile = boardManager.findTileByIndex(piecePosition);
			return piece.findMoves(boardManager.getBoard(), 
								   currentTile.getRow(), currentTile.getColumn());
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<Capture> findTakesForPiece(int piecePosition) {
		Piece piece = null;
		try {
			piece = boardManager.findPieceByIndex(piecePosition);		
			Tile currentTile = boardManager.findTileByIndex(piecePosition);
			return piece.findCaptures(boardManager.getBoard(), 
								   currentTile.getRow(), currentTile.getColumn());
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		return null;
		
	}

}
