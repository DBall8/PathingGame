package Game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Character{
	
	private static final float MAX_SPEED = 10;
	
	private KeyPressHandler keys = null;

	public Player(int startx, int starty) {
		super(startx, starty);
	}
	
	public Player(){
		super();
	}
	
	public void draw(Graphics2D g2){
		g2.setPaint(Color.BLUE);
		g2.fillRoundRect((int)(x-width/2), (int)(y-height/2), (int)width, (int)height, 20, 20);
	}
	
	public void setKeys(KeyPressHandler k){
		keys = k;
	}
	
	@Override
	public void update(){
		if(keys == null){
			System.out.println("NO KEYS");
			return;
		}
		
		if(keys.up && !keys.down){
			yvol = -MAX_SPEED;
		}
		else if(!keys.up && keys.down){
			yvol = MAX_SPEED;
		}
		else{
			yvol = 0;
		}
		
		if(keys.right && !keys.left){
			xvol = MAX_SPEED;
		}
		else if(!keys.right && keys.left){
			xvol = -MAX_SPEED;
		}
		else{
			xvol = 0;
		}
		
	}

}
