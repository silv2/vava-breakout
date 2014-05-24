package sk.fiit.silvia.vava.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import sk.fiit.silvia.vava.logic.Serialization;


/**
 * trieda pre Jpanel zaverecnej obrazovky
 * @author Silvia
 *
 */
@SuppressWarnings("serial")
public class BoardScore extends JPanel implements Commons {	//v tejto riede vztvaram JPanel pre vyslednu obrazovku vypisania skore
	
	private Serialization serial = new Serialization();
	private List<String> score = new ArrayList<String>();
	private List<String> scorelog = new ArrayList<String>();
	private List<String> scorespeed = new ArrayList<String>();
	private List<Integer> scoreint = new ArrayList<Integer>();
	private Locale aLocale;

	
	/**
	 * kontruktor triedy
	 * @param serial
	 * @param aLocale
	 */
	public BoardScore(Serialization serial,Locale aLocale) {	//v konstruktore si nasvaim objekt triedy Serialization na ten isty ako v triede Board
		this.serial = serial;
		this.aLocale=aLocale;
	}

	
	/**
	 * prepisana metoda paint Jpanelu, kde sa vykresli cela obrazovka
	 */
	public void paint(Graphics g) {	//v tejto funkcie nastavujem cele vypisovanie skore
		super.paint(g);
		Font font = new Font("Verdana", Font.BOLD, 30);
		
		ResourceBundle messages = ResourceBundle.getBundle("sk.fiit.silvia.vava.view.MessagesBundle", aLocale);
		String message = messages.getString("score");
		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString(message, 130, 35);
		font = new Font("Verdana", Font.BOLD, 15);
		
		score = serial.getScore();
		// System.out.println(score);
		g.setColor(Color.black);
		g.setFont(font);

		for (int i = 0; i < score.size(); i++) { //pre kazdy prvok suboru si rozdelim co je login, obtiaznost a skore
			String login = new String();
			String scor = new String();
			String speed = new String();
			char[] charscore;
			charscore = score.get(i).toCharArray();
			int j = 0;

			while (charscore[j] != 45) {
				login += charscore[j];
				j++;
			}

			j += 1;

			while (charscore[j] != 32) {
				speed += charscore[j];
				j++;
			}

			scorelog.add(login);
			scorespeed.add(speed);
			j++;
			for (int m = j; m < charscore.length; m++) {
				scor += charscore[m];
			}
			scoreint.add(Integer.parseInt(scor));

		}
		List<Integer> scoreintpom = new ArrayList<Integer>();
		for (int i = 0; i < score.size(); i++) {		//vytvorim si pomocne pole pre skore
			scoreintpom.add(scoreint.get(i));
		}

		int place = 80;

		Collections.sort(scoreintpom);	//pousporiadam pomocne pole pre skore
		int pom = score.size() - 1;

		int size = 0;
		if (score.size() < 10)		//osetrujem, ak je velkost vsetkych skore mensia ako 10
			size = score.size();
		if (score.size() > 9)
			size = 10;
		for (int i = 0; i < size; i++) {	//vypis skore 
			int cislo = scoreintpom.get(pom);	//najde cislo skora
			int index = scoreint.indexOf(cislo);	//najde jeho index

			pom--;
			g.drawString(i + 1 + ". " + scorelog.get(index), 20, place);	//vypise login podla indexu co som nasla
			g.drawString(scorespeed.get(index), 220, place);	//napise jeho obtiaznost
			g.drawString(" " + cislo, 380, place);		// nakoniec skore
			scorelog.remove(index);		//vymaze ich z pola, aby sa uz neopakovali
			scoreint.remove(index);
			scorespeed.remove(index);
			place += 35;

		}
		/*
		 * g.drawString(message, (Commons.WIDTH - metr.stringWidth(message)) /
		 * 2, (Commons.HEIGTH / 2)+30);
		 */
	}
}
