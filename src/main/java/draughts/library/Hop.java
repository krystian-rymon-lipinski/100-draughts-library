package draughts.library;

public class Hop {
	
	private int source;
	private int destination;
	
	public Hop(int source, int destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public int getSource() {
		return source;
	}
	
	public int getDestination() {
		return destination;
	}
	
	public String toString() {
		return source + " -> " + destination; 
	}

}
