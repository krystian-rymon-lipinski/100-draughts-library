package draughts.library;

public class HopWithTake extends Hop {
	
	private int takenPawn;
	
	public HopWithTake(int source, int destination, int takenPawn) {
		super(source, destination);
		this.takenPawn = takenPawn;
	}
	
	public int getTakenPawn() {
		return takenPawn;
	}

}
