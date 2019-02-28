package controllers;

import boundaries.MatchGui;

public class RackSlotController extends AbsSlotController {

	public RackSlotController(MatchGui matchGui, int playerIndex, int j) {
		super(matchGui);
		this.thisSlot = match.getPlayer(playerIndex).getRack().getSlot(j);
	}
	
}