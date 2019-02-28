package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import boundaries.HomeGui;
import boundaries.MatchGui;
import entities.Match;

public class StartMatchController implements ActionListener {

	private HomeGui homeGui;
	
	public StartMatchController(HomeGui homeGui) {
		this.homeGui = homeGui;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		homeGui.closeWindow();
		MatchGui matchGui = new MatchGui(new Match(homeGui.getSettings()));
		matchGui.openWindow();
	}
	
}