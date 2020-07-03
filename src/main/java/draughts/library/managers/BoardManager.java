package draughts.library.managers;

import java.util.ArrayList;
import java.util.Collections;
import draughts.library.boardmodel.BlackPawn;
import draughts.library.boardmodel.BlackQueen;
import draughts.library.boardmodel.Board;
import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;
import draughts.library.boardmodel.WhitePawn;
import draughts.library.boardmodel.WhiteQueen;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class BoardManager {
	
	private Tile[][] board;
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	private boolean isWhiteQueenOnBoard;
	private boolean isBlackQueenOnBoard;
	
	public BoardManager() {
		board = new Tile[Board.NUMBER_OF_ROWS][Board.TILES_IN_ROW];
		whitePieces = new ArrayList<>();
		blackPieces = new ArrayList<>();
		this.isWhiteQueenOnBoard = false;
		this.isBlackQueenOnBoard = false;
	}
	
	public BoardManager(BoardManager boardManager) {
		this.board = boardManager.board;
		this.whitePieces = boardManager.whitePieces;
		this.blackPieces = boardManager.blackPieces;
		this.isWhiteQueenOnBoard = boardManager.isWhiteQueenOnBoard;
		this.isBlackQueenOnBoard = boardManager.isBlackQueenOnBoard;
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
	
	public boolean getIsWhiteQueenOnBoard() {
		return isWhiteQueenOnBoard;
	}
	
	public void setIsWhiteQueenOnBoard(boolean isWhiteQueenOnBoard) {
		this.isWhiteQueenOnBoard = isWhiteQueenOnBoard;
	}
	
	public boolean getIsBlackQueenOnBoard() {
		return isBlackQueenOnBoard;
	}
	
	public void setIsBlackQueenOnBoard(boolean isBlackQueenOnBoard) {
		this.isBlackQueenOnBoard = isBlackQueenOnBoard;
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
	
	public void createStartingPosition() {
		createEmptyBoard();
		createPiecesForStartingPosition();
	}
	
	public WhitePawn addWhitePawn(int index) {
		Tile position = findTileByIndex(index);
		position.setState(Tile.State.WHITE_PAWN);
		WhitePawn whitePawn = new WhitePawn(position);
		whitePieces.add(whitePawn);
		return whitePawn;
	}
	
	public BlackPawn addBlackPawn(int index) {
		Tile position = findTileByIndex(index);
		position.setState(Tile.State.BLACK_PAWN);
		BlackPawn blackPawn = new BlackPawn(position);
		blackPieces.add(blackPawn);
		return blackPawn;
	}
	
	public WhiteQueen addWhiteQueen(int index) {
		Tile position = findTileByIndex(index);
		position.setState(Tile.State.WHITE_QUEEN);
		WhiteQueen whiteQueen = new WhiteQueen(position);
		whitePieces.add(whiteQueen);
		isWhiteQueenOnBoard = true;
		return whiteQueen;
	}
	
	public BlackQueen addBlackQueen(int index) {
		Tile position = findTileByIndex(index);
		position.setState(Tile.State.BLACK_QUEEN);
		BlackQueen blackQueen = new BlackQueen(position);
		blackPieces.add(blackQueen);
		isBlackQueenOnBoard = true;
		return blackQueen;
	}
	
	public Piece addPiece(Piece piece) {
		if (piece.isWhite()) {
			if (piece.isQueen()) return addWhiteQueen(piece.getPosition().getIndex());
			else 				 return addWhitePawn(piece.getPosition().getIndex());
		}
		else {
			if (piece.isQueen()) return addBlackQueen(piece.getPosition().getIndex());
			else 				 return addBlackPawn(piece.getPosition().getIndex());
		}
	}
	
	public void removeWhitePiece(Piece piece) {
		boolean wasQueen = piece.isQueen();
		
		piece.getPosition().setState(Tile.State.EMPTY);	
		whitePieces.remove(piece);
		
		if(wasQueen) {
			for(Piece whitePiece : whitePieces) {
				if (whitePiece.isQueen()) return;
			}
			isWhiteQueenOnBoard = false;
		}
	}
	
	public void removeBlackPiece(Piece piece) {
		boolean wasQueen = piece.isQueen();
		
		piece.getPosition().setState(Tile.State.EMPTY);
		blackPieces.remove(piece);
		
		if(wasQueen) {
			for(Piece blackPiece : blackPieces) {
				if (blackPiece.isQueen()) return;
			}
			isBlackQueenOnBoard = false;
		}		
	}
	
	public void makeHop(Piece movedPiece, Tile destination) {
		movedPiece.getPosition().setState(Tile.State.EMPTY);	
		movedPiece.hop(destination);
		
	}
	
	public void makeCapture(Piece movedPiece, Tile destination, Piece takenPiece) {
		makeHop(movedPiece, destination);
		if(takenPiece.isWhite()) removeWhitePiece(takenPiece);
		else                     removeBlackPiece(takenPiece);
	}
	
	public void makeWholeMove(Move<? extends Hop> move) {
		for (Hop hop : move.getHops()) {
			if(move.isCapture()) {
				Capture capture = (Capture) hop;
				makeCapture(move.getMovingPiece(), capture.getDestination(), capture.getTakenPiece());
			}
			else {
				makeHop(move.getMovingPiece(), hop.getDestination());
			}
		}
	}
	
	public ArrayList<Piece> reverseWholeMove(Move<? extends Hop> move) {
		ArrayList<Piece> piecesToReturn = new ArrayList<Piece>();
		Collections.reverse(move.getHops());
		
		for (Hop hop : move.getHops()) {
			makeHop(move.getMovingPiece(), hop.getSource());
			if(move.isCapture()) {
				Capture capture = (Capture) hop;
				piecesToReturn.add(addPiece(capture.getTakenPiece()));
			}
		}

		return piecesToReturn;
	}
	
	public Piece promotePawn(Piece pawnToPromote) {
		Piece newQueen;
		if(pawnToPromote.isWhite()) {
			removeWhitePiece(pawnToPromote);
			newQueen = addWhiteQueen(pawnToPromote.getPosition().getIndex());
		} else {
			removeBlackPiece(pawnToPromote);
			newQueen = addBlackQueen(pawnToPromote.getPosition().getIndex());
		}
		return newQueen;
	}

	public Piece demoteQueen(Piece queenToDemote) {
		Piece newPawn;
		if (queenToDemote.isWhite()) {
			removeWhitePiece(queenToDemote);
			newPawn = addWhitePawn(queenToDemote.getPosition().getIndex());
		} else {
			removeBlackPiece(queenToDemote);
			newPawn = addBlackPawn(queenToDemote.getPosition().getIndex());
		}
		return newPawn;
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
			if(piece.getPosition().getIndex() == tileIndex)
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
			pieceMoves = piece.findMoves(board);
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
		ArrayList<Capture> captures = new ArrayList<>();
		ArrayList<Move<Capture>> moves = new ArrayList<>();
		ArrayList<Move<Capture>> newMoves = new ArrayList<>();
		
		ArrayList<ArrayList<Piece>> allPieces = new ArrayList<>();
		allPieces.add(whitePieces);
		allPieces.add(blackPieces);
		
		do  {
						
			if(moves.size() == 0) { //first capture 
				captures = piece.findCaptures(board, allPieces);
				if(captures.size() == 0) break; //no captures available for piece
				else {
					for(Capture capture : captures) {
						newMoves.add(new Move<Capture>(piece, capture));
					}
				}
			}
			else { //consecutive captures
				newMoves.clear();

				for(int i=0; i<moves.size(); i++) {
					for(int j=0; j<moves.get(i).getNumberOfHops(); j++) {
						makeHop(piece, moves.get(i).getHop(j).getDestination());
					}
					captures = piece.findCaptures(board, allPieces);
					for(Capture capture: captures) {
						if(!isPawnAlreadyTaken(moves.get(i), capture)) { //cannot take the same pawn twice
							newMoves.add(new Move<Capture>(moves.get(i)));
							newMoves.get(newMoves.size()-1).addHop(capture);
						}			
					}
					for(int j=moves.get(i).getNumberOfHops()-1; j>=0; j--) {
						makeHop(piece, moves.get(i).getHop(j).getSource());
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
	
	
	private boolean isPawnAlreadyTaken(Move<Capture> move, Capture capture) {
		for(int i=0; i<move.getNumberOfHops(); i++) {
			if(move.getHop(i).getTakenPiece() == capture.getTakenPiece())
				return true;
		}
		return false;
	}

}
