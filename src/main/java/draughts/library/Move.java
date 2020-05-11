package draughts.library;

import java.util.ArrayList;

public class Move {
	
	private int source;
	private int destination;
	private ArrayList<Integer> hops;
	private ArrayList<Integer> pawnsTaken;
	
	public Move(int source, int destination) {
		this.source = source;
		this.destination = destination;
		
		hops = new ArrayList<>();
		hops.add(destination);
		
		pawnsTaken = new ArrayList<>();
	}
	
	public Move(int source, int destination, int taken) {
		this(source, destination);
		pawnsTaken.add(taken);
	}
	
	
	public int getSource(int hopsMadeInMove) {
		if(hopsMadeInMove == 0) return source;
		else return hops.get(hopsMadeInMove-1);
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getDestination(int hopsMadeInMove) {
		return hops.get(hopsMadeInMove);
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public ArrayList<Integer> getHops() {
		return hops;
	}
	public void setHops(ArrayList<Integer> hops) {
		this.hops = hops;
	}
	public ArrayList<Integer> getPawnsTaken() {
		return pawnsTaken;
	}
	public void setPawnsTaken(ArrayList<Integer> pawnsTaken) {
		this.pawnsTaken = pawnsTaken;
	}
	
	public boolean isTake() {
		return (pawnsTaken.size() != 0) ? true : false;
	}
	
	public int getTakenPawn(int hopsMadeInMove) {
		return pawnsTaken.get(hopsMadeInMove);
	}

	public void addHop(int destination) {
		this.destination = destination;
		hops.add(destination);
	} 
	
	public void addHop(int destination, int taken) {
		addHop(destination);
		pawnsTaken.add(taken);
	}
	
	
	public String toString() {
		return hops.toString();
	}
}
