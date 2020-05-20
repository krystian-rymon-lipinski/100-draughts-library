package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.Piece;
import draughts.library.exceptions.NoCorrectMovesForSelectedPieceException;
import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.exceptions.WrongColorFoundInRequestedTileException;

public class GameEngine {
	
	private MoveManager moveManager;
	private BoardManager boardManager;
	private boolean isWhiteToMove;
	private boolean pieceAlreadyMarked;
	private ArrayList<Integer> possibleHopDestinations;
	
	public GameEngine() {
		moveManager = new MoveManager();
		boardManager = new BoardManager();
	}
	
	public void startGame() {
		boardManager.createStartingPosition();
		pieceAlreadyMarked = false;
		possibleHopDestinations = new ArrayList<>();
		isWhiteToMove = true;
		moveManager.findAllCorrectMoves(isWhiteToMove);
	}
	
	public void tileClicked(int position) throws NoPieceFoundInRequestedTileException, 
												 WrongColorFoundInRequestedTileException,
												 NoCorrectMovesForSelectedPieceException {
		if(!pieceAlreadyMarked) {
			Piece markedPiece = boardManager.findColorPieceByIndex(position, isWhiteToMove);
			if(!isPieceProperColor(markedPiece)) 
				throw new WrongColorFoundInRequestedTileException("No piece of your color on chosen tile!");
			addPossibleHopDestinations(position);
			pieceAlreadyMarked = true;
		}
		
		else {
			
		}
		
	}
	
	public boolean isPieceProperColor(Piece piece) {
		if(isWhiteToMove)
			return boardManager.getWhitePieces().contains(piece);
		else
			return boardManager.getBlackPieces().contains(piece);
	}
	
	public void addPossibleHopDestinations(int position) throws NoCorrectMovesForSelectedPieceException {
		ArrayList<Integer> possibleHopDestinations = moveManager.doesChosenPawnHaveMoves(position);
		if(possibleHopDestinations.size() == 0)
			throw new NoCorrectMovesForSelectedPieceException("Another piece should move");
		else possibleHopDestinations.addAll(possibleHopDestinations);
	}
	
	public void changeMove() {
		isWhiteToMove = !isWhiteToMove;
		pieceAlreadyMarked = false;
		possibleHopDestinations.clear();
	}

}
