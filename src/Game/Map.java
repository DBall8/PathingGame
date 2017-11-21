package Game;

public class Map {

	private Obstacle[] obstacles;
	public Map() {
		obstacles = new Obstacle[200];
		obstacles[0] = new Obstacle(200, 0, 20, 400, false);
		obstacles[1] = new Obstacle(400, MainWindow.DIMS.height-400, 20, 400, false);
		obstacles[2] = new Obstacle(600, 0, 20, 400, false);
		obstacles[3] = new Obstacle(800, MainWindow.DIMS.height-400, 20, 400, false);
	}
	
	public Obstacle[] getObs(){
		return this.obstacles;
	}
	
	public boolean pointInObstacle(int x, int y){
		for(Obstacle o: obstacles){
			if(o == null){
				return false;
			}
			if(o.getBiggerRect(30).contains(x, y)){
				return true;
			}
		}
		return false;
	}

}
