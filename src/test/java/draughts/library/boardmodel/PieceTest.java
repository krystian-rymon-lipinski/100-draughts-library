package draughts.library.boardmodel;

import draughts.library.managers.BoardManager;

public class PieceTest {
	
	public BoardManager boardManager;

	public void setUp() {
		boardManager = new BoardManager();
		boardManager.createEmptyBoard();
	}

	public Tile getTile(int index) {
		return boardManager.findTileByIndex(index);
	}

}
