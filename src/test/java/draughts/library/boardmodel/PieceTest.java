package draughts.library.boardmodel;

import java.util.ArrayList;

import draughts.library.exceptions.NoPieceFoundInRequestedTileException;
import draughts.library.managers.BoardManager;
import draughts.library.movemodel.Capture;
import draughts.library.movemodel.Hop;
import draughts.library.movemodel.Move;

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
