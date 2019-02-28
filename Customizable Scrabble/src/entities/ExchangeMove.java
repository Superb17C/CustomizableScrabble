package entities;

import java.util.HashMap;
import java.util.LinkedList;

public class ExchangeMove implements IMove {

	private LinkedList<Tile> tiles;
	
	public ExchangeMove() {
		this.tiles = new LinkedList<Tile>();
	}
	
	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	@Override
	public boolean isValid(Match match) {
		Bag bag = match.getBag();
		return tiles.size() <= bag.tilesLeft();
	}
	
	@Override
	public int calculateScore(HashMap<Integer, Integer> lengthBonuses) {
		return lengthBonuses.get(0);
	}
	
}