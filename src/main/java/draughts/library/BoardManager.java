package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.BlackQueen;
import draughts.library.boardmodel.Board;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.boardmodel.WhiteQueen;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;

public class BoardManager {
	
	private Tile[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	
	public BoardManager() {
		board = new Tile[Board.NUMBER_OF_ROWS][Board.TILES_IN_ROW];
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
	}
	
	public BoardManager(BoardManager boardManager) {
		this.board = boardManager.board;
		this.whitePieces = boardManager.whitePieces;
		this.blackPieces = boardManager.blackPieces;
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
		try {
			Piece movedPiece = findPieceByIndex(source);
			Tile src = findTileByIndex(source);
			src.setState(Tile.State.EMPTY);
			
			Tile dst = findTileByIndex(destination);
			movedPiece.hop(dst);
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void reverseHop(int source, int destination) {
		try {
			Piece movedPiece = findPieceByIndex(destination);
			Tile dst = findTileByIndex(destination);
			dst.setState(Tile.State.EMPTY);
			
			Tile src = findTileByIndex(source);
			movedPiece.reverseHop(src);
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void makeCapture(int source, int destination, int taken){
		try {
			Piece takenPiece = findPieceByIndex(taken);
			makeHop(source, destination);
			
			Tile takenTile = findTileByIndex(taken);
			takenTile.setState(Tile.State.EMPTY);
			
			if(isTakenPieceWhite(takenPiece))
				whitePieces.remove(takenPiece);
			else 
				blackPieces.remove(takenPiece);
		} catch(NoPieceFoundInRequestedTileException ex) {
			ex.printStackTrace();
		}		
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
	
	public Piece findPieceByIndex(int tileIndex) throws NoPieceFoundInRequestedTileException {
		ArrayList<Piece> pieces = new ArrayList<>();
		
		pieces.addAll(whitePieces);
		pieces.addAll(blackPieces);
		
		for(Piece piece : pieces) {
			if(piece.getPosition() == tileIndex)
				return piece;
		}
		
		throw new NoPieceFoundInRequestedTileException("No piece found in tile: " + tileIndex);		
	}
	
	public ArrayList<Move<Hop>> findMovesForAllPieces(boolean isWhiteToMove) {
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
	
	public ArrayList<Move<Capture>> findCapturesForAllPieces(boolean isWhiteToMove) {
		ArrayList<Piece> pieces;
		if (isWhiteToMove) pieces = whitePieces;
		else pieces = blackPieces;
		
		ArrayList<Move<Capture>> allMoves = new ArrayList<>();
		ArrayList<Move<Capture>> pieceMoves = new ArrayList<>();
		int longestConsecutiveCapture = 1;
		
		for(Piece piece : pieces) {
			pieceMoves = findLongestConsecutiveCaptures(piece);
			if(pieceMoves.size() > 0)
				if(pieceMoves.get(0).getNumberOfHops() > longestConsecutiveCapture) {
					allMoves.clear();
					allMoves.addAll(pieceMoves);
					longestConsecutiveCapture = pieceMoves.get(0).getNumberOfHops();
				}
				else if(pieceMoves.get(0).getNumberOfHops() == longestConsecutiveCapture)
					allMoves.addAll(pieceMoves);
		}
			
		return allMoves;
	}
	
	public ArrayList<Move<Capture>> findLongestConsecutiveCaptures(Piece piece) {
		Tile source = findTileByIndex(piece.getPosition());
		
		ArrayList<Capture> captures = new ArrayList<>();
		ArrayList<Move<Capture>> moves = new ArrayList<>();
		ArrayList<Move<Capture>> newMoves = new ArrayList<>();
		
		do  {
			newMoves.clear();
						
			if(moves.size() == 0) { //first capture 
				captures = piece.findCaptures(board, source.getRow(), source.getColumn());
				if(captures.size() == 0) break; //no captures available for piece
				else {
					for(Capture capture : captures) {
						newMoves.add(new Move<Capture>(capture));
					}
				}
			}
			else { //consecutive captures
				for(int i=0; i<moves.size(); i++) {
					for(int j=0; j<moves.get(i).getNumberOfHops(); j++) {
						makeHop(moves.get(i).getHop(j).getSource(), moves.get(i).getHop(j).getDestination());
					}
					source = findTileByIndex(moves.get(i).getHop(moves.get(i).getNumberOfHops()-1).getDestination());
					captures = piece.findCaptures(board, source.getRow(), source.getColumn());
					for(Capture capture: captures) {
						if(!pawnAlreadyTaken(moves.get(i), capture)) { //cannot take the same pawn twice
							newMoves.add(new Move<Capture>(moves.get(i)));
							newMoves.get(newMoves.size()-1).addHop(capture);
						}			
					}
					for(int j=moves.get(i).getNumberOfHops()-1; j>=0; j--) {
						reverseHop(moves.get(i).getHop(j).getSource(), moves.get(i).getHop(j).getDestination());
					}
				}
			}		
			
			if(newMoves.size() > 0) {
				moves.clear();
				moves.addAll(newMoves);
			}
			
		} while (newMoves.size() != 0 );
	
	return moves;
	}
	
	
	public boolean pawnAlreadyTaken(Move<Capture> move, Capture capture) {
		for(int i=0; i<move.getNumberOfHops(); i++) {
			if(move.getHop(i).getTakenPawn() == capture.getTakenPawn())
				return true;
		}
		return false;
	}
	
	
	public boolean isTakenPieceWhite(Piece takenPiece) {
		return (takenPiece instanceof WhitePawn || takenPiece instanceof WhiteQueen) ? true : false;
	}	
	
	public boolean isMovedPieceQueen(int source) {
		try {
			Piece piece = findPieceByIndex(source);
			return (piece instanceof WhiteQueen || piece instanceof BlackQueen) ? true : false;
		} catch(NoPieceFoundInRequestedTileException ex) {
			return false;
		}
	}
	

}
