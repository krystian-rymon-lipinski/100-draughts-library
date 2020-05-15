package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.BlackQueen;
import draughts.library.boardmodel.Board;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.boardmodel.WhiteQueen;

public class BoardManager {
	
	private Tile[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	public BoardManager() {
		board = new Tile[Board.NUMBER_OF_ROWS][Board.TILES_IN_ROW];
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
	}
	
	public void createStartingPosition() {
		createEmptyBoard();
		createPiecesForStartingPosition();
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
	
	public void createPiecesForStartingPosition() {
		
		for(int i=0; i<20; i++) {
			addWhitePawn(30+i+1); //start positions for white pieces are 31 through 50
			addBlackPawn(i+1); //start positions for black pieces are 1 through 20
		}
	}
	
	public void createEmptyBoard() {
		
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[0].length; j++) {
				board[i][j] = new Tile(i+1, j+1);
				if(board[i][j].getIndex() > 0) board[i][j].setState(Tile.State.EMPTY);
				else board[i][j].setState(Tile.State.WHITE_TILE);
			}
		}
	}
	
	public void addWhitePawn(int position) {
		whitePieces.add(new WhitePawn(position));
		findTileByIndex(position).setState(Tile.State.WHITE_PAWN);
	}
	
	public void addBlackPawn(int position) {
		blackPieces.add(new BlackPawn(position));
		findTileByIndex(position).setState(Tile.State.BLACK_PAWN);
	}
	
	public void addWhiteQueen(int position) {
		whitePieces.add(new WhiteQueen(position));
		findTileByIndex(position).setState(Tile.State.WHITE_QUEEN);
	}
	
	public void addBlackQueen(int position) {
		blackPieces.add(new BlackQueen(position));
		findTileByIndex(position).setState(Tile.State.BLACK_QUEEN);
	}
	
	public void makeHop(int source, int destination) {
		Tile src = findTileByIndex(source);
		src.setState(Tile.State.EMPTY);
		
		Piece movedPiece = findPieceByIndex(source);
		Tile dst = findTileByIndex(destination);
		try {
			movedPiece.hop(dst);
		} catch (NullPointerException err) {
			System.out.println("No piece in selected tile!");
		}
		
	}
	
	public void makeCapture(int source, int destination, int taken) {
		makeHop(source, destination);
		
		Tile takenTile = findTileByIndex(taken);
		takenTile.setState(Tile.State.EMPTY);
		
		Piece takenPiece = findPieceByIndex(taken);
		if(isTakenPieceWhite(takenPiece))
			whitePieces.remove(takenPiece);
		else 
			blackPieces.remove(takenPiece);
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
	
	public ArrayList<Move<Hop>> findMoves(boolean isWhiteToMove) {
		ArrayList<Piece> pieces;
		if (isWhiteToMove) pieces = whitePieces;
		else pieces = blackPieces;
		
		ArrayList<Move<Hop>> allMoves = new ArrayList<>();
		ArrayList<Move<Hop>> pieceMoves = new ArrayList<>();
		
		for(Piece piece : pieces) {
			Tile currentPosition = findTileByIndex(piece.getPosition());
			pieceMoves = piece.findMoves(board, currentPosition.getRow(), currentPosition.getColumn());
			if(pieceMoves.size() > 0) allMoves.addAll(pieceMoves);
		}
		
		return allMoves;
	}
	
	public ArrayList<Capture> findCaptures(boolean isWhiteToMove) {
		ArrayList<Piece> pieces;
		if (isWhiteToMove) pieces = whitePieces;
		else pieces = blackPieces;
		
		ArrayList<Capture> allMoves = new ArrayList<>();
		ArrayList<Capture> pieceMoves = new ArrayList<>();
		
		for(Piece piece : pieces) {
			Tile currentPosition = findTileByIndex(piece.getPosition());
			pieceMoves = piece.findCaptures(board, currentPosition.getRow(), currentPosition.getColumn());
			if(pieceMoves.size() > 0) allMoves.addAll(pieceMoves);
		}
			
		return allMoves;
	}
	
	public boolean isTakenPieceWhite(Piece takenPiece) {
		return (takenPiece instanceof WhitePawn || takenPiece instanceof WhiteQueen) ? true : false;
	}	
	

}
