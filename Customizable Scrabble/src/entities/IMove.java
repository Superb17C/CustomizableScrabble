package entities;

import java.util.HashMap;

public interface IMove {

	public boolean isValid(Match match);
	public int calculateScore(HashMap<Integer, Integer> lengthBonuses);
	
}