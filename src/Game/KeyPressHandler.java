package Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class KeyPressHandler extends KeyAdapter{
	
	public boolean up = false;
	public boolean right = false;
	public boolean down = false;
	public boolean left = false;
	
	private HashMap<String, Integer> binds = new HashMap<String, Integer>();

	public KeyPressHandler() {
		
		binds.put("up", 38);
		binds.put("right", 39);
		binds.put("down", 40);
		binds.put("left", 37);
		
	}
	
	public void keyPressed(KeyEvent e){

		if(!left && e.getKeyCode() == binds.get("left")){
			left = true;
		}
		if(!up && e.getKeyCode() == binds.get("up")){
			up = true;
		}
		if(!right && e.getKeyCode() == binds.get("right")){
			right = true;
		}
		if(!down && e.getKeyCode() == binds.get("down")){
			down = true;
		}
		
	}
	
	public void keyReleased(KeyEvent e){

		if(e.getKeyCode() == binds.get("left")){
			left = false;
		}
		if(e.getKeyCode() == binds.get("up")){
			up = false;
		}
		if(e.getKeyCode() == binds.get("right")){
			right = false;
		}
		if(e.getKeyCode() == binds.get("down")){
			down = false;
		}
	}

}
