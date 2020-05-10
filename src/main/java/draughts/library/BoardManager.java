package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.Board;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;

public class BoardManager {
	
	private Tile[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	
	
	public void prepareBoard() {
		board = new Tile[Board.NUMBER_OF_ROWS][Board.TILES_IN_ROW];
		
		prepareTiles();
		preparePieces();
	}
	
	public Tile[][] getBoard() {
		return board;
	}
	
	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}
	
	public ArrayList<Piece> getBlackPieces() {
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
	
	public void makeMove(int source, int destination) {
		Piece piece = findPieceByIndex(source);
		Tile src = findTileByIndex(source);
		Tile dst = findTileByIndex(destination);
		piece.move(src, dst);
		
	}
	
	public Tile findTileByIndex(int tileIndex) {
		for (int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				if(board[i][j].getIndex() == tileIndex)
					return board[i][j];
			}
		}
		return null;
	}
	
	public Piece findPieceByIndex(int tileIndex) {
		ArrayList<Piece> pieces = new ArrayList<>();
		
		pieces.addAll(whitePieces);
		pieces.addAll(blackPieces);
		
		for(Piece piece : pieces) {
			if(piece.getPosition() == tileIndex)
				return piece;
		}
		
		return null;
		
		
	}

}
