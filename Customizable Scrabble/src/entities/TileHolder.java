package entities;

public class TileHolder {

	protected int capacity;
	protected Slot[] slots;
	
	public TileHolder(int capacity) {
		this.capacity = capacity;
		this.slots = new Slot[capacity];
		for (int slotIndex = 0; slotIndex < capacity; slotIndex++) {
			slots[slotIndex] = new Slot(new LackOfTile());
		}
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public Slot getSlot(int slotIndex) {
		return slots[slotIndex];
	}
	
}