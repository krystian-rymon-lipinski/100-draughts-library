package draughts.library;

public class Capture extends Hop {
	
	private int takenPawn;
	
	public Capture(int source, int destination, int takenPawn) {
		super(source, destination);
		this.takenPawn = takenPawn;
	}
	
	public int getTakenPawn() {
		return takenPawn;
	}

}
