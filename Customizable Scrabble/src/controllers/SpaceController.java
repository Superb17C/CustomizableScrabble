package controllers;

import java.awt.event.MouseEvent;

import boundaries.MatchGui;
import entities.Space;

public class SpaceController extends AbsSlotController {
	
	public SpaceController(MatchGui matchGui, int i, int j) {
		super(matchGui);
		this.thisSlot = match.getBoard().getSpace(i, j);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (!((Space) thisSlot).getIsLocked()) {
			super.mouseClicked(event);
		}
	}
	
}