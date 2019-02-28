package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boundaries.MatchGui;
import entities.Match;

public class PlayMoveController implements ActionListener {

	private MatchGui matchGui;
	private Match match;
	
	public PlayMoveController(MatchGui matchGui) {
		this.matchGui = matchGui;
		this.match = matchGui.getMatch();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		match.attemptPlayerPlay();
		matchGui.refresh(match);
	}
	
}