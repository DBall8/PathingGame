package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character{
	
	private final float MAX_VOL = 5;
	
	private Player tracking;
	
	private float tx, ty;
	private boolean neednewpath = true;
	private Path path;

	public Enemy(Player p, int startx, int starty) {
		super(startx, starty);
		this.tracking = p;
		tx = tracking.x;
		ty = tracking.y;
		path = new Path(this.x, this.y, tracking.x, tracking.y);
		
		startTracking();
	}
	
	public Enemy(Player p){
		super();
		this.tracking = p;
		path = new Path(this.x, this.y, tracking.x, tracking.y);
		
		startTracking();
	}
	
	
	public void draw(Graphics2D g2){
		g2.setPaint(Color.RED);
		g2.fillRoundRect((int)(x-width/2), (int)(y-height/2), (int)width, (int)height, 20, 30);
		//path.draw(g2);
	}
	
	public void startTracking(){
		
		Thread t = new Thread(){
			public void run(){
				while(true){
					if(neednewpath){
						neednewpath = false;
						path.astar((int)x, (int)y, (int)tx, (int)ty);
					}
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		
	}

	public void update(){
		if(tracking.x != tx || tracking.y != ty){
			tx = tracking.x;
			ty = tracking.y;
			neednewpath = true;
			
		}
		
		if(path.points.size() > 1){
			Point p = path.points.get(path.points.size()-1);
			if(Physics.getDistance(this.x, this.y, p.x, p.y) < 40){
				path.points.remove(path.points.size()-1);
				p = path.points.get(path.points.size()-1);
			}
			
			if(p.x > this.x){
				this.xvol = Math.min(MAX_VOL, p.x-this.x);
			}
			else if(p.x < this.x){
				this.xvol = Math.max(-MAX_VOL, p.x-this.x);
			}
			else{
				this.xvol = 0;
			}
			
			if(p.y > this.y){
				this.yvol = Math.min(MAX_VOL, p.y-this.y);
			}
			else if(p.y < this.y){
				this.yvol = Math.max(-MAX_VOL, p.y-this.y);
			}
			else{
				this.yvol = 0;
			}
		}
		else{
			xvol = yvol = 0;
		}
		
		
	}

}
