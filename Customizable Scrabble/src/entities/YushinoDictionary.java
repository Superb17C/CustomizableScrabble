package entities;

import java.util.LinkedList;

public class YushinoDictionary implements IDictionary {

	@Override
	public boolean isValid(String query) {
		LinkedList<Integer> sequence = new LinkedList<Integer>();
		for (char digitChar: query.toCharArray()) {
			sequence.add(Integer.parseInt(Character.toString(digitChar)));
		}
		if (sequence.size() == 2) {
			return Math.abs(sequence.get(0) - sequence.get(1)) == 1;
		} else if (sequence.size() > 2) {
			for (int digitIndex = 2; digitIndex < sequence.size(); digitIndex++) {
				if ((sequence.get(digitIndex - 2)
						+ sequence.get(digitIndex - 1)) % 10
						!= sequence.get(digitIndex)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}