package Data;

import java.io.*;

@SuppressWarnings("serial")
public final class Scoredata implements Serializable {
	public static transient int score = 0;
	public static String[] names = { "Nummer 1", "Nummer2", "Dritter Platz", "Mr.Keine Medaille mehr", "Letzter Platz" };

	public static int[] scores = { 0, 0, 0, 0, 0 };

	public final static boolean isItBetter() {
		if (score > scores[4])
			return true;
		return false;
	}

	public final static void saveNewScore(String s) {
			int pos = 4;
			for (int i = 3; i >= 0; --i) {
				if (score > scores[i]) {
					pos--;
				}
			}

			for (int n = 4; n >= pos; --n) {
				if (n == pos) {
					scores[n + 1] = scores[n];
					names[n + 1] = names[n];
					scores[n] = score;
					names[n] = s;
				} else {
					if (n != 4) {
						scores[n + 1] = scores[n];
						names[n + 1] = names[n];
					}
				}
			}
		
		score=0;
	}

	public final static void speichern() {
		try {
			FileOutputStream fos = new FileOutputStream("Score.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			// Serialisieren der Objekte
			oos.writeObject(scores);
			oos.writeObject(names);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Highscores gespeichert
	}

	public final static void laden() {
		scores = null;
		names = null;
		try {
			FileInputStream fis = new FileInputStream("Score.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			scores = (int[]) ois.readObject();
			names = (String[]) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			names = new String[] { "Nummer 1", "Nummer2", "Dritter Platz", "Mr.Keine Medaille mehr", "Letzter Platz" };

			scores = new int[] { 0, 0, 0, 0, 0 };

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Highscores wiederhergestellt
	}

}
