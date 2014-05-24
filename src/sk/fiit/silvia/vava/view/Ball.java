package sk.fiit.silvia.vava.view;

import javax.swing.ImageIcon;

/**
 * trieda pre lopticku
 * @author Silvia
 *
 */
public class Ball extends Sprite implements Commons {

   private int xdir;
   private int ydir;

   protected String ball = "./images/ball_white_small.png";

   public Ball() {		//nastavim obrazok lopty a jej povodnu polohu plus smer, ktorym prv pojde

     xdir = 1;
     ydir = -1;

     ImageIcon ii = new ImageIcon(ball);
     image = ii.getImage();

     width = image.getWidth(null);
     heigth = image.getHeight(null);

     resetState();
    }


   /**
    * metota na pohyb lotpicky-zmena suradnic
    */
    public void move()		//tato funkcia sa vola v triede Board, zmeni suradnice lopty-pohyb
    {
      x += xdir;
      y += ydir;

      if (x == 0) {
        setXDir(1);
      }

      if (x == BALL_RIGHT) {	//osetrenie ked narazi na okraj
        setXDir(-1);
      }
         
      if (y == 20) {
        setYDir(1);
      }
    }

    public void resetState() 	//nastavenie defaultnej pozicie
    {
      x = 230;
      y = 355;
    }

    public void setXDir(int x)
    {
      xdir = x;
    }

    public void setYDir(int y)
    {
      ydir = y;
    }

    public int getYDir()
    {
      return ydir;
    }
}