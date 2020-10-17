package draughts.library.movemodel;

import java.util.ArrayList;
import java.util.Objects;

import draughts.library.boardmodel.Piece;
import draughts.library.boardmodel.Tile;

public class Move<T extends Hop> {
	
	private Piece movingPiece;
	private ArrayList<T> hops;
	private boolean promotion;
	private boolean capture;
	
	public Move(Piece movingPiece) {
		this.movingPiece = movingPiece;
		hops = new ArrayList<>();
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
		this.promotion = move.promotion;
	}

	public void classify() {
		if (hops.get(0) instanceof Capture) setCapture(true);
		if (!movingPiece.isQueen() && (getMoveDestination().getIndex() > 45 || getMoveDestination().getIndex() < 6))
			setPromotion(true);
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
		return capture;
	}

	public void setCapture(boolean capture) { this.capture = capture; }
	
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

	public void setMoveTakenPawns(ArrayList<Piece> moveTakenPawns) {
		if (isCapture()) {
			int takenPawnIndex = 0;
			for (T hop : hops) {
				Capture capture = (Capture) hop;
				capture.setTakenPiece(moveTakenPawns.get(takenPawnIndex++));
			}
		}
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean isPromotion) {
		this.promotion = isPromotion;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Move<?> move = (Move<?>) o;
		return promotion == move.promotion &&
				movingPiece.equals(move.movingPiece) &&
				hops.equals(move.hops);
	}

	@Override
	public int hashCode() {
		return Objects.hash(movingPiece, hops, promotion);
	}
}
