package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.BlackPiece;
import draughts.library.boardmodel.Board;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.boardmodel.WhitePiece;

public class BoardManager {
	
	private Tile[][] board;
	private ArrayList<WhitePiece> whitePieces;
	private ArrayList<BlackPiece> blackPieces;
	
	
	
	public void prepareBoard() {
		board = new Tile[Board.NUMBER_OF_ROWS][Board.TILES_IN_ROW];
		
		prepareTiles();
		preparePieces();
	}
	
	public Tile[][] getBoard() {
		return board;
	}
	
	public ArrayList<WhitePiece> getWhitePieces() {
		return whitePieces;
	}
	
	public ArrayList<BlackPiece> getBlackPieces() {
		return blackPieces;
	}
	
	public void prepareTiles() {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				board[i][j] = new Tile(i+1, j+1);
				if(board[i][j].getIndex() >= 1 && board[i][j].getIndex() <= 20)
					board[i][j].setState(Tile.State.BLACK_PAWN);
				else if(board[i][j].getIndex() >= 21 && board[i][j].getIndex() <= 30)
					board[i][j].setState(Tile.State.EMPTY);
				else if(board[i][j].getIndex() >= 31 && board[i][j].getIndex() <= 50)
					board[i][j].setState(Tile.State.WHITE_PAWN);
				else 
					board[i][j].setState(Tile.State.WHITE_TILE);
			}
		}
	}
	
	public void preparePieces() {
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		
		for(int i=0; i<20; i++) {
			blackPieces.add(new BlackPawn(i+1)); //start positions for black pieces are 1 through 20
			whitePieces.add(new WhitePawn(30+i+1)); //start positions for white pieces are 31 through 50
		}
	}

}
