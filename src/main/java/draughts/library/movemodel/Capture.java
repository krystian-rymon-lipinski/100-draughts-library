package draughts.library.movemodel;

import draughts.library.boardmodel.Tile;

public class Capture extends Hop {
	
	private Tile takenPawn;
	
	public Capture(Tile source, Tile destination, Tile takenPawn) {
		super(source, destination);
		this.takenPawn = takenPawn;
	}
	
	public Tile getTakenPawn() {
		return takenPawn;
	}	

}
