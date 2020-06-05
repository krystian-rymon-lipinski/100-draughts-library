package draughts.library;

import java.util.ArrayList;

import draughts.library.boardmodel.Tile;

public class Move<T extends Hop> {
	
	private ArrayList<T> hops;
	
	public Move(T hop) {
		hops = new ArrayList<>();
		hops.add(hop);
	}
	
	public Move(Move<T> move) {
		hops = new ArrayList<>();
		for(int i=0; i<move.getNumberOfHops(); i++) {
			hops.add(move.getHop(i));
		}
	}
	
	public ArrayList<T> getHops() {
		return hops;
	}
	
	public T getHop(int numberOfHopInMove) {
		return hops.get(numberOfHopInMove);
	}
	
	public void addHop(T hop) {
		hops.add(hop);
	}
	
	public int getNumberOfHops() {
		return hops.size();
	}
	
	public boolean isCapture() {
		if(hops.get(0) instanceof Capture) return true;
		else return false;
	}
	
	public Tile getMoveSource() {
		return hops.get(0).getSource();
	}
	
	public Tile getMoveDestination() {
		return hops.get(hops.size()-1).getDestination();
	}
	
	public ArrayList<Tile> getMoveTakenPawns() {
		ArrayList<Tile> takenPawns = new ArrayList<>();
		if(isCapture()) {
			for(T hop : hops) {
				Capture capture = (Capture) hop;
				takenPawns.add(capture.getTakenPawn());
			}
		}
		return takenPawns;
	}
	
	public boolean doesSourceMatch(int source) {
		return getMoveSource().getIndex() == source;
	}
	
	public boolean doesDestinationMatch(int destination) {
		return getMoveDestination().getIndex() == destination;
	}
	
	public boolean doesTakenPawnsMatch(ArrayList<Integer> takenPawns) {
		if(getMoveTakenPawns().size() != takenPawns.size()) return false;
		else {
			for(int i=0; i<takenPawns.size(); i++) {
				if(takenPawns.get(i) != ((Capture) hops.get(i)).getTakenPawn().getIndex()) return false;
			}
			return true;
		}
	}

	public String toString() {
		return hops.toString();
	}
}
