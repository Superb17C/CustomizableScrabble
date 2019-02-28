package entities;

public class LackOfTile implements ISlotContents {
	
	public LackOfTile() {}

	@Override
	public boolean fillsSlot() {
		return false;
	}
	
	@Override
	public String getText() {
		return "";
	}
	
	@Override
	public int getScore() {
		return 0;
	}
	
	@Override
	public void checkForWilds() {}
	
	@Override
	public void revert() {}
	
}