package Game;

public class Collision {

	float t; // time of the collision
	boolean xcollide; // new speed in x direction
	boolean ycollide; // new speed in y direction
	
	float T_EPSILON = 0.005f; // small factor to ensure boundaries are not crossed

	public Collision(){
		reset();
	}

	// start with largest value for t so any collision is earlier
	public void reset(){
		t = Float.MAX_VALUE;
		xcollide = false;
		ycollide = false;
	}

	// copy over another collision to this collision
	public void copy(Collision c2){
		this.t = c2.t;
		this.xcollide = c2.xcollide;
		this.ycollide = c2.ycollide;
	}
	
	// get the new x position 
	public float getNewX(float x, float xvol){
		if(t > T_EPSILON){
			return (float) x + xvol*(t - T_EPSILON);
		}
		else{
			return x;
		}
	}
	
	// get the new y position
	public float getNewY(float y, float yvol){
		if(t > T_EPSILON){
			return (float) y + yvol* (t - T_EPSILON);
		}
		else{
			return y;
		}
	}

}
