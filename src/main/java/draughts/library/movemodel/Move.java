package draughts.library.movemodel;

import java.util.ArrayList;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

public class Move<T extends Hop> {
	
	private Piece movingPiece;
	private ArrayList<T> hops;
	private boolean isPromotion;
	
	public Move(Piece movingPiece) {
		this.movingPiece = movingPiece;
		hops = new ArrayList<>();
		isPromotion = false;
	}
	
	public Move(Piece movingPiece, T hop) {
		this(movingPiece);
		hops.add(hop);
	}
	
	public Move(Move<T> move) {
		this(move.movingPiece);
		for(int i=0; i<move.getNumberOfHops(); i++) {
			this.hops.add(move.hops.get(i));
		}
	}
	
	public ArrayList<T> getHops() {
		return hops;
	}
	
	public Piece getMovingPiece() {
		return movingPiece;
	}

	public void setMovingPiece(Piece movingPiece) {this.movingPiece = movingPiece;}
	
	public T getHop(int numberOfHop) {
		return hops.get(numberOfHop);
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
	
	public ArrayList<Piece> getMoveTakenPawns() {
		ArrayList<Piece> takenPawns = new ArrayList<>();
		if(isCapture()) {
			for(T hop : hops) {
				Capture capture = (Capture) hop;
				takenPawns.add(capture.getTakenPiece());
			}
		}
		return takenPawns;
	}

	public boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(boolean isPromotion) {
		this.isPromotion = isPromotion;
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
				Capture capture = (Capture) hops.get(i);
				if(takenPawns.get(i) != capture.getTakenPiece().getPosition().getIndex()) {
					return false;
				}
			}
			return true;
		}
	}

	public String toString() {
		return hops.toString();
	}
}
