package sk.fiit.silvia.vava.view;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


/**
 * trieda odrazadla
 * @author Silvia
 *
 */
public class Paddle extends Sprite implements Commons {

    String paddle = "./images/paddle2.png";

    int dx;

    public Paddle() {		//v konstruktore nastavim jeho obrazok a defaultne umiestnenie

        ImageIcon ii = new ImageIcon(paddle);
        image = ii.getImage();

        width = image.getWidth(null);
        heigth = image.getHeight(null);

        resetState();

    }

    public void move() {	//funkcia, ktoru vola trieda Board, sluzi na zmenu umiestnenia objektu na panely
        x += dx;
        if (x <= 2) 
          x = 2;
        if (x >= Commons.PADDLE_RIGHT)		//osetrenie, aby nevysiel z obrazovky
          x = Commons.PADDLE_RIGHT;
    }

    public void keyPressed(KeyEvent e) {	//nastavenie zmeny premennej co sa pripocita pri move() pri stlaceni klavesy vlavo a vpravo

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;

        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {	//nastavy premennu na 0 pri pusteni klavesy
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void resetState() {	//funkcia na nastavenie defaultnych suradnic
        x = 200;
        y = 400;
    }
    
    public void crash(int life){
    	ImageIcon ii=null;
    	if(life==1)
    		ii= new ImageIcon("./images/paddleTwo.png");
    	if(life==2)
    		ii= new ImageIcon("./images/paddle3.png");
		image = ii.getImage();
	}
    
}