package boundaries;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import controllers.StartMatchController;
import entities.Bag;
import entities.MatchSettings;

@SuppressWarnings("serial")
public class HomeGui extends AbsGui {
	
	private MatchSettings settings;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeGui frame = new HomeGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public HomeGui() {
		super(640, 480, Color.DARK_GRAY, new EmptyBorder(5, 5, 5, 5));
		LinkedList<String> sampleList = new LinkedList<String>();
		sampleList.add("Alice");
		sampleList.add("Bob");
		HashMap<Integer, Integer> sampleTable = new HashMap<Integer, Integer>();
		sampleTable.put(9, 50);
		sampleTable.put(7, 50);
		sampleTable.put(8, 50);
		this.settings = new MatchSettings(sampleList, // player names
				9, // rack size
				"english-scrabble-super", // fdist
				"english-scrabble", // sdist
				Bag.WHOLE_BAG, // draw limit
				"scrabble-super", // board
				"english", // dict
				sampleTable, // length bonuses
				true, // show tile scores
				false, // mirror horizontal
				false); // mirror vertical
		showComponents();
	}
	
	@Override
	protected void showComponents() {
		JButton button;
		
		button = new JButton();
		button.setText("Start Game");
		button.addActionListener(new StartMatchController(this));
		button.setFont(new Font("Dialog", Font.BOLD, h * 1/32));
		button.setBounds(w * 19/24, h * 19/24, w * 5/32, h * 1/12);
		pane.add(button);
	}
	
	public MatchSettings getSettings() {
		return settings;
	}
	
}