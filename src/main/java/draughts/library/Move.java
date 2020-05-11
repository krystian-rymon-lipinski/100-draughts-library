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
	
	
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getDestination() {
		return destination;
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
