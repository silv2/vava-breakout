package sk.fiit.silvia.vava.logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serialization { 
							
	private List<String> score = new ArrayList<String>();
												
	Logger log = Logger.getLogger("Serialization");
	public List<String> getScore() {
		return score;
	}

	public void saveScore(){

		try (FileOutputStream fileOut = new FileOutputStream(
				"C:/Users/Silvia/workspace/breakout/tmp/score.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(score);
			System.out.printf("Serialized data is saved in /tmp/score.ser");
		} catch (IOException i) {
			log.log(Level.INFO, "sdfabjh", i);
			log.info("jskfhijfae"+i.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public void gScore() {

		try (FileInputStream fileIn = new FileInputStream(
				"C:/Users/Silvia/workspace/breakout/tmp/score.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			score = (ArrayList<String>) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.err.println("score class not found");
			c.printStackTrace();
		}
	}
}
