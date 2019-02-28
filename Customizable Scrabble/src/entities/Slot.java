package entities;

public class Slot {

	protected ISlotContents contents;
	
	public Slot(ISlotContents contents) {
		this.contents = contents;
	}
	
	public ISlotContents getContents() {
		return contents;
	}
	
	public void setContents(ISlotContents contents) {
		this.contents = contents;
	}
	
	public void processWilds() {
		contents.revert();
	}
	
}