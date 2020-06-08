package draughts.library.movemodel;

import draughts.library.boardmodel.Tile;

public class Hop {
	
	private Tile source;
	private Tile destination;
	
	public Hop(Tile source, Tile destination) {
		this.source = source;
		this.destination = destination;
	}
	
	public Tile getSource() {
		return source;
	}
	
	public Tile getDestination() {
		return destination;
	}
	
	public String toString() {
		return source.getIndex() + " -> " + destination.getIndex(); 
	}

}
