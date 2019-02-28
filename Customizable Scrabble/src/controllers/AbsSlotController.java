package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import boundaries.MatchGui;
import entities.ISlotContents;
import entities.LackOfTile;
import entities.Match;
import entities.Slot;

public abstract class AbsSlotController implements MouseListener {
	
	protected MatchGui matchGui;
	protected Match match;
	protected Slot thisSlot;
	
	public AbsSlotController(MatchGui matchGui) {
		this.matchGui = matchGui;
		this.match = matchGui.getMatch();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (match.getActiveSlot().getContents().fillsSlot()) {
			System.out.println("Target slot set to " + thisSlot.getContents().getText());
			ISlotContents temp = thisSlot.getContents();
			thisSlot.setContents(match.getActiveSlot().getContents());
			match.getActiveSlot().setContents(temp);
			match.setActiveSlot(new Slot(new LackOfTile()));
			thisSlot.processWilds();
			matchGui.refresh(match);
			return;
		} else {
			System.out.println("Source slot set to " + thisSlot.getContents().getText());
			match.setActiveSlot(thisSlot);
		}
		matchGui.refresh(match);
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {}
	
}