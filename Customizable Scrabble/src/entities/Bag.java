package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Bag {

	public static int WHOLE_BAG = 2147483647;
	private HashMap<String, Integer> frequencyDist;
	private HashMap<String, Integer> scoreDist;
	private LinkedList<Tile> tiles;
	private int drawCount;
	private int drawLimit;
	
	public Bag(String frequencyDistName, String scoreDistName, int drawLimit) {
		String frequencyDistFileName = "fdist_" + frequencyDistName + ".txt";
		File frequencyDistFile = new File("./readables/fdists", frequencyDistFileName);
		this.frequencyDist = parseToHashMap(frequencyDistFile);
		String scoreDistFileName = "sdist_" + scoreDistName + ".txt";
		File scoreDistFile = new File("./readables/sdists", scoreDistFileName);
		this.scoreDist = parseToHashMap(scoreDistFile);
		this.tiles = new LinkedList<Tile>();
		for (String text: frequencyDist.keySet()) {
			for (int rep = 0; rep < frequencyDist.get(text); rep++) {
				Tile tile = new Tile(text, scoreDist.get(text));
				tiles.add(tile);
			}
		}
		this.drawCount = 0;
		this.drawLimit = drawLimit;
		shuffle();
	}
	
	public Bag() {
		this.tiles = new LinkedList<Tile>();
	}
	
	private HashMap<String, Integer> parseToHashMap(File file) {
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lineEntries = line.trim().split("\\s+");
				hashMap.put(lineEntries[0], Integer.parseInt(lineEntries[1]));
			}
			reader.close();
		} catch (IOException ioException) {
			System.err.println("Bag excepted!");
		}
		return hashMap;
	}
	
	public void shuffle() {
		Collections.shuffle(tiles);
	}
	
	public ISlotContents draw() {
		if (tiles.isEmpty()
				|| (drawCount >= drawLimit)) {
			return new LackOfTile();
		} else {
			drawCount++;
			return tiles.remove();
		}
	}
	
	public int tilesLeft() {
		return tiles.size();
	}
	
}