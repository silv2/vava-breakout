package sk.fiit.silvia.vava.view;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import sk.fiit.silvia.vava.logic.Serialization;


/**
 * Toto je trieda Jpanela, kde je riesena aj zakladna logika hry
 * @author Silvia
 *
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements Commons {

	private Timer timer, timer2;
	private String message;
	private Ball ball, ball2;
	private Paddle paddle;
	private Brick bricks[];
	private Window gui;
	private int number, pocc = 0, life = 0;
	private Serialization serial = new Serialization();
	private boolean ingame = true, twoballs = false;
	private int countBricks = 0, level, speed, scoreBeg;
	private AudioClip song;
	private URL songPath;
	private Locale aLocale;

	/**
	 * konstruktor triedy Board
	 * @param gui
	 * @param speed
	 * @param twoballs
	 * @param level
	 * @param score
	 * @param life
	 * @param aLocale
	 */
	public Board(Window gui, int speed, boolean twoballs, int level, int score,
			int life,Locale aLocale) {
		this.gui = gui;
		addKeyListener(new TAdapter());
		setFocusable(true);
		this.twoballs = twoballs;
		bricks = null;
		bricks = new Brick[80];
		setDoubleBuffered(true);
		timer = new Timer();
		this.aLocale=aLocale;
		this.level = level;
		this.speed = speed;
		if (speed == 0) {
			this.speed = 1;
			timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 1);
		} else
			timer.scheduleAtFixedRate(new ScheduleTask(), 1000, speed);
		this.scoreBeg = score;
		this.life = life;
		ResourceBundle messages = ResourceBundle.getBundle("sk.fiit.silvia.vava.view.MessagesBundle", aLocale);
		message = messages.getString("lose");
		

	}
	
	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	

	Logger log = Logger.getLogger("Game Board");
	/**
	 * Init hry, kde si nastavujem, o aky level ide a vytvorim vsetky komponenty
	 */
	public void gameInit() {

		log.info("Starting board and initializing game");
		ball = new Ball();
		if (twoballs)
			ball2 = new Ball();

		paddle = new Paddle();
		if (life != 0)
			paddle.crash(life);
		String score = Integer.toString(scoreBeg);
		number = scoreBeg;
		gui.getTextField().setText("Score: " + score);

		if (level == 2)
			classic();

		if (level == 1)
			chess();

	}

	/**
	 * metoda, ktora vytvori dany pattern levelu
	 */
	public void classic() {
		int k = 0;
		countBricks = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 10; j++) {
				if (i % 2 == 0) {
					bricks[k] = new Brick(j * 45 + 1, i * 22 + 40, i / 2 + 1,
							false);
					k++;
					countBricks++;
				}
			}
		}
	}

	
	/**
	 * metoda, ktora vytvori dany pattern levelu
	 */
	public void chess() {
		int k = 0, poc = 0;
		int color = 2;
		countBricks = 0;
		for (int i = 0; i < 8; i++) {
			if (i % 2 == 0)
				poc++;
			for (int j = 0; j < 10; j++) {
				// color=i/2+1;
				if (i % 2 == 1) {
					if (j % 2 == 1) {

						bricks[k] = new Brick(j * 45 + 1, i * 22 + 40, 5, true);
						k++;
						countBricks++;
						// System.out.println("j je "+j+
						// " i je "+i+" a count je "+countBricks );
					}
				}
				if (i % 2 == 0) {
					if (j % 2 == 0) {
						bricks[k] = new Brick(j * 45 + 1, i * 22 + 40, poc,
								false);
						k++;
						countBricks++;
						// System.out.println("j je "+j+
						// " i je "+i+" a count je "+countBricks );
					}
				}

			}

		}
	}

	/**
	 * doplnena metoda paint Jpanelu, kde vykreslujem celu obrazovku
	 */
	public void paint(Graphics g) {
		super.paint(g);

		if (ingame) {
			g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
					ball.getWidth(), ball.getHeight(), this);
			g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
					paddle.getWidth(), paddle.getHeight(), this);
			if (twoballs) {
				g.drawImage(ball2.getImage(), ball2.getX(), ball2.getY(),
						ball2.getWidth(), ball2.getHeight(), this);
			}

			for (int i = 0; i < countBricks; i++) {
				if (!bricks[i].isDestroyed())
					g.drawImage(bricks[i].getImage(), bricks[i].getX(),
							bricks[i].getY(), bricks[i].getWidth(),
							bricks[i].getHeight(), this);
			}
		} else {
			String speed = "easy";
			if (gui.getChckbxmntmHard().isSelected() == true)
				speed = "hard";
			if (gui.getChckbxmntmMedium().isSelected() == true)
				speed = "medium";
			if (gui.getChckbxmntmHfh().isSelected() == true)
				speed = "easy";

			log.info("Selecting game speed: " + speed);

			Font font = new Font("Verdana", Font.BOLD, 30);
			FontMetrics metr = this.getFontMetrics(font);

			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString(message,
					(Commons.WIDTH - metr.stringWidth(message)) / 2,
					Commons.HEIGTH / 2);

			// System.out.println(gui.getTextField_1().getText()+" "+number);
			
			serial.gScore();
			
			serial.getScore()
					.add(gui.getTextField_1().getText() + "-" + speed + " "
							+ number);
			// System.out.println("bu");
			
			serial.saveScore();
			
			timer2 = new Timer();
			timer2.scheduleAtFixedRate(new ScheduleTask2(), 1000, 10);

		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	
	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}
	}

	/**
	 * timer k vypisaniu obrazvky o vyhre alebo skonceni
	 * @author Silvia
	 *
	 */
	class ScheduleTask2 extends TimerTask {

		public void run() {
			// System.out.println("bu");
			pocc++;
			if (pocc == 10) {
				gui.score(serial);
				timer2.cancel();
			}

		}
	}

	/**
	 * timer pre hlavnu hru
	 * @author Silvia
	 *
	 */
	class ScheduleTask extends TimerTask {

		public void run() {
			ball.move();
			if (twoballs)
				ball2.move();
			paddle.move();
			checkCollision();
			repaint();

		}
	}

	/**
	 * metoda, kde sa program dostane ked nastane koniec hry
	 */
	public void stopGame() {

		ingame = false;
		timer.cancel();
		log.info("End of the game");

	}

	/**
	 * metoda, kde sa kontroluju kolizie a taktiez sa aj riesia
	 */
	public void checkCollision() {
		if (twoballs) {
			if (ball.getRect().getMaxY() > Commons.BOTTOM
					&& ball2.getRect().getMaxY() > Commons.BOTTOM) {
				try {
					playSoundEnd();
					log.info("Playing sound for game");
				} catch (LineUnavailableException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				life++;
				if (life == 3)
					stopGame();
				if (life == 1 || life == 2) {
					paddle.crash(life);
					ball = new Ball();
				}
			}
			if (ball2.getRect().getMaxY() > Commons.BOTTOM)
				twoballs = false;

		} else {
			if (ball.getRect().getMaxY() > Commons.BOTTOM) {
				life++;
				try {
					playSoundEnd();
					log.info("Playing sound for game");
				} catch (LineUnavailableException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (life == 3)
					stopGame();
				if (life == 1 || life == 2) {
					paddle.crash(life);
					ball = new Ball();
				}
			}
		}

		for (int i = 0, j = 0; i < countBricks; i++) {
			if (bricks[i].isDestroyed()) {
				j++;
			}
			// System.out.println("count "+countBricks+" rozbite"+j);
			if (j == countBricks) {
				ResourceBundle messages = ResourceBundle.getBundle("sk.fiit.silvia.vava.view.MessagesBundle", aLocale);
				message = messages.getString("win");
				
				
				// stopGame();
				if (level == 1) {
					gui.play(speed - 1, twoballs, 2, number, life);
					timer.cancel();
				} else
					stopGame();
			}
		}

		if ((ball.getRect()).intersects(paddle.getRect())) {
			try {
				playSoundPaddle();
				log.info("Playing sound for game");
			} catch (LineUnavailableException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int paddleLPos = (int) paddle.getRect().getMinX();
			int ballLPos = (int) ball.getRect().getMinX();

			int first = paddleLPos + 8;
			int second = paddleLPos + 16;
			int third = paddleLPos + 24;
			int fourth = paddleLPos + 32;

			if (ballLPos <= first) {
				ball.setXDir(-1);
				ball.setYDir(-1);
			}

			if (ballLPos > first && ballLPos <= second) {
				ball.setXDir(-1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos > second && ballLPos < third) {
				ball.setXDir(0);
				ball.setYDir(-1);
			}

			if (ballLPos >= third && ballLPos < fourth) {
				ball.setXDir(1);
				ball.setYDir(-1 * ball.getYDir());
			}

			if (ballLPos > fourth) {
				ball.setXDir(1);
				ball.setYDir(-1);
			}

		}

		if (twoballs) {
			if ((ball2.getRect()).intersects(paddle.getRect())) {
				try {
					playSoundPaddle();
					log.info("Playing sound for game");
				} catch (LineUnavailableException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int paddleLPos = (int) paddle.getRect().getMinX();
				int ball2LPos = (int) ball2.getRect().getMinX();

				int first = paddleLPos + 8;
				int second = paddleLPos + 16;
				int third = paddleLPos + 24;
				int fourth = paddleLPos + 32;

				if (ball2LPos <= first) {
					ball2.setXDir(-1);
					ball2.setYDir(-1);
				}

				if (ball2LPos > first && ball2LPos <= second) {
					ball2.setXDir(-1);
					ball2.setYDir(-1 * ball2.getYDir());
				}

				if (ball2LPos > second && ball2LPos < third) {
					ball2.setXDir(0);
					ball2.setYDir(-1);
				}

				if (ball2LPos >= third && ball2LPos < fourth) {
					ball2.setXDir(1);
					ball2.setYDir(-1 * ball2.getYDir());
				}

				if (ball2LPos > fourth) {
					ball2.setXDir(1);
					ball2.setYDir(-1);
				}

			}

		}
		for (int i = 0; i < countBricks; i++) {
			if ((ball.getRect()).intersects(bricks[i].getRect())) {

				int count = 0;
				int ballLeft = (int) ball.getRect().getMinX();
				int ballHeight = (int) ball.getRect().getHeight();
				int ballWidth = (int) ball.getRect().getWidth();
				int ballTop = (int) ball.getRect().getMinY();

				Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom = new Point(ballLeft, ballTop + ballHeight
						+ 1);

				if (!bricks[i].isDestroyed()) {
					try {
						playSoundBrick();
						log.info("Playing sound for game");
					} catch (LineUnavailableException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
					if (bricks[i].getRect().contains(pointRight)) {
						ball.setXDir(-1);

					}

					else if (bricks[i].getRect().contains(pointLeft)) {
						ball.setXDir(1);

					}

					if (bricks[i].getRect().contains(pointTop)) {
						ball.setYDir(1);

					}

					else if (bricks[i].getRect().contains(pointBottom)) {
						ball.setYDir(-1);

					}

					if (!bricks[i].isTwoTime())
						bricks[i].setDestroyed(true);
					else {
						bricks[i].setTwoTime(false);
						bricks[i].crash();
					}
					number += count * 10;
					String score = Integer.toString(number);
					gui.getTextField().setText("Score: " + score);
				}

			}

			if (twoballs) {
				if ((ball2.getRect()).intersects(bricks[i].getRect())) {

					int count = 0;
					int ball2Left = (int) ball2.getRect().getMinX();
					int ball2Height = (int) ball2.getRect().getHeight();
					int ball2Width = (int) ball2.getRect().getWidth();
					int ball2Top = (int) ball2.getRect().getMinY();

					Point pointRight = new Point(ball2Left + ball2Width + 1,
							ball2Top);
					Point pointLeft = new Point(ball2Left - 1, ball2Top);
					Point pointTop = new Point(ball2Left, ball2Top - 1);
					Point pointBottom = new Point(ball2Left, ball2Top
							+ ball2Height + 1);

					if (!bricks[i].isDestroyed()) {
						try {
							playSoundBrick();
							log.info("Playing sound for game");
						} catch (LineUnavailableException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						count++;
						if (bricks[i].getRect().contains(pointRight)) {
							ball2.setXDir(-1);

						}

						else if (bricks[i].getRect().contains(pointLeft)) {
							ball2.setXDir(1);

						}

						if (bricks[i].getRect().contains(pointTop)) {
							ball2.setYDir(1);

						}

						else if (bricks[i].getRect().contains(pointBottom)) {
							ball2.setYDir(-1);

						}

						if (!bricks[i].isTwoTime())
							bricks[i].setDestroyed(true);
						else {
							bricks[i].setTwoTime(false);
							bricks[i].crash();
						}
						number += count * 10;
						String score = Integer.toString(number);
						gui.getTextField().setText("Score: " + score);
					}

				}
			}
		}
	}

	/**
	 * metoda na prehravanie zvuku
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	public void playSoundBrick() throws LineUnavailableException, IOException {

		try(AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
				"jump.wav"))) {
			
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * metoda na prehravanie zvuku
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	public void playSoundPaddle() throws LineUnavailableException, IOException {

		try(AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
				"pad.wav"))) {
			
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * metoda na prehravanie zvuku
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	public void playSoundEnd() throws LineUnavailableException, IOException {

		try(AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
				"end.wav"))) {
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
