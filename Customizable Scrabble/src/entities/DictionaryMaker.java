package entities;

public class DictionaryMaker {

	public static IDictionary makeDict(String dictName) {
		try {
			String className = "entities." + capitalizeFirst(dictName) + "Dictionary";
			Class<?> dictClass = Class.forName(className);
			return (IDictionary) dictClass.newInstance();
		} catch (Exception exception) {
			return new WordDictionary(dictName);
		}
	}
	
	private static String capitalizeFirst(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}
	
}