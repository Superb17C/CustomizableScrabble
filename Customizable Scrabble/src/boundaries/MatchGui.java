package boundaries;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controllers.PlayMoveController;
import controllers.RackSlotController;
import controllers.SpaceController;
import entities.ISlotContents;
import entities.Match;
import entities.Player;
import entities.Rack;
import entities.Space;

@SuppressWarnings("serial")
public class MatchGui extends AbsGui {

	private Match match;
	
	public MatchGui(Match match) {
		super(1024, 768, Color.DARK_GRAY, new EmptyBorder(5, 5, 5, 5));
		this.match = match;
		showComponents();
	}
	
	@Override
	protected void showComponents() {
		int rowCount = match.getBoard().getRowCount();
		int colCount = match.getBoard().getColCount();
		int rackCapacity = match.getSettings().getPlayerRackCapacity();
		int outerGapW = w * 1/100;
		int outerGapH = outerGapW;
		int spaceW = w * 1/30 * 3/4;//modified from ideal
		int spaceH = spaceW * 11/10;
		int spaceGapW = spaceW * 1/10;
		int spaceGapH = spaceH * 1/10;
		int tileW = spaceW * 3/2;
		int tileH = tileW * 11/10;
		int boardW = spaceW * colCount + spaceGapW * (colCount - 1);
		int boardH = spaceH * rowCount + spaceGapH * (rowCount - 1);
		int tileGapW = (boardW - rackCapacity * tileW) / (rackCapacity - 1);
		int rackGapH = h * 1/25;
		int playW = tileW * 2;
		int playH = tileH;
		int meridianW = w * 1/15;
		int scoreW = boardW;
		int scoreH = spaceH;
		int scoreGapH = spaceGapH;
		JPanel panel;
		JLabel label;
		JButton button;
		
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				panel = coloredLabeledPanel(i, j, spaceH);
				panel.setBounds(outerGapW + (spaceW + spaceGapW) * physicalColIndex(j, colCount), outerGapH + (spaceH + spaceGapH) * physicalRowIndex(i, rowCount), spaceW, spaceH);
				panel.addMouseListener(new SpaceController(this, i, j));
				pane.add(panel);
			}
		}
		
		Rack rack = match.getActivePlayer().getRack();
		for (int j = 0; j < rack.getCapacity(); j++) {
			panel = new JPanel();
			panel.setBounds(outerGapW + (tileW + tileGapW) * j, outerGapH + boardH + rackGapH, tileW, tileH);
			label = labelForContents(rack.getSlot(j).getContents(), tileH);
			panel.add(label);
			panel.addMouseListener(new RackSlotController(this, match.getTurn(), j));
			pane.add(panel);
		}
		
		button = new JButton();
		button.setText("Play");
		button.setBounds(outerGapW + boardW + tileGapW, outerGapH + boardH + rackGapH, playW, playH);
		button.addActionListener(new PlayMoveController(this));
		pane.add(button);
		
		for (int i = 0; i < match.getPlayers().size(); i++) {
			Player player = match.getPlayer(i);
			label = new JLabel();
			label.setText(player.getName() + ": " + player.getScore());
			label.setForeground(Color.WHITE);
			label.setBounds(outerGapW + boardW + meridianW, outerGapH + (scoreH + scoreGapH) * i, scoreW, scoreH);
			pane.add(label);
		}
	}
	
	private int physicalRowIndex(int i, int rowCount) {
		if (match.getSettings().getMirrorVertical()) {
			return rowCount - 1 - i;
		} else {
			return i;
		}
	}
	
	private int physicalColIndex(int j, int colCount) {
		if (match.getSettings().getMirrorHorizontal()) {
			return colCount - 1 - j;
		} else {
			return j;
		}
	}
	
	private JPanel coloredLabeledPanel(int i, int j, int spaceH) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		Space space = match.getBoard().getSpace(i, j);
		ISlotContents contents = space.getContents();
		if (contents.fillsSlot()) {
			panel.setBackground(new Color(235, 205, 180));
			label = labelForContents(contents, spaceH);
		} else if (space.getIsLocked()) {
			panel.setBackground(Color.DARK_GRAY);
		} else {
			int letterMultiplier = space.getLetterMultiplier();
			int wordMultiplier = space.getWordMultiplier();
			if ((letterMultiplier == 1) && (wordMultiplier == 1)) {
				if (space.getIsOverflow()) {
					panel.setBackground(new Color(110, 107, 97));
				} else {
					panel.setBackground(new Color(220, 213, 194));
				}
			} else if ((letterMultiplier == 2) && (wordMultiplier == 1)) {
				panel.setBackground(new Color(188, 230, 246));
				label.setText("DL");
			} else if ((letterMultiplier == 3) && (wordMultiplier == 1)) {
				panel.setBackground(new Color(93, 174, 214));
				label.setText("TL");
			} else if ((letterMultiplier == 4) && (wordMultiplier == 1)) {
				panel.setBackground(Color.BLUE);
				label.setText("QL");
			} else if ((letterMultiplier == 1) && (wordMultiplier == 2)) {
				panel.setBackground(new Color(243, 173, 179));
				label.setText("DW");
			} else if ((letterMultiplier == 1) && (wordMultiplier == 3)) {
				panel.setBackground(new Color(232, 81, 87));
				label.setText("TW");
			} else if ((letterMultiplier == 1) && (wordMultiplier == 4)) {
				panel.setBackground(Color.RED);
				label.setText("QW");
			} else {
				panel.setBackground(new Color(255, 255, 255));
			}
			if (space.getIsStart()) {
				label.setText("*");
			}
		}
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label);
		return panel;
	}
	
	private JLabel labelForContents(ISlotContents contents, int imageH) {
		JLabel label = new JLabel();
		if (match.getSettings().getShowTileScores()) {
			label.setText(contents.getText() + subscript(contents.getScore()));
		} else {
			label.setText(contents.getText());
		}
		label.setFont(new Font("Dialog", Font.PLAIN, imageH * 3/4));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
	
	private String subscript(int n) {
		String regulars = "" + n;
		String subscripts = "";
		for (int i = 0; i < regulars.length(); i++) {
			switch (regulars.charAt(i)) {
				case '0': subscripts += '\u2080';
				break;
				case '1': subscripts += '\u2081';
				break;
				case '2': subscripts += '\u2082';
				break;
				case '3': subscripts += '\u2083';
				break;
				case '4': subscripts += '\u2084';
				break;
				case '5': subscripts += '\u2085';
				break;
				case '6': subscripts += '\u2086';
				break;
				case '7': subscripts += '\u2087';
				break;
				case '8': subscripts += '\u2088';
				break;
				case '9': subscripts += '\u2089';
				break;
				default: subscripts += '\u2093';
				break;
			}
		}
		return subscripts;
	}
	
	public Match getMatch() {
		return match;
	}
	
	@Override
	public void refresh(Object object) {
		match = (Match) object;
		pane.removeAll();
		showComponents();
		pane.repaint();
		pane.validate();
	}

}