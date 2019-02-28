package entities;

public interface ISlotContents {

	public boolean fillsSlot();
	public String getText();
	public int getScore();
	public void checkForWilds();
	public void revert();
	
}