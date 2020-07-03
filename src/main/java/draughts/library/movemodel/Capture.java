package draughts.library.movemodel;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

public class Capture extends Hop {
	
	private Piece takenPiece;
	
	public Capture(Tile source, Tile destination, Piece takenPiece) {
		super(source, destination);
		this.takenPiece = takenPiece;
	}
	
	public Piece getTakenPiece() {
		return takenPiece;
	}

	public void setTakenPiece(Piece takenPiece) { this.takenPiece = takenPiece; }

}
