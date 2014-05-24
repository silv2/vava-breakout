package sk.fiit.silvia.vava.view;

import javax.swing.ImageIcon;

/**
 * trieda kocky
 * @author Silvia
 *
 */
public class Brick extends Sprite {	

    String brick = "./images/brick_red.png";

    private  boolean destroyed;
    private boolean twoTime;
    


    public Brick(int x, int y, int color,boolean twoTime) { //nastavim obrazok a polohu kocky a premennu, ktora urcuje, ci je znicena na false
      this.x = x;
      this.y = y;
      
      if(color==1) brick= "./images/brick_red.png";
      if(color==2) brick= "./images/brick_blue.png";
      if(color==3) brick= "./images/brick_yellow.png";
      if(color==4) brick= "./images/brick_purple.png";
      if(color==5) brick= "./images/brick_twice.png";
      
      ImageIcon ii = new ImageIcon(brick);
      image = ii.getImage();

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
      this.twoTime=twoTime;
      
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
      this.destroyed = destroyed;
    }

	public boolean isTwoTime() {
		return twoTime;
	}

	public void setTwoTime(boolean twoTime) {
		this.twoTime = twoTime;
	}

	public void crash(){
		ImageIcon ii= new ImageIcon("./images/brick_twice_crash.png");
		image = ii.getImage();
	}
    

}