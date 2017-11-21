package Game;

import java.util.LinkedList;


public class Point{
	public int x;
	public int y;
	public float gscore;
	public float hscore;
	
	public Point prev = null;
	
	public Point(int x, int y, float gscore, float hscore){
		this.x = x;
		this.y = y;
		this.gscore = gscore;
		this.hscore = hscore;
	}
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
		this.gscore = -1;
		this.hscore = -1;
	}
	
	public boolean equal(Point p){

		if(this.x == p.x && this.y == p.y){
			return true;
		}
		return false;
	}
	
	public boolean inside(Point p, int slice){
		int dx = Math.abs(this.x - p.x);
		int dy = Math.abs(this.y - p.y);
		if(dx <= slice/2 && dy <= slice/2){
			return true;
		}
		return false;
	}
	
	public LinkedList<Point> getNeighbords(int slice){
		
		int n = slice/2;
		int tempx, tempy;
		LinkedList<Point> result = new LinkedList<Point>();
		
		tempx = this.x - n;
		tempy = this.y - n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x;
		tempy = this.y - n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x + n;
		tempy = this.y - n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x - n;
		tempy = this.y;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x + n;
		tempy = this.y;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x - n;
		tempy = this.y + n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x;
		tempy = this.y + n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}
		
		tempx = this.x + n;
		tempy = this.y + n;
		if(!GamePanel.map.pointInObstacle(tempx, tempy) && inbounds(tempx, tempy)){
			result.add(new Point(tempx, tempy));
		}

		return result;
		
	}
	
	public boolean inbounds(int x, int y){
		if(x > 0 && x < MainWindow.DIMS.width && y > 0 && y < MainWindow.DIMS.height){
			return true;
		}
		return false;
	}
	

}
