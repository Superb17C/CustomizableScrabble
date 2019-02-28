package entities;

import java.util.HashMap;
import java.util.LinkedList;

public class MatchSettings {

	// TODO ideas
	// restrict certain letters from being used with blanks
	// recycling
	// small word point cap
	// blanks have to work
	// support for "??" blanks
	// add exchange button
	// add shuffle button
	// add recall button
	// size gui elements appropriately
	// stretch bonus
	// endgame logic
	// undo
	// tile bag
	private LinkedList<String> playerNames;
	private int playerRackCapacity;
	private String bagFrequencyDistName;
	private String bagScoreDistName;
	private int bagDrawLimit;
	private String boardLayoutName;
	private String dictName;
	private HashMap<Integer, Integer> lengthBonuses;
	private boolean showTileScores;
	private boolean mirrorHorizontal;
	private boolean mirrorVertical;
	
	public MatchSettings(LinkedList<String> playerNames,
			int playerRackSize,
			String bagFrequencyDistName,
			String bagScoreDistName,
			int bagDrawLimit,
			String boardLayoutName,
			String dictName,
			HashMap<Integer, Integer> lengthBonuses,
			boolean showTileScores,
			boolean mirrorHorizontal,
			boolean mirrorVertical) {
		this.playerNames = playerNames;
		this.playerRackCapacity = playerRackSize;
		this.bagFrequencyDistName = bagFrequencyDistName;
		this.bagScoreDistName = bagScoreDistName;
		this.bagDrawLimit = bagDrawLimit;
		this.boardLayoutName = boardLayoutName;
		this.dictName = dictName;
		this.lengthBonuses = lengthBonuses;
		this.showTileScores = showTileScores;
		this.mirrorHorizontal = mirrorHorizontal;
		this.mirrorVertical = mirrorVertical;
	}
	
	public LinkedList<String> getPlayerNames() {
		return playerNames;
	}
	
	public int getPlayerRackCapacity() {
		return playerRackCapacity;
	}
	
	public String getBagFrequencyDistName() {
		return bagFrequencyDistName;
	}
	
	public String getBagScoreDistName() {
		return bagScoreDistName;
	}
	
	public int getBagDrawLimit() {
		return bagDrawLimit;
	}
	
	public String getBoardLayoutName() {
		return boardLayoutName;
	}
	
	public String getDictName() {
		return dictName;
	}
	
	public HashMap<Integer, Integer> getLengthBonuses() {
		return lengthBonuses;
	}
	
	public boolean getShowTileScores() {
		return showTileScores;
	}
	
	public boolean getMirrorHorizontal() {
		return mirrorHorizontal;
	}
	
	public boolean getMirrorVertical() {
		return mirrorVertical;
	}
	
}