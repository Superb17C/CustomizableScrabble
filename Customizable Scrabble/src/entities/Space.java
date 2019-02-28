package entities;

public class Space extends Slot {

	private int row;
	private int col;
	private int letterMultiplier;
	private int wordMultiplier;
	private boolean isStart;
	private boolean isOverflow;
	private boolean isLocked;
	
	public Space(ISlotContents contents,
			int row,
			int col,
			int letterMultiplier,
			int wordMultiplier,
			boolean isStart,
			boolean isOverflow,
			boolean isLocked) {
		super(contents);
		this.row = row;
		this.col = col;
		this.letterMultiplier = letterMultiplier;
		this.wordMultiplier = wordMultiplier;
		this.isStart = isStart;
		this.isOverflow = isOverflow;
		this.isLocked = isLocked;
	}
	
	public void lock() {
		isLocked = true;
	}
	
	@Override
	public void processWilds() {
		contents.checkForWilds();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getLetterMultiplier() {
		return letterMultiplier;
	}
	
	public int getWordMultiplier() {
		return wordMultiplier;
	}
	
	public boolean getIsStart() {
		return isStart;
	}
	
	public boolean getIsOverflow() {
		return isOverflow;
	}
	
	public boolean getIsLocked() {
		return isLocked;
	}
	
}