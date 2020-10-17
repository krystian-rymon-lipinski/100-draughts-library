package draughts.library.managers;

import draughts.library.boardmodel.Piece;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

public class GameEngine {
	
	private final MoveManager moveManager;
	private boolean isWhiteToMove;
	private GameState gameState;
	private DrawArbiter drawArbiter;
	private final BoardManager boardManager;

	public GameEngine() {
		boardManager = new BoardManager();
		moveManager = new MoveManager();
		drawArbiter = new DrawArbiter();
	}
	
	public boolean getIsWhiteToMove() {
		return isWhiteToMove;
	}
	
	public void setIsWhiteToMove(boolean isWhiteToMove) {
		this.isWhiteToMove = isWhiteToMove;
	}

	public MoveManager getMoveManager() {
		return moveManager;
	}
	
	public BoardManager getBoardManager() {
		return boardManager;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public DrawArbiter getDrawArbiter() {
		return drawArbiter;
	}

	public void setDrawArbiter(DrawArbiter drawArbiter) { this.drawArbiter = drawArbiter; }
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
	public void startGame() {
		boardManager.createStartingPosition();
		gameState = GameState.RUNNING;
		isWhiteToMove = true;
	}

	public void finishMove(Move<? extends Hop> move) {
		updateDrawArbiter(move);
		checkGameState();
		endPlayerTurn();
	}
	
	public void endPlayerTurn() {
		isWhiteToMove = !isWhiteToMove;
	}
	
	public void updateDrawArbiter(Move<? extends Hop> move) {
		drawArbiter.updateCounter(move.isCapture(), move.getMovingPiece().isQueen());
		drawArbiter.updateConditions((boardManager.getIsWhiteQueenOnBoard() && boardManager.getIsBlackQueenOnBoard()), 
								 boardManager.getWhitePieces().size(), boardManager.getBlackPieces().size());
	}
	
	public void checkGameState() {
		if (!boardManager.isAnyMovePossible(!isWhiteToMove)) { //find a move for opponent
			if(isWhiteToMove) setGameState(GameState.WON_BY_WHITE);
			else setGameState(GameState.WON_BY_BLACK);
		}
		if(drawArbiter.isGameDrawn()) setGameState(GameState.DRAWN);
	}
	
	
	public enum GameState {
		RUNNING,
		WON_BY_WHITE,
		WON_BY_BLACK,
		DRAWN
	}

}
