package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Match {

	private MatchSettings settings;
	private Bag bag;
	private Board board;
	private TileHolder exchanger;
	private IDictionary dict;
	private HashMap<Integer, Integer> lengthBonuses;
	private LinkedList<Player> players;
	private int turn;
	private Slot activeSlot;
	
	public Match(MatchSettings settings) {
		this.settings = settings;
		this.bag = new Bag(settings.getBagFrequencyDistName(),
				settings.getBagScoreDistName(),
				settings.getBagDrawLimit());
		this.board = new Board(settings.getBoardLayoutName());
		this.exchanger = new TileHolder(settings.getPlayerRackCapacity());
		this.dict = DictionaryMaker.makeDict(settings.getDictName());
		this.lengthBonuses = settings.getLengthBonuses();
		this.players = new LinkedList<Player>();
		for (String name: settings.getPlayerNames()) {
			players.add(new Player(name, settings.getPlayerRackCapacity(), bag));
		}
		this.turn = ThreadLocalRandom.current().nextInt(0, players.size());
		this.activeSlot = new Slot(new LackOfTile());
	}
	
	public int totalMoves() {
		int totalMoveCount = 0;
		for (Player player: players) {
			totalMoveCount += player.moveCount();
		}
		return totalMoveCount;
	}
	
	public void attemptPlayerPlay() {
		PlayMove move = new PlayMove();
		for (int rowIndex = 0; rowIndex < board.getRowCount(); rowIndex++) {
			for (int colIndex = 0; colIndex < board.getColCount(); colIndex++) {
				Space space = board.getSpace(rowIndex, colIndex);
				if ((space.getContents().fillsSlot()) &&
						(!space.getIsLocked())) {
					move.addSpace(space);
				}
			}
		}
		if (move.isValid(this)) {
			activeSlot = new Slot(new LackOfTile());
			board.lockAll(move.getNewSpaces());
			activePlayer().play(move, bag, lengthBonuses);
			turn++;
			turn = turn % players.size();
		}
	}
	
	private Player activePlayer() {
		return players.get(turn);
	}
	
	public MatchSettings getSettings() {
		return settings;
	}
	
	public Bag getBag() {
		return bag;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public TileHolder getExchanger() {
		return exchanger;
	}
	
	public IDictionary getDict() {
		return dict;
	}
	
	public LinkedList<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(int playerIndex) {
		return players.get(playerIndex);
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Player getActivePlayer() {
		return activePlayer();
	}
	
	public Slot getActiveSlot() {
		return activeSlot;
	}
	
	public void setActiveSlot(Slot slot) {
		activeSlot = slot;
	}
	
}