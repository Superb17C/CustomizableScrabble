package entities;

import javax.swing.JOptionPane;

public class Tile implements ISlotContents {

	private String text;
	private String originalText;
	private int score;
	
	public Tile(String text, int score) {
		this.text = text;
		this.originalText = text;
		this.score = score;
	}
	
	@Override
	public boolean fillsSlot() {
		return true;
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public int getScore() {
		return score;
	}
	
	@Override
	public void checkForWilds() {
		if (isWild()) {
			text = JOptionPane.showInputDialog("What would you like this tile to represent?");
		}
	}
	
	@Override
	public void revert() {
		text = originalText;
	}
	
	private boolean isWild() {
		return originalText.contains("?");
	}
	
}