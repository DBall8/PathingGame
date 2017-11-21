package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class Path {
	
	private int slice = 20;
	
	private int startx;
	private int starty;
	public List<Point> points = new ArrayList<Point>();
	
	private List<Point> OPEN = new ArrayList<Point>();
	private List<Point> CLOSED = new ArrayList<Point>();

	public Path(float startx, float starty, float endx, float endy) {
		this.startx = (int)startx;
		this.starty = (int)starty;
		
		
		astar((int)startx, (int)starty, (int)endx, (int)endy);

		
	}
	
	public void draw(Graphics2D g2){
		for(Point p: points){
			g2.setColor(Color.GREEN);
			g2.drawRect((int)p.x, (int)p.y, 1, 1);

		}
		
	}
	
	public void newEnd(float endx, float endy){
		astar(startx, starty, (int)endx, (int)endy);
		
	}
	
	
	public void astar(int startx, int starty, int endx, int endy){

		List<Point> neighbors;
		
		OPEN = new ArrayList<Point>();
		CLOSED = new ArrayList<Point>();
		Point end = new Point(endx, endy);
		float h = computeH(startx, starty, endx, endy);
		OPEN.add(new Point(startx, starty, 0, h));
		Point current = null;
		Point temp;
		
		while(!OPEN.isEmpty()){
			current = OPEN.get(0);//getNearest();
			//System.out.println(current.x + "," + current.y);
			OPEN.remove(0);
			
			if(current.inside(end, slice)){
				buildPath(current);
				return;
			}
			
			CLOSED.add(current);
			neighbors = current.getNeighbords(slice);
			for(Point n: neighbors){
				n.gscore = current.gscore + Physics.getDistance(current.x, current.y, n.x, n.y);
				n.hscore = computeH(n.x, n.y, end.x, end.y);
				if((temp = listContainsPoint(CLOSED, n)) != null){
					//if(temp.gscore > n.gscore){
					//	CLOSED.remove(temp);
					//	addToOpen(n);
					//	n.prev = current;
						
					//}
					continue;
				}
				if((temp = listContainsPoint(OPEN, n)) != null){
					if(temp.gscore > n.gscore){
						OPEN.remove(temp);
						addToOpen(n);
						n.prev = current;
						
					}
					continue;
				}
				
				addToOpen(n);
				n.prev = current;
			}
			
		}
		
		
		
	}
	
	public float computeH(int x1, int y1, int x2, int y2){
		int distx = Math.abs(x2 - x1);
		int disty = Math.abs(y2 - y1);
		return (float)((distx + disty) + (Math.sqrt(2) - 2) * Physics.min(distx, disty));
	}
	
	public void buildPath(Point p){
		List<Point> newpoints = new ArrayList<Point>();
		while(p.prev != null){
			newpoints.add(p);
			p = p.prev;
		}
		setpath(newpoints);
	}
	
	public synchronized void setpath(List<Point> newlist){
		this.points = newlist;
	}
	
	public Point listContainsPoint(List<Point> list, Point p){
		if(list.size() <= 0){
			return null;
		}
		for(Point i: list){
			if(p.equal(i)){
				return i;
			}
		}
		return null;
	}
	
	/*--------------------- TEMP -------------------*/

	public Point getNearest(){
		if(OPEN.isEmpty()){
			return null;
		}
		Point nearest = OPEN.get(0);
		for(Point p: OPEN){
			if(nearest.gscore + nearest.hscore > p.gscore + p.hscore){
				nearest = p;
			}
		}
		return nearest;
	}
	
	public void addToOpen(Point p){
		for(int i=0; i<OPEN.size(); i++){
			if(p.gscore + p.hscore < OPEN.get(i).gscore + OPEN.get(i).hscore){
				OPEN.add(i, p);
				return;
			}
		}
		OPEN.add(p);
	}

}
