package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Board {
	
	private Space[][] spaces;
	private int rowCount;
	private int colCount;
	
	public Board(String boardLayoutName) {
		String boardLayoutFileName = "board_" + boardLayoutName + ".txt";
		File boardLayoutFile = new File("./readables/boards", boardLayoutFileName);
		this.spaces = parseToSpaces(boardLayoutFile);
		this.rowCount = spaces.length;
		this.colCount = spaces[0].length;
	}
	
	private Space[][] parseToSpaces(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			String[] lineEntries = line.trim().split("\\s*x\\s*");
			int rowCount = Integer.parseInt(lineEntries[0]);
			int colCount = Integer.parseInt(lineEntries[1]);
			Space[][] spaces = new Space[rowCount][colCount];
			LinkedList<String[]> spaceStrings = new LinkedList<String[]>();
			line = reader.readLine();
			int rowIndex = 0;
			while ((line = reader.readLine()) != null) {
				lineEntries = line.trim().split("\\s+");
				spaceStrings.add(lineEntries);
				rowIndex++;
			}
			int rowBasisIndex;
			String[] rowBasis;
			int colBasisIndex;
			for (rowIndex = 0; rowIndex < rowCount; rowIndex++) {
				if (rowIndex < spaceStrings.size()) {
					rowBasisIndex = rowIndex;
				} else {
					rowBasisIndex = 2 * (spaceStrings.size() - 1) - rowIndex;
				}
				rowBasis = spaceStrings.get(rowBasisIndex);
				for (int colIndex = 0; colIndex < colCount; colIndex++) {
					if (colIndex < rowBasis.length) {
						colBasisIndex = colIndex;
					} else {
						colBasisIndex = 2 * (rowBasis.length - 1) - colIndex;
					}
					spaces[rowIndex][colIndex] = makeSpace(rowBasis[colBasisIndex], rowIndex, colIndex);
				}
			}
			reader.close();
			return spaces;
		} catch (IOException ioException) {
			System.err.println("Board excepted!");
			return new Space[15][15];
		}
	}
	
	private Space makeSpace(String entry, int rowIndex, int colIndex) {
		int letterMultiplier = 1;
		int wordMultiplier = 1;
		boolean isStart = false;
		boolean isOverflow = false;
		boolean isLocked = false;
		if (entry.contains("L")) {
			letterMultiplier = intPreceding("L", entry);
		}
		if (entry.contains("W")) {
			wordMultiplier = intPreceding("W", entry);
		}
		if (entry.contains("*")) {
			isStart = true;
		}
		if (entry.contains("O")) {
			isOverflow = true;
		}
		if (entry.contains("X")) {
			isLocked = true;
		}
		return new Space(new LackOfTile(),
				rowIndex,
				colIndex,
				letterMultiplier,
				wordMultiplier,
				isStart,
				isOverflow,
				isLocked);
	}
	
	private int intPreceding(String substring, String string) {
		int digitIndex = string.indexOf(substring);
		String intString = "";
		char digit;
		boolean digitFound = true;
		while (digitFound && (digitIndex > 0)) {
			digitIndex--;
			digit = string.charAt(digitIndex);
			if ((digit == '0')
					|| (digit == '1')
					|| (digit == '2')
					|| (digit == '3')
					|| (digit == '4')
					|| (digit == '5')
					|| (digit == '6')
					|| (digit == '7')
					|| (digit == '8')
					|| (digit == '9')) {
				intString = digit + intString;
			} else if (digit == '-') {
				intString = digit + intString;
				digitFound = false;
			} else {
				digitFound = false;
			}
		}
		if (intString == "") {
			return 1;
		} else {
			return Integer.parseInt(intString);
		}
	}
	
	public Board(int rowCount, int colCount) {
		this.spaces = new Space[rowCount][colCount];
	}
	
	public boolean coordsWithinBounds(int row, int col) {
		return ((row >= 0)
				&& (col >= 0)
				&& (row < rowCount)
				&& (col < colCount));
	}
	
	public void lockAll(LinkedList<Space> newSpaces) {
		for (Space newSpace: newSpaces) {
			Space space = space(newSpace.getRow(), newSpace.getCol());
			space.lock();
		}
	}
	
	private Space space(int row, int col) {
		return spaces[row][col];
	}
	
	public Space getSpace(int row, int col) {
		return space(row, col);
	}
	
	public int getRowCount() {
		return rowCount;
	}
	
	public int getColCount() {
		return colCount;
	}
	
}