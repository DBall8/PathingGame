package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class GamePanel extends JPanel {
	
	public final static int UPDATE_RATE = 30;
	private static final float EPSILON_TIME = 1e-3f; // 0.01

	private static final long serialVersionUID = 1L;
	private MainWindow parent;
	
	private BufferedImage bg;
	
	private Player p1;
	
	public static final Map map = new Map();
	
	private boolean paused = false;
	
	private InputMap inputMap;
	private ActionMap actionMap;
	
	private Character[] characters;

	public GamePanel(MainWindow parent) {
		this.parent = parent;
		
		setPreferredSize(MainWindow.DIMS);
		
		// listener for clicks
		addMouseListener(new ClickListener());
		
		characters = new Character[2];
		
		p1 = new Player(100, 100);
		characters[0] = p1;
		
		KeyPressHandler p1keys = new KeyPressHandler();
		p1.setKeys(p1keys);
		this.addKeyListener(p1keys);
		
		characters[1] = new Enemy(p1, 100, 100);
		
		inputMap = getInputMap(JPanel.WHEN_FOCUSED);
		actionMap = getActionMap();
		setKeyBindings();
		
		// create a black image the size of the game area
		bg = new BufferedImage(MainWindow.DIMS.width, MainWindow.DIMS.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bg.createGraphics();
		graphics.setPaint(Color.BLACK);
		graphics.fillRect(0, 0, MainWindow.DIMS.width, MainWindow.DIMS.height);
		
		repaint();
		requestFocusInWindow();
		play();
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, MainWindow.DIMS.width, MainWindow.DIMS.height, parent);
		
		for(Character c: characters){
			c.draw(g2);
		}

		for(Obstacle o: map.getObs()){
			if(o != null){
				o.draw(g2);	
			}
			else{
				break;
			}
			
		}
	}
	
	private void frameTick(){
		float timeleft = 1.00f; // 100%
		float earliestTime, tempTime;
		
		for(Character c: characters){
			c.update();
		}
		
		do{
			for(Character c: characters){
				c.reset();
			}
			
			earliestTime = timeleft;
			
			for(Character c: characters){
				tempTime = c.CheckBoundaryCollision(timeleft);
	            if(tempTime < earliestTime)
	            {
	                earliestTime = tempTime;
	            }
	            for(Obstacle o: map.getObs()){
	            	if(o == null){
	            		break;
	            	}
	            	tempTime = c.checkObstacleCollision(timeleft, o);
	            	if(tempTime < earliestTime)
		            {
		                earliestTime = tempTime;
		            }
	            }
			}
			
			for(Character c: characters){
				c.move(earliestTime);
			}
			

            // finds the remaining time
            timeleft -= earliestTime;
		}while(timeleft > EPSILON_TIME);
		
		repaint();
	}
	
	private void play(){
		System.out.println("Playing");
		
		Thread time = new Thread(){
			synchronized public void run(){
				while(!paused){
					long startTime, timeTaken, timeLeft;
					startTime = System.currentTimeMillis(); // get start time of tick
					
					// update positions
					frameTick(); 
					
					timeTaken = System.currentTimeMillis() - startTime; // get time taken to update
					// time left after updating

					timeLeft = 1000L / UPDATE_RATE - timeTaken;
					
					try{ // wait for amount of time left in the tick
						if(timeLeft > 0){
							sleep(timeLeft);
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		
		time.start();
		paused = false;
	}
	
	private void pause(){
		System.out.println("Paused");
		paused = true;
	}
	
	
	// makes mouse clicks pause/play game
	private class ClickListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e){
			if(paused){
				// reset game on click if game has ended
				play();
			}
			else{
				pause();
			}
		}
	}
	
	// key bindings to make keypresshandler work, binds esc to pause/play
			private void setKeyBindings(){
				Action escAction = new AbstractAction(){
					public void actionPerformed(ActionEvent e){

					}
				};

				inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "escAction");
				
				actionMap.put("escAction", escAction);

			}

}
