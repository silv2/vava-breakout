package sk.fiit.silvia.vava.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JTextField;

import sk.fiit.silvia.vava.logic.Serialization;

/**
 * trieda gui, v ktorej sa nachadza aj main
 * @author Silvia
 *
 */
@SuppressWarnings("serial")
public class Window extends JFrame {			
	private JTextField textField;
	private Locale aLocale;

	private JTextField textField_1;			
	private JTextField textField_2;			
	private JCheckBoxMenuItem chckbxmntmHfh, chckbxmntmMedium, chckbxmntmHard,		
	chckbxmntmTwoballs, chckbxmntmBall,checkBoxSr,checkBoxEng;

	public JCheckBoxMenuItem getChckbxmntmHfh() {
		return chckbxmntmHfh;
	}

	public JCheckBoxMenuItem getChckbxmntmMedium() {
		return chckbxmntmMedium;
	}

	public JCheckBoxMenuItem getChckbxmntmHard() {
		return chckbxmntmHard;
	}

	public JTextField getTextField_1() {
		return textField_1;
	}

	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	

	/**
	 * konstruktor triedy
	 */
	public Window() {		
		checkBoxSr = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/sr.png"));
	
		checkBoxSr.setBackground(Color.LIGHT_GRAY);
		checkBoxSr.setBounds(361, 384, 37, 43);
		
		
		checkBoxEng = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/eng.png"));
		checkBoxEng.setBackground(Color.LIGHT_GRAY);
		checkBoxEng.setBounds(399, 384, 71, 43);
		
		aLocale = new Locale("en","US");
		
		
		
		BoardStart board = new BoardStart(aLocale);
		
		
		/*
		checkBoxSr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				board.kresli(aLocale);
			}
		});*/
		board.add(checkBoxSr);
		board.add(checkBoxEng);
		board.requestFocusInWindow();
		board.setBounds(0, 0, 454, 427);
		getContentPane().add(board);
		board.setLayout(null);
		board.setBackground(Color.lightGray);
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(368, 0, 86, 20);
		textField.setBackground(Color.black);
		// board.add(textField);
		textField.setColumns(10);
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setBounds(0, 0, 86, 20);
		// board.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setBackground(Color.black);

		JButton btnButton = new JButton("play");

		board.add(btnButton);
		btnButton.setBounds(185, 299, 89, 23);

		textField_1 = new JTextField();
		textField_1.setBounds(185, 227, 86, 20);
		board.add(textField_1);
		textField_1.setColumns(10);
		
		chckbxmntmHfh = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/easy2.png"));
		chckbxmntmHfh.setBackground(Color.LIGHT_GRAY);
		chckbxmntmHfh.setBounds(58, 50, 71, 29);
		board.add(chckbxmntmHfh);

		chckbxmntmMedium = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/medium.png"));
		chckbxmntmMedium.setBackground(Color.LIGHT_GRAY);
		chckbxmntmMedium.setBounds(171, 50, 117, 29);
		board.add(chckbxmntmMedium);

		chckbxmntmHard = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/hard.png"));
		chckbxmntmHard.setBackground(Color.LIGHT_GRAY);
		chckbxmntmHard.setBounds(317, 50, 71, 29);
		board.add(chckbxmntmHard);

		chckbxmntmTwoballs = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/twoball.png"));
		chckbxmntmTwoballs.setBackground(Color.LIGHT_GRAY);
		chckbxmntmTwoballs.setBounds(118, 109, 89, 22);
		board.add(chckbxmntmTwoballs);

		chckbxmntmBall = new JCheckBoxMenuItem("", new ImageIcon(
				"./images/ball.png"));
		chckbxmntmBall.setBackground(Color.LIGHT_GRAY);
		chckbxmntmBall.setBounds(254, 109, 71, 22);
		board.add(chckbxmntmBall);
		
		

		setTitle("Breakout");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(Commons.WIDTH, Commons.HEIGTH);
		setLocationRelativeTo(null);
		setIgnoreRepaint(true);
		setResizable(false);
		setVisible(true);
		setBounds(100, 100, 460, 455);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnButton.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseClicked(MouseEvent e) {
				int speed = 10;
				if(checkBoxSr.isSelected()) { aLocale = new Locale("sk","SK");  }
				if(checkBoxEng.isSelected()) aLocale = new Locale("en","US");
				boolean ball = false;
				if (chckbxmntmHfh.isSelected()) {		
					speed = 7;
				}
				if (chckbxmntmMedium.isSelected()) {
					speed = 5;
				}
				if (chckbxmntmHard.isSelected()) {
					speed = 3;
				}
				if (chckbxmntmHard.isSelected()
						&& chckbxmntmMedium.isSelected()
						&& chckbxmntmHfh.isSelected())
					speed = 10;

				if (chckbxmntmTwoballs.isSelected()) {
					ball = true;
				}
				textField_2.setText(textField_1.getText());		
				play(speed, ball,1,0,0);		
			}
		});

	}

	public void play(int speed, boolean ball,int level,int score, int life) {		

		getContentPane().removeAll();
		Board board2 = new Board(this, speed, ball,level,score,life,aLocale);

		// board2.setBackground(Color.cyan);
		getContentPane().add(board2);
		board2.requestFocusInWindow();
		// repaint();
		board2.setBounds(0, 0, 454, 427);
		// getContentPane().add(board2);
		board2.setLayout(null);
		board2.add(textField);
		board2.add(textField_2);
		board2.setBackground(Color.black);
	}

	public void score(Serialization serial) {		
		getContentPane().removeAll();
		BoardScore boardscore = new BoardScore(serial,aLocale);
		//boardscore.setBackground(Color.black);

		boardscore.setBackground(Color.lightGray);
		getContentPane().add(boardscore);
		boardscore.requestFocusInWindow();
		// repaint();
		boardscore.setBounds(0, 0, 454, 427);
		// getContentPane().add(board2);
		boardscore.setLayout(null);

	}

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {		
		new Window();

	}
}