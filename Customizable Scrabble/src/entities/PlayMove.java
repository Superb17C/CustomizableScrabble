package entities;

import java.util.HashMap;
import java.util.LinkedList;

public class PlayMove implements IMove {

	private LinkedList<Space> newSpaces;
	private LinkedList<Space> oldSpaces;
	private LinkedList<LinkedList<Space>> entries;
	private Direction direction;
	private Space currentSpaceForEntry;
	
	public PlayMove() {
		this.newSpaces = new LinkedList<Space>();
		this.oldSpaces = new LinkedList<Space>();
		this.entries = new LinkedList<LinkedList<Space>>();
	}
	
	public void addSpace(Space space) {
		newSpaces.add(space);
	}

	@Override
	public boolean isValid(Match match) {
		int totalMoves = match.totalMoves();
		if ((newSpaces.size() < 2) && (totalMoves == 0)) {
			System.out.println("The first move must contain at least two tiles.");
			return false;
		} else {
			return isLinear()
					&& isConsecutive(match.getBoard())
					&& usesOverflowAppropriately()
					&& connects(match.getBoard(), totalMoves)
					&& queriesAreValid(match.getDict());
		}
	}
	
	private boolean hasConstantCoords(int constantCoord) {
		for (Space space: newSpaces) {
			switch (direction) {
			case HORIZONTAL:
				if (space.getRow() != constantCoord) {
					System.out.println("All tiles must be in the same row or column.");
					return false;
				}
				break;
			case VERTICAL:
				if (space.getCol() != constantCoord) {
					System.out.println("All tiles must be in the same row or column.");
					return false;
				}
				break;
			}
		}
		return true;
	}
	
	private boolean isLinear() {
		int constantCoord;
		if (newSpaces.size() < 2) {
			direction = Direction.HORIZONTAL;
			return true;
		} else if (newSpaces.get(0).getRow() == newSpaces.get(1).getRow()) {
			direction = Direction.HORIZONTAL;
			constantCoord = newSpaces.get(0).getRow();
		} else {
			direction = Direction.VERTICAL;
			constantCoord = newSpaces.get(0).getCol();
		}
		return hasConstantCoords(constantCoord);
	}
	
	private boolean tileAlreadyOnSpace(int row, int col, Board board) {
		if (!board.coordsWithinBounds(row, col)) {
			return false;
		}
		Space space = board.getSpace(row, col);
		ISlotContents contents = space.getContents();
		if (contents.fillsSlot() && space.getIsLocked()) {
			oldSpaces.add(space);
			currentSpaceForEntry = space;
			return true;
		} else {
			return false;
		}
	}
	
	private boolean tileProposedOnSpace(int row, int col, LinkedList<Space> spaces) {
		for (int spaceIndex = 0; spaceIndex < spaces.size(); spaceIndex++) {
			Space space = spaces.get(spaceIndex);
			if ((space.getRow() == row) && (space.getCol() == col)) {
				currentSpaceForEntry = space;
				spaces.remove(spaceIndex);
				return true;
			}
		}
		return false;
	}
	
	private boolean isConsecutive(Board board) {
		@SuppressWarnings("unchecked")
		LinkedList<Space> newSpacesClone = (LinkedList<Space>) newSpaces.clone();
		int pivotRow = newSpacesClone.get(0).getRow();
		int pivotCol = newSpacesClone.get(0).getCol();
		LinkedList<Space> entry = new LinkedList<Space>();
		entry.add(newSpacesClone.get(0));
		newSpacesClone.remove();
		int targetRow = pivotRow;
		int targetCol = pivotCol;
		boolean targetFound = true;
		currentSpaceForEntry = null;
		while (targetFound) {
			if (currentSpaceForEntry != null) {
				entry.addLast(currentSpaceForEntry);
			}
			switch (direction) {
			case HORIZONTAL:
				targetCol++;
				break;
			case VERTICAL:
				targetRow++;
				break;
			}
			targetFound = tileAlreadyOnSpace(targetRow, targetCol, board)
					|| tileProposedOnSpace(targetRow, targetCol, newSpacesClone);
		}
		targetRow = pivotRow;
		targetCol = pivotCol;
		targetFound = true;
		currentSpaceForEntry = null;
		while (targetFound) {
			if (currentSpaceForEntry != null) {
				entry.addFirst(currentSpaceForEntry);
			}
			switch (direction) {
			case HORIZONTAL:
				targetCol--;
				break;
			case VERTICAL:
				targetRow--;
				break;
			}
			targetFound = tileAlreadyOnSpace(targetRow, targetCol, board)
					|| tileProposedOnSpace(targetRow, targetCol, newSpacesClone);
		}
		if (entry.size() > 1) {
			entries.add(entry);
		}
		if (newSpacesClone.isEmpty()) {
			return true;
		} else {
			System.out.println("No gaps are allowed between tiles.");
			return false;
		}
	}
	
	private boolean usesOverflowAppropriately() {
		for (Space space: newSpaces) {
			if (!space.getIsOverflow()) {
				return true;
			}
		}
		for (Space space: oldSpaces) {
			if (!space.getIsOverflow()) {
				return true;
			}
		}
		System.out.println("No entry can be entirely in the overflow-only zone.");
		return false;
	}
	
	private boolean connects(Board board, int totalMoves) {
		if (totalMoves == 0) {
			for (Space space: newSpaces) {
				if (space.getIsStart()) {
					return true;
				}
			}
			System.out.println("The first move must include the start space.");
			return false;
		} else {
			int pivotRow;
			int pivotCol;
			LinkedList<Space> entry;
			int targetRow;
			int targetCol;
			boolean targetFound;
			for (Space space: newSpaces) {
				pivotRow = space.getRow();
				pivotCol = space.getCol();
				entry = new LinkedList<Space>();
				entry.add(space);
				targetRow = pivotRow;
				targetCol = pivotCol;
				targetFound = true;
				currentSpaceForEntry = null;
				while (targetFound) {
					if (currentSpaceForEntry != null) {
						entry.addLast(currentSpaceForEntry);
					}
					switch(direction) {
					case HORIZONTAL:
						targetRow++;
						break;
					case VERTICAL:
						targetCol++;
						break;
					}
					targetFound = tileAlreadyOnSpace(targetRow, targetCol, board);
				}
				targetRow = pivotRow;
				targetCol = pivotCol;
				targetFound = true;
				currentSpaceForEntry = null;
				while (targetFound) {
					if (currentSpaceForEntry != null) {
						entry.addFirst(currentSpaceForEntry);
					}
					switch(direction) {
					case HORIZONTAL:
						targetRow--;
						break;
					case VERTICAL:
						targetCol--;
						break;
					}
					targetFound = tileAlreadyOnSpace(targetRow, targetCol, board);
				}
				if (entry.size() > 1) {
					entries.add(entry);
				}
			}
			return !oldSpaces.isEmpty();
		}
	}
	
	private boolean queriesAreValid(IDictionary dict) {
		for (LinkedList<Space> entry: entries) {
			String query = "";
			for (Space space: entry) {
				query += space.getContents().getText();
			}
			if (!dict.isValid(query)) {
				System.out.println("Invalid word: " + query);
				return false;
			}
			System.out.println("Word Made: " + query);
		}
		return true;
	}
	
	@Override
	public int calculateScore(HashMap<Integer, Integer> lengthBonuses) {
		int score = 0;
		for (LinkedList<Space> entry: entries) {
			int totalTextScore = 0;
			int totalWordMultiplier = 1;
			for (Space space: entry) {
				if (newSpaces.contains(space)) {
					totalTextScore += space.getContents().getScore() * space.getLetterMultiplier();
					totalWordMultiplier *= space.getWordMultiplier();
				} else {
					totalTextScore += space.getContents().getScore();
				}
			}
			System.out.println("Word Score: " + totalTextScore * totalWordMultiplier);
			score += totalTextScore * totalWordMultiplier;
		}
		if (lengthBonuses.containsKey(newSpaces.size())) {
			System.out.println("Length Bonus: " + lengthBonuses.get(newSpaces.size()));
			score += lengthBonuses.get(newSpaces.size());
		}
		System.out.println("Total Score: " + score);
		return score;
	}
	
	public LinkedList<Space> getNewSpaces() {
		return newSpaces;
	}
	
}