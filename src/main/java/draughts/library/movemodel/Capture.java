package draughts.library.movemodel;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Capture capture = (Capture) o;
		return Objects.equals(takenPiece, capture.takenPiece);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), takenPiece);
	}
}
