package entities;

import java.util.Collections;
import java.util.LinkedList;

public class Rack extends TileHolder {
	
	public Rack(int capacity) {
		super(capacity);
	}
	
	public void replenish(Bag bag) {
		for (int slotIndex = 0; slotIndex < capacity; slotIndex++) {
			Slot slot = slots[slotIndex];
			ISlotContents contents = slot.getContents();
			if (!contents.fillsSlot()) {
				slot.setContents(bag.draw());
			}
		}
	}
	
	public void shuffle() {
		LinkedList<Integer> shuffledIndices = new LinkedList<Integer>();
		for (int slotIndex = 0; slotIndex < capacity; slotIndex++) {
			shuffledIndices.add(slotIndex);
		}
		Collections.shuffle(shuffledIndices);
		Slot[] shuffledSlots = new Slot[capacity];
		for (int slotIndex = 0; slotIndex < capacity; slotIndex++) {
			shuffledSlots[slotIndex] = slots[shuffledIndices.get(slotIndex)];
		}
		slots = shuffledSlots;
	}
	
}