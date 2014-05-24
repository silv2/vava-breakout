package sk.fiit.silvia.vava.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JPanel;

/**
 * trieda na Jpanel pre zaciatocnu obrazovku
 * @author Silvia
 *
 */
@SuppressWarnings("serial")
public class BoardStart extends JPanel implements Commons {
	private String messageEng = "Welcome in game Breakout";
	private String messageEng2 = "Write your name";
	private String messageSr = "Vitaj v hre Breakout";
	private String messageSr2 = "Napíš svoje meno";
	private int language=1;
	private Locale aLocale;
	private Graphics g;

	
	/**
	 * konstruktor triedy
	 * @param aLocale
	 */
	public BoardStart(Locale aLocale) {
		this.aLocale=aLocale;
		
	}

	
	/**
	 * prepisana metoda Jpanelu na vykreslenie ceej obrazovky
	 */
	public void paint(Graphics g) {	//funkcia paint, kde nastavim ako ma vyzerat tento JPanel - len vypisy, checkboxi, button a textField je nastaveny v triede Window
		super.paint(g);
		this.g=g;
		// UIManager.put("Panel.background", new Color(0,255,0));
		//System.out.println("jdsbfvh");
		// UIManager.setLookAndFeel(info.getClassName());
		Font font = new Font("Verdana", Font.BOLD, 18);
		FontMetrics metr = this.getFontMetrics(font);

		g.setColor(Color.BLACK);
		g.setFont(font);
		
		
		ResourceBundle messages = ResourceBundle.getBundle("sk.fiit.silvia.vava.view.MessagesBundle", aLocale);
		String message = messages.getString("greetings");
		g.drawString(message, (Commons.WIDTH - metr.stringWidth(message)) / 2,
				Commons.HEIGTH / 2);
		font = new Font("Verdana", Font.ITALIC, 12);
		metr = this.getFontMetrics(font);

		g.setColor(Color.BLACK);
		g.setFont(font);
		message = messages.getString("name");
		g.drawString(message,
				(Commons.WIDTH - metr.stringWidth(message)) / 2,
				(Commons.HEIGTH / 2) + 30);
	}
	
	

	
	

}
