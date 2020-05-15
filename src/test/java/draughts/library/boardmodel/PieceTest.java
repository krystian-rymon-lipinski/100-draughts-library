package draughts.library.boardmodel;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import draughts.library.BoardManager;
import draughts.library.Move;

@RunWith(MockitoJUnitRunner.class)
public class PieceTest {
	
	public BoardManager boardManager;
	
	public void setUp() {
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}
	
	public ArrayList<Move> findMovesForPiece(int piecePosition) {
		Piece piece = boardManager.findPieceByIndex(piecePosition);
		Tile currentTile = boardManager.findTileByIndex(piecePosition);
		return piece.findMoves(boardManager.getBoard(), 
							   currentTile.getRow(), currentTile.getColumn());
	}
	
	public ArrayList<Move> findTakesForPiece(int piecePosition) {
		Piece piece = boardManager.findPieceByIndex(piecePosition);
		Tile currentTile = boardManager.findTileByIndex(piecePosition);
		return piece.findTakes(boardManager.getBoard(), 
							   currentTile.getRow(), currentTile.getColumn());
	}
	
	@Test(expected = NullPointerException.class)
	public void findMoves_noPieceInChosenPosition_test() {
		findMovesForPiece(11);
	}
	
	@Test(expected = NullPointerException.class)
	public void findTakes_noPieceInChosenPosition_test() {
		findMovesForPiece(11);
	}

}
