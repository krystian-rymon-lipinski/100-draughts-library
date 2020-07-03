package draughts.library.movemodel;

import draughts.library.boardmodel.Tile;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hop hop = (Hop) o;
		return source.equals(hop.source) &&
				destination.equals(hop.destination);
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, destination);
	}
}
