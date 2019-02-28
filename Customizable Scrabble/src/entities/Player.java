package entities;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {

	private String name;
	private int score;
	private LinkedList<IMove> moves;
	private Rack rack;
	
	public Player(String name, int rackSize, Bag bag) {
		this.name = name;
		this.score = 0;
		this.moves = new LinkedList<IMove>();
		this.rack = new Rack(rackSize);
		rack.replenish(bag);
	}
	
	public void play(PlayMove move, Bag bag, HashMap<Integer, Integer> lengthBonuses) {
		score += move.calculateScore(lengthBonuses);
		moves.add(move);
		rack.replenish(bag);
	}
	
	public int moveCount() {
		return moves.size();
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public Rack getRack() {
		return rack;
	}
	
}