package entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class WordDictionary implements IDictionary {
	
	private LinkedList<String> words;
	
	public WordDictionary(String dictName) {
		this.words = new LinkedList<String>();
		String dictFileName = "dict_" + dictName + ".txt";
		File dictFile = new File("./readables/dicts", dictFileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dictFile));
			String word;
			while ((word = reader.readLine()) != null) {
				words.add(word);
			}
			reader.close();
		} catch (IOException ioException) {
			System.err.println("Dictionary excepted!");
		}
	}

	@Override
	public boolean isValid(String query) {
		int validity = Collections.binarySearch(words, query.toLowerCase());
		return validity >= 0;
	}
	
}