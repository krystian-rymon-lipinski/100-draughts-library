package draughts.library;

import java.util.ArrayList;

public class Move<T extends Hop> {
	
	private ArrayList<T> hops;
	
	public Move(T hop) {
		hops = new ArrayList<>();
		hops.add(hop);
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
	
	public boolean isTake() {
		if(hops.get(0) instanceof Hop) return false;
		else return true;
		
	}

	public String toString() {
		return hops.toString();
	}
}
