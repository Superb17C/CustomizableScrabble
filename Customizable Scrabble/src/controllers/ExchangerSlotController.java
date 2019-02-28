package controllers;

import boundaries.MatchGui;

public class ExchangerSlotController extends AbsSlotController {

	public ExchangerSlotController(MatchGui matchGui, int j) {
		super(matchGui);
		this.thisSlot = match.getExchanger().getSlot(j);
	}
	
}